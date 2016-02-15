package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import tn.taktak.GestCommerciale_V1.entity.Fournisseur;

public interface FournisseurRepository extends	JpaRepository<Fournisseur, String> {
	
//	@Query(value="SELECT * FROM Fournisseur f where LOWER(f.cfournisseur) LIKE LOWER(CONCAT('%', :t, '%'))  ",
//			nativeQuery=true)
//	  List<Fournisseur> filterFournisseur( @Param("t") String t);
	
	@Query("select f from Fournisseur f where f.cfournisseur like %?1% or f.raisonSociale like %?1% or "+
			" f.matFiscale like %?1% or f.tel like %?1% or f.fax like %?1% or f.gsm like %?1%")
	List<Fournisseur> filterFournisseur(String t);
}
