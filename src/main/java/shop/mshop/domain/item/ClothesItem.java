package shop.mshop.domain.item;

import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class ClothesItem extends Item{
    private String color;
    private String size;
}
