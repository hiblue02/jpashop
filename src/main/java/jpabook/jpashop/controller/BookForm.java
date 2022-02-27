package jpabook.jpashop.controller;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String writer;


}
