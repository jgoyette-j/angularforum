package net.mv.forum.forum.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import net.mv.forum.category.domain.Category;
import net.mv.forum.category.repository.CategoryRepository;
import net.mv.forum.forum.domain.Forum;
import net.mv.forum.forum.dto.ForumDto;
import net.mv.forum.forum.repository.ForumRepository;
import net.mv.forum.user.domain.User;
import net.mv.forum.user.repository.UserRepository;

/**
 * Service to facilitate the business logic of Forum creation, retrieval,
 * counting, and pagination. Some methods may be kept until culled in later
 * releases.
 * 
 * @author Jeff
 *
 */
@Service
@Transactional
public class ForumServiceImpl implements ForumService {

	@Autowired
	private ForumRepository forumRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * Save forum prior to saving post
	 */
	@Override
	public ForumDto addForum(ForumDto forumDto) {
		System.out.println(forumDto);
		Forum forum = new Forum(forumDto);
		User user = userRepository.findUserByUsername(forumDto.getAuthor());
		forum.setUser(user);
		forum.setDateCreated(new Date());
		// forum.getPosts().add(new Post(forumDto.getPosts().get(0)));
		
		/*
		 * Get Category and set in Forum
		 */
		Category cat = categoryRepository.findOne(forumDto.getCategory().getId());
		
		forum.setCategory(cat);
		
		forumRepository.saveAndFlush(forum);
		forumDto.setId(forum.getId());
		forumDto.setAuthor(user.getUsername());
		return forumDto;
	}

	/**
	 * Legacy method to retrieve every single forum in the DB. Candidate for
	 * removal.
	 */
	@Override
	@Deprecated
	public List<ForumDto> findAllForums() {
		List<Forum> forums = forumRepository.findAllByOrderByDateCreatedDesc();

		List<ForumDto> forumDtos = new ArrayList<ForumDto>();

		for (Forum forum : forums) {
			ForumDto forumDto = new ForumDto(forum);
			forumDtos.add(forumDto);
		}

		return forumDtos;
	}

	/**
	 * Returns a single forum in its detailed glory by id.
	 */
	@Override
	public ForumDto findForumById(Long id) {

		Forum forum = forumRepository.findOne(id);

		/* System.out.println(forum.getPosts()); */

		ForumDto forumDto = new ForumDto(forum, forum.getPosts());

		System.out.println(forumDto.getPosts());

		return forumDto;
	}

	/**
	 * Passes a Pageable object to get a subset of the Forums with their Posts
	 * attached.
	 */
	@Override
	public Page<ForumDto> findAllForums(Pageable page) {

		Page<Forum> forums = forumRepository.findAllByOrderByDateCreatedDesc(page);

		List<ForumDto> forumDtoList = new ArrayList<>();

		for (Forum forum : forums) {
			forumDtoList.add(new ForumDto(forum, forum.getPosts()));
		}

		Page<ForumDto> forumDtos = new PageImpl<ForumDto>(forumDtoList);

		return forumDtos;
	}

	/**
	 * Gets a total count of all the Forums. Primarily used in the pagination on
	 * the front end.
	 */
	@Override
	public Long findForumCount() {
		return forumRepository.count();
	}

	/**
	 * Used for the most recent posts feature on the front page.
	 */
	@Override
	public List<ForumDto> findTopFiveForums() {

		Pageable topFive = new PageRequest(0, 5, Direction.DESC, "dateCreated");

		List<Forum> forums = forumRepository.findTop5Forums(topFive);

		List<ForumDto> forumDtoList = new ArrayList<>();

		for (Forum forum : forums) {
			forumDtoList.add(new ForumDto(forum));
		}

		return forumDtoList;
	}

	/**
	 * New method to retrieve all Forum objects by Category. The categoryId
	 * should be passed in along with the ForumDto when creating forums. This is
	 * not the case for viewing. It will need to come from elsewhere.
	 */
	@Override
	public Page<ForumDto> findForumsByCategory(Long categoryId, Pageable page) {

		Category category = categoryRepository.findOne(categoryId);

		Page<Forum> forums = forumRepository.findAllByCategory(category, page);

		List<ForumDto> forumDtoList = new ArrayList<>();

		for (Forum forum : forums) {
			forumDtoList.add(new ForumDto(forum, forum.getPosts()));
		}

		Page<ForumDto> forumDtos = new PageImpl<ForumDto>(forumDtoList);

		return forumDtos;
	}

	@Override
	public Long findForumCountByCategoryId(Long categoryId) {

		Category category = categoryRepository.findOne(categoryId);

		return forumRepository.countByCategory(category);
	}

}
