package vnteleco.com.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BizCreateCallResponse {

	@SerializedName(value = "conversation_id")
	private String conversationId;
	private String msg = "Success";
	private int status;
	
	@Override
	public String toString() {
		return "BizCreateCallResponseDto [conversationId=" + conversationId + ", msg=" + msg + ", status=" + status+ "]";
	}

}
