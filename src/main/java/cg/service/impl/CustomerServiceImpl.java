package cg.service.impl;

import cg.model.Customer;
import cg.repository.ICustomerRepository;
import cg.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public ArrayList<Customer> getAllCustomer() {
        return customerRepository.getAllCustomer();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.saveCustomer(customer);
    }

    @Override
    public Customer deleteCustomer(int id) {
        return customerRepository.deleteCustomer(id);
    }

    @Override
    public Customer getCustomer(int id) {
        return customerRepository.getCustomer(id);
    }

    @Override
    public ArrayList<Customer> findByNameContaining(String name) {
        return customerRepository.findByNameContaining(name);
    }
}
