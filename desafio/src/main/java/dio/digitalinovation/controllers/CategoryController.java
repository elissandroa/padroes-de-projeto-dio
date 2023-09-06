package dio.digitalinovation.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dio.digitalinovation.entities.Category;
import dio.digitalinovation.services.CategoryService;

@RestController
@RequestMapping(value="/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping()
	public ResponseEntity<List<Category>> findAll(){
		List<Category> categories = categoryService.findAll();
		return ResponseEntity.ok().body(categories);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id){
		Category category = categoryService.findById(id);
		return ResponseEntity.ok().body(category);
	}
	
	@PostMapping
	public ResponseEntity<Category> save(@RequestBody Category category) {
		Category newCategory = categoryService.saveCategory(category);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCategory.getId()).toUri();
		return ResponseEntity.created(uri).body(newCategory);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Category> update(@RequestBody Category category, @PathVariable Long id) {
		Category newCategory = categoryService.updateCategory(id, category);
		return ResponseEntity.ok().body(newCategory);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Category> delete(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}
}
