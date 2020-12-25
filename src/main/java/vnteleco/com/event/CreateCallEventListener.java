package vnteleco.com.event;

import java.sql.Timestamp;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import vnteleco.com.api.call.BizCallCommunication;
import vnteleco.com.api.request.BizCalloutRequest;
import vnteleco.com.api.request.BizCreateCallRequest;
import vnteleco.com.api.response.BizCalloutResponse;
import vnteleco.com.api.response.BizCreateCallResponse;
import vnteleco.com.api.response.BizGetInputSlotsResponse;
import vnteleco.com.entity.Conversation;
import vnteleco.com.entity.UserEntity;
import vnteleco.com.entity.dto.ConversationDto;
import vnteleco.com.exeption.BizException;
import vnteleco.com.mapper.ConversationMapper;
import vnteleco.com.service.ConversationService;
import vnteleco.com.service.UserAccountService;
import vnteleco.com.util.Constant;

@Component
@PropertySource("classpath:config.properties")
public class CreateCallEventListener{
	
	@Value("${service.token}")
    private String token;
	
	@Value("${call_center}")
    private String callCenter;
	
	@Autowired
    ConversationMapper conversationMapper;
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
    BizCallCommunication bizCallCommunication;
	
	@Autowired
	private UserAccountService userAccountService;

	@Async("taskExecutor")
    @EventListener
    public void doorBellEventListener(CreateCallEvent createCallEvent) throws InterruptedException {
		
		System.out.println(Thread.currentThread().getName() + " đang xử lý msisdn: " + createCallEvent.getProperties().get("msisdn"));
		
		try {
						
			//bc1: call api get input slot
			BizGetInputSlotsResponse bizGetInputSlotsResponseDto = bizCallCommunication.getInputSlots(createCallEvent.getCallbotId(), token);
			String[] inputSlots = null;
			if (bizGetInputSlotsResponseDto.getStatus() == Constant.API_CODE_SUCCESS) {
				inputSlots = bizGetInputSlotsResponseDto.getInputSlots();
			}
			
			//bc2: Create properties
			String conversationId = null;
			HashMap<String, Object> properties_1 = new HashMap<>();
			if (inputSlots != null && inputSlots.length > 0) {
			    for (String string : inputSlots) {
			    	if (createCallEvent.getProperties().containsKey(string)) {
			    		properties_1.put(string, createCallEvent.getProperties().get(string));
			    	}else {
			    		properties_1.put(string, "");
					}
				}
			}
		
			//bc3: call api create call
		    BizCreateCallRequest bizCreateCallRequest = new BizCreateCallRequest(createCallEvent.getCallbotId(), callCenter, String.valueOf(createCallEvent.getProperties().get("msisdn")),"", properties_1);
		    
		    BizCreateCallResponse bizCreateCallResponseDto = bizCallCommunication.createCall(token, bizCreateCallRequest);
		    if (bizCreateCallResponseDto.getStatus() == Constant.API_CODE_SUCCESS) {
		    	conversationId = bizCreateCallResponseDto.getConversationId();
			}
				    
		    //bc4: call api callout
		    if (conversationId != null) {
		    	BizCalloutRequest bizCalloutRequest = new BizCalloutRequest(conversationId);
			    BizCalloutResponse bizCalloutResponseDto = bizCallCommunication.callout(token, bizCalloutRequest);
	
			    if (bizCalloutResponseDto.getStatus() == Constant.API_CODE_SUCCESS) {
			    				    	
			    	Conversation conversation = new Conversation();
			    	conversation.setMsisdn(String.valueOf(createCallEvent.getProperties().get("msisdn")));
			    	conversation.setConversation(conversationId);
			    	conversation.setCallbotId(createCallEvent.getCallbotId());
			    	conversation.setStatus(Constant.CONVERSATION_STATUS_CREATED);
			    	conversation.setCreateAt(new Timestamp(System.currentTimeMillis()));
			    	conversation.setUpdateAt(new Timestamp(System.currentTimeMillis()));
			    	conversation.setUserId(createCallEvent.getUserEntity().getId());
			    	conversation.setUserEntity(createCallEvent.getUserEntity());
			    	
			    	conversationService.addConversation(conversation);

				}
			}
		    
		    Thread.sleep(50);
		}catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

}
