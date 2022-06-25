package DAO;


import model.Customer;

public interface CustomerDAO {

    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    boolean isExist(Long discordId);
    Customer getCustomer (Long discordId);

}
