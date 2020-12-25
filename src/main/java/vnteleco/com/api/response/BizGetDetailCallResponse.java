package vnteleco.com.api.response;

import lombok.Getter;
import lombok.Setter;
import vnteleco.com.api.response.dto.ConversationResponseDTO;

@Setter
@Getter
public class BizGetDetailCallResponse {

	private ConversationResponseDTO conversation;
	private String msg = "Success";
	private int status;
	
}
