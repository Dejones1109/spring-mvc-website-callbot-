package vnteleco.com.entity.response;

import lombok.Getter;
import lombok.Setter;
import vnteleco.com.entity.Conversation;

@Getter
@Setter
public class ResponseDto<T> {
	private T data;
	private String message;
	private int status;
	
	
}
