package DAO;


import model.Customer;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Transactional
public class CustomerDAOImpl implements CustomerDAO {

    EntityManager entityManager = Persistence.createEntityManagerFactory("model.Customer",config()).createEntityManager();

    private static Map<String,Object> config(){
        Map<String, Object> configOverrides = new HashMap<String, Object>();
        configOverrides.put("hibernate.dialect","org.hibernate.dialect.PostgreSQL81Dialect");
        configOverrides.put("hibernate.hbm2ddl.auto","update");
        configOverrides.put("javax.persistence.jdbc.driver","org.postgresql.Driver");
        configOverrides.put("javax.persistence.jdbc.url",System.getenv("URL"));
        configOverrides.put("javax.persistence.jdbc.user",System.getenv("USERNAME"));
        configOverrides.put("javax.persistence.jdbc.password",System.getenv("PASSWORD"));
        return configOverrides;
    }


    @Override
    public List<Customer> getStats() {
        ArrayList<Customer> arrayList = (ArrayList<Customer>) entityManager.createQuery("from Customer ").getResultList()
                .stream()
                .sorted()
                .limit(10)
                .collect(Collectors.toCollection(() -> new ArrayList<Customer>()));
        return arrayList;
    }

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
