package vnteleco.com.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BizCalloutRequest {

	@SerializedName(value = "conversation_id")
	private String conversationId;

	public BizCalloutRequest(String conversationId) {
		super();
		this.conversationId = conversationId;
	}

}
