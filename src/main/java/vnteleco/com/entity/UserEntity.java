package vnteleco.com.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "create_at")
	private Timestamp createAt;
	
	@Column(name = "update_at")
	private Timestamp updateAt;
	
	@Column(name = "enabled")
	private int enabled;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", 
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id") }, 
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id") })
	private Set<RoleEntity> roles;

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", userName=" + userName + ", password=" + password + "]";
	}
	
}
