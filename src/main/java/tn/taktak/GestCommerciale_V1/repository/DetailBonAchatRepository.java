package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.DetailBonAchat;
import tn.taktak.GestCommerciale_V1.entity.DetailBonAchatId;

public interface DetailBonAchatRepository extends JpaRepository<DetailBonAchat, DetailBonAchatId> {


	@Query("select f from DetailBonAchat f where f.cbonAchat=?1")
	  List<DetailBonAchat> findDetailOfBA(String t);
}
