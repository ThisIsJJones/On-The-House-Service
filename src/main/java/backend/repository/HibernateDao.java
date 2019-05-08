package backend.repository;

import org.hibernate.SessionFactory;

import util.SessionFactoryUtil;

public class HibernateDao {
	public static SessionFactory sessionFactory = SessionFactoryUtil.getInstance();
}
