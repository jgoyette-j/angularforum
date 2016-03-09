package net.mv.forum.post.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.mv.forum.forum.domain.Forum;
import net.mv.forum.post.dto.PostDto;
import net.mv.forum.user.domain.User;

@Entity
@Table(name="post")
public class Post {

	@Id
	@Column(name = "p_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length=5000)
	private String text;
	@Column(name="date_authored")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAuthored;
	@ManyToOne
	@JoinColumn(name="u_id")
	@JsonProperty(value="user")
	private User user;
	@ManyToOne
	@JoinColumn(name="f_id")
	private Forum forum;
	
	public Post(PostDto postDto) {
		this.text = postDto.getText();
		this.dateAuthored = new Date();
	}

	public Post() {
		super();
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", text=" + text + ", dateAuthored=" + dateAuthored + ", user=" + user + ", forum="
				+ forum + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDateAuthored() {
		return dateAuthored;
	}

	public void setDateAuthored(Date dateAuthored) {
		this.dateAuthored = dateAuthored;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

}
