package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter @Setter
@DiscriminatorValue("BOOK")
@Table(name = "book")
public class Book extends Item {
    private String writer;
}