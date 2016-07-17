package com.facingsea.exercise.c_relate.c_one2many;

import java.util.Date;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.c_relate.c_one2many.domain.Customer;
import com.facingsea.exercise.c_relate.c_one2many.domain.Order;
import com.facingsea.exercise.util.HibernateUtil;

public class One2Many2Test {
	
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtil.getSession("com/facingsea/exercise/c_relate/c_one2many/res/hibernate.cfg.xml");
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Customer c = new Customer();
		c.setName("zxczxc");
		c.setRegister(new Date());
		
		Order o = new Order();
		o.setPrice(343.44);
		
		c.getOrders().add(o);
		o.setCustomer(c);
		
		//session.save(o);
		session.save(c);
		
		tx.commit();
		session.close();
		
		
		// --
		session = factory.openSession();
		tx = session.beginTransaction();
		
		Customer c2 = new Customer();
		c2.setName("testdelete-orphan1");
		c2.setRegister(new Date());
		session.save(c2);
		
		Order o2 = new Order();
		o2.setPrice(200.0);
		c2.getOrders().add(o2);
		
		tx.commit();
		session.close();
		
		
		// ----
		System.out.println("====== select =====");
		session = factory.openSession();
		tx = session.beginTransaction();
		
		Customer c3 = (Customer) session.get(Customer.class, c2.getId());
		System.out.println(c3.getName());
		Set<Order> orders = c3.getOrders();
		for(Order order : orders){
			System.out.println(order.getPrice());
			System.out.println(order.getCustomer().getName());
			System.out.println(order.getCustomer().getOrders().size());
		}
		
		tx.commit();
		session.close();
		
		
		// --- delete
		System.out.println("====== delete =====");
		session = factory.openSession();
		tx = session.beginTransaction();
		
		Customer c4 = (Customer) session.get(Customer.class, c2.getId());
		//session.delete(c4);
		
		c4.getOrders().clear(); // 需要配置级联delete-orphan 才能删除订单，否则订单不能删除，只是外键设为null
		
		tx.commit();
		session.close();
		
		
		// --- inverse测试
		System.out.println("====== inverse =====");
		session = factory.openSession();
		tx = session.beginTransaction();
		
		Customer c5 = (Customer) session.get(Customer.class, 37);
		Order o5 = (Order) session.get(Order.class, 38);
		
		// 下面这两步会产生两个update语句去更新外键值，这是因为Cuntomer和Order都有维护外键的权力
		// 可以使用配置inverse（默认为false）来让某一方放弃维护外键的权力，交由另外一方来维护
		// 即：设置inverse=true 当前的一方放弃了维护关系表外键权利，由多方维护
		c5.getOrders().add(o5);
		o5.setCustomer(c5);
		
		tx.commit();
		session.close();
		
		factory.close();
	}

}
