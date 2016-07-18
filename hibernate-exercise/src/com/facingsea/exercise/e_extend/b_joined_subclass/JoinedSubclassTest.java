package com.facingsea.exercise.e_extend.b_joined_subclass;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.e_extend.b_joined_subclass.domain.Employee2;
import com.facingsea.exercise.e_extend.b_joined_subclass.domain.HourEmployee2;
import com.facingsea.exercise.e_extend.b_joined_subclass.domain.SalaryEmployee2;
import com.facingsea.exercise.util.HibernateUtil;

public class JoinedSubclassTest {
	
	public static void main(String[] args) {
		SessionFactory f = HibernateUtil.getSession("com/facingsea/exercise/e_extend/b_joined_subclass/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Employee2 e = new Employee2();
		e.setName("张三");
		
		HourEmployee2 he = new HourEmployee2();
		he.setName("李四");
		he.setRate(40.45);
		
		SalaryEmployee2 se = new SalaryEmployee2();
		se.setName("王五");
		se.setSalary(400.33);
		
		s.save(e);
		s.save(he);
		s.save(se);
		
		tx.commit();
		s.close();
		
		//============ select ==========
		s = f.openSession();
		tx = s.beginTransaction();
		
		HourEmployee2 he2 = (HourEmployee2) s.get(HourEmployee2.class, 2);
		System.out.println(he2.getName());
		System.out.println(he2.getRate());
		
		tx.commit();
		s.close();
		
		// ======== delete ============
		s = f.openSession();
		tx = s.beginTransaction();
		
		HourEmployee2 he3 = (HourEmployee2) s.get(HourEmployee2.class, 5);
		s.delete(he3);
		
		tx.commit();
		s.close();
		
		
		f.close();
	}

}
