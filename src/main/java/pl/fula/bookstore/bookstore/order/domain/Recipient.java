package pl.fula.bookstore.bookstore.order.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.fula.bookstore.bookstore.jpa.BaseEntity;

import javax.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
public class Recipient extends BaseEntity {
    private String email;
    private String name;
    private String phone;
    private String street;
    private String city;
    private String zipCode;

    public Recipient(String name, String phone, String street, String city, String zipCode, String email) {
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.email = email;
    }
}

class Address {
    String street;
    String city;
    String zipCode;
}
