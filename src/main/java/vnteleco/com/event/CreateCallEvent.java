package vnteleco.com.event;

import java.util.HashMap;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;
import vnteleco.com.entity.UserEntity;

@Getter
@Setter
public class CreateCallEvent extends ApplicationEvent{
	
	private int callbotId;
	private HashMap<String, Object> properties;
	private UserEntity userEntity;

	public CreateCallEvent(Object source, UserEntity userEntity, int callbotId, HashMap<String, Object> properties) {
		super(source);
		// TODO Auto-generated constructor stub
		this.callbotId = callbotId;
		this.properties = properties;
		this.userEntity = userEntity;
	}

}
