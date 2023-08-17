package br.com.pessoas.controller;

import br.com.pessoas.controller.templates.AddressTemplate;
import br.com.pessoas.controller.templates.PersonTemplate;
import br.com.pessoas.model.Address;
import br.com.pessoas.model.Person;
import br.com.pessoas.service.AddressService;
import br.com.pessoas.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private AddressService addressService;

    private final PersonTemplate personTemplate =
            PersonTemplate.getInstance();

    private final AddressTemplate addressTemplate =
            AddressTemplate.getInstance();

    @Test
    public void testCreatePersonWithSuccess() throws Exception {
        Person person = personTemplate.getValid();
        Address address = addressTemplate.getValid();
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        person.setAddress(addressList);
        person.setId(null);

        ObjectMapper mapper = new ObjectMapper();
        String personJson = mapper.writeValueAsString(person);

        when(personService.savePerson(ArgumentMatchers.any(Person.class))).thenReturn(person);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/people")
                        .content(personJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        final String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, Matchers.notNullValue());
    }

    @Test
    public void testGetPersonsWithSuccess() throws Exception {
        Person person = personTemplate.getValid();

        List<Person> list = new ArrayList<>();
        list.add(person);


        when(personService.listPerson()).thenReturn(list);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/people/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        final String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, Matchers.notNullValue());
    }

    @Test
    public void testGetAllPersonWithSuccess() throws Exception {
        Person person = personTemplate.getValid();


        when(personService.searchPerson(any())).thenReturn(person);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/people/" + person.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        final String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, Matchers.notNullValue());
    }

    @Test
    public void testDeletePerson() throws Exception {
        Person person = personTemplate.getValid();

        doNothing().when(personService).deletePerson(person.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/people/" + person.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateAddressWithSuccess() throws Exception {
        Person person = personTemplate.getValid();
        Address address = addressTemplate.getValid();
        address.setPerson(person);


        ObjectMapper mapper = new ObjectMapper();
        String addressJson = mapper.writeValueAsString(address);

        when(addressService.saveAddress(any(), ArgumentMatchers.any(Address.class))).thenReturn(address);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/address/" + person.getId())
                        .content(addressJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        final String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, Matchers.notNullValue());
    }

    @Test
    public void testGetAddressWithSuccess() throws Exception {
        Person person = personTemplate.getValid();
        Address address = addressTemplate.getValid();
        address.setPerson(person);

        List<Address> list = new ArrayList<>();
        list.add(address);


        when(addressService.listAdressByPerson(any())).thenReturn(list);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/address/" + person.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        final String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, Matchers.notNullValue());
    }

    @Test
    public void testSetAddressIsPrincipal() throws Exception {
        Person person = personTemplate.getValid();
        Address address = addressTemplate.getValid();
        address.setId(1L);

        doNothing().when(addressService).setPrincipalAddress(person.getId(), address.getId());

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/address/" + person.getId() + "/" + address.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}