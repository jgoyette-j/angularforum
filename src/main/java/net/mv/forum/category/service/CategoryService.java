package net.mv.forum.category.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.mv.forum.category.dto.CategoryDto;

public interface CategoryService {
	
	public List<CategoryDto> findAllCategories();
	public Page<CategoryDto> findAllCategories(Pageable page);
	public Long findCategoryCount();

}
