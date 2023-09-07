package dio.digitalinovation.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dio.digitalinovation.dto.CategoryDTO;
import dio.digitalinovation.entities.Category;
import dio.digitalinovation.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	
	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.get();
		return new CategoryDTO(entity);
		
	}

	@Transactional(readOnly = true)
	public CategoryDTO saveCategory(CategoryDTO dto) {
		Category newCategory = new Category();
		newCategory.setName(dto.getName());
		newCategory = repository.save(newCategory);
		return new CategoryDTO(newCategory);
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
		Category categ = repository.getReferenceById(id);
		categ.setName(dto.getName());
		categ = repository.save(categ);
		return new CategoryDTO(categ);
	}
	
	public void deleteCategory(Long id) {
		repository.deleteById(id);
	}
}
