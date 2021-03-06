package tn.taktak.GestCommerciale_V1.entity;

// Generated 3 nov. 2015 16:25:43 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Getter;
import lombok.Setter;

/**
 * DetailOrdrePaiementClient generated by hbm2java
 */

@Entity @IdClass(DetailOrdrePaiementClientId.class)
@Getter
@Setter
public class DetailOrdrePaiementClient {

    //private DetailOrdrePaiementClientId id;
	@Id
	private int nligne;
	@Id
	private String cordrePaiementClient;
	private String cordrePaiementClientAcompte;
	private String cavoirClient;
	private String cfactureClient;
	private String carrivageClient;
	private BigDecimal mtSoldee;
	private BigDecimal mtRetiree;
	private BigDecimal mtNonSoldee;
	private String commentaire;

	public DetailOrdrePaiementClient() {
	}

	public DetailOrdrePaiementClient(int nligne,String cordrePaiementClient,
			BigDecimal mtSoldee, BigDecimal mtRetiree, BigDecimal mtNonSoldee) {
		this.nligne=nligne;
		this.cordrePaiementClient=cordrePaiementClient;
		this.mtSoldee = mtSoldee;
		this.mtRetiree = mtRetiree;
		this.mtNonSoldee = mtNonSoldee;
	}

	public DetailOrdrePaiementClient(int nligne,String cordrePaiementClient,
			String cordrePaiementClientAcompte, String cavoirClient,
			String cfactureClient, String carrivageClient, BigDecimal mtSoldee,
			BigDecimal mtRetiree, BigDecimal mtNonSoldee, String commentaire) {
		this.nligne=nligne;
		this.cordrePaiementClient=cordrePaiementClient;
		this.cordrePaiementClientAcompte = cordrePaiementClientAcompte;
		this.cavoirClient = cavoirClient;
		this.cfactureClient = cfactureClient;
		this.carrivageClient = carrivageClient;
		this.mtSoldee = mtSoldee;
		this.mtRetiree = mtRetiree;
		this.mtNonSoldee = mtNonSoldee;
		this.commentaire = commentaire;
	}
}
