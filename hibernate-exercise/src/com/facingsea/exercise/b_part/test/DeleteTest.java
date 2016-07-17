package com.facingsea.exercise.b_part.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.b_part.domain.Customer;
import com.facingsea.exercise.util.HibernateUtil;

public class DeleteTest {
	
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSession("com/facingsea/exercise/b_part/res/hibernate.cfg.xml");
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Customer c = new Customer();
		c.setId(18);
		
		session.delete(c);
		
		
		tx.commit();
		session.close();
		
		factory.close();
	}

}
