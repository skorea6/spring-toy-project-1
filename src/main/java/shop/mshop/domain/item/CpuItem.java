package shop.mshop.domain.item;

import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class CpuItem extends Item {
    private String socket;
    private Integer core;
}
