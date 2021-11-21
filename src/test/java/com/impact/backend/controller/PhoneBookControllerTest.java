package com.impact.backend.controller;

import com.google.gson.Gson;
import com.impact.backend.entity.PhoneDetails;
import com.impact.backend.repository.PhoneDetailsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PhoneBookControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    PhoneBookController phoneBookController;

    @Mock
    PhoneDetailsRepository phoneDetailsRepository;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(phoneBookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(final String arg0, final Locale arg1) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                }).build();
    }

    @Test
    public void retrieveAllPhoneDetails() throws Exception {

        List<PhoneDetails> phoneList = new ArrayList<>();
        PhoneDetails phoneDetails = new PhoneDetails();
        phoneDetails.setName("Testing");
        phoneList.add(phoneDetails);
        Mockito.when(phoneDetailsRepository.findAll()).thenReturn(phoneList);

        mockMvc.perform(get(new URI("/phonedetails/all")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Testing")));
    }

    @Test
    public void retrievePhoneDetails() throws Exception {

        long pk = 10l;
        PhoneDetails phoneDetails = new PhoneDetails();
        phoneDetails.setName("Testing");
        phoneDetails.setPk(pk);
        Mockito.when(phoneDetailsRepository.findById(pk)).thenReturn(Optional.of(phoneDetails));

        mockMvc.perform(get(new URI("/phonedetails/" + pk)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Testing")))
                .andExpect(content().string(containsString("10")));
    }

    @Test
    public void deletePhoneDetails() throws Exception {

        long pk = 10l;

        mockMvc.perform(delete(new URI("/phonedetails/" + pk)))
                .andExpect(status().isOk());
    }

    @Test
    public void createPhoneDetails() throws Exception {

        PhoneDetails input = new PhoneDetails();
        input.setName("Testing");
        input.setEmail("Testing");
        input.setNumber("Testing");

        long pk = 10l;
        PhoneDetails output = new PhoneDetails();
        output.setName("Testing");
        output.setEmail("Testing");
        output.setNumber("Testing");
        output.setPk(pk);
        Mockito.when(phoneDetailsRepository.save(Matchers.any())).thenReturn(output);

        mockMvc.perform(post(new URI("/phonedetails"))
                        .content(new Gson().toJson(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().stringValues("Location", "http://localhost/phonedetails/10"));
    }

    @Test
    public void updatePhoneDetails() throws Exception {

        long pk = 10l;
        PhoneDetails old = new PhoneDetails();
        old.setName("Testing");
        old.setEmail("Testing");
        old.setNumber("Testing");
        old.setPk(pk);

        PhoneDetails newData = new PhoneDetails();
        newData.setName("Changed");
        newData.setEmail("Changed");
        newData.setNumber("Changed");

        Mockito.when(phoneDetailsRepository.findById(pk)).thenReturn(Optional.of(old));
        Mockito.when(phoneDetailsRepository.save(Matchers.any())).thenReturn(newData);

        mockMvc.perform(put(new URI("/phonedetails/" + pk))
                        .content(new Gson().toJson(old))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Changed")));
    }
}