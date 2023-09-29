/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanagerdemo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Address;
import model.Customer;
import model.CustomerTable;

/**
 *
 * @author sarun
 */
public class EntityManagerDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        createData(1L, "Antony", "Balla", "tballa@mail.com" ,1L, "Ritherdon Rd", "Pathumthani", "30500", "TH");
        createData(2L, "John", "Henry", "tballa@mail.com" ,2L, "123/4 Rd", "Bangkok", "10900", "TH");
        createData(3L, "Marry", "Jane", "la@mail.com" ,3L, "123/5 Rd", "Bangkok", "10900", "TH");
        createData(4L, "Peter", "Parker", "pp@mail.com" ,4L, "Rin Rd", "Nonthaburi", "20900", "TH");
    
          
        printAllCustomer();
          
        printCustomerByCity("Bangkok");
          
          
    }

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static void createData(Long id, String firstname, String lastname, String email, Long idAddress, String street, String city, String zipcode, String country){
        Customer customer = new Customer(id, firstname, lastname, email); 
        Address address = new Address(idAddress, street, city , zipcode, country); 
        address.setCustomerFk(customer);
        customer.setAddressId(address); 
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        
         em.getTransaction().begin();
        try {
            em.persist(address);
            em.persist(customer);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    
    public static void printAllCustomer(){
        List<Customer> customerList = CustomerTable.findAllCustomer();
        customerList.forEach(cus -> {
            System.out.println("First Name: " + cus.getFirstname());
            System.out.println("Last Name: " + cus.getLastname());
            System.out.println("Email: " + cus.getEmail());
            System.out.println("Street: " + cus.getAddressId().getStreet());
            System.out.println("City: " + cus.getAddressId().getCity());
            System.out.println("Country: " + cus.getAddressId().getCountry());
            System.out.println("ZipCode: " + cus.getAddressId().getZipcode());
            System.out.println("---------------------------");
            System.out.println("---------------------------");
        });
    }
    
    public static void printCustomerByCity(String city){
        List<Customer> customerList = CustomerTable.findAllCustomer();
        for (Customer customer : customerList) {
            if(customer.getAddressId().getCity().equals(city)){
                    System.out.println("First Name: " + customer.getFirstname());
                    System.out.println("Last Name: " + customer.getLastname());
                    System.out.println("Email: " + customer.getEmail());
                    System.out.println("Street: " + customer.getAddressId().getStreet());
                    System.out.println("City: " + customer.getAddressId().getCity());
                    System.out.println("Country: " + customer.getAddressId().getCountry());
                    System.out.println("ZipCode: " + customer.getAddressId().getZipcode());
                    System.out.println("---------------------------");
                    System.out.println("---------------------------");
            }
        }
        
    }
}
