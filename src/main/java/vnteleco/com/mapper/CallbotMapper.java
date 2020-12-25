package vnteleco.com.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import vnteleco.com.entity.Callbot;
import vnteleco.com.entity.dto.CallbotDto;
import vnteleco.com.util.FileUtil;


@Component
@PropertySource("classpath:config.properties")
public class CallbotMapper implements EntityDtoMapper<Callbot, CallbotDto>{
	
	@Value("${url_folder_avatar}")
	private String urlFolderAvatar;
	
	@Value("${default_avatar}")
	private String defaultAvatar;
	
	 @Override
	 public CallbotDto transform(Callbot callbot) {
		 CallbotDto callbotDto = new CallbotDto();
		 callbotDto.setId(callbot.getId());
		 callbotDto.setName(callbot.getName());
		 callbotDto.setFileTemplateName(callbot.getFileTemplateName());
		 callbotDto.setCategoryId(callbot.getCategoryId());
		 		 
		 if (callbot.getAvatar() !=null && callbot.getAvatar().length() > 0) {
			 callbotDto.setAvatar(FileUtil.encodedFile(urlFolderAvatar+callbot.getAvatar()));
		 }else {
			 callbotDto.setAvatar(FileUtil.encodedFile(urlFolderAvatar+defaultAvatar));
		 }
		
		 return callbotDto;
	        
	 }

	@Override
	public Callbot transformReverse(CallbotDto model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CallbotDto> transform(Collection<Callbot> entities) {
		
		List<CallbotDto> listOfCallbotDto = new ArrayList<>();
		for (Callbot callbot : entities) {
			CallbotDto callbotDto = transform(callbot);
			
			listOfCallbotDto.add(callbotDto);
		}
		
		return listOfCallbotDto;
	}

	@Override
	public Collection<Callbot> transformReverse(Collection<CallbotDto> models) {
		// TODO Auto-generated method stub
		return null;
	}
}
