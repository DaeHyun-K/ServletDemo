package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CustomerDao;
import dao.CustomerDaoImpl;
import entity.Customer;
import entity.GlobalStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomerServlet extends HttpServlet {
    CustomerDao customerDao = new CustomerDaoImpl();
    GlobalStore gs = new GlobalStore();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Customer> customers = customerDao.getAll();
           gs.setCustList(customers);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(customers); // map the output to json
            resp.setStatus(202);
            resp.getWriter().print(json);
            List<Customer> custs = gs.getCustList();
            for(int i=0; i<custs.size(); i++) {
                System.out.println(custs.get(i).toString());
            }
        }
        catch (IOException e) {
            resp.setStatus(500);
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // information getting from the request is being mapped to a Customer object. (input, format)
            Customer payload = mapper.readValue(req.getInputStream(), Customer.class);

            //then the input is being run in the Dao -> database.
            customerDao.insert(payload);

            resp.setStatus(203);
            resp.getWriter().print("New Customer added");
        } catch(IOException e) {
            resp.setStatus(500);
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Customer payload = mapper.readValue(req.getInputStream(), Customer.class);
            customerDao.update(payload);
            resp.setStatus(203);
            resp.getWriter().print("Record updated");

        }catch (IOException e){
            resp.setStatus(500);
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String str = req.getQueryString(); -- this is the input in the domain
//        System.out.println(str.split("=")[1]);

        // int id = mapper.readValue(req.getInputStream(), Integer.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Customer payload = mapper.readValue(req.getInputStream(), Customer.class);
            customerDao.delete(payload);
            resp.setStatus(202);
            resp.getWriter().print("customer deleted");
        } catch (IOException e) {
            resp.setStatus(500);
            System.out.println(e.getLocalizedMessage());
        }
    }
}