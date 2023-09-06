package dio.digitalinovation.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dio.digitalinovation.entities.Category;
import dio.digitalinovation.repositories.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	
	
	@Autowired
	private CategoryRepository repository;

	@Transactional
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	@Transactional
	public Category findById(Long id) {
		Optional<Category> category = repository.findById(id);
		return category.get();
	}

	@Transactional
	public Category saveCategory(Category category) {
		Category newCategory = new Category();
		newCategory.setName(category.getName());
		category = repository.save(newCategory);
		return category;
	}
	
	@Transactional
	public Category updateCategory(Long id, Category category) {
		Category categ = repository.getReferenceById(id);
		categ.setName(category.getName());
		categ = repository.save(categ);
		return categ;
	}
	
	public void deleteCategory(Long id) {
		repository.deleteById(id);
	}
}
