package vnteleco.com.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vnteleco.com.entity.Conversation;
import vnteleco.com.entity.dto.ConversationDto;
import vnteleco.com.enumjava.CallStatus;
import vnteleco.com.service.ConversationService;


@Component
public class ConversationMapper implements EntityDtoMapper<Conversation, ConversationDto>{
	
	@Autowired
	private ConversationService conversationService;
	
	@Override
	public ConversationDto transform(Conversation conversation) {
				
		ConversationDto conversationDto = new ConversationDto();
		conversationDto.setConversationId(conversation.getConversation());
		conversationDto.setCustomer(conversation.getMsisdn());
		conversationDto.setCreateTime(conversation.getCreateAt());
		conversationDto.setStatus(CallStatus.CALL_STATUS_MAP.get(conversation.getStatus()));
		conversationDto.setAccountName(conversation.getUserEntity().getUserName());
		 
		return conversationDto;
	        
	}

	@Override
	public Conversation transformReverse(ConversationDto model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ConversationDto> transform(Collection<Conversation> entities) {
		
		List<ConversationDto> listOfConversationDto = new ArrayList<>();
		for (Conversation conversation : entities) {
			ConversationDto conversationDto = transform(conversation);
			
			listOfConversationDto.add(conversationDto);
		}
		
		return listOfConversationDto;
	}

	@Override
	public Collection<Conversation> transformReverse(Collection<ConversationDto> models) {
		// TODO Auto-generated method stub
		return null;
	}
}
