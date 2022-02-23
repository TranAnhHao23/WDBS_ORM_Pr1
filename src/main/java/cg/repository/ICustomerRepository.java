package cg.repository;

import cg.model.Customer;

import java.util.ArrayList;

public interface ICustomerRepository {
    ArrayList<Customer> getAllCustomer();

    Customer saveCustomer(Customer customer);

    Customer deleteCustomer(int id);

    Customer getCustomer(int id);

    ArrayList<Customer> findByNameContaining(String name);
}
