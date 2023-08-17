package br.com.pessoas.controller.service;

import br.com.pessoas.controller.templates.AddressTemplate;
import br.com.pessoas.controller.templates.PersonTemplate;
import br.com.pessoas.model.Address;
import br.com.pessoas.model.Person;
import br.com.pessoas.repository.AddressRepository;
import br.com.pessoas.repository.PersonRepository;
import br.com.pessoas.service.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressService.class)
@AutoConfigureMockMvc
public class AddressServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PersonRepository personRepository;

    private final PersonTemplate personTemplate = PersonTemplate.getInstance();
    private final AddressTemplate addressTemplate = AddressTemplate.getInstance();

    @Test
    public void testSaveAddress_Success() {
        Person person = personTemplate.getValid();
        Address address = addressTemplate.getValid();
        Long personId = person.getId();

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(addressRepository.save(address)).thenReturn(address);

        Address savedAddress = addressService.saveAddress(personId, address);

        assertNotNull(savedAddress);
        assertEquals("rua", savedAddress.getPublicPlace());
        assertEquals("13000-000", savedAddress.getCep());

        verify(personRepository, times(1)).findById(personId);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    public void testSetPrincipalAddress_Success() {
        Person person = personTemplate.getValid();
        Address address = addressTemplate.getValid();
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        person.setAddress(addressList);

        Long personId = person.getId();
        Long addressId = address.getId();

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        addressService.setPrincipalAddress(personId, addressId);

        assertTrue(address.isPrincipal());

        verify(personRepository, times(1)).findById(personId);
        verify(addressRepository, times(1)).findById(addressId);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void testListAddressByPerson_Success() {
        Person person = personTemplate.getValid();
        Address address = addressTemplate.getValid();
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        person.setAddress(addressList);

        Long personId = person.getId();

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        List<Address> addresses = addressService.listAdressByPerson(personId);

        assertNotNull(addresses);
        assertEquals(person.getAddress(), addresses);

        verify(personRepository, times(1)).findById(personId);
    }
}
