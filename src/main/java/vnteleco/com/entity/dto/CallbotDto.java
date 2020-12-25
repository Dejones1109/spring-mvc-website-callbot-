package vnteleco.com.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallbotDto {
	
	private int id;
	private String name;
	private String fileTemplateName;
	private int categoryId;
	private String avatar;
}
