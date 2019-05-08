package backend.repository;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import backend.models.Business;

@Repository
public class BusinessRepo extends HibernateDao{

    public static Business get(Integer id) {
        Session session = sessionFactory.openSession();
        Business business = null;
        try {
            Transaction t = null;
            t = session.beginTransaction();
            business = (Business)session.get(Business.class, id);
            t.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return business;
    }
    
    public static List<Business> getAll() {
        Session session = sessionFactory.openSession();
        List<Business> businesses = new ArrayList<Business>();
        try {
            Transaction t = null;
            t = session.beginTransaction();
            CriteriaQuery<Business> criteria = session.getCriteriaBuilder().createQuery(Business.class);
            criteria.from(Business.class);
            businesses = session.createQuery(criteria).getResultList();
            t.commit();
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return businesses;
    }

    public static boolean insert(Business business) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.save(business);
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

    public static boolean update(Business business) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.update(business);
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

    public static boolean delete(Business business) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.remove(business);
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
