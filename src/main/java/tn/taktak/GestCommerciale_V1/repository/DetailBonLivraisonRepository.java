package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.DetailBonLivraison;
import tn.taktak.GestCommerciale_V1.entity.DetailBonLivraisonId;


public interface DetailBonLivraisonRepository extends JpaRepository<DetailBonLivraison, DetailBonLivraisonId> {


	@Query("select f from DetailBonLivraison f where f.cbonLivraison=?1")
	List<DetailBonLivraison> findDetailOfBonLiv(String t);
	
}
