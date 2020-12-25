package vnteleco.com.service;

import java.util.List;

import vnteleco.com.entity.Conversation;

public interface ConversationService {
	public boolean addConversation(Conversation conversation);
	public List<Conversation> findConversationByCallbotId(int callbotId, int userId);
	public List<Conversation> findConversationByAdvance(int calbotId, String msisdn, String startDate, String endDate, int userId);
	public boolean updateConversation(Conversation conversation);
}
