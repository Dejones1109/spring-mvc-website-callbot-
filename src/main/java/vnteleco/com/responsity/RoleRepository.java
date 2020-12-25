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

import vnteleco.com.entity.RoleEntity;

@Repository
@Transactional
public class RoleRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	public List<RoleEntity> findAllRole() {
		List<RoleEntity> listOfRoleEntity = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(RoleEntity.class);
	        listOfRoleEntity = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return listOfRoleEntity;		
	}
	
	public RoleEntity findRoleById(int roleId) {
		RoleEntity roleEntity = null;
    	try {
    		Session session = sessionFactory.getCurrentSession();
    		Criteria crit = session.createCriteria(RoleEntity.class);
            crit.add(Restrictions.eq("id", roleId));
            roleEntity = (RoleEntity) crit.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return roleEntity;
        
	}
}
