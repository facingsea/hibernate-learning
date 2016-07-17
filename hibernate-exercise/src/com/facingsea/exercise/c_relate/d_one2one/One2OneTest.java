package com.facingsea.exercise.c_relate.d_one2one;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.c_relate.d_one2one.domain.Address;
import com.facingsea.exercise.c_relate.d_one2one.domain.Address2;
import com.facingsea.exercise.c_relate.d_one2one.domain.Company;
import com.facingsea.exercise.c_relate.d_one2one.domain.Company2;
import com.facingsea.exercise.util.HibernateUtil;

public class One2OneTest {
	
	public static void main(String[] args) {
		SessionFactory f = HibernateUtil.getSession("com/facingsea/exercise/c_relate/d_one2one/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		//===外键关联
		Company c = new Company();
		c.setName("谷歌");
		
		Address a = new Address();
		a.setName("加利福尼亚");
		
		c.setAddress(a);
		a.setCompany(c);
		
		s.save(c);
		s.save(a);
		
		//====主键关联
		Company2 c2 = new Company2();
		c2.setName("苹果");
		Address2 a2 = new Address2();
		a2.setName("硅谷");
		
		a2.setCompany2(c2);
		c2.setAddress2(a2);
		
		s.save(c2);
		s.save(a2);
		
		tx.commit();
		s.close();
		f.close();
	}

}
