package net.mv.forum.category.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.mv.forum.category.domain.Category;
import net.mv.forum.category.dto.CategoryDto;
import net.mv.forum.category.repository.CategoryRepository;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> findAllCategories() {

		List<CategoryDto> catDtos = new ArrayList<CategoryDto>();

		List<Category> categories = categoryRepository.findAll();

		if (categories != null && !categories.isEmpty()) {
			for (Category cat : categories) {
				catDtos.add(new CategoryDto(cat));
			}
		}

		return catDtos;
	}

	@Override
	public Page<CategoryDto> findAllCategories(Pageable page) {

		Page<Category> pagedCats = categoryRepository.findAll(page);

		List<CategoryDto> catDtos = new ArrayList<>();

		if (pagedCats != null && !(pagedCats.getTotalElements() == 0)) {
			for (Category cat : pagedCats) {
				catDtos.add(new CategoryDto(cat));
			}
		}

		return new PageImpl<CategoryDto>(catDtos);
	}

	@Override
	public Long findCategoryCount() {
		return categoryRepository.count();
	}

}
