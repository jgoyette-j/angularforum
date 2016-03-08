package net.mv.forum.category.dto;

import java.util.ArrayList;
import java.util.List;

import net.mv.forum.category.domain.Category;
import net.mv.forum.forum.domain.Forum;
import net.mv.forum.forum.dto.ForumDto;

public class CategoryDto {

	private Long id;
	private String name;
//	private List<ForumDto> forums = new ArrayList<ForumDto>();

	public CategoryDto() {
		super();
	}

	public CategoryDto(Category cat) {
		super();
		this.id = cat.getId();
		this.name = cat.getCatName();
//		this.forums = cat.getForums();
		
//		List<Forum> forums = cat.getForums();
//		
//		if(forums != null && !forums.isEmpty()){
//			for(Forum forum : forums){
//				this.forums.add(new ForumDto(forum));
//			}
//		}
	}

	@Override
	public String toString() {
		return "CategoryDto [id=" + id + ", name=" + name + "]";
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

//	public List<ForumDto> getForums() {
//		return forums;
//	}
//
//	public void setForums(List<ForumDto> forums) {
//		this.forums = forums;
//	}

}
