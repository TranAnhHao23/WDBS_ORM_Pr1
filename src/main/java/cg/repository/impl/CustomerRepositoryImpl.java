package cg.repository.impl;

import cg.model.Customer;
import cg.repository.ICustomerRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

@Repository
public class CustomerRepositoryImpl implements ICustomerRepository {

    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomer() {
        String queryStr = "SELECT c FROM Customer AS c";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr,Customer.class);
        return (ArrayList<Customer>) query.getResultList();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        Transaction transaction = null;
        Customer origin;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (customer.getId() != 0){
                origin = getCustomer(customer.getId());
                origin.setAddress(customer.getAddress());
                origin.setEmail(customer.getEmail());
                origin.setName(customer.getName());
            } else {
                origin = customer;
            }
            session.saveOrUpdate(origin);
            transaction.commit();
            return origin;
        } catch (Exception e){
            e.printStackTrace();
            if (transaction != null){
                transaction.rollback();
            }
        }
        return null;
    }

    @Override
    public Customer deleteCustomer(int id) {
        Transaction transaction = null;
        Customer origin = getCustomer(id);
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            if (origin != null){
                session.delete(origin);
            }
            transaction.commit();
            return origin;
        } catch (Exception e){
            e.printStackTrace();
            if (transaction != null){
                transaction.rollback();
            }
        }
        return null;
    }

    @Override
    public Customer getCustomer(int id) {
        String queryStr = "SELECT c FROM Customer AS c WHERE c.id = :id";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr,Customer.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public ArrayList<Customer> findByNameContaining(String name) {
        String queryStr = "SELECT c FROM Customer AS c WHERE c.name LIKE :name";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr,Customer.class);
        query.setParameter("name","%" + name + "%");
        return (ArrayList<Customer>) query.getResultList();
    }
}
