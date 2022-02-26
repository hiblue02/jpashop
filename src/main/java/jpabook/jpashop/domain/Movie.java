package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "movie")
@Getter @Setter
@DiscriminatorValue("MOVIE")
public class Movie extends Item{

}