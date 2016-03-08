package net.mv.forum.category.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.mv.forum.category.dto.CategoryDto;
import net.mv.forum.forum.domain.Forum;

@Entity
@Table(name = "category", uniqueConstraints = { @UniqueConstraint(columnNames = { "c_name" }, name = "c_name_unique") })
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "c_id")
	private Long id;
	@Column(name = "c_name")
	private String catName;
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, targetEntity = Forum.class)
	private List<Forum> forums = new ArrayList<Forum>();

	public Category() {
		super();
	}

	public Category(CategoryDto catDto) {
		super();
		this.id = catDto.getId();
		this.catName = catDto.getName();
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", catName=" + catName + ", forums=" + forums + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public List<Forum> getForums() {
		return forums;
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

}
