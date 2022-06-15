package com.sample.billsexchange.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.billsexchange.BillsExchangeApplication;
import com.sample.billsexchange.model.Coin;
import com.sample.billsexchange.model.ErrorResponse;
import com.sample.billsexchange.service.ChangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ChangeControllerTest {

    @Autowired
    private ChangeController subject;

    @Autowired
    private ChangeService changeService;

    private MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    private final String URL_PREFIX = "/get-change/bills";
    private final String ERROR_MSG_NO_CHANGE = "Change not available for this bill !";
    private final String ERROR_MSG_INVALID_BILL = "Invalid bill amount";
    @BeforeEach
    void setUp() {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getChange_returns200_and_25cent_coins() throws Exception {
        String uri = URL_PREFIX + "/10";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<Coin> coins = mapper.readValue(content, new TypeReference<List<Coin>>(){});
        assertEquals(coins.size(), 1);
        assertEquals(coins.get(0).getValue(), 0.25);
        assertEquals(coins.get(0).getCount(), 40);
    }

    @Test
    void getChange_returns400_not_enough_coins() throws Exception {
        String uri = URL_PREFIX + "/50";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse response = mapper.readValue(content, ErrorResponse.class);
        assertEquals(response.getErrorMessage(), ERROR_MSG_NO_CHANGE);
    }

    @Test
    void getChange_returns400_invalid_bill() throws Exception {
        String uri = URL_PREFIX + "/18";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse response = mapper.readValue(content, ErrorResponse.class);
        assertEquals(response.getErrorMessage(), ERROR_MSG_INVALID_BILL);
    }

}