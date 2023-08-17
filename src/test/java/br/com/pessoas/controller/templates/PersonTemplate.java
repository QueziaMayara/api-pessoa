package br.com.pessoas.controller.templates;

import br.com.pessoas.model.Person;
import lombok.Getter;

import java.time.LocalDate;

public class PersonTemplate {

    @Getter
    private static final PersonTemplate instance = new PersonTemplate();

    public Person getValid() {
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setBirthDate(LocalDate.of(2000, 1, 1));
        return person;
    }
}