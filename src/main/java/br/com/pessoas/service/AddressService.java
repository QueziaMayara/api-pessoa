package br.com.pessoas.service;

import br.com.pessoas.model.Address;
import br.com.pessoas.model.Person;
import br.com.pessoas.repository.AddressRepository;
import br.com.pessoas.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private PersonRepository personRepository;

    public Address saveAddress(Long id, Address address){
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person does not exist"));
        address.setPerson(person);
        return repository.save(address);
    }

    public void setPrincipalAddress(Long id, Long idAddress) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person does not exist"));
        Address address = repository.findById(idAddress).orElseThrow(() -> new RuntimeException("Address does not exist"));
        person.getAddress().forEach(e -> e.setPrincipal(false));
        address.setPrincipal(true);
        personRepository.save(person);
    }

    public List<Address> listAdressByPerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person does not exist"));
        return person.getAddress();
    }
}
