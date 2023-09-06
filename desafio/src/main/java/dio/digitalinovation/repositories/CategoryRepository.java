package dio.digitalinovation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dio.digitalinovation.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
