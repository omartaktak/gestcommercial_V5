package tn.taktak.GestCommerciale_V1.entity;

// Generated 3 nov. 2015 16:25:43 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * SocieteId generated by hbm2java
 */
public class SocieteId implements java.io.Serializable {

	private String raisonSociale;
	private String cregimeTva;
	private BigDecimal tauxFodec;
	private BigDecimal tauxTaxeSurTtc;
	private BigDecimal tauxConsommation;
	private BigDecimal prixTimbre;
	private boolean fodec;
	private boolean taxeSurTtc;
	private boolean timbre;
	private String matFiscale;
	private String nregistreCommerce;
	private String ncd;
	private String siteWeb;
	private String skype;
	private String email;
	private String tel;
	private String fax;
	private String gsm;
	private String desAdresse;
	private String pays;
	private String ville;
	private String region;
	private String codePostal;
	private String observation;
	private String desMonnaies;
	private String desSousUnite;
	private String symbole;
	private byte[] logo;
	private boolean imprimerPieceAvecLogo;
	private boolean afficheMarge;
	private boolean alerteSiMargeNegativeArticle;
	private boolean alerteQuantiteStock;
	private boolean actifDroitAcces;
	private boolean afficheCodeArticle;
	private boolean afficheCodeBarre;
	private boolean actifLibelleArticle;
	private boolean actifPrixUvente;
	private boolean actifTauxRemise;
	private boolean actifMtUremise;
	private boolean actifTva;
	private boolean actifUnite;
	private boolean actifPrixUht;
	private boolean actifPoidsUbrut;
	private boolean actifPoidsUnet;
	private boolean actifDescription;
	private boolean impressionParDefaut;
	private boolean annulerImpression;
	private boolean choisirImpression;
	private String nomImprimante;
	private String cclientParDefaut;

	public SocieteId() {
	}

	public SocieteId(String raisonSociale, String cregimeTva,
			BigDecimal tauxFodec, BigDecimal tauxTaxeSurTtc,
			BigDecimal tauxConsommation, BigDecimal prixTimbre, boolean fodec,
			boolean taxeSurTtc, boolean timbre, String desMonnaies,
			String desSousUnite, String symbole, boolean imprimerPieceAvecLogo,
			boolean afficheMarge, boolean alerteSiMargeNegativeArticle,
			boolean alerteQuantiteStock, boolean actifDroitAcces,
			boolean afficheCodeArticle, boolean afficheCodeBarre,
			boolean actifLibelleArticle, boolean actifPrixUvente,
			boolean actifTauxRemise, boolean actifMtUremise, boolean actifTva,
			boolean actifUnite, boolean actifPrixUht, boolean actifPoidsUbrut,
			boolean actifPoidsUnet, boolean actifDescription,
			boolean impressionParDefaut, boolean annulerImpression,
			boolean choisirImpression) {
		this.raisonSociale = raisonSociale;
		this.cregimeTva = cregimeTva;
		this.tauxFodec = tauxFodec;
		this.tauxTaxeSurTtc = tauxTaxeSurTtc;
		this.tauxConsommation = tauxConsommation;
		this.prixTimbre = prixTimbre;
		this.fodec = fodec;
		this.taxeSurTtc = taxeSurTtc;
		this.timbre = timbre;
		this.desMonnaies = desMonnaies;
		this.desSousUnite = desSousUnite;
		this.symbole = symbole;
		this.imprimerPieceAvecLogo = imprimerPieceAvecLogo;
		this.afficheMarge = afficheMarge;
		this.alerteSiMargeNegativeArticle = alerteSiMargeNegativeArticle;
		this.alerteQuantiteStock = alerteQuantiteStock;
		this.actifDroitAcces = actifDroitAcces;
		this.afficheCodeArticle = afficheCodeArticle;
		this.afficheCodeBarre = afficheCodeBarre;
		this.actifLibelleArticle = actifLibelleArticle;
		this.actifPrixUvente = actifPrixUvente;
		this.actifTauxRemise = actifTauxRemise;
		this.actifMtUremise = actifMtUremise;
		this.actifTva = actifTva;
		this.actifUnite = actifUnite;
		this.actifPrixUht = actifPrixUht;
		this.actifPoidsUbrut = actifPoidsUbrut;
		this.actifPoidsUnet = actifPoidsUnet;
		this.actifDescription = actifDescription;
		this.impressionParDefaut = impressionParDefaut;
		this.annulerImpression = annulerImpression;
		this.choisirImpression = choisirImpression;
	}

	public SocieteId(String raisonSociale, String cregimeTva,
			BigDecimal tauxFodec, BigDecimal tauxTaxeSurTtc,
			BigDecimal tauxConsommation, BigDecimal prixTimbre, boolean fodec,
			boolean taxeSurTtc, boolean timbre, String matFiscale,
			String nregistreCommerce, String ncd, String siteWeb, String skype,
			String email, String tel, String fax, String gsm,
			String desAdresse, String pays, String ville, String region,
			String codePostal, String observation, String desMonnaies,
			String desSousUnite, String symbole, byte[] logo,
			boolean imprimerPieceAvecLogo, boolean afficheMarge,
			boolean alerteSiMargeNegativeArticle, boolean alerteQuantiteStock,
			boolean actifDroitAcces, boolean afficheCodeArticle,
			boolean afficheCodeBarre, boolean actifLibelleArticle,
			boolean actifPrixUvente, boolean actifTauxRemise,
			boolean actifMtUremise, boolean actifTva, boolean actifUnite,
			boolean actifPrixUht, boolean actifPoidsUbrut,
			boolean actifPoidsUnet, boolean actifDescription,
			boolean impressionParDefaut, boolean annulerImpression,
			boolean choisirImpression, String nomImprimante,
			String cclientParDefaut) {
		this.raisonSociale = raisonSociale;
		this.cregimeTva = cregimeTva;
		this.tauxFodec = tauxFodec;
		this.tauxTaxeSurTtc = tauxTaxeSurTtc;
		this.tauxConsommation = tauxConsommation;
		this.prixTimbre = prixTimbre;
		this.fodec = fodec;
		this.taxeSurTtc = taxeSurTtc;
		this.timbre = timbre;
		this.matFiscale = matFiscale;
		this.nregistreCommerce = nregistreCommerce;
		this.ncd = ncd;
		this.siteWeb = siteWeb;
		this.skype = skype;
		this.email = email;
		this.tel = tel;
		this.fax = fax;
		this.gsm = gsm;
		this.desAdresse = desAdresse;
		this.pays = pays;
		this.ville = ville;
		this.region = region;
		this.codePostal = codePostal;
		this.observation = observation;
		this.desMonnaies = desMonnaies;
		this.desSousUnite = desSousUnite;
		this.symbole = symbole;
		this.logo = logo;
		this.imprimerPieceAvecLogo = imprimerPieceAvecLogo;
		this.afficheMarge = afficheMarge;
		this.alerteSiMargeNegativeArticle = alerteSiMargeNegativeArticle;
		this.alerteQuantiteStock = alerteQuantiteStock;
		this.actifDroitAcces = actifDroitAcces;
		this.afficheCodeArticle = afficheCodeArticle;
		this.afficheCodeBarre = afficheCodeBarre;
		this.actifLibelleArticle = actifLibelleArticle;
		this.actifPrixUvente = actifPrixUvente;
		this.actifTauxRemise = actifTauxRemise;
		this.actifMtUremise = actifMtUremise;
		this.actifTva = actifTva;
		this.actifUnite = actifUnite;
		this.actifPrixUht = actifPrixUht;
		this.actifPoidsUbrut = actifPoidsUbrut;
		this.actifPoidsUnet = actifPoidsUnet;
		this.actifDescription = actifDescription;
		this.impressionParDefaut = impressionParDefaut;
		this.annulerImpression = annulerImpression;
		this.choisirImpression = choisirImpression;
		this.nomImprimante = nomImprimante;
		this.cclientParDefaut = cclientParDefaut;
	}
}