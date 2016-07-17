package com.facingsea.exercise.c_relate.a_many_2_one;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.c_relate.a_many_2_one.domain.Customer;
import com.facingsea.exercise.c_relate.a_many_2_one.domain.Order;
import com.facingsea.exercise.util.HibernateUtil;

public class Many2OneTest {
	
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSession("com/facingsea/exercise/c_relate/a_many_2_one/res/hibernate.cfg.xml");
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Customer c = new Customer();
		c.setName("张三22");
		c.setRegister(new Date());
		
		Order o = new Order();
		o.setPrice(34.45);
		o.setCustomer(c);
		
		session.save(o);
		//session.save(c);
		
		tx.commit();
		session.close();
		
		session = factory.openSession();
		tx = session.beginTransaction();
		// select
		String hql = "from Order o where o.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", o.getId());
		Order o2 = (Order) query.uniqueResult();
		System.out.println(o2.getPrice());
		System.out.println(o2.getCustomer().toString());
		
		tx.commit();
		session.close();
		factory.close();
	}

}
