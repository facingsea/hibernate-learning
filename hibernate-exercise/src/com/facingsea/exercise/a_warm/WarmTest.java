package com.facingsea.exercise.a_warm;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
		c.setName("王五");
		c.setAge(33);
		c.setCity("上海");
		session.save(c);
		System.out.println(c.getId());
		
		int id = c.getId();
		Customer c2 = (Customer) session.get(Customer.class, id);
		System.out.println(c2.getName());
		
		Customer c3 = (Customer) session.load(Customer.class, id);
		System.out.println(c3.getName());
		
		System.out.println("====== hql =====");
		String hql = "from Customer c";
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		for(Object obj : list){
			System.out.println(obj.toString());
		}
		
		System.out.println("===== sql =====");
		String sql = "select * from customer";
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		List<Object[]> list2 = sqlQuery.list();
		for(Object[] objs : list2){
			System.out.println(Arrays.toString(objs));
		}
		
		tx.commit();
		session.close();
		factory.close();
	}

}
