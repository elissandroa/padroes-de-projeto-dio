package dio.digitalinovation.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dio.digitalinovation.dto.CategoryDTO;
import dio.digitalinovation.entities.Category;
import dio.digitalinovation.repositories.CategoryRepository;
import dio.digitalinovation.services.exceptions.DatabaseException;
import dio.digitalinovation.services.exceptions.ResourseNotFoundException;
import jakarta.persistence.EntityNotFoundException;

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
		Category entity = obj.orElseThrow(() -> new ResourseNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
		
	}

	@Transactional
	public CategoryDTO saveCategory(CategoryDTO dto) {
		Category newCategory = new Category();
		newCategory.setName(dto.getName());
		newCategory = repository.save(newCategory);
		return new CategoryDTO(newCategory);
	}
	
	@Transactional
	public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
		try {
			Category categ = repository.getReferenceById(id);
			categ.setName(dto.getName());
			categ = repository.save(categ);
			return new CategoryDTO(categ);
		} catch(EntityNotFoundException e) {
			throw new ResourseNotFoundException("id not found"+ id);
		}
	}
	
	public void deleteCategory(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourseNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
