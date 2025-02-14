package com.shop_style.customers_service.controllers;

import com.shop_style.customers_service.domain.dtos.CustomerDTO;
import com.shop_style.customers_service.domain.facade.CustomerFacade;
import com.shop_style.customers_service.domain.model.Customer;
import com.shop_style.customers_service.exceptions.ResourceNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@PropertySource("classpath:constants.properties")
@WebMvcTest(CustomersController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerFacade customersFacade;

    private final static String API_ENDPOINT = "/v1/customers";
    private final static int SUCCESSFUL_CUSTOMER_ID = 1;
    private final static int UNSUCCESSFUL_CUSTOMER_ID = 2;

    private final CustomerDTO DUMMY_CUSTOMER = new CustomerDTO(1,"122.123.451-11",
            "João",
            "Almeida",
            "Masculino",
            "2001-9-10",
            "joao@gmail.com",
            true,null);

    private final CustomerDTO DUMMY_CUSTOMER_UPDATES = new CustomerDTO(1,"122.123.451-11",
            "João",
            "Almeida",
            "Masculino",
            "2001-9-10",
            "joao_5467@gmail.com",
            true,null);

    @Value("${dummy.customer}")
    private String DUMMY_CUSTOMER_JSON;

    @Value("${dummy.customer.updates}")
    private String DUMMY_CUSTOMER_UPDATES_JSON;

    @Value(value = "${dummy.customer.invalid}")
    private String INVALID_DUMMY_CUSTOMER_JSON;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Successful GET Customer by Id on API.")
    public void getCustomerShouldReturnStatus200AndCustomer() throws Exception {
        when(customersFacade.getCustomer(SUCCESSFUL_CUSTOMER_ID)).thenReturn(DUMMY_CUSTOMER);

        mockMvc.perform(get(API_ENDPOINT + "/" + SUCCESSFUL_CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(DUMMY_CUSTOMER.getCpf()))
                .andExpect(jsonPath("$.firstName").value(DUMMY_CUSTOMER.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(DUMMY_CUSTOMER.getLastName()))
                .andExpect(jsonPath("$.gender").value(DUMMY_CUSTOMER.getGender()))
                .andExpect(jsonPath("$.birthdate").value(DUMMY_CUSTOMER.getBirthdate()))
                .andExpect(jsonPath("$.email").value(DUMMY_CUSTOMER.getEmail()))
                .andExpect(jsonPath("$.active").value(DUMMY_CUSTOMER.getActive().toString()))
                .andExpect(jsonPath("$.password").doesNotExist());

        verify(customersFacade).getCustomer(SUCCESSFUL_CUSTOMER_ID);
    }

    @Test
    @DisplayName("Unsuccessful GET Customer By Id on API.")
    public void getCustomerShouldReturn404AndErrorsResponse() throws Exception{

        when(customersFacade.getCustomer(UNSUCCESSFUL_CUSTOMER_ID)).thenThrow(new ResourceNotFound(Customer.class, 2));

        mockMvc.perform(get(API_ENDPOINT + "/" + UNSUCCESSFUL_CUSTOMER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.errors[0]").value("Customer with provided ID(2) does not exist."));

        verify(customersFacade).getCustomer(UNSUCCESSFUL_CUSTOMER_ID);
    }

    @Test
    @DisplayName("Successful GET List Of Customers.")
    public void getAllCustomersShouldReturnStatus200AndList() throws Exception {
        when(customersFacade.getAllCustomers()).thenReturn(new HashSet<>());
        mockMvc.perform(get(API_ENDPOINT)).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Successful POST Of New Customer.")
    public void postCustomerShouldReturnStatus201AndCreatedCustomer() throws Exception {
        when(customersFacade.createCustomer(any())).thenReturn(DUMMY_CUSTOMER);

        mockMvc.perform(post(API_ENDPOINT)
                .content(DUMMY_CUSTOMER_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value(DUMMY_CUSTOMER.getCpf()))
                .andExpect(jsonPath("$.firstName").value(DUMMY_CUSTOMER.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(DUMMY_CUSTOMER.getLastName()))
                .andExpect(jsonPath("$.gender").value(DUMMY_CUSTOMER.getGender()))
                .andExpect(jsonPath("$.birthdate").value(DUMMY_CUSTOMER.getBirthdate()))
                .andExpect(jsonPath("$.email").value(DUMMY_CUSTOMER.getEmail()))
                .andExpect(jsonPath("$.active").value(DUMMY_CUSTOMER.getActive().toString()))
                .andExpect(jsonPath("$.password").doesNotExist());

        verify(customersFacade).createCustomer(any());
    }

//    // When submitting invalid Customers, the API should return the BAD REQUEST status code(400)
//    @DisplayName("Unsuccessful POST of new Customer")
//    public void postCustomerShouldReturnStatus400AndErrorResponse() throws Exception {
//
//        mockMvc.perform(post(API_ENDPOINT).content(DUMMY_CUSTOMER_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
//                .andExpect(jsonPath("$.errors").isArray());
//    }

    @Test
    @DisplayName("Successful PUT of Customer.")
    public void putCustomerShouldReturnStatus200AndUpdatedCustomer() throws Exception {
        when(customersFacade.updateCustomer(eq(SUCCESSFUL_CUSTOMER_ID), any())).thenReturn(DUMMY_CUSTOMER_UPDATES);

        mockMvc.perform(put(API_ENDPOINT + "/" + SUCCESSFUL_CUSTOMER_ID)
                .content(DUMMY_CUSTOMER_UPDATES_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(DUMMY_CUSTOMER_UPDATES.getEmail()));

        verify(customersFacade).updateCustomer(eq(SUCCESSFUL_CUSTOMER_ID), any());
    }

    // If the request body is invalid, the API should return the validations results and BAD REQUEST status code(400).
//    @Test
//    @DisplayName("Unsuccessful PUT of Customer with invalid updates.")
//    public void putCustomerShouldReturnStatus400() throws Exception {
//        when(customersFacade.updateCustomer())
//
//        mockMvc.perform(put(API_ENDPOINT + "/" + SUCCESSFUL_CUSTOMER_ID)
//                .content(INVALID_DUMMY_CUSTOMER_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
//                .andExpect(jsonPath("$.errors").isArray());
//    }

    // If the customer with provided id doesn't exist, the API should return NOT FOUND status code(404) and message.
    @Test
    @DisplayName("Unsuccessful PUT of nonexistent customer.")
    public void putCustomerShouldReturnStatus400AndMessage() throws Exception {

        when(customersFacade.updateCustomer(eq(SUCCESSFUL_CUSTOMER_ID), any())).thenThrow(new ResourceNotFound(Customer.class, 2));

        mockMvc.perform(put(API_ENDPOINT + "/" + UNSUCCESSFUL_CUSTOMER_ID)
                .content(DUMMY_CUSTOMER_UPDATES_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.errors[0]").value("Customer with provided ID(2) does not exist."));

        verify(customersFacade).updateCustomer(eq(SUCCESSFUL_CUSTOMER_ID), any());
    }


    @Test
    @DisplayName("Successful update of customer's password.")
    public void passwordUpdateShouldReturnStatus204() throws Exception {
        String password = "randomPassword";
        mockMvc.perform(put(API_ENDPOINT + "/" + SUCCESSFUL_CUSTOMER_ID + "/password")
                .content(String.format("{senha : \"%s\"}", password)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customersFacade).updateCustomerPassword(eq(SUCCESSFUL_CUSTOMER_ID), any());
    }

    @Test
    @DisplayName("Unsuccessful update of password for nonexistent customers.")
    public void passwordUpdateShouldReturnStatus404() throws Exception {
        String password = "randomPassword";
        doThrow(new ResourceNotFound(Customer.class, UNSUCCESSFUL_CUSTOMER_ID))
                .when(customersFacade).updateCustomerPassword(eq(UNSUCCESSFUL_CUSTOMER_ID), any());

        mockMvc.perform(put(API_ENDPOINT + "/" + UNSUCCESSFUL_CUSTOMER_ID + "/password")
                .content(String.format("{senha : \"%s\"}", password)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.errors[0]").value("Customer with provided ID(2) does not exist."));

        verify(customersFacade).updateCustomerPassword(eq(UNSUCCESSFUL_CUSTOMER_ID), any());
    }

    // If the password is invalid, the API returns BAD REQUEST status code (400) and message with password validation.
//    @Test
//    @DisplayName("Unsuccessful update of password of Customer.")
//    public void shouldReturnStatus400() throws Exception {
//        mockMvc.perform(put(API_ENDPOINT + "/" + SUCCESSFUL_CUSTOMER_ID + "/password"))
//                .
//                .andExpect(status().isBadRequest());
//    }

    @Test
    @DisplayName("Successful DELETE of Customer.")
    public void shouldReturnStatus200AndRemovedCustomer() throws Exception {
        when(customersFacade.removeCustomer(SUCCESSFUL_CUSTOMER_ID)).thenReturn(DUMMY_CUSTOMER);
        mockMvc.perform(delete(API_ENDPOINT + "/" +  SUCCESSFUL_CUSTOMER_ID))
                .andExpect(status().isOk());

        verify(customersFacade).removeCustomer(SUCCESSFUL_CUSTOMER_ID);
    }

    @Test
    @DisplayName("Unsuccessful deletion of Customer.")
    public void shouldReturnStatusAndRemovedCustomer() throws Exception {
        when(customersFacade.removeCustomer(UNSUCCESSFUL_CUSTOMER_ID)).thenThrow(new ResourceNotFound(Customer.class, 2));

        mockMvc.perform(delete(API_ENDPOINT + "/" + UNSUCCESSFUL_CUSTOMER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.errors[0]").value("Customer with provided ID(2) does not exist."));
    }

}