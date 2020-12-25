package vnteleco.com.entity.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportDataDto {
	private String conversationId;
	private String hotline;
	private String msisdn;
	private String callAt;
	private String status;
	private String callTime;
	private String content;
	private String audioFileUrl;
}
