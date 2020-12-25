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
@Table(name = "callbot")
public class Callbot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "callbot_id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "file_template_name")
	private String fileTemplateName;
	
	@Column(name = "category_id")
	private int categoryId;
	
	@Column(name = "avatar")
	private String avatar;
}
