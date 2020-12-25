package vnteleco.com.responsity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vnteleco.com.entity.UserEntity;

@SuppressWarnings("deprecation")
@Transactional
@Repository
public class UserAccountRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean addUserEntity(UserEntity userEntity) {
		Session session = sessionFactory.getCurrentSession();
		boolean check = true;
		try {
			session.save(userEntity);
		} catch (HibernateException e) {
			check = false;
			e.printStackTrace();
		}
		return check;
	}

	public UserEntity findAccountByUserName(String userName) {
		UserEntity userAccount = null;
    	try {
    		Session session = sessionFactory.getCurrentSession();
    		Criteria crit = session.createCriteria(UserEntity.class);
            crit.add(Restrictions.eq("userName", userName));
            userAccount = (UserEntity) crit.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return userAccount;
        
	}
	
	public UserEntity findAccountById(int userId) {
		UserEntity userAccount = null;
    	try {
    		Session session = sessionFactory.getCurrentSession();
    		Criteria crit = session.createCriteria(UserEntity.class);
            crit.add(Restrictions.eq("id", userId));
            userAccount = (UserEntity) crit.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return userAccount;
        
	}
	
	public List<UserEntity> findAllUserAccount() {
		List<UserEntity> listOfUserAccount = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(UserEntity.class);
	        listOfUserAccount = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return listOfUserAccount;		
	}	
}
