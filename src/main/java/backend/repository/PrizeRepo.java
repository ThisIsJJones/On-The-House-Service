package backend.repository;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import backend.models.Prize;

@Repository
public class PrizeRepo extends HibernateDao{

    public static Prize get(Integer id) {
        Session session = sessionFactory.openSession();
        Prize prize = null;
        try {
            Transaction t = null;
            t = session.beginTransaction();
            prize = (Prize)session.get(Prize.class, id);
            t.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return prize;
    }
    
    public static List<Prize> getAll() {
        Session session = sessionFactory.openSession();
        List<Prize> prizes = new ArrayList<Prize>();
        try {
            Transaction t = null;
            t = session.beginTransaction();
            CriteriaQuery<Prize> criteria = session.getCriteriaBuilder().createQuery(Prize.class);
            criteria.from(Prize.class);
            prizes = session.createQuery(criteria).getResultList();
            t.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return prizes;
    }

    public static boolean insert(Prize prize) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.save(prize);
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

    public static boolean update(Prize prize) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.update(prize);
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

    public static boolean delete(Prize prize) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.remove(prize);
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
	public static Prize getPrizeWithPrizename(String prizename) {
		Prize prize = null;
		Session session = sessionFactory.openSession();
		Transaction t = null;
		try {
			t = session.beginTransaction();	
			Query<Prize> query = session.createQuery("FROM Prize WHERE prizename = :prizename");	
			query.setParameter("prizename", prizename);
			if(!query.list().isEmpty()) {
				prize = query.list().get(0);
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return prize;
	}
}
