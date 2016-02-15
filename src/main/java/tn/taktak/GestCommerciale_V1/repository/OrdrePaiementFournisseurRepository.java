package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementFournisseur;

public interface OrdrePaiementFournisseurRepository extends JpaRepository<OrdrePaiementFournisseur, String> {

	
	@Query("select f from OrdrePaiementFournisseur f "
			+ " where f.cfournisseur=?1"
			+ " AND f.genererAcompte=true")
	  List<OrdrePaiementFournisseur> findListOfOrdrePayFournisseur(String idFourn);
	
	@Query("select f from OrdrePaiementFournisseur f "
			+ " where f.cfournisseur=?1")
	List<OrdrePaiementFournisseur> findAllListOfOrdrePayFournisseur(String idFourn);
}
