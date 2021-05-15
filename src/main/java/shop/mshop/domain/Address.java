package shop.mshop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private Integer zipcode;

    protected Address() {
    }

    public Address(String city, String street, Integer zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
