package net.mv.forum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.mv.forum.category.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	

}
