package dio.digitalinovation.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dio.digitalinovation.dto.CategoryDTO;
import dio.digitalinovation.dto.ProductDTO;
import dio.digitalinovation.entities.Category;
import dio.digitalinovation.entities.Product;
import dio.digitalinovation.repositories.CategoryRepository;
import dio.digitalinovation.repositories.ProductRepository;
import dio.digitalinovation.services.exceptions.DatabaseException;
import dio.digitalinovation.services.exceptions.ResourseNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAll();
		return list.stream().map(x -> new ProductDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourseNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
		
	}

	@Transactional
	public ProductDTO saveProduct(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);

	}
	
	@Transactional
	public ProductDTO updateProduct(Long id, ProductDTO dto) {
		try {	
			Product entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourseNotFoundException("entity not found" + id);
		}
	}
	

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.getCategories().clear();
			
		for (CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getReferenceById(catDto.getId());
			entity.getCategories().add(category);
		}
	}

	
	
	public void deleteProduct(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourseNotFoundException("entity not found" + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
