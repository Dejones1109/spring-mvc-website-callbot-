package vnteleco.com.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "conversation")
public class Conversation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "conversation_id")
	private Long id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "msisdn")
	private String msisdn;
	
	@Column(name = "create_at")
	private Timestamp createAt;
	
	@Column(name = "update_at")
	private Timestamp updateAt;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "callbot_id")
	private int callbotId;
	
	@Column(name = "conversation")
	private String conversation;
	
	@Column(name = "callcenter_phone")
	private String callcenterPhone;
	
	@Column(name = "hangup_at")
	private Long hangupAt;
	
	@Column(name = "pickup_at")
	private Long pickupAt;
	
	@Column(name = "call_at")
	private Long callAt;
	
	@Column(name = "audio_url")
	private String audioUrl;
	
	@OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL) 
    private List<ConversationContent> conversationContent;

	@ManyToOne(optional=false)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id",  insertable=false, updatable=false)
	private UserEntity userEntity;
	
}
