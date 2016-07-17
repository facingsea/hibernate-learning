package com.facingsea.exercise.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	
	public static SessionFactory getSession(String resPath){
		Configuration cfg = new Configuration().configure(resPath);
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder().applySettings(cfg.getProperties());
		ServiceRegistry registry = builder.buildServiceRegistry();
		SessionFactory factory = cfg.buildSessionFactory(registry);
		return factory;
	}

}
