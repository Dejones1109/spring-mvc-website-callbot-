package vnteleco.com.api.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConversationResponseDTO {
	@SerializedName(value = "action_end")
	private String actionEnd;
	
	@SerializedName(value = "action_path")
	private String actionPath;
	
	@SerializedName(value = "audio_url")
	private String audioUrl;
	
	@SerializedName(value = "call_at")
	private long callAt;
	
	@SerializedName(value = "callbot_id")
	private int callbotId;
	
	@SerializedName(value = "callcenter_phone")
	private String callcenterPhone;
	
	@SerializedName(value = "content")
	private ContentCallResponseDTO[] content;
	
	@SerializedName(value = "conversation_id")
	private String conversationId;

	@SerializedName(value = "created_at")
	private long createdAt;
	
	@SerializedName(value = "customer_area")
	private String customerArea;
	
	@SerializedName(value = "customer_phone")
	private String customerPhone;
	
	@SerializedName(value = "hangup_at")
	private long hangupAt;
	
	@SerializedName(value = "pickup_at")
	private long pickupAt;
	
	@SerializedName(value = "sip_code")
	private String sipCode;
	
	@SerializedName(value = "status")
	private int status;
	
}
