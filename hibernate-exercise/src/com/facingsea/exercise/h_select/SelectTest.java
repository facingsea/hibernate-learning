package com.facingsea.exercise.h_select;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.facingsea.exercise.h_select.domain.Customer;
import com.facingsea.exercise.h_select.domain.Order;
import com.facingsea.exercise.util.HibernateUtil;

public class SelectTest {

	
	public static void main(String[] args) {
		
		//testHql();
		//testCriteria();
		//testSql();
		//testObject();
		//testOrderBy();
		//testPage();
		//testNamedQuery();
		//testCriteriaCondition();
		//testLeftOuterJoinFetch();
		//testLeftOuterJoin();
		//testInnerJoin();
		//testInnerJoinFetch();
		//testImplicitInnerJoin();
		//testRightOuterJoin();
		//testProjectionQuery();
		//testAggregationFunction();
		//testGroupBy();
		testDetachedCriteria();
	}
	
	/**
	 * 离线条件查询
	 * https://docs.jboss.org/hibernate/orm/3.5/reference/zh-CN/html/querycriteria.html
	 * DetachedCriteria类使你在一个session范围之外创建一个查询，并且可以使用任意的 Session来执行它
	 */
	public static void testDetachedCriteria(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		criteria.add(Restrictions.eq("name", "zhangsan"));

		@SuppressWarnings("unchecked")
		List<Customer> list = 
		         criteria.getExecutableCriteria(s).list();
		System.out.println(list.size());
		for(Customer c : list){
			System.out.println(c.getName());
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 分组测试
	 */
	public static void testGroupBy(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = s.createQuery(" select c.name,count(c) from Customer c group by c.name").list();
		System.out.println(list.size());
		System.out.println(list.get(0).getClass().getName());
		for(Object[] os : list){
			//System.out.println(os.length);
			String name = (String) os[0];
			Long count = (Long) os[1];
			System.out.println(name + "=====" + count);
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 聚集函数测试
	 */
	public static void testAggregationFunction(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Query query = s.createQuery("select count(*) from Customer c");
		Long count = (Long) query.uniqueResult();
		System.out.println("count " + count);

		Query query2 = s.createQuery("select avg(o.price) from Order o");
		double avg = (double) query2.uniqueResult();
		System.out.println("avg " + avg);
		
		Query query3 = s.createQuery("select max(o.price), min(o.price) from Order o");
		Object[] maxmin = (Object[]) query3.uniqueResult();
		System.out.println("max " + (double)maxmin[0]);
		System.out.println("min " + (double)maxmin[1]);
		
		Query query4 = s.createQuery("select sum(o.price) from Order o");
		double sum = (double) query4.uniqueResult();
		System.out.println("sum " + sum);

		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 投影查询测试
	 */
	public static void testProjectionQuery(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		String hql = "select c, o.price from Customer c join c.orders o where c.name = ? ";
		Query q = s.createQuery(hql);
		q.setParameter(0, "zhangsan");
		List<Object[]> list = q.list();
		System.out.println(list.get(0).getClass().getName());
		System.out.println(list.size());
		for(Object[] objs : list){
			//System.out.println(objs.length);
			Customer c = (Customer) objs[0];
			double o = (double) objs[1]; //o.price为单独的一个字段
			System.out.println(c.getName() + " === " + o);
		}
		
		//选取部分字段
		String hql2 = "select c.id, c.name, o.price from Customer c join c.orders o where c.name = ? ";
		Query q2 = s.createQuery(hql2);
		q2.setParameter(0, "zhangsan");
		List<Object[]> list2 = q2.list();
		System.out.println(list2.get(0).getClass().getName());
		System.out.println(list2.size());
		for(Object[] objs : list2){
			//System.out.println(objs.length);
			//System.out.println(objs[0].getClass().getName());
			//System.out.println(objs[1].getClass().getName());
			Integer id = (Integer) objs[0];
			String name = (String) objs[1];
			double price = (double) objs[2];
			System.out.println(id + "===" + name + "===" + price);
		}
		
		
		// QBC
		@SuppressWarnings("unchecked")
		List<Object[]> list3 = s.createCriteria(Customer.class)
				.setProjection(
						Projections.projectionList()
							.add(Property.forName("id"))
							.add(Property.forName("name"))
							)
				.list();
		System.out.println(list3.get(0).getClass().getName());
		System.out.println(list3.size());
		for(Object[] os : list3){
			Integer id = (Integer) os[0];
			String name = (String) os[1];
			System.out.println(id + "=====" + name);
		}
		
		//使用构造函数查询（需要有对应的构造函数）
		String hql4 = "select new Customer(id, name) from Customer c ";
		Query q4 = s.createQuery(hql4);
		List<Customer> list4 = q4.list();
		System.out.println(list4.get(0).getClass().getName());
		System.out.println(list4.size());
		//.....
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试右连接
	 */
	public static void testRightOuterJoin(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		String hql = " from Customer c right outer join c.orders where c.name = ?";
		Query q = s.createQuery(hql);
		q.setParameter(0, "zhangsan");
		List<Object[]> list = q.list();
		System.out.println(list.get(0).getClass().getName());
		System.out.println(list.size());
		for(Object[] c : list){
			// 测试产生的重复数据
			//System.out.println(c.hashCode()); //不一样
			//System.out.println(c.length);
			//System.out.println(c[0].getClass().getName());
			//System.out.println(c[1].getClass().getName());
			//System.out.println(c[0].getClass().hashCode());
			//System.out.println(c[1].getClass().hashCode());
			Customer cc = (Customer) c[0];
			Order oo = (Order) c[1];
			System.out.println(cc.getName() + "=====" + oo.getPrice());
		}
		
		// 添加distinct
		String hql2 = "select distinct c " + hql;
		Query q2 = s.createQuery(hql2);
		q2.setParameter(0, "zhangsan");
		List<Customer> list2 = q2.list();
		System.out.println(list2.get(0).getClass().getName());
		System.out.println(list2.size());
		for(Customer c : list2){
			System.out.println(c.getName());
			Set<Order> os = c.getOrders();
			System.out.println(os.size());
			for(Order o : os){
				System.out.println(o.getPrice());
			}
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试隐式内连接
	 */
	public static void testImplicitInnerJoin(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		String hql = " from Order o where o.customer.name = ?";
		Query q = s.createQuery(hql);
		
		q.setParameter(0, "zhangsan");
		List<Order> list = q.list();
		System.out.println(list.get(0).getClass().getName());
		System.out.println(list.size());
		for(Order o : list){
			System.out.println(o.getCustomer().getName() + "=====" + o.getPrice());
		}
		
		//上面hql等同于:
		hql = "from Order o join o.customer c where c.name = ?";
		Query q2 = s.createQuery(hql);
		
		q2.setParameter(0, "zhangsan");
		List<Object[]> list2 = q2.list(); // 返回数组形式
		System.out.println(list2.get(0).getClass().getName());
		System.out.println(list2.size());
		for(Object[] objs : list2){
			//System.out.println(objs.length);
			//System.out.println(objs[0].getClass().getName());
			//System.out.println(objs[1].getClass().getName());
			Order o = (Order) objs[0];
			System.out.println(o.getCustomer().getName() + "======" + o.getPrice());
		}
		
		// distinct，可以忽略
		hql = "select distinct o from Order o join o.customer c where c.name = ?";
		Query q3 = s.createQuery(hql);
		
		q3.setParameter(0, "zhangsan");
		List<Order> list3 = q3.list();
		System.out.println(list3.get(0).getClass().getName());
		System.out.println(list3.size());
		for(Order o : list3){
			System.out.println(o.getCustomer().getName() + "=====" + o.getPrice());
		}
		
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试迫切内连接：inner join fetch
	 */
	public static void testInnerJoinFetch(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Query q = s.createQuery("select distinct c from Customer c inner join fetch c.orders o where c.name = ?");
		q.setParameter(0, "zhangsan");
		List<Customer> list = q.list();
		System.out.println(list.get(0).getClass().getName());
		System.out.println(list.size());
		for(Customer c : list){
			System.out.println(c.getName());
			Set<Order> os = c.getOrders();
			System.out.println(os.size());
			for(Order o : os){
				System.out.println(o.getPrice());
			}
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 内连接inner join 测试：
	 * 默认检索的是成对的对象Customer和Order。返回是对象数组
	 */
	public static void testInnerJoin(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Query q = s.createQuery(" from Customer c inner join c.orders o where c.name = ?");
		q.setParameter(0, "zhangsan");
		List<Object[]> list = q.list();
		System.out.println(list.get(0).getClass().getName());
		System.out.println(list.size());
		for(Object[] c : list){
			Customer cc = (Customer) c[0];
			Order oo = (Order) c[1];
			System.out.println(cc.getName() + "=====" + oo.getPrice());
		}
		
		// 添加distinct
		Query q2 = s.createQuery("select distinct c from Customer c inner join c.orders o where c.name = ?");
		q2.setParameter(0, "zhangsan");
		List<Customer> list2 = q2.list();
		System.out.println(list2.get(0).getClass().getName());
		System.out.println(list2.size());
		for(Customer c : list2){
			System.out.println(c.getName());
			Set<Order> os = c.getOrders();
			System.out.println(os.size());
			for(Order o : os){
				System.out.println(o.getPrice());
			}
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试左外连接
	 */
	public static void testLeftOuterJoin(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		// 没有distinct，产生数据结构为List(Object(){Cutomer, Order}, Object(){Cutomer, Order})
		Query q = s.createQuery(" from Customer c left outer join c.orders o where c.name = ?");
		q.setParameter(0, "zhangsan");
		List<Object[]> list = q.list();
		System.out.println(list.get(0).getClass().getName());
		System.out.println(list.size());
		for(Object[] c : list){
			// 测试产生的重复数据
			//System.out.println(c.hashCode()); //不一样
			//System.out.println(c.length);
			//System.out.println(c[0].getClass().getName());
			//System.out.println(c[1].getClass().getName());
			//System.out.println(c[0].getClass().hashCode());
			//System.out.println(c[1].getClass().hashCode());
			Customer cc = (Customer) c[0];
			Order oo = (Order) c[1];
			System.out.println(cc.getName() + "=====" + oo.getPrice());
		}
		
		// 添加distinct
		Query q2 = s.createQuery("select distinct c from Customer c left outer join c.orders o where c.name = ?");
		q2.setParameter(0, "zhangsan");
		List<Customer> list2 = q2.list();
		System.out.println(list2.get(0).getClass().getName());
		System.out.println(list2.size());
		for(Customer c : list2){
			System.out.println(c.getName());
			Set<Order> os = c.getOrders();
			System.out.println(os.size());
			for(Order o : os){
				System.out.println(o.getPrice());
			}
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试迫切左外连接
	 * 若在HQL、QBC代码中没有显式指定检索策略，使用映射文件中
		的检索策略。但HQL总是忽略映射文件中设置的迫切左外(内)连
		接检索策略。 也就是说，即使映射文件中设置了迫切左外(内)连接检索策略，如果HQL查询语句中没有显示指定这种策略，那么HQL
		仍然采用立即检索策略。
	 */
	public static void testLeftOuterJoinFetch(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		// select distinct c 用来去除重复的数据，去掉的话将会产生重复数据
		Query q = s.createQuery("select distinct c from Customer c left outer join fetch c.orders o where c.name = ?");
		q.setParameter(0, "zhangsan");
		List<Customer> list = q.list();
		System.out.println(list.size());
		for(Customer c : list){
			System.out.println(c.getName());
			Set<Order> os = c.getOrders();
			System.out.println(os.hashCode());
			for(Order oo : os){
				System.out.println("order: " + oo.getId() + " == price: " + oo.getPrice());
			}
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试Criteria条件查询
	 */
	public static void testCriteriaCondition(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Criteria c = s.createCriteria(Order.class);
		c.createAlias("customer", "c"); //如果没有此步骤，下面的customer.name会报错
		SimpleExpression expre = Restrictions.lt("id", 4);
		SimpleExpression expre2 = Restrictions.like("c.name", "%zhang%");
		c.add(Restrictions.and(expre, expre2));
		
		List<Order> list = c.list();
		for(Order o : list){
			System.out.println(o.getId() + "====" + o.getPrice());
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	
	/**
	 * 命名查询语句 NamedQuery 
		    在实际开发中，经常需要使用一些HQL，将这些语句写入程序中，不便于维护 ------  将这些语句写入配置文件，并为这些语句进行命名 
		查询某个客户的订单 from Order where customer.name = ? 
		Order.hbm.xml 
			<query name="findOrdersByCustomerName">
				<![CDATA[ from Order where customer.name = ? ]]>
			</query>
		<query>标签的位置在<class>标签的下面，即他们是兄弟

	 */
	public static void testNamedQuery(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Query q = s.getNamedQuery("findOrdersByCustomerName");
		q.setParameter(0, "zhangsan");
		List<Order> list = q.list();
		for(Order o : list){
			System.out.println(o.getId() + " == " + o.getPrice());
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试分页
	 * 	setFirstResult(int firstResult): 设定从哪一个对象开始检索, 参数 firstResult 表示这个对象在查询结果中的索引位置, 索引位置的起始值为 0. 默认情况下, Query 从查询结果中的第一个对象开始检索
		setMaxResult(int maxResults): 设定一次最多检索出的对象的数目. 在默认情况下, Query 和 Criteria 接口检索出查询结果中所有的对象
	 */
	public static void testPage(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		//hql
		Query q = s.createQuery("from Order order by id desc");
		q.setFirstResult(3);
		q.setMaxResults(5);
		List<Order> orders = q.list();
		for(Order o : orders){
			System.out.println(o.getId());
		}
		
		//QBC
		Criteria c = s.createCriteria(Order.class);
		c.addOrder(org.hibernate.criterion.Order.desc("id"));
		c.setFirstResult(3);
		c.setMaxResults(5);
		List<Order> o2s = c.list();
		for(Order o : o2s){
			System.out.println(o.getId());
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	
	/**
	 * 测试排序
	 */
	public static void testOrderBy(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		//hql
		List<Order> orders = s.createQuery("from Order order by id desc").list();
		for(Order o : orders){
			System.out.println(o.getId());
		}
		
		// QBC(Query by Criteria)
		Criteria c = s.createCriteria(Order.class);
		c.addOrder(org.hibernate.criterion.Order.desc("id"));
		List<Order> o2s = c.list();
		for(Order o : o2s){
			System.out.println(o.getId());
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 多态查询：是指查询出当前类及所有子类的实例
	 * 进行查询时，查询PO类的父类，所有该父类的PO子类，对应表中数据 都会被查询
	 * 非PO类，必须写完整包名+类名
	 */
	public static void testObject(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		List list = s.createQuery("from java.lang.Object").list(); // 查询Object的所有子类
		System.out.println(list);
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * sql查询测试
	 */
	public static void testSql(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		String sql = "select * from customer_search where id = ?";
		SQLQuery q = s.createSQLQuery(sql);
		q.setParameter(0, 1);
		List<Object[]> list = q.list(); // 默认封装成Object数组
		for(Object[] c : list){
			System.out.println(c.length);
			System.out.println(c[0]);
			System.out.println(c[1]);
			
		}
		System.out.println("===================");
		
		SQLQuery q2 = s.createSQLQuery(sql);
		q2.setParameter(0, 1);
		q2.addEntity(Customer.class); // 封装成Customer
		List<Customer> list2 = q2.list();
		for(Customer c : list2){
			System.out.println(c.getName());
		}
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * 测试Criteria查询
	 */
	public static void testCriteria(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Criteria criteria = s.createCriteria(Customer.class)
				.add(Restrictions.eq("name", "zhangsan"));
		List<Customer> list = criteria.list();
		for(Customer c : list){
			System.out.println(c.getName());
		}
		
		//查询订单
		System.out.println("===========================");
		Criteria c2 = s.createCriteria(Order.class);
		Customer cc = new Customer();
		cc.setId(1);
		c2.add(Restrictions.eq("customer", cc));
		List<Order> os = c2.list();
		System.out.println(os.size());
		
		
		// 关联customer的name属性查询
		System.out.println("==================");
		Criteria c3 = s.createCriteria(Order.class);
		c2.createAlias("customer", "c");
		c2.add(Restrictions.eq("c.name", "zhangsan"));
		List<Order> os3 = c3.list();
		System.out.println(os3.size());
		
		tx.commit();
		s.close();
		f.close();
	}
	
	/**
	 * hql基本测试
	 */
	public static void testHql(){
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/h_select/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		String hql = "from Customer";
		Query query = s.createQuery(hql);
		List<Customer> cs = query.list();
		for(Customer c : cs){
			System.out.println(c.getName());
		}
		
		tx.commit();
		s.close();
		f.close();
	}
}
