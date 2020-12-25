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

import vnteleco.com.entity.Callbot;
import vnteleco.com.entity.CallbotFields;

@SuppressWarnings("deprecation")
@Transactional
@Repository
public class CallbotFieldsRepository {
	@Autowired
	private SessionFactory sessionFactory;

	
	public List<CallbotFields> findByCallbotId(int callbotId) {
		List<CallbotFields> listOfCallbotFields = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(CallbotFields.class);
	        crit.add(Restrictions.eq("callbotId", callbotId));
	        listOfCallbotFields = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return listOfCallbotFields;		
	}	

}
