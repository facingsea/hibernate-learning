package com.facingsea.exercise.b_part.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.b_part.domain.Customer;
import com.facingsea.exercise.util.HibernateUtil;

public class UpdateTest {
	
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSession("com/facingsea/exercise/b_part/res/hibernate.cfg.xml");
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Customer c = new Customer();
		c.setName("asdas");
		c.setAge(23);
		c.setCity("北京海淀区");
		session.save(c);
		
		session.evict(c);
		
		//c.setCity("郑州市");
		session.update(c); // 如果class配置了select-before-update属性，会先查询比对，如果没发生变化，将不会更新
		
		tx.commit();
		session.close();
		
		factory.close();
	}

}
