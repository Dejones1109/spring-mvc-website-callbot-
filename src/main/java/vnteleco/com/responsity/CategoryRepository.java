package vnteleco.com.responsity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vnteleco.com.entity.Category;

@SuppressWarnings("deprecation")
@Transactional
@Repository
public class CategoryRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<Category> findAllCategory() {
		List<Category> listOfCategory = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(Category.class);
	        listOfCategory = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return listOfCategory;		
	}	

}
