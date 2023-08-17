package br.com.pessoas.controller;


import br.com.pessoas.model.Address;
import br.com.pessoas.model.Person;
import br.com.pessoas.service.AddressService;
import br.com.pessoas.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/people")
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PutMapping("/people/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    @GetMapping("/people/{id}")
    public Person searchPerson (@PathVariable Long id) {
        return personService.searchPerson(id);
    }

    @DeleteMapping("/people/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    @GetMapping("/people")
    public List<Person> listPerson () {
        return personService.listPerson();
    }

    @PostMapping("/address/{id}")
    public Address createAddress(@PathVariable Long id, @RequestBody Address address) {
        return addressService.saveAddress(id, address);
    }

    @PutMapping("/address/{id}/{idAddress}")
    public void setPrincipalAddress(@PathVariable Long id, @PathVariable Long idAddress) {
        addressService.setPrincipalAddress(id, idAddress);
    }

    @GetMapping("/address/{id}")
    public List<Address> listAdressByPerson(@PathVariable Long id) {
        return addressService.listAdressByPerson(id);
    }
}
