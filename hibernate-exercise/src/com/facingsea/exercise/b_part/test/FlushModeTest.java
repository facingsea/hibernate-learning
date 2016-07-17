package com.facingsea.exercise.b_part.test;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.b_part.domain.Customer;
import com.facingsea.exercise.util.HibernateUtil;

/**
 * 
 * 清理缓存的模式	         Session的查询方法	  tx的commit（）	Session的flush（）
	FlushMode.AUTO(默认)	            刷出                                      刷出                                刷出
	FlushMode.COMMIT         不刷出                                  刷出                               刷出
	FlushMode.ALWAYS         刷出                                       刷出                              刷出
	FLushMode.MANUAL         不刷出                                  不刷出                           刷出

 * @author wangzhf
 *
 */
public class FlushModeTest {
	
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSession("com/facingsea/exercise/b_part/res/hibernate.cfg.xml");
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Customer c = new Customer();
		c.setName("wer");
		c.setAge(23);
		c.setCity("北京海淀区");
		session.save(c);
		
		tx.commit();
		session.close();
		
		session = factory.openSession();
		session.setFlushMode(FlushMode.MANUAL);
		tx = session.beginTransaction();
		
		Query query = session.createQuery("from Customer c where c.id = :id");
		query.setParameter("id", c.getId());
		Customer c2 = (Customer) query.uniqueResult();
		c2.setCity("广州市");
		
		tx.commit();
		session.close();
		
		factory.close();
	}

}
