package shop.mshop.domain.item;

import lombok.Getter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
public class FoodItem extends Item{
    private LocalDateTime expiration;
    private String storage;
}
