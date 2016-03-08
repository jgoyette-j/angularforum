package net.mv.forum.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.mv.forum.forum.domain.Forum;

/**
 * ForumRepository interface using Spring Data to handle
 * ORM operations on Forum objects. 
 * @author Jeff
 *
 */
public interface ForumRepository extends JpaRepository<Forum, Long>{
	
	public List<Forum> findAllByOrderByDateCreatedDesc();
	public Page<Forum> findAllByOrderByDateCreatedDesc(Pageable pageable);
	@Query(value="select f from Forum f")
	public List<Forum> findTop5Forums(Pageable page);
	
}
