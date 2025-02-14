package com.shop_style.customers_service.domain.services;

import com.shop_style.customers_service.domain.model.Customer;
import com.shop_style.customers_service.domain.repositories.CustomerRepository;
import com.shop_style.customers_service.exceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(int id){
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFound(Customer.class, id));
    }

    public Set<Customer> getAllCustomers(){
        return new HashSet<>(customerRepository.findAll());
    }

    public Customer createCustomer(Customer newCustomer){
        return customerRepository.save(newCustomer);
    }

    public Customer updateCustomer(int id, Customer customerUpdates){
        Customer customer = getCustomerAndUpdateAttributes(id, customerUpdates);
        return customerRepository.save(customer);
    }

    public Customer removeCustomer(int id){
        Customer customerToBeRemoved = getCustomer(id);
        customerRepository.delete(customerToBeRemoved);

        return customerToBeRemoved;
    }

    private Customer getCustomerAndUpdateAttributes(int id, Customer customerWithUpdates){
        Customer customerToBeUpdated = getCustomer(id);

        customerToBeUpdated.setCpf(customerWithUpdates.getCpf());
        customerToBeUpdated.setFirstName(customerWithUpdates.getFirstName());
        customerToBeUpdated.setLastName(customerWithUpdates.getLastName());
        customerToBeUpdated.setGender(customerWithUpdates.getGender());
        customerToBeUpdated.setBirthdate(customerWithUpdates.getBirthdate());
        customerToBeUpdated.setEmail(customerWithUpdates.getEmail());
        customerToBeUpdated.setActive(customerWithUpdates.isActive());

        return customerToBeUpdated;
    }

    // Atualização da senha -> A senha tem que ser hasheada com algum método de encriptação
    // a partir disso ou utilizamos o mesmo metodo para qualquer atualizacao ou utilizamos um metodo especifico
    // para atualizacao da senha


}
