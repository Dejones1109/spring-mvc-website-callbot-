package vnteleco.com.entity.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationDto {
	private Timestamp createTime;
	private String conversationId;
	private String customer;
	private String accountName;
	private String status;
	
}
