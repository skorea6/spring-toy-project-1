package shop.mshop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private Integer postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;

    protected Address() {
    }

    public Address(Integer postcode, String address, String detailAddress, String extraAddress) {
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.extraAddress = extraAddress;
    }
}
