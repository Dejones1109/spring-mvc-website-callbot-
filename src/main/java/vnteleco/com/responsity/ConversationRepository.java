package vnteleco.com.responsity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vnteleco.com.entity.Conversation;

@SuppressWarnings("deprecation")
@Transactional
@Repository
public class ConversationRepository {
	@Autowired
	private SessionFactory sessionFactory;

	public boolean addConversation(Conversation conversation) {
		Session session = sessionFactory.getCurrentSession();
		boolean check = true;
		try {
			session.save(conversation);
		} catch (HibernateException e) {
			check = false;
			e.printStackTrace();
		}
		return check;
	}
	
	public List<Conversation> findConversationByCallbotId(int callbotId, int userId) {
		List<Conversation> listOfConversation = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Conversation.class);
			crit.add(Restrictions.eq("callbotId", callbotId));
			if ( userId != 0) {
				crit.add(Restrictions.eq("userId", userId));
			}
			listOfConversation = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return listOfConversation;
	}
	
	public List<Conversation> findConversationByAdvance(int calbotId, String msisdn, String startDate, String endDate, int userId) {
		List<Conversation> listOfConversation = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder hql = new StringBuilder();
			hql.append(" FROM Conversation _entity where _entity.callbotId = " + calbotId);
			
			if (!StringUtils.isBlank(msisdn)) {
				hql.append(" and _entity.msisdn like '%" + msisdn + "%'");
			}
			
			if (!StringUtils.isBlank(startDate)) {
				hql.append(" and DATE_FORMAT(_entity.createAt,'%Y-%m-%d') >= '" + startDate + "'");
			}
			
			if (!StringUtils.isBlank(endDate)) {
				hql.append(" and '" + endDate + "' >= DATE_FORMAT(_entity.createAt,'%Y-%m-%d') ");
			}
			
			if (userId != 0) {
				hql.append(" and _entity.userId = " + userId);
			}
			Query query = session.createQuery(hql.toString());
			listOfConversation = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return listOfConversation;

	}

	public boolean updateConversation(Conversation conversation) {
		Session session = sessionFactory.getCurrentSession();
		boolean result = true;
		try {
			if (conversation != null) {
				session.saveOrUpdate(conversation);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return result;
	}
}
