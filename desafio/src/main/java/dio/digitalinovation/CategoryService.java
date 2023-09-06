package dio.digitalinovation;

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
	
	public Category findById(Long id) {
		Optional<Category> category = repository.findById(id);
		return category.get();
	}
	
}
