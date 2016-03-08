package net.mv.forum.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mv.forum.category.dto.CategoryDto;
import net.mv.forum.category.service.CategoryService;
import net.mv.forum.forum.dto.CountDto;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryServiceImpl;
	
	@RequestMapping(value="/view",produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<CategoryDto>> viewAllCategories(){
		return new ResponseEntity<List<CategoryDto>>(categoryServiceImpl.findAllCategories(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/viewPaginated", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<CategoryDto>> viewPaginatedCategoriess(String sort, Integer page, Integer size){
		Pageable pageRequest = new PageRequest(page,size);
		
		return new ResponseEntity<Page<CategoryDto>>(categoryServiceImpl.findAllCategories(pageRequest), HttpStatus.OK);
	}
	
	@RequestMapping(value="/count", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CountDto> countCategorys(){
		return new ResponseEntity<CountDto>(new CountDto(categoryServiceImpl.findCategoryCount()),HttpStatus.OK);
	}
	

}
