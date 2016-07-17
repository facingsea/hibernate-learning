package com.facingsea.exercise.c_relate.e_many2many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.facingsea.exercise.c_relate.e_many2many.domain.Course;
import com.facingsea.exercise.c_relate.e_many2many.domain.Student;
import com.facingsea.exercise.util.HibernateUtil;

public class Many2ManyTest {
	
	public static void main(String[] args) {
		SessionFactory f = HibernateUtil.getSession("com/facingsea/exercise/c_relate/e_many2many/res/hibernate.cfg.xml");
		Session s = f.openSession();
		Transaction tx = s.beginTransaction();
		
		Student stu = new Student();
		stu.setSname("lisi");
		Student stu2 = new Student();
		stu2.setSname("wangwu");
		
		Course c = new Course();
		c.setCname("hibernate");
		Course c2 = new Course();
		c2.setCname("spring");
		
		stu.getCourses().add(c);
		stu.getCourses().add(c2);
		stu2.getCourses().add(c);
		stu2.getCourses().add(c2);
		c.getStudents().add(stu);
		c.getStudents().add(stu2);
		c2.getStudents().add(stu);
		c2.getStudents().add(stu2);
		
		s.save(c);
		s.save(c2);
		s.save(stu);
		s.save(stu2);
		
		
		tx.commit();
		s.close();
		
		
		//======
		s = f.openSession();
		tx = s.beginTransaction();
		//将5号学生的4号课程换成2号课程
		Student stu3 = (Student) s.get(Student.class, 5);
		Course c3 = (Course) s.get(Course.class, 4);
		Course c4 = (Course) s.get(Course.class, 2);
		
		stu3.getCourses().remove(c3);
		stu3.getCourses().add(c4);
		
		tx.commit();
		s.close();
		
		//======
		System.out.println("======delete===============");
		s = f.openSession();
		tx = s.beginTransaction();
		
		// 删除学生6号
		Student stu5 = (Student) s.get(Student.class, 6);
		s.delete(stu5);
		
		tx.commit();
		s.close();
		
		
		f.close();
	}

}
