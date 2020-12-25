package vnteleco.com.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import vnteleco.com.api.call.BizCallCommunication;
import vnteleco.com.api.response.BizGetDetailCallResponse;
import vnteleco.com.api.response.dto.ContentCallResponseDTO;
import vnteleco.com.api.response.dto.ConversationResponseDTO;
import vnteleco.com.entity.dto.ContentDetailDto;
import vnteleco.com.entity.dto.ConversationInfoDto;
import vnteleco.com.enumjava.CallStatus;
import vnteleco.com.exeption.BizException;
import vnteleco.com.service.ConversationService;
import vnteleco.com.util.Constant;
import vnteleco.com.util.DateUtil;

@Controller
@Transactional
@EnableWebMvc
@PropertySource("classpath:config.properties")
public class CallDetailController {

	@Value("${service.token}")
    private String token;
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
    BizCallCommunication bizCallCommunication;
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);
	}

	// GET: Show Index Page
	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
	public String getDetailCall(Model model, HttpServletRequest request, @RequestParam("conversation_id") String conversationId) {
		// get array info
		
		model.addAttribute("conversation", null);
		
		try {
			BizGetDetailCallResponse bizGetDetailCallResponseDto = bizCallCommunication.getConversation(token, conversationId);
			if (bizGetDetailCallResponseDto.getStatus() == Constant.API_CODE_SUCCESS) {
				
				//get info
				ConversationResponseDTO conversationResponseDTO = bizGetDetailCallResponseDto.getConversation();
				
				ConversationInfoDto conversationDTO = new ConversationInfoDto();
				conversationDTO.setConversationId(conversationResponseDTO.getConversationId());
				conversationDTO.setCallbotId(conversationResponseDTO.getCallbotId()+"");
				conversationDTO.setCallCenter(conversationResponseDTO.getCallcenterPhone());
				conversationDTO.setCustomer(conversationResponseDTO.getCustomerPhone());
				conversationDTO.setAudioUrl(conversationResponseDTO.getAudioUrl());
				conversationDTO.setCallAt(convertMilisecondToDate(conversationResponseDTO.getCallAt()));
				conversationDTO.setStatus(CallStatus.CALL_STATUS_MAP.get(conversationResponseDTO.getStatus()));
				if (conversationResponseDTO.getPickupAt()!= 0) {
					conversationDTO.setWaitTime(convertMiliSecondsToHMS(conversationResponseDTO.getPickupAt()-conversationResponseDTO.getCallAt()));
					conversationDTO.setCallTime(convertMiliSecondsToHMS(conversationResponseDTO.getHangupAt()-conversationResponseDTO.getPickupAt()));
				} else {
					conversationDTO.setWaitTime(convertMiliSecondsToHMS(conversationResponseDTO.getHangupAt()-conversationResponseDTO.getCallAt()));
				}
												
				//get content
				ContentCallResponseDTO[] listOfContent = conversationResponseDTO.getContent();
				
				List<ContentDetailDto> listOfContentDetail = new ArrayList<>();
				for (ContentCallResponseDTO contentCallResponseDTO : listOfContent) {
					if (contentCallResponseDTO.getTimestamp() != 0) {
						ContentDetailDto contentDetailDto = new ContentDetailDto();
						contentDetailDto.setType(1);
						contentDetailDto.setText(contentCallResponseDTO.getTextBot());
						contentDetailDto.setTime(convertMiliSecondsToHMS(contentCallResponseDTO.getTimestamp()*1000-conversationResponseDTO.getPickupAt()));
						contentDetailDto.setSecondInAudio(((contentCallResponseDTO.getTimestamp()*1000-conversationResponseDTO.getPickupAt())/1000));
						
						listOfContentDetail.add(contentDetailDto);
					}
					
					if (contentCallResponseDTO.getTimestampUser() != 0) {
						ContentDetailDto contentDetailDto = new ContentDetailDto();
						contentDetailDto.setType(2);
						contentDetailDto.setText(contentCallResponseDTO.getTextUser());
						contentDetailDto.setTime(convertMiliSecondsToHMS(contentCallResponseDTO.getTimestampUser()*1000-conversationResponseDTO.getPickupAt()));
						contentDetailDto.setSecondInAudio(((contentCallResponseDTO.getTimestampUser()*1000-conversationResponseDTO.getPickupAt())/1000));
						
						listOfContentDetail.add(contentDetailDto);
					}
				}
				
				Collections.sort(listOfContentDetail, new Comparator<ContentDetailDto>() {
					  @Override
					  public int compare(ContentDetailDto u1, ContentDetailDto u2) {
					    return u1.getSecondInAudio().compareTo(u2.getSecondInAudio());
					  }
				});
				
				model.addAttribute("conversation", conversationDTO);
				model.addAttribute("listOfContentDetail", listOfContentDetail);
			}
		
		} catch (BizException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "call_detail";
	}
	
	private String convertMilisecondToDate(long miliseconds) {
		Date date = new Date(miliseconds);
		return DateUtil.datetoString(date,"dd/MM/yyyy HH:mm:ss");
	}
	
	private String convertMiliSecondsToHMS(long miliseconds) {
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(miliseconds),
	            TimeUnit.MILLISECONDS.toMinutes(miliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(miliseconds)),
	            TimeUnit.MILLISECONDS.toSeconds(miliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(miliseconds)));
		
		return hms;
	}
	

}
