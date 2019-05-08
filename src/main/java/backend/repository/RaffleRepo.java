package backend.repository;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import backend.models.Raffle;

@Repository
public class RaffleRepo extends HibernateDao{

    public static Raffle get(Integer id) {
        Session session = sessionFactory.openSession();
        Raffle raffle = null;
        try {
            Transaction t = null;
            t = session.beginTransaction();
            raffle = (Raffle)session.get(Raffle.class, id);
            t.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return raffle;
    }
    
    public static List<Raffle> getAll() {
        Session session = sessionFactory.openSession();
        List<Raffle> raffles = new ArrayList<Raffle>();
        try {
            Transaction t = null;
            t = session.beginTransaction();
            CriteriaQuery<Raffle> criteria = session.getCriteriaBuilder().createQuery(Raffle.class);
            criteria.from(Raffle.class);
            raffles = session.createQuery(criteria).getResultList();
            t.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return raffles;
    }

    public static boolean insert(Raffle raffle) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.save(raffle);
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

    public static boolean update(Raffle raffle) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.update(raffle);
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

    public static boolean delete(Raffle raffle) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.remove(raffle);
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
}
