package tn.taktak.GestCommerciale_V1.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.Unite;

public interface UniteRepository extends JpaRepository<Unite, String>  {

	@Query("select desUnite from Unite ")
	  List<String> findDesUnite();
	
	@Query("select f from Unite f where f.cunite like %?1% or f.desUnite like %?1% ")
	List<Unite> filterUnite(String t);
}
