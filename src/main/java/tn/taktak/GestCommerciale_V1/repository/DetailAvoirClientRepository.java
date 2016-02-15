package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.DetailAvoirClient;
import tn.taktak.GestCommerciale_V1.entity.DetailAvoirClientId;


public interface DetailAvoirClientRepository extends JpaRepository<DetailAvoirClient, DetailAvoirClientId> {

	@Query("select f from DetailAvoirClient f where f.cavoirClient=?1")
	  List<DetailAvoirClient> findDetailOfAvClt(String t);
}
