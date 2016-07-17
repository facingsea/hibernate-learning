package com.facingsea.exercise.c_relate.b_one2many;

import java.util.Date;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.c_relate.b_one2many.domain.Customer;
import com.facingsea.exercise.c_relate.b_one2many.domain.Order;
import com.facingsea.exercise.util.HibernateUtil;

public class One2ManyTest {
	
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSession("com/facingsea/exercise/c_relate/b_one2many/res/hibernate.cfg.xml");
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Customer c = new Customer();
		c.setName("eqweqweq");
		c.setRegister(new Date());
		
		Order o = new Order();
		o.setPrice(333.56);
		c.getOrders().add(o);
		
		session.save(c);
		//session.save(o);
		
		tx.commit();
		session.close();
		
		// select
		session = factory.openSession();
		tx = session.beginTransaction();
		
		Customer c2 = (Customer) session.get(Customer.class, c.getId());
		System.out.println(c2.getName());
		Set<Order> set = c2.getOrders();
		for(Order o2 : set){
			System.out.println(o2.getPrice());
		}
		
		// delete
		session.delete(c2);
		
		tx.commit();
		session.close();
		
		
		factory.close();
	}

}
