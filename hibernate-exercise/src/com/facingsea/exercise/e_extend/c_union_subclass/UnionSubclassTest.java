package com.facingsea.exercise.e_extend.c_union_subclass;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.e_extend.c_union_subclass.domain.Employee3;
import com.facingsea.exercise.e_extend.c_union_subclass.domain.HourEmployee3;
import com.facingsea.exercise.e_extend.c_union_subclass.domain.SalaryEmployee3;
import com.facingsea.exercise.util.HibernateUtil;

public class UnionSubclassTest {
	
	public static void main(String[] args) {
		SessionFactory f = HibernateUtil.getSession("com/facingsea/exercise/e_extend/c_union_subclass/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Employee3 e = new Employee3();
		e.setName("张三");
		
		HourEmployee3 he = new HourEmployee3();
		he.setName("李四");
		he.setRate(30.32);
		
		SalaryEmployee3 se = new SalaryEmployee3();
		se.setName("王五");
		se.setSalary(500.32);
		
		s.save(e);
		s.save(he);
		s.save(se);
		
		tx.commit();
		s.close();
		f.close();
	}

}
