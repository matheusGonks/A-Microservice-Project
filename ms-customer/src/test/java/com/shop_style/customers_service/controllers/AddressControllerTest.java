package com.shop_style.customers_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop_style.customers_service.DummyAddressBuilder;
import com.shop_style.customers_service.domain.dtos.AddressDTO;
import com.shop_style.customers_service.domain.facade.CustomerFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerFacade customersFacade;

    DummyAddressBuilder dummyAddressBuilder = new DummyAddressBuilder();

    AddressDTO addressDTOForTest;

    String addressJsonForTransfer;

    @BeforeEach
    void setup() throws JsonProcessingException {
        addressDTOForTest = dummyAddressBuilder.buildDto();
        addressJsonForTransfer = new ObjectMapper().writeValueAsString(addressDTOForTest);
    }

    @Test
    @DisplayName("Address Controller - successful POST request of address.")
    public void addressControllerShouldAcceptPostRequestAndReturnAddressDto() throws Exception {

        when(customersFacade.createAddress(any())).thenReturn(addressDTOForTest);

        mockMvc.perform(
                post("/v1/addresses")
                        .content(addressJsonForTransfer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(addressDTOForTest.getId()))
                .andExpect(jsonPath("$.state").value(addressDTOForTest.getState()))
                .andExpect(jsonPath("$.district").value(addressDTOForTest.getDistrict()))
                .andExpect(jsonPath("$.street").value(addressDTOForTest.getStreet()))
                .andExpect(jsonPath("$.number").value(addressDTOForTest.getNumber()))
                .andExpect(jsonPath("$.zipcode").value(addressDTOForTest.getZipcode()))
                .andExpect(jsonPath("$.complement").value(addressDTOForTest.getComplement()));

    }

    @Test
    @DisplayName("Address Controller - successful PUT request of address.")
    public void addressControllerShouldUpdateAndReturnDto() throws Exception {
        when(customersFacade.updateAddress(eq(1),any())).thenReturn(addressDTOForTest);

        mockMvc.perform(
                put("/v1/addresses/1")
                        .content(addressJsonForTransfer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addressDTOForTest.getId()))
                .andExpect(jsonPath("$.state").value(addressDTOForTest.getState()))
                .andExpect(jsonPath("$.district").value(addressDTOForTest.getDistrict()))
                .andExpect(jsonPath("$.street").value(addressDTOForTest.getStreet()))
                .andExpect(jsonPath("$.number").value(addressDTOForTest.getNumber()))
                .andExpect(jsonPath("$.zipcode").value(addressDTOForTest.getZipcode()))
                .andExpect(jsonPath("$.complement").value(addressDTOForTest.getComplement()));
    }

    @Test
    @DisplayName("Address Controller - successful DELETE request of address.")
    public void addressControllerShouldReturnDtoOfRemovedAddress() throws Exception {
        when(customersFacade.removeAddress(1)).thenReturn(addressDTOForTest);

        mockMvc.perform(
                delete("/v1/addresses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addressDTOForTest.getId()))
                .andExpect(jsonPath("$.state").value(addressDTOForTest.getState()))
                .andExpect(jsonPath("$.district").value(addressDTOForTest.getDistrict()))
                .andExpect(jsonPath("$.street").value(addressDTOForTest.getStreet()))
                .andExpect(jsonPath("$.number").value(addressDTOForTest.getNumber()))
                .andExpect(jsonPath("$.zipcode").value(addressDTOForTest.getZipcode()))
                .andExpect(jsonPath("$.complement").value(addressDTOForTest.getComplement()));
    }

}