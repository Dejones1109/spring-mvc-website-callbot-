package vnteleco.com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "conversation_content")
public class ConversationContent {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "conversation_content_id")
	private Long id;
	
	@Column(name =  "action_name")
	private String actionName;
	
	@Column(name =  "intent_name")
	private String intentName;
	
	@Column(name =  "pre_action")
	private String preAction;
	
	@Column(name =  "status_bot")
	private String statusBot;
	
	@Column(name =  "text_bot")
	private String textBot;
	
	@Column(name =  "text_tts_bot")
	private String textTtsBot;
	
	@Column(name =  "text_user")
	private String textUser;
	
	@Column(name =  "timestamp")
	private long timestamp;
	
	@Column(name =  "timestamp_user")
	private long timestampUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", referencedColumnName = "conversation_id")
    private Conversation conversation;
}
