package com.facingsea.exercise.j_ehcache;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.j_ehcache.domain.Customer;
import com.facingsea.exercise.j_ehcache.domain.Order;
import com.facingsea.exercise.util.HibernateUtil;

/**
 * ehcache测试
 * 
 * 注意：二级缓存只对hql有效，对sql无效
 * @author wangzhf
 *
 */
public class EHCacheTest {
	
	public static void main(String[] args) {
		//initData();
		//testSecondCache1();
		//testSecondCache2();
		//testSecondCache3();
		//testSecondCache4();
		//testSecondCache5();
		///testSecondCache6();
		testSecondCache7();
	}
	
	/**
	 * 测试查询缓存
	 * 
	 * 启用查询缓存 ，必须要先开启二级缓存 （查询缓存依赖二级缓存 ）
			在hibernate.cfg.xml 中 添加 <property name=“hibernate.cache.use_query_cache">true</property>
	       对于希望启用查询缓存的查询语句, 调用 Query 的 setCacheable(true) 方法

	 */
	public static void testSecondCache7(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/j_ehcache/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		// 希望将查询结果 放入查询缓存
		Query query = s.createQuery("select name from Customer");
		query.setCacheable(true); // 启用查询缓存
		
		List list = query.list(); // 只查询 数据表部分属性，无法缓存 -- 可以被放入查询缓存
		System.out.println(list);
		
		tx.commit();
		s.close();
		System.out.println("开启新session。。。");
		s = f.openSession();
		tx = s.beginTransaction();
		Query query2 = s.createQuery("select name from Customer");
		query2.setCacheable(true); // 对于想从查询缓存中 获取数据的查询，也必须开启查询缓存
		
		List list2 = query2.list(); // 才能从查询缓存 获取数据，不会产生sql
		System.out.println(list2);
		System.out.println("从缓存中取值完毕");
		
		Customer customer = (Customer) s.get(Customer.class, 2);
		System.out.println(customer);

		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试Query的iterate方法<br>
	 * 	Query的iterate方法，遍历时其中的每个对象，都是只有OID的代理对象，只有在访问对象其它属性时，
	 *  才进行初始化，初始化时，优先找二级缓存，如果找不到，生成SQL语句<br>
	 *  iterator 先到数据库中检索符合条件的id,然后根据id分别到一级和二级缓冲中查找对象 (没有在查询数据库,每次只能查一个,可能导致n+1次查询 )<br>
	 *  当使用二级缓存时，iterate比list 效率要好<br>
	 *  
	 *  	Query 接口的 iterate() 方法
	 *		•	同 list() 一样也能执行查询操作
	 *		•	list() 方法执行的 SQL 语句包含实体类对应的数据表的所有字段
	 *		•	Iterate() 方法执行的SQL 语句中仅包含实体类对应的数据表的 ID 字段
	 *		•	当遍历访问结果集时, 该方法先到 Session 缓存及二级缓存中查看是否存在特定 OID 的对象, 如果存在, 就直接返回该对象, 如果不存在该对象就通过相应的 SQL Select 语句到数据库中加载特定的实体对象
	 *		大多数情况下, 应考虑使用 list() 方法执行查询操作. iterate() 方法仅在满足以下条件的场合, 可以稍微提高查询性能:
	 *		•	要查询的数据表中包含大量字段
	 *		•	启用了二级缓存, 且二级缓存中可能已经包含了待查询的对象
     *
	 */
	public static void testSecondCache6(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/j_ehcache/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		List<Order> list = s.createQuery("from Order where id <= 3").list(); // 查询前3条订单，list会将结果存入二级缓存
		System.out.println(list.size());
		System.out.println("查询完，测试是否存到二级缓存....");
		
		
		tx.commit();
		s.close();
		System.out.println("开启新session111111111");
		s = f.openSession();
		tx = s.beginTransaction();
		
		Order o = (Order) s.load(Order.class, 2);
		System.out.println(o.getPrice());
		System.out.println("测试二级缓存查询完毕///");
		
		tx.commit();
		s.close();
		System.out.println("开启新session22222222222");
		s = f.openSession();
		tx = s.beginTransaction();
		
		Iterator<Order> iterator = s.createQuery("from Order where id <= 5").iterate(); //查询前5条 订单
		while(iterator.hasNext()){ // 因为二级缓存已经存在前3条，前3条不会生成SQL ，4-5条 生成2条SQL语句
			Order order = iterator.next(); 
			System.out.println(order.getPrice());
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试更新时间戳<br>
	 * 
	 * 更新时间戳缓存区域主要是保存数据被更新的时间的，比如某个事物从数据库取出某些数据，
	 * 将其放在类级别缓存（给其添加一个时间戳），并将此时间戳保存在更新时间戳缓存区域，当另一个事物更改数据库中的数据后，
	 * 此对象的信息也发生改变，但是不会更新类级别缓存的，可是会将更新时间戳缓存中的相应的时间戳更改为更改数据的时间戳，
	 * 这样，当第一个事物再次获得此对象的时候，会先比较两个缓存中的时间戳，如果更新时间戳中的时间比类级别中的时间大，
	 * 证明数据被更新过，需要重新查找数据库。
	 */
	public static void testSecondCache5(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/j_ehcache/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer customer = (Customer) s.get(Customer.class, 2); // 存入 一级缓存和 二级缓存
		System.out.println(customer);
		// 更新数据库数据 ，是否重新查询
		// customer.setName("xxx"); // 更新一级缓存，同时会同步到二级缓存，这种情况下测不出来
		
		// 通过SQL语句 直接改数据库
		s.createQuery("update Customer set name='小王' where id = 2").executeUpdate(); // 更新时间，session中会改变，但是二级缓存中并没变
		
		tx.commit();
		s.close();
		System.out.println("开启新session");
		s = f.openSession();
		tx = s.beginTransaction();
		
		Customer customer2 = (Customer) s.get(Customer.class, 2); // 判断t2 > t1 是大于 ，重新查询
		System.out.println(customer2);

		
		tx.commit();
		s.close();
		f.close();
	}
	
	
	/**
	 * 测试一级缓存更新数据会同步到二级缓存
	 */
	public static void testSecondCache4(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/j_ehcache/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer customer = (Customer) s.get(Customer.class, 2); // 存入 一级缓存和 二级缓存
		System.out.println(customer);
		customer.setName("王小五"); // 修改一级缓存的数据 ， 自动同步到二级缓存
		
		tx.commit();
		s.close();
		
		s = f.openSession();
		tx = s.beginTransaction();
		
		Customer customer2 = (Customer) s.get(Customer.class, 2); // 使用二级缓存 
		System.out.println(customer2);

		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试集合级别的缓存 <br>
	 * 测试1：注释cfg.xml中的order类缓存<br>
	 * 测试2：打开cfg.xml中的order类缓存<br>
	 * 
	 * 当第一次获取数据时，会存放在二级缓存中，原理是在类级别缓存区中保存对象，而在集合级别缓存区中保存对象的id值，
	 * 也就是OID，这一点可以通过程序实验（测试1），
	 * 方法就是将cfg.xml文件中对Order类级别缓存的配置注释掉（<class-cache usage="read-write" class="...Order"/>），
	 * 这时，当第一次进行查询后，将order对象的id值保存在了集合级别缓存区中，而由于没有配置Order的类级别缓存区，
	 * 所以说Order对象不能保存在类级别缓存区中；当第二次进行查询时，会搜索集合级别缓存区获取id，
	 * 但是根据这些id不能在类级别缓存中取得相应的数据，所以说，他会生成新的查询语句并查找数据库以获得数据，
	 * 而且每条记录都会有一条select语句。
	 */
	public static void testSecondCache3(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/j_ehcache/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer customer = (Customer) s.get(Customer.class, 2);
		System.out.println("after get ....");
		System.out.println(customer.getOrders().size()); // 查询集合，会缓存
		System.out.println(customer.getOrders()); // 1
		
		tx.commit();
		s.close();
		
		s = f.openSession();
		tx = s.beginTransaction();
		
		Customer customer2 = (Customer) s.get(Customer.class, 2);
		for (Order order : customer2.getOrders()) { // 从二级缓存取数据
			System.out.println(order); // hashCode值与 1 处的不一样，是组成的新对象
			System.out.println(order.getPrice());
		}

		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 类级别的二级缓存只适用于get和load获取数据，对query接口可以将数据放置到类级别的二级缓存中，
	 * 但是不能使用query接口的 list方法从缓存中获取数据；
	 * query接口将查询的对象放置到二级缓存的查询缓存
	 */
	public static void testSecondCache2(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/j_ehcache/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		List<Customer> customers = s.createQuery("from Customer").list(); // 向二级缓存存入数据
		System.out.println(customers);
		
		tx.commit();
		s.close();
		System.out.println("开启新session");
		s = f.openSession();
		tx = s.beginTransaction();
		
		List<Customer> customers2 = s.createQuery("from Customer").list(); //产生新的sql，不能从二级缓存 获取数据
		System.out.println(customers2);
		
		System.out.println("============");
		
		Customer customer = (Customer) s.get(Customer.class, 2); //未产生sql，从二级缓存获取数据
		System.out.println(customer); //hashCode对应最后存进去的数据

		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试类级别缓存：<br>
	 * 
	 * 当没有配置二级缓存时，在一个session里查找同一个持久类时，不会再产生sql语句，因为这是查找了一级缓存（session缓存）的缘故，
	 * 当session关闭时，一级缓存也就清空不复存在了，所以当第二次在创建一个session对象去查找同一个持久对象时，
	 * 将会产生新的查询语句，因为新的session缓存中并没有缓存这个持久对象；当配置二级缓存时，
	 * 使用多个session进行查找同一对象时，只会产生一条查询语句，这是因为第一次查询时会将此对象放在二级缓存中，
	 * 后来的查询都会先查询二级缓存，然后没有的话，再去查找数据库。
	 * 但是这里每个session对象得到的持久对象的hashCode值是不一样的，这是因为在二级缓存中，对象是以散装的方式存储的
	 */
	public static void testSecondCache1(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/j_ehcache/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c = (Customer) s.get(Customer.class, 2);
		System.out.println(c);
		
		//一级缓存
		Customer c2 = (Customer) s.get(Customer.class, 2);
		System.out.println(c2);
		
		tx.commit();
		s.close();
		
		///====================
		
		s = f.openSession();
		tx = s.beginTransaction();
		
		System.out.println("新的session：");
		// 没有产生sql语句，二级缓存生效
		Customer c3 = (Customer) s.get(Customer.class, 2);
		System.out.println(c3); // ..j_ehcache.domain.Customer@7d9d0818
		
		
		tx.commit();
		s.close();
		
		///====================
		
		s = f.openSession();
		tx = s.beginTransaction();
		
		System.out.println("新的session2：");
		// 没有产生sql语句，二级缓存生效
		Customer c4 = (Customer) s.get(Customer.class, 2);
		System.out.println(c4); // ..j_ehcache.domain.Customer@4d1c005e
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 初始化测试数据
	 */
	public static void initData(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/j_ehcache/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c = new Customer();
		c.setName("赵六");
		c.setRegister(new Date());
		s.save(c);
		for (int i = 1; i <= 10; i++) {
			Order o = new Order();
			o.setPrice(365 + i * 10d);
			c.getOrders().add(o);
			o.setCustomer(c);
			s.save(o);
		}
		
		tx.commit();
		s.close();
		f.close();
	}

}
