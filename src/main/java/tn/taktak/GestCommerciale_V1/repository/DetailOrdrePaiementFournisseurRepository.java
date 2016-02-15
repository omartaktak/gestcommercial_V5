package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.DetailOrdrePaiementFournisseur;
import tn.taktak.GestCommerciale_V1.entity.DetailOrdrePaiementFournisseurId;

public interface DetailOrdrePaiementFournisseurRepository extends JpaRepository<DetailOrdrePaiementFournisseur, DetailOrdrePaiementFournisseurId> {

	
	@Query("select f from DetailOrdrePaiementFournisseur f where f.cordrePaiementFournisseur=?1")
	List<DetailOrdrePaiementFournisseur> findDetailOfOrdrePayFournisseur(String t);
	
	
	@Query("select f from DetailOrdrePaiementFournisseur f "
			 + " where f.cbonAchat=?1 "
			 + " ORDER BY f.mtNonSoldee ASC")
	List<DetailOrdrePaiementFournisseur> findListBADetail(String ba);
}
