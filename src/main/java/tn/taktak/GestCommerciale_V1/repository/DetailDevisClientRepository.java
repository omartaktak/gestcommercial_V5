package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.DetailDevisClient;
import tn.taktak.GestCommerciale_V1.entity.DetailDevisClientId;

public interface DetailDevisClientRepository extends JpaRepository<DetailDevisClient, DetailDevisClientId> {

	@Query("select f from DetailDevisClient f where f.cdevisClient=?1")
	  List<DetailDevisClient> findDetailOfDevis(String t);
}
