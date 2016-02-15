package tn.taktak.GestCommerciale_V1.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import tn.taktak.GestCommerciale_V1.entity.Tva;;

public interface TVARepository extends JpaRepository<Tva, String> {

	@Query("select tauxTva from Tva ")
	  List<BigDecimal> findTauxTva();
	
	@Query("select f from Tva f where f.ctva like %?1% or f.tauxTva like %?1% ")
	List<Tva> filterTva(String t);
}
