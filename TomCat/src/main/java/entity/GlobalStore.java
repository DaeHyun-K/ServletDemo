package entity;

import java.util.ArrayList;
import java.util.List;

public class GlobalStore {
    private static Customer cust;
    public static List<Customer> custList = new ArrayList<>();

    public static Customer getCust() {
        return cust;
    }

    public static void setCust(Customer cust) {
        GlobalStore.cust = cust;
    }

    public List<Customer> getCustList() {
        return custList;
    }

    public void setCustList(List<Customer> custList) {
        this.custList = custList;
    }
}
