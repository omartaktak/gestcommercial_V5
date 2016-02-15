package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.DetailFactureClient;
import tn.taktak.GestCommerciale_V1.entity.DetailFactureClientId;


public interface DetailFactureClientRepository extends JpaRepository<DetailFactureClient, DetailFactureClientId> {

	@Query("select f from DetailFactureClient f where f.cfactureClient=?1")
	  List<DetailFactureClient> findDetailOfFacture(String t);
	
	
}
