package dao;

import entity.Customer;

import java.util.List;

public interface CustomerDao {
    public void insert(Customer customer);
    public Customer get(int id);
    public List<Customer> getAll();
    public void update(Customer customer);
    public void delete(Customer customer);
}
