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

@SuppressWarnings("deprecation")
@Transactional
@Repository
public class CallbotRepository {
	@Autowired
	private SessionFactory sessionFactory;

	public Callbot findCallbotById(int id) {
		Callbot callbot = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Callbot.class);
			crit.add(Restrictions.eq("id", id));
			callbot = (Callbot) crit.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return callbot;

	}
	
	public List<Callbot> findCallbotByCategoryId(int categoryId) {
		List<Callbot> listOfCallbot = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Callbot.class);
			crit.add(Restrictions.eq("categoryId", categoryId));
			listOfCallbot = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return listOfCallbot;

	}
	
	public List<Callbot> findAllCallbot() {
		List<Callbot> listOfCallbot = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(Callbot.class);
	        listOfCallbot = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return listOfCallbot;		
	}	

}
