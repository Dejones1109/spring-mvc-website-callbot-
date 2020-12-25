package vnteleco.com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vnteleco.com.entity.Conversation;
import vnteleco.com.responsity.ConversationRepository;
import vnteleco.com.service.ConversationService;

@Service
@Transactional
public class ConversationServiceImpl implements ConversationService {
	
	@Autowired
	ConversationRepository conversationRepository;

	@Override
	public boolean addConversation(Conversation conversation) {
		// TODO Auto-generated method stub
		return conversationRepository.addConversation(conversation);
	}

	@Override
	public boolean updateConversation(Conversation conversation) {
		// TODO Auto-generated method stub
		return conversationRepository.updateConversation(conversation);
	}

	@Override
	public List<Conversation> findConversationByCallbotId(int callbotId, int userId) {
		// TODO Auto-generated method stub
		return conversationRepository.findConversationByCallbotId(callbotId, userId);
	}

	@Override
	public List<Conversation> findConversationByAdvance(int calbotId, String msisdn, String startDate, String endDate, int userId) {
		// TODO Auto-generated method stub
		return conversationRepository.findConversationByAdvance(calbotId, msisdn, startDate, endDate, userId);
	}

}
