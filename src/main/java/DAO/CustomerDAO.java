package DAO;


import model.Customer;

import java.util.List;


public interface CustomerDAO {

    List<Customer> getStats();

    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    boolean isExist(Long discordId);
    Customer getCustomer (Long discordId);

}
