package vnteleco.com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "callbot_fields")
public class CallbotFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "callbot_id")
	private int callbotId;
	
	@Column(name = "en_name")
	private String enName;
	
	@Column(name = "vn_name")
	private String vnName;
	
	@Column(name = "type")
	private int type;
	
	@Column(name = "min_length")
	private int minLength;
	
	@Column(name = "max_length")
	private int maxLength;
	
	@Column(name = "min_value")
	private String minValue;
	
	@Column(name = "max_value")
	private String maxValue;
	
	@Column(name = "default_value")
	private String defaultValue;
}
