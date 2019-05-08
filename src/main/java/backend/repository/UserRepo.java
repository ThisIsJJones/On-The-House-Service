package backend.repository;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import backend.models.User;

@Repository
public class UserRepo extends HibernateDao{

    public static User get(Integer id) {
        Session session = sessionFactory.openSession();
        User user = null;
        try {
            Transaction t = null;
            t = session.beginTransaction();
            user = (User)session.get(User.class, id);
            t.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return user;
    }
    
    public static List<User> getAll() {
        Session session = sessionFactory.openSession();
        List<User> users = new ArrayList<User>();
        try {
            Transaction t = null;
            t = session.beginTransaction();
            CriteriaQuery<User> criteria = session.getCriteriaBuilder().createQuery(User.class);
            criteria.from(User.class);
            users = session.createQuery(criteria).getResultList();
            t.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return users;
    }

    public static boolean insert(User user) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.save(user);
            t.commit();
            success = true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return success;
    }

    public static boolean update(User user) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.update(user);
            t.commit();
            success = true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return success;
    }

    public static boolean delete(User user) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.remove(user);
            t.commit();
            success = true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return success;
    }

	@SuppressWarnings("unchecked")
	public static User getUserWithUsername(String username) {
		User user = null;
		Session session = sessionFactory.openSession();
		Transaction t = null;
		try {
			t = session.beginTransaction();	
			Query<User> query = session.createQuery("FROM User WHERE username = :username");	
			query.setParameter("username", username);
			if(!query.list().isEmpty()) {
				user = query.list().get(0);
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}
}
