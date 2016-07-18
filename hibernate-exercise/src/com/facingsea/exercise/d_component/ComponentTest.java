package com.facingsea.exercise.d_component;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.d_component.domain.Address;
import com.facingsea.exercise.d_component.domain.Person;
import com.facingsea.exercise.util.HibernateUtil;

public class ComponentTest {
	
	public static void main(String[] args) {
		SessionFactory f = HibernateUtil.getSession("com/facingsea/exercise/d_component/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Person p = new Person();
		p.setName("李四");
		
		Address home = new Address();
		home.setProvince("上海");
		home.setCity("浦东");
		Address work = new Address();
		work.setProvince("北京");
		work.setCity("海淀");
		p.setHomeAddress(home);
		p.setWorkAddress(work);
		
		s.save(p);
		
		tx.commit();
		s.close();
		
		//-------------- select----
		s = f.openSession();
		tx = s.beginTransaction();
		
		Person p2 = (Person) s.get(Person.class, 1);
		System.out.println(p2.getName());
		System.out.println(p2.getHomeAddress().getCity());
		
		tx.commit();
		s.close();
		
		
		// ------------ update -----------
		s = f.openSession();
		tx = s.beginTransaction();
		
		Person p3 = (Person) s.get(Person.class, 2);
		p3.getWorkAddress().setCity("朝阳");
		
		tx.commit();
		s.close();
		
		f.close();
	}

}
