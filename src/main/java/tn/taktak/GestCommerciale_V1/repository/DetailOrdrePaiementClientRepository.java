package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.DetailOrdrePaiementClient;
import tn.taktak.GestCommerciale_V1.entity.DetailOrdrePaiementClientId;

public interface DetailOrdrePaiementClientRepository extends JpaRepository<DetailOrdrePaiementClient, DetailOrdrePaiementClientId>  {

	@Query("select f from DetailOrdrePaiementClient f where f.cordrePaiementClient=?1")
	  List<DetailOrdrePaiementClient> findDetailOfOrdrePayClient(String t);
	
	/*@Query("select f from DetailOrdrePaiementClient f "
		 + " where f.cfactureClient=?1 and f.mtNonSoldee in (select min(g.mtNonSoldee) "
		 + " from DetailOrdrePaiementClient g where g.cfactureClient=?1 ")*/
	@Query("select f from DetailOrdrePaiementClient f "
			 + " where f.cfactureClient=?1 "
			 + " ORDER BY f.mtNonSoldee ASC")
	List<DetailOrdrePaiementClient> findListFactureDetail(String t);
}
