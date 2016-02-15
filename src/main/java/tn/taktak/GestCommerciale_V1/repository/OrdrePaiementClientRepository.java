package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.OrdrePaiementClient;

public interface OrdrePaiementClientRepository extends JpaRepository<OrdrePaiementClient, String> {

	@Query("select f from OrdrePaiementClient f "
			+ " where f.cclient=?1"
			+ " AND f.genererAcompte=true")
	  List<OrdrePaiementClient> findListOfOrdrePayClient(String idClient);
	
	@Query("select f from OrdrePaiementClient f "
			+ " where f.cclient=?1")
	List<OrdrePaiementClient> findAllListOfOrdrePayClient(String idClient);
}
