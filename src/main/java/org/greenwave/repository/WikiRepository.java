package org.greenwave.repository;

import java.util.List;

import org.greenwave.model.DataDomain;
import org.greenwave.model.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WikiRepository extends JpaRepository <Wiki , Long> {
	
	public List<Wiki> findByNom (String n);
	
	@Query("select w from Wiki w where w.nom like:x")
	public List<Wiki> findIndicator (@Param("x") String mc);
	
	@Query("select w from Wiki w where w.nom=:x")
	public Wiki findByName(@Param("x")String name);

	
	@Query("select w from Wiki w where w.idDomain=:x")
	public List<Wiki> findByWikiDomain(@Param("x")DataDomain id);
}
