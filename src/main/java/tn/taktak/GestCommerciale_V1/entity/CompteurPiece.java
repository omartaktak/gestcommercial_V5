package tn.taktak.GestCommerciale_V1.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CompteurPiece {

	@Id
	private String cTypePiece;
	private String designationPiece;
	private String prefixe;
	private int compteur;
	private int longueur;
	private boolean anneeValidee;
	private boolean moisValidee;
	private String siffuxe;
	private int nbAnnee;
	private String nPiece;
	
	public CompteurPiece() {
		
	}
	
	public CompteurPiece(String cTypePiece, String designationPiece,
			String prefixe, int compteur, int longueur, boolean anneeValidee,
			boolean moisValidee, String siffuxe, int nbAnnee, String nPiece) {
		
		this.cTypePiece = cTypePiece;
		this.designationPiece = designationPiece;
		this.prefixe = prefixe;
		this.compteur = compteur;
		this.longueur = longueur;
		this.anneeValidee = anneeValidee;
		this.moisValidee = moisValidee;
		this.siffuxe = siffuxe;
		this.nbAnnee = nbAnnee;
		this.nPiece = nPiece ;

	}
	
}
