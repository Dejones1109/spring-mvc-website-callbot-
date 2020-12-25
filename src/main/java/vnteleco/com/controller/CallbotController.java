package vnteleco.com.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import vnteleco.com.api.call.BizCallCommunication;
import vnteleco.com.api.request.BizCalloutRequest;
import vnteleco.com.api.request.BizCreateCallRequest;
import vnteleco.com.api.response.BizCalloutResponse;
import vnteleco.com.api.response.BizCreateCallResponse;
import vnteleco.com.api.response.BizGetInputSlotsResponse;
import vnteleco.com.entity.Callbot;
import vnteleco.com.entity.CallbotFields;
import vnteleco.com.entity.Conversation;
import vnteleco.com.entity.ConversationContent;
import vnteleco.com.entity.UserEntity;
import vnteleco.com.entity.dto.CallbotDto;
import vnteleco.com.entity.dto.ConversationDto;
import vnteleco.com.entity.dto.ExportDataDto;
import vnteleco.com.entity.response.ResponseDto;
import vnteleco.com.enumjava.CallStatus;
import vnteleco.com.event.CreateCallEventPublisher;
import vnteleco.com.exeption.BizException;
import vnteleco.com.export.ExportReportData;
import vnteleco.com.mapper.CallbotMapper;
import vnteleco.com.mapper.ConversationMapper;
import vnteleco.com.service.CallbotFieldsService;
import vnteleco.com.service.CallbotService;
import vnteleco.com.service.ConversationService;
import vnteleco.com.service.UserAccountService;
import vnteleco.com.util.Constant;
import vnteleco.com.util.DateUtil;
import vnteleco.com.util.FileUtil;

@Controller
@Transactional
@EnableWebMvc
public class CallbotController {

	@Autowired
	private CallbotService callbotService;

	@Value("${service.token}")
	private String token;

	@Value("${call_center}")
	private String callCenter;

	@Value("${url_folder_file_template_download}")
	private String urlFolderFileTemplateDownload;

	@Value("${url_folder_file_template_update}")
	private String urlFolderFileTemplateUpdate;

	@Autowired
	private ConversationService conversationService;

	@Autowired
	private CallbotFieldsService callbotFieldsService;

	@Autowired
	BizCallCommunication bizCallCommunication;

	@Autowired
	ConversationMapper conversationMapper;

	@Autowired
	private CallbotMapper callbotMapper;

	@Autowired
	CreateCallEventPublisher createCallEventPublisher;

	@Autowired
	ExportReportData exportReportData;
	
	@Autowired
	private UserAccountService userAccountService;

	ArrayList<String> extAllow = new ArrayList(Arrays.asList(new String[] { "xlsx" }));

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);
	}

	// GET: Show Index Page
	@RequestMapping(value = { "/callbot" }, method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request, @RequestParam("category_id") int categoryId) {

		List<CallbotDto> listOfCallBot = (List<CallbotDto>) callbotMapper.transform(callbotService.findCallbotByCategoryId(categoryId));

		// get callbot
		CallbotDto firstCallbot = listOfCallBot.get(0);

		// get conversation
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		UserEntity userAccount = userAccountService.findAccountByUserName(username);
		
		List<ConversationDto> listOfConversationDto = new ArrayList<>();
		int userId;
		if (hasRole("ROLE_ADMIN")) {
			userId = 0;
		} else {
			userId =  userAccount.getId();
		}
		
		listOfConversationDto = (List<ConversationDto>) conversationMapper.transform(conversationService.findConversationByCallbotId(firstCallbot.getId(), userId));
		
		// call api get input slot
		List<CallbotFields> listOfField = callbotFieldsService.findByCallbotId(firstCallbot.getId());

		model.addAttribute("callbot", firstCallbot);
		model.addAttribute("listOfField", listOfField);
		model.addAttribute("listOfConversationDto", listOfConversationDto);
		model.addAttribute("listOfCallBot", listOfCallBot);
		return "callbot";
	}

	@RequestMapping(value = { "/conversation" }, method = RequestMethod.GET)
	public String conversation(Model model, HttpServletRequest request, @RequestParam("callbot_id") int callbotId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		UserEntity userAccount = userAccountService.findAccountByUserName(username);

		// get list conversation
		List<ConversationDto> listOfConversationDto = new ArrayList<>();
		int userId;
		if (hasRole("ROLE_ADMIN")) {
			userId = 0;
		} else {
			userId =  userAccount.getId();
		}
		
		listOfConversationDto = (List<ConversationDto>) conversationMapper.transform(conversationService.findConversationByCallbotId(callbotId, userId));
		
		// get callbot info
		Callbot callbot = callbotService.findCallbotById(callbotId);

		// get list callbot
		List<CallbotDto> listOfCallBot = (List<CallbotDto>) callbotMapper.transform(callbotService.findCallbotByCategoryId(callbot.getCategoryId()));

		// call api get input slot
		List<CallbotFields> listOfField = callbotFieldsService.findByCallbotId(callbotId);

		model.addAttribute("callbot", callbot);
		model.addAttribute("listOfField", listOfField);
		model.addAttribute("listOfConversationDto", listOfConversationDto);
		model.addAttribute("listOfCallBot", listOfCallBot);
		return "callbot";
	}
	
	@RequestMapping(value = { "/search_conversation" }, method = RequestMethod.GET)
	public @ResponseBody String searchConversation(HttpServletRequest request) {
		
		ResponseDto<List<ConversationDto>> responseDto = new ResponseDto<List<ConversationDto>>();
		
		int calbotId = Integer.valueOf(request.getParameter("searchCallbotID"));
		String msisdn = request.getParameter("msisdn");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		// set default
		Gson gson = new Gson();
		responseDto.setData(null);
		responseDto.setMessage("Hệ thống có lỗi!");
		responseDto.setStatus(Constant.FAIL);
		
		// check validate
		if ( StringUtils.isBlank(msisdn) &&  StringUtils.isBlank(startDate) &&  StringUtils.isBlank(endDate)) {
			responseDto.setMessage("Vui lòng nhập điều kiện search");
			return gson.toJson(responseDto);
		}
		
		
		//get list of conversation
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		UserEntity userAccount = userAccountService.findAccountByUserName(username);
				
		List<ConversationDto> listOfConversationDto = new ArrayList<>();
		int userId;
		if (hasRole("ROLE_ADMIN")) {
			userId = 0;
		} else {
			userId =  userAccount.getId();
		}
		
		// get list conversation
		listOfConversationDto = (List<ConversationDto>) conversationMapper.transform(conversationService.findConversationByAdvance(calbotId, msisdn, startDate, endDate, userId));
		
		responseDto.setData(listOfConversationDto);
		responseDto.setMessage("tìm kiếm thành công");
		responseDto.setStatus(Constant.SUCCESS);

		return  gson.toJson(responseDto);
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/call")
	public @ResponseBody String call(HttpServletRequest request) {

		ResponseDto<ConversationDto> responseDto = new ResponseDto<ConversationDto>();

		String msisdn = request.getParameter("customer_phone");
		String area = request.getParameter("customer_area");
		int callbotId = Integer.valueOf(request.getParameter("callbotId"));

		// set default
		Gson gson = new Gson();
		responseDto.setData(null);
		responseDto.setMessage("Hệ thống có lỗi!");
		responseDto.setStatus(Constant.FAIL);

		// check validate
		if (msisdn == null || msisdn.length() == 0) {
			responseDto.setMessage("Please enter the phone number");
			return gson.toJson(responseDto);
		}

		try {

			// bc1: call api get input slot
			BizGetInputSlotsResponse bizGetInputSlotsResponseDto = bizCallCommunication.getInputSlots(callbotId, token);
			String[] inputSlots = null;
			if (bizGetInputSlotsResponseDto.getStatus() == Constant.API_CODE_SUCCESS) {
				inputSlots = bizGetInputSlotsResponseDto.getInputSlots();
			}

			// bc2: Create properties
			String conversationId = null;
			HashMap<String, Object> properties = new HashMap<>();
			if (inputSlots != null && inputSlots.length > 0) {
				for (String string : inputSlots) {
					if (request.getParameterMap().containsKey(string)) {
						properties.put(string, request.getParameter(string));
					} else {
						properties.put(string, "");
					}
				}
			}

			// bc3: call api create call
			BizCreateCallRequest bizCreateCallRequest = new BizCreateCallRequest(callbotId, callCenter, msisdn, area,
					properties);

			BizCreateCallResponse bizCreateCallResponseDto = bizCallCommunication.createCall(token,
					bizCreateCallRequest);
			if (bizCreateCallResponseDto.getStatus() == Constant.API_CODE_SUCCESS) {
				conversationId = bizCreateCallResponseDto.getConversationId();
			}

			// bc4: call api callout
			if (conversationId != null) {
				BizCalloutRequest bizCalloutRequest = new BizCalloutRequest(conversationId);
				BizCalloutResponse bizCalloutResponseDto = bizCallCommunication.callout(token, bizCalloutRequest);

				if (bizCalloutResponseDto.getStatus() == Constant.API_CODE_SUCCESS) {
					
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					String username = authentication.getName();
					UserEntity userEntity = userAccountService.findAccountByUserName(username);
					
					Conversation conversation = new Conversation();
					conversation.setMsisdn(msisdn);
					conversation.setConversation(conversationId);
					conversation.setCallbotId(callbotId);
					conversation.setStatus(Constant.CONVERSATION_STATUS_CREATED);
					conversation.setCreateAt(new Timestamp(System.currentTimeMillis()));
					conversation.setUpdateAt(new Timestamp(System.currentTimeMillis()));
					conversation.setUserId(userEntity.getId());
					conversation.setUserEntity(userEntity);

					ConversationDto conversationDto = new ConversationDto();
					if (conversationService.addConversation(conversation)) {
						conversationDto = conversationMapper.transform(conversation);
					}

					responseDto.setData(conversationDto);
					responseDto.setMessage("Tạo cuộc gọi thành công");
					responseDto.setStatus(Constant.SUCCESS);
				}
			}

		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return gson.toJson(responseDto);
		}

	}
	
	@RequestMapping(value = "/download/{file_name:.+}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable("file_name") String fileName, HttpServletResponse response) throws IOException {
		try {
			String path = urlFolderFileTemplateDownload + fileName;
			File file = new File(path);
			byte[] data = FileUtils.readFileToByteArray(file);
			// Thiết lập thông tin trả về
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportFile(HttpServletRequest request,  HttpServletResponse response) throws IOException {
		
		int callbotId = Integer.valueOf(request.getParameter("exportFileCallbotID"));
		String msisdn = request.getParameter("exportFileMsisdn");
		String startDate = request.getParameter("exportFileStartDate");
		String endDate = request.getParameter("exportFileEndDate");
		
		//create data
		List<ExportDataDto> listExportDataDto = new ArrayList<>();
		
		//get list of conversation
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		UserEntity userAccount = userAccountService.findAccountByUserName(username);
						
		List<ConversationDto> listOfConversationDto = new ArrayList<>();
		int userId;
		if (hasRole("ROLE_ADMIN")) {
			userId = 0;
		} else {
			userId =  userAccount.getId();
		}
		
		List<Conversation> listConversation = conversationService.findConversationByAdvance(callbotId, msisdn, startDate, endDate, userId);
		
		for (Conversation conversation : listConversation) {
			
			//get info
			ExportDataDto exportDataDto = new ExportDataDto();
			exportDataDto.setConversationId(conversation.getConversation());
			exportDataDto.setHotline(conversation.getCallcenterPhone());
			exportDataDto.setStatus(CallStatus.CALL_STATUS_MAP.get(conversation.getStatus()));
			exportDataDto.setAudioFileUrl(conversation.getAudioUrl());
			exportDataDto.setMsisdn(conversation.getMsisdn());
			
			if (conversation.getCallAt() != null) {
				exportDataDto.setCallAt(DateUtil.convertMilisecondToDate(conversation.getCallAt()));
			} 
			
			if (conversation.getPickupAt() != null) {
				exportDataDto.setCallTime(DateUtil.convertMiliSecondsToHMS(conversation.getHangupAt()-conversation.getPickupAt()));
			} 
			
			//get content
			StringBuilder content = new StringBuilder();
			List<ConversationContent> listConversationContent = conversation.getConversationContent();
			if (listConversationContent != null && listConversationContent.size() > 0) {
				
				for (ConversationContent conversationContent : listConversationContent) {
					if (conversationContent.getTimestamp() != 0) {						
						content.append("Bot : " + conversationContent.getTextBot() + "\n");
					}
					
					if (conversationContent.getTimestampUser() != 0) {
						content.append("User : " + conversationContent.getTextUser() + "\n");
					}
				}
				
			}
			exportDataDto.setContent(content.toString());
			
			listExportDataDto.add(exportDataDto);
		}
		
		SXSSFWorkbook wb = exportReportData.exportBigDataExcel(listExportDataDto);
		
		String fileName = "Export Data.xlsx";
        try {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
            OutputStream stream = response.getOutputStream();
            if (null != wb && null != stream) {
                wb.write(stream);// Write the data out  
                wb.close();
                stream.close();
                
                long stopTime = System.currentTimeMillis();        //Writing time
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("/upload_file")
	public @ResponseBody String uploadFile(Model model, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		int callbotId = Integer.valueOf(request.getParameter("callbotId"));
		ResponseDto<String> responseDto = new ResponseDto<String>();

		// set default
		Gson gson = new Gson();
		responseDto.setData(null);
		responseDto.setMessage("Hệ thống có lỗi!");
		responseDto.setStatus(Constant.FAIL);

		try {

			// get upload file
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			HashMap<String, String> uploadData = new HashMap();
			MultipartFile file = multiRequest.getFile("exampleInputFile");

			if (file.getOriginalFilename().equals("")) {
				responseDto.setMessage("Please choose the file");
				return gson.toJson(responseDto);
			}

			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			if (!extAllow.contains(ext)) {
				responseDto.setMessage("File Extension is not valid!");
				return gson.toJson(responseDto);
			}

			// save file
			uploadData = FileUtil.doUpload(file, urlFolderFileTemplateUpdate);
			String fileName = (String) uploadData.remove("filepath");
			String er1 = (String) uploadData.remove("error");
			if (er1 != null) {
				System.out.println("UploadError:" + er1);
				responseDto.setMessage("Tạo cuộc gọi thất bại");
				return gson.toJson(responseDto);
			}

			if (fileName == "") {
				System.out.println("UploadError:" + er1);
				responseDto.setMessage("Tạo cuộc gọi thất bại");
				return gson.toJson(responseDto);
			}

			// read upload file
			File convFile = new File(fileName);
			readProperties(callbotId, convFile);

			responseDto.setMessage("Tạo cuộc gọi thành công");
			responseDto.setStatus(Constant.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			return gson.toJson(responseDto);
		}

	}
	
	public void readProperties(int callbotId, File file) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		UserEntity userEntity = userAccountService.findAccountByUserName(username);
		
		HashMap<String, Object> properties = new HashMap<>();

		try {
			FileInputStream inputStream = new FileInputStream(file);

			// Đối tượng workbook cho file xlsx.
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			// Lấy ra sheet đầu tiên từ workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
			Iterator<Row> rowIterator = sheet.iterator();
			Row firstRow = sheet.rowIterator().next();
			for (Row row : sheet) {

				if (!isRowEmpty(row)) {
					if (row.equals(firstRow)) {

					} else {
						String value = "";
						properties = new HashMap<>();

						for (int cn = 0; cn < firstRow.getLastCellNum(); cn++) {

							Cell cell = row.getCell(cn);
							
							if (cell != null) {
								CellType cellType = cell.getCellTypeEnum();

								switch (cellType) {
									case _NONE:
										value = "";
										break;
									case BOOLEAN:
										value = String.valueOf(cell.getBooleanCellValue());
										break;
									case FORMULA:
										FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
										value = String.valueOf(evaluator.evaluate(cell).getNumberValue());
										break;
									case NUMERIC:
										value = String.valueOf(cell.getNumericCellValue());
										break;
									case STRING:
										value = cell.getStringCellValue();
										break;
									case ERROR:
										break;
									default:
										value = "";
										break;

								}
							}

							
							properties.put(firstRow.getCell(cn).getStringCellValue(), value);
						}

						createCallEventPublisher.createCallBy(userEntity, callbotId, properties);

					}
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellTypeEnum() != CellType.BLANK)
				return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private boolean hasRole(String role) {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}

}
