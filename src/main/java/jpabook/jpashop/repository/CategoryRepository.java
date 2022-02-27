package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository{
    private final EntityManager em;

    public void save(Category category){
        if(category.getId() == null){
            em.persist(category);
        }else{
            em.merge(category);
        }
    }

    public Category findOne(Long id){
        return em.find(Category.class, id);
    }

    public List<Category> findByName(String name){
        return em.createQuery("select c from Category c where c.name = :name", Category.class)
                .setParameter("name", name)
                .getResultList();
    }

}
