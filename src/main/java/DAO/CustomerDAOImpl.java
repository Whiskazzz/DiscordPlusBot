package DAO;


import model.Customer;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;


@Transactional
public class CustomerDAOImpl implements CustomerDAO {
    EntityManager entityManager = Persistence.createEntityManagerFactory("model.Customer").createEntityManager();


    @Override
    public void addCustomer(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();

    }

    @Override
    public void updateCustomer(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.merge(customer);
        entityManager.getTransaction().commit();
    }

    @Override
    public boolean isExist(Long discordId) {
        String jpql = "from Customer where discordId = :discordId";
        TypedQuery<Customer> query = entityManager.createQuery(jpql, Customer.class).setParameter("discordId",discordId);
        return query.getResultList().size() > 0;
    }

    @Override
    public Customer getCustomer(Long discordId) {
        String jpql = "from Customer where discordId = :discordId";
        TypedQuery<Customer> query = entityManager.createQuery(jpql, Customer.class).setParameter("discordId",discordId);
        return query.getSingleResult();
    }
}
