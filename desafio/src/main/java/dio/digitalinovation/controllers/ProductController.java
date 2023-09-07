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

import dio.digitalinovation.dto.ProductDTO;
import dio.digitalinovation.services.ProductService;

@RestController
@RequestMapping(value="/products")
public class ProductController {

	@Autowired
	private ProductService ProductService;
	
	@GetMapping()
	public ResponseEntity<List<ProductDTO>> findAll(){
		List<ProductDTO> products = ProductService.findAll();
		return ResponseEntity.ok().body(products);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
		ProductDTO Product = ProductService.findById(id);
		return ResponseEntity.ok().body(Product);
	}
	
	@PostMapping
	public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO dto) {
		dto = ProductService.saveProduct(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO dto, @PathVariable Long id) {
		ProductDTO newProduct = ProductService.updateProduct(id, dto);
		return ResponseEntity.ok().body(newProduct);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ProductDTO> delete(@PathVariable Long id) {
		ProductService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}
