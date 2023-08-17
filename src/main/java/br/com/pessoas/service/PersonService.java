package br.com.pessoas.service;

import br.com.pessoas.model.Person;
import br.com.pessoas.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public Person savePerson(Person person) {
        try {
            return repository.save(person);
        } catch (Exception e) {
            throw new RuntimeException("Person invalid");
        }
    }

    public Person updatePerson(Long id, Person person) {
        Person existingPerson = repository.findById(id).orElseThrow(() -> new RuntimeException("Person does not exist"));
        existingPerson.setName(person.getName());
        existingPerson.setBirthDate(person.getBirthDate());
        return repository.save(existingPerson);
    }

    public void deletePerson(Long id) {
        repository.deleteById(id);
    }

    public Person searchPerson(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Person does not exist"));
    }

    public List<Person> listPerson() {
        return repository.findAll();
    }
}
