package vnteleco.com.api.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContentCallResponseDTO {
	@SerializedName(value = "action_name")
	private String actionName;
	
	@SerializedName(value = "intent_name")
	private String intentName;
	
	@SerializedName(value = "pre_action")
	private String preAction;
	
	@SerializedName(value = "status_bot")
	private String statusBot;
	
	@SerializedName(value = "text_bot")
	private String textBot;
	
	@SerializedName(value = "text_tts_bot")
	private String textTtsBot;
	
	@SerializedName(value = "text_user")
	private String textUser;
	
	@SerializedName(value = "timestamp")
	private long timestamp;
	
	@SerializedName(value = "timestamp_user")
	private long timestampUser;
}
