package vnteleco.com.api.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BizCalloutResponse {

	private String msg = "Success";
	private int status;

	public BizCalloutResponse(String msg, int status) {
		super();
		this.msg = msg;
		this.status = status;
	}

}
