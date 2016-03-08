package net.mv.forum.forum.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.mv.forum.category.domain.Category;
import net.mv.forum.forum.dto.ForumDto;
import net.mv.forum.post.domain.Post;
import net.mv.forum.user.domain.User;

@Entity
@Table(name = "forum")
public class Forum {

	@Id
	@Column(name = "f_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "f_name")
	private String name;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "u_id")
	private User user;
	@Column(name = "date_authored")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "forum", fetch = FetchType.LAZY, targetEntity = Post.class)
	private List<Post> posts = new ArrayList<Post>();
	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;

	public Forum() {
	}

	public Forum(ForumDto forumDto) {
		this.name = forumDto.getTitle();
	}

	@Override
	public String toString() {
		return "Forum [id=" + id + ", name=" + name + ", user=" + user + ", dateCreated=" + dateCreated + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
