package com.facingsea.exercise.a_warm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.facingsea.exercise.a_warm.domain.Customer;

/**
 * 构建sessionFactory参考：http://www.open-open.com/lib/view/open1356339689713.html
 * @author wangzhf
 *
 */
public class WarmTest {
	
	public static void main(String[] args) {
		Configuration cfg = new Configuration().configure("com/facingsea/exercise/a_warm/res/hibernate.cfg.xml");
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
		// 此步骤将应用配置信息，不然将会按照hibernate内部默认配置连接h2数据库
		builder.applySettings(cfg.getProperties());
		ServiceRegistry registry = builder.buildServiceRegistry();
		SessionFactory factory = cfg.buildSessionFactory(registry);
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Customer c = new Customer();
		c.setName("zhangsan");
		c.setAge(20);
		c.setCity("北京");
		session.save(c);
		
		tx.commit();
		session.close();
		factory.close();
	}

}
