package tn.taktak.GestCommerciale_V1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.taktak.GestCommerciale_V1.entity.Client;

public interface ClientRepository extends JpaRepository<Client, String> {

	@Query("select f from Client f where f.cclient like %?1% or f.raisonSociale like %?1% or "+
		  " f.matFiscale like %?1% or f.tel like %?1% or f.fax like %?1% or f.gsm like %?1% ")
	List<Client> filterClient(String t);
}
