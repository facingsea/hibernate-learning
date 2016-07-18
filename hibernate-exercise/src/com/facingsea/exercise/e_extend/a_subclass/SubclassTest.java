package com.facingsea.exercise.e_extend.a_subclass;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.e_extend.a_subclass.domain.Employee;
import com.facingsea.exercise.e_extend.a_subclass.domain.HourEmployee;
import com.facingsea.exercise.e_extend.a_subclass.domain.SalaryEmployee;
import com.facingsea.exercise.util.HibernateUtil;

public class SubclassTest {
	
	public static void main(String[] args) {
		SessionFactory f = HibernateUtil.getSession("com/facingsea/exercise/e_extend/a_subclass/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		//Employee e = new Employee();
		//e.setName("李四");
		HourEmployee he = new HourEmployee();
		he.setRate(85.36);
		he.setName("王五");
		SalaryEmployee se = new SalaryEmployee();
		se.setSalary(456.12);
		se.setName("赵六");
		
		//s.save(e);
		s.save(he);
		s.save(se);
		
		tx.commit();
		s.close();
		
		
		// ============ select ===============
		s = f.openSession();
		tx = s.beginTransaction();
		System.out.println("===== select =====");
		Employee e2 = (Employee) s.get(Employee.class, 2);
		System.out.println(e2.getName());
		
		
		tx.commit();
		s.close();
		f.close();
	}

}
