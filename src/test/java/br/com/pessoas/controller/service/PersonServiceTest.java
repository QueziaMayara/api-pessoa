package br.com.pessoas.controller.service;


import br.com.pessoas.controller.templates.AddressTemplate;
import br.com.pessoas.controller.templates.PersonTemplate;
import br.com.pessoas.model.Address;
import br.com.pessoas.model.Person;
import br.com.pessoas.repository.PersonRepository;
import br.com.pessoas.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonService.class)
@AutoConfigureMockMvc
public class PersonServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    private final PersonTemplate personTemplate =
            PersonTemplate.getInstance();

    private final AddressTemplate addressTemplate =
            AddressTemplate.getInstance();

    @Test
    public void testSavePerson_Success() {
        Person person = personTemplate.getValid();
        Address address = addressTemplate.getValid();
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        person.setAddress(addressList);

        when(personRepository.save(person)).thenReturn(person);

        Person savedPerson = personService.savePerson(person);

        assertNotNull(savedPerson);
        assertEquals("John Doe", savedPerson.getName());
        assertEquals(LocalDate.of(2000, 1, 1), savedPerson.getBirthDate());

        verify(personRepository, times(1)).save(person);
    }

    @Test(expected = RuntimeException.class)
    public void testSavePerson_Failure() {
        Person person = personTemplate.getValid();

        when(personRepository.save(person)).thenThrow(DataIntegrityViolationException.class);

        personService.savePerson(person);
    }

    @Test
    public void testUpdatePerson_Success() {
        Person person = personTemplate.getValid();
        Address address = addressTemplate.getValid();
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        person.setAddress(addressList);

        when(personRepository.findById(person.getId())).thenReturn(java.util.Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);

        person.setName("New Name");
        Person update = personService.updatePerson(person.getId(), person);

        assertNotNull(update);
        assertEquals("New Name", update.getName());
        assertEquals(LocalDate.of(2000, 1, 1), update.getBirthDate());

        verify(personRepository, times(1)).save(person);
    }

    @Test(expected = RuntimeException.class)
    public void testUpdatePerson_Failure() {
        Person person = personTemplate.getValid();

        when(personRepository.findById(person.getId())).thenThrow(DataIntegrityViolationException.class);

        personService.updatePerson(person.getId(), person);
    }

    @Test
    public void testDeletePerson_Success() {
        Person person = personTemplate.getValid();

        doNothing().when(personRepository).deleteById(person.getId());
        personService.deletePerson(person.getId());

        verify(personRepository, times(1)).deleteById(person.getId());
    }

    @Test
    public void testSearchPerson_Success() {
        Person person = personTemplate.getValid();

        when(personRepository.findById(person.getId())).thenReturn(java.util.Optional.of(person));

        Person result = personService.searchPerson(person.getId());

        assertNotNull(result);
        assertEquals(person, result);

        verify(personRepository, times(1)).findById(person.getId());
    }

    @Test(expected = RuntimeException.class)
    public void testSearchPerson_Failure() {
        Person person = personTemplate.getValid();

        when(personRepository.findById(person.getId())).thenThrow(DataIntegrityViolationException.class);

        personService.searchPerson(person.getId());
    }

    @Test
    public void testSearchAllPerson_Success() {
        Person person = personTemplate.getValid();
        List<Person> personList = new ArrayList<>();
        personList.add(person);

        when(personRepository.findAll()).thenReturn(personList);

        List<Person> result = personService.listPerson();

        assertNotNull(result);
        assertEquals(personList, result);

        verify(personRepository, times(1)).findAll();
    }
}
