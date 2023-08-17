package br.com.pessoas.controller.templates;

import br.com.pessoas.model.Address;
import lombok.Getter;

public class AddressTemplate {

    @Getter
    private static final AddressTemplate instance = new AddressTemplate();

    public Address getValid() {
        Address address = new Address();
        address.setPrincipal(true);
        address.setCep("13000-000");
        address.setPublicPlace("rua");
        address.setCity("SP");
        address.setNumber("00");
        return address;
    }
}