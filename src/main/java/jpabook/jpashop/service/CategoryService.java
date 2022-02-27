package jpabook.jpashop.service;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void save(Category category){
        validateDuplicate(category);
        categoryRepository.save(category);
    }

    private void validateDuplicate(Category category) {
        List<Category> byName = categoryRepository.findByName(category.getName());
        if(! byName.isEmpty()){
            throw new IllegalStateException("이미 존재하는 카테고리입니다.");
        }
    }

    public Category findOne(Long id){
        return categoryRepository.findOne(id);
    }


}
