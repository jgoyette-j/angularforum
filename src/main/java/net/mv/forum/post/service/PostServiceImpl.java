package net.mv.forum.post.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import net.mv.forum.forum.domain.Forum;
import net.mv.forum.forum.repository.ForumRepository;
import net.mv.forum.post.domain.Post;
import net.mv.forum.post.dto.PostDto;
import net.mv.forum.post.repository.PostRepository;
import net.mv.forum.user.domain.User;
import net.mv.forum.user.repository.UserRepository;

@Service
@Transactional
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ForumRepository forumRepository;

	@Override
	/**
	 * Figure this out why it was not properly saving Post objects.
	 * 3-8-16 ~Jeff : Started double saving posts to a forum. Assume 
	 * that it has to do with getting the forum and adding it? Look 
	 * for the ***** for the lines with suspect code.
	 */
	public PostDto addPost(PostDto postDto) {
		
		User user = userRepository.findUserByUsername(postDto.getAuthor());
		
		Forum forum = forumRepository.findOne(postDto.getForumId());
		
		Post post = new Post();
		
		post.setText(postDto.getText());
		post.setForum(forum);
		post.setUser(user);
		post.setDateAuthored(new Date());
		
		//*****
		//forum.getPosts().add(post);
		postRepository.save(post);
		//*****
		//forumRepository.save(forum);
		
		postDto.setId(post.getId());
		postDto.setCreated(post.getDateAuthored());
		
		return postDto;
	}

	@Override
	public List<PostDto> findTop5Posts() {

		Pageable topFive = new PageRequest(0, 5, Direction.DESC, "dateAuthored"); 
		
		List<Post> posts = postRepository.findTop5Posts(topFive);
		
		List<PostDto> postDtos = new ArrayList<PostDto>();
		
		for(Post post : posts){
			postDtos.add(new PostDto(post));
		}
		
		return postDtos;
	}

}
