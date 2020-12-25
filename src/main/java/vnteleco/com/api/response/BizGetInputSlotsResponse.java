package vnteleco.com.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BizGetInputSlotsResponse {

	@SerializedName (value = "inputSlots")
	private String[] inputSlots;
	private String msg = "Success";
	private int status;
	
	@Override
	public String toString() {
		return "BizResponseDto [data=" + inputSlots + ", msg=" + msg + ", status=" + status + "]";
	}

}
