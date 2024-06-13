package saad.projet.jo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saad.projet.jo.model.Category;
import saad.projet.jo.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository){
        this.repository = repository;
    }


    public List<Category> findAllCategory () {
        System.out.println("Toutes les category");
        return repository.findAll();
    }

    public Page<Category> findAllCategoryPaginated (int page, int size) {
        System.out.println("Toutes les category");
        Pageable paging = PageRequest.of(page, size);
        return repository.findAll(paging);
    }

    public int getTotalPage(int size) {
        long totalItems = repository.count();
        return (int) Math.ceil((double) totalItems / size);
    }

    public Category findCategoryById(String uuid) {
        return repository.findOneByUuid(uuid).orElse(null);
    }

    public Category createCategory (Category category){
        System.out.println("Categorie créer");
        return repository.save(category);
    }


    @Transactional
    public Boolean update(String uuid, Category category) {

        Category categoryAModifier = findCategoryById(uuid);
        if(categoryAModifier != null) {
            categoryAModifier.setName(category.getName());
            categoryAModifier.setGender(category.getGender());
            categoryAModifier.setType(category.getType());
            repository.save(categoryAModifier);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteCategory(String id) {
        System.out.println("Catégorie supprimée");
        Optional<Category> categoryASupprimer = repository.findById(id);
        if(categoryASupprimer != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
