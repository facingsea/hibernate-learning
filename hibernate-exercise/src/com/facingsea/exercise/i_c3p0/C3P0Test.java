package com.facingsea.exercise.i_c3p0;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.i_c3p0.domain.C3P0Bean;
import com.facingsea.exercise.util.HibernateUtil;

public class C3P0Test {
	
	public static void main(String[] args) {
		SessionFactory f = HibernateUtil
				.getSession("com/facingsea/exercise/i_c3p0/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		C3P0Bean b = new C3P0Bean();
		b.setName("zhangsan");
		b.setRegister(new Date());
		s.save(b);
		
		tx.commit();
		s.close();
		f.close();
	}

}
