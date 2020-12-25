package vnteleco.com.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationInfoDto {
	private String conversationId;
	private String callbotId;
	private String callCenter;
	private String customer;
	private String callAt;
	private String audioUrl;
	private String waitTime;
	private String callTime;
	private String status;
	
}
