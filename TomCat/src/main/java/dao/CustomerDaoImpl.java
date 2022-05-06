package dao;

import entity.Customer;
import org.example.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{
    Connection connection;
    public CustomerDaoImpl() {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void insert(Customer customer) {
        String sql ="INSERT INTO customer (id, username, password) VALUES (default, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getUsername());
            preparedStatement.setString(2, customer.getPassword());
            int count = preparedStatement.executeUpdate();
            if (count ==1) {
                System.out.println("new customer generated");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Customer get(int id) {
        String sql = "SELECT * FROM customer WHERE id =?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idData = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Customer customer = new Customer(idData, username, password);
                return customer;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Customer customer = new Customer(id, username, password);
                customers.add(customer);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        if (customers.size() > 0){
            System.out.println("Retrieved account!");
        }
        else {
            System.out.println("something went wrong.");
            System.exit(0);
        }
        return customers;
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customer SET username = ? WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,customer.getUsername());
            preparedStatement.setInt(2,customer.getId());
            int count = preparedStatement.executeUpdate();
            if (count==1) {
                System.out.println("update successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Customer customer) {
        String sql = "DELETE FROM customer WHERE id=?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,customer.getId());
            int count = preparedStatement.executeUpdate();
            if (count==1){
                System.out.println("delete successful");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
