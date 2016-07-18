package com.facingsea.exercise.f_search;

import java.util.Date;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.f_search.domain.Customer;
import com.facingsea.exercise.f_search.domain.Order;
import com.facingsea.exercise.util.HibernateUtil;

/**
 * 检索策略测试
 * 
 * @author wangzhf
 *
 */
public class SearchTest {

	public static void main(String[] args) {
		
		//testCustomerLazy();
		//loadCustomertrueProxyInit();
		testLazyExtra();
		//testFetchSelect();
		//testFetchJoin();
		//testFetchSubselect();
		
	}
	
	/**
	 * 当fetch为subselect时：
	 * 	使用嵌套子查询(检索多个customer对象时) Lazy属性决定检索策略
	 */
	public static void testFetchSubselect(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/f_search/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();

		Customer c = (Customer) s.load(Customer.class, 1);
		System.out.println(c.getClass().getName());
		System.out.println(c.getName());
		Set<Order> orders = c.getOrders();
		System.out.println(orders.getClass().getName());
		System.out.println(orders.size()); 
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试fetch为join
	 * 当fetch为join时，会忽略lazy属性，使用左外连接立即加载
	 */
	public static void testFetchJoin(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/f_search/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();

		Customer c = (Customer) s.load(Customer.class, 1);
		System.out.println(c.getClass().getName());
		System.out.println(c.getName());
		Set<Order> orders = c.getOrders();
		System.out.println(orders.getClass().getName());
		System.out.println(orders.size()); 
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试fetch为select
	 *  在映射文件中, 用 <set> 元素来配置一对多关联及多对多关联关系. <set> 元素有 lazy 和 fetch 属性
	 *  •	lazy: 主要决定 orders 集合被初始化的时机. 即到底是在加载 Customer 对象时就被初始化, 还是在程序访问 orders 集合时被初始化
	 *  •	fetch: 取值为 “select” 或 “subselect” 时, 决定初始化 orders 的查询语句的形式;  若取值为”join”, 则决定 orders 集合被初始化的时机
	 *  •	若把 fetch 设置为 “join”, lazy 属性将被忽略
     *
	 */
	public static void testFetchSelect(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/f_search/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();

		Customer c = (Customer) s.load(Customer.class, 1);
		System.out.println(c.getClass().getName());
		System.out.println(c.getName());
		Set<Order> orders = c.getOrders();
		System.out.println(orders.getClass().getName()); //lazy为true时，返回的是代理对象
		System.out.println(orders.size()); // lazy为true时，开始执行查询语句
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试lazy为extra
	 */
	public static void testLazyExtra(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/f_search/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();

		Customer c = (Customer) s.load(Customer.class, 1);
		System.out.println(c.getClass().getName());
		System.out.println(c.getName());
		Set<Order> orders = c.getOrders();
		System.out.println(orders.getClass().getName());
		System.out.println(orders.size());  //lazy为extra时，会执行统计语句（count(1)），而不会查询具体的值
		for(Order o : orders){
			System.out.println(o.getPrice()); //lazy为extra时，才会执行具体查询语句
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 使用Hibernate.isInitialized来初始化
	 */
	@SuppressWarnings("rawtypes")
	public static void loadCustomertrueProxyInit(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/f_search/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();

		Customer c = (Customer) s.load(Customer.class, 1);
		System.out.println(c.getClass().getName());
		Class[] ins = c.getClass().getInterfaces();
		if(ins.length > 0){
			for(Class cls : ins){
				System.out.println(cls.getName());
			}
		}
		Class parent = c.getClass().getSuperclass(); // Customer
		System.out.println(parent.getName());
		if(!Hibernate.isInitialized(c)){
			System.out.println("未初始化。。。");
			Hibernate.initialize(c);
			System.out.println(c.getClass().getName());
		}

		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试一方class的lazy属性
	 * 如果lazy为true，默认返回代理对象，不产生sql查询语句
	 * 为false时，会直接查询
	 */
	public static void testCustomerLazy(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/f_search/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();

		Customer c = (Customer) s.load(Customer.class, 1);
		System.out.println(c.getClass().getName());
		System.out.println(c.getId());
		System.out.println(c.getName());

		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 初始化数据
	 */
	public static void initData() {
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/f_search/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();

		Customer c = new Customer();
		c.setName("zhangsan");
		c.setRegister(new Date());
		s.save(c);
		for (int i = 1; i <= 10; i++) {
			Order o = new Order();
			o.setPrice(5000 + i * 10d);
			c.getOrders().add(o);
			o.setCustomer(c);
			s.save(o);
		}

		tx.commit();
		s.close();
		f.close();
	}
}
