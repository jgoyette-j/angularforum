package net.mv.forum.forum.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.mv.forum.forum.domain.Forum;
import net.mv.forum.post.domain.Post;
import net.mv.forum.post.dto.PostDto;
import net.mv.forum.serializer.JsonDateSerializer;

public class ForumDto {

	private Long id;
	private String title;
	private String author;
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date created;
	@JsonProperty(value = "posts")
	private List<PostDto> posts = new ArrayList<PostDto>();
	private Long categoryId;

	public ForumDto() {
	}

	public ForumDto(Forum forum) {
		this.id = forum.getId();
		this.title = forum.getName();
		this.author = forum.getUser().getUsername();
		this.created = forum.getDateCreated();
		for (Post post : forum.getPosts()) {
			posts.add(new PostDto(post));
		}
	}

	@Override
	public String toString() {
		return "ForumDto [id=" + id + ", title=" + title + ", author=" + author + ", created=" + created + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<PostDto> getPosts() {
		return posts;
	}

	public void setPosts(List<PostDto> posts) {
		this.posts = posts;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
