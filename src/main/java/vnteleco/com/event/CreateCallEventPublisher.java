package vnteleco.com.event;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import vnteleco.com.entity.UserEntity;

@Component
public class CreateCallEventPublisher {
	@Autowired
    ApplicationEventPublisher applicationEventPublisher;
	
	public void createCallBy(UserEntity userEntity, int callbotId, HashMap<String, Object> properties) {
        // Phát ra một sự kiện DoorBellEvent
        // source (Nguồn phát ra) chính là class này
        applicationEventPublisher.publishEvent(new CreateCallEvent(this, userEntity, callbotId, properties));
      
    }
}
