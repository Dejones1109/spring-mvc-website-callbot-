package vnteleco.com.api.request;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BizCreateCallRequest {
	
	@SerializedName(value = "callbot_id")
	private int callbotId;
	
	@SerializedName(value = "callcenter_phone")
	private String callcenterPhone;
	
	@SerializedName(value = "customer_phone")
	private String customerPhone;
	
	@SerializedName(value = "customer_area")
	private String customerArea;
	
	@SerializedName(value = "input_slots")
	private HashMap<String, Object> inputSlots;

	public BizCreateCallRequest(int callbotId, String callcenterPhone, String customerPhone, String customerArea,
			HashMap<String, Object> inputSlots) {
		super();
		this.callbotId = callbotId;
		this.callcenterPhone = callcenterPhone;
		this.customerPhone = customerPhone;
		this.customerArea = customerArea;
		this.inputSlots = inputSlots;
	}

}
