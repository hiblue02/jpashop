package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "/items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = Book.builder().name(form.getName())
                .writer(form.getWriter())
                .price(form.getPrice())
                .stockQuantity(form.getStockQuantity())
                .build();
        itemService.saveItem(book);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        model.addAttribute("items", itemService.findItems());
        return "/items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = BookForm.builder().id(item.getId())
                .price(item.getPrice())
                .writer(item.getWriter())
                .name(item.getName())
                .stockQuantity(item.getStockQuantity())
                .build();
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form ){

        itemService.updateBook(itemId, form.getPrice(), form.getStockQuantity(), form.getName());
        return "redirect:/items";
    }
}
