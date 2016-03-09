package net.mv.forum.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mv.forum.forum.dto.CountDto;
import net.mv.forum.forum.dto.ForumDto;
import net.mv.forum.forum.service.ForumService;
import net.mv.forum.post.service.PostService;

@Controller
@RequestMapping("/forum")
public class ForumController {
	
	@Autowired
	private ForumService forumServiceImpl;
	
	@Autowired
	private PostService postServiceImpl;
	
	@RequestMapping(value="/add", produces={MediaType.APPLICATION_JSON_VALUE}
					,consumes={MediaType.APPLICATION_JSON_VALUE})	
	public ResponseEntity<ForumDto> addNewForum(@RequestBody ForumDto forumDto){
		
		forumDto = forumServiceImpl.addForum(forumDto);
		
		forumDto.getPosts().get(0).setForumId(forumDto.getId());
		
		/*
		 * Magic I guess. Saves objects now.
		 */
		postServiceImpl.addPost(forumDto.getPosts().get(0));
		System.out.println("Before Call");
		
		ForumDto newDto = forumServiceImpl.findForumById(forumDto.getId());
		
		System.out.println("After Call");
		
		return new ResponseEntity<ForumDto>(newDto, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/view",produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<ForumDto>> viewAllForums(){
		return new ResponseEntity<List<ForumDto>>(forumServiceImpl.findAllForums(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/viewPaginated", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<ForumDto>> viewPaginatedForums(String sort, Integer page, Integer size){
		
		Pageable pageRequest = new PageRequest(page,size);
		
		return new ResponseEntity<Page<ForumDto>>(forumServiceImpl.findAllForums(pageRequest), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/count", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CountDto> countForums(){
		return new ResponseEntity<CountDto>(new CountDto(forumServiceImpl.findForumCount()),HttpStatus.OK);
	}
	
	@RequestMapping(value="/top",produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<ForumDto>> viewFiveMostRecentForums(){
		return new ResponseEntity<List<ForumDto>>(forumServiceImpl.findTopFiveForums(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/viewPaginatedForumsByCategory",produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<ForumDto>> viewPaginatedForumsByCategory(Long categoryId, Integer page, Integer size){
		
		Pageable pageRequest = new PageRequest(page, size, Direction.DESC, "dateCreated");
		
		return new ResponseEntity<Page<ForumDto>>(forumServiceImpl.findForumsByCategory(categoryId, pageRequest), HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/countByCategoryId", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CountDto> countForumsByCategoryId(Long categoryId){
		return new ResponseEntity<CountDto>(new CountDto(forumServiceImpl.findForumCountByCategoryId(categoryId)),HttpStatus.OK);
	}

}
