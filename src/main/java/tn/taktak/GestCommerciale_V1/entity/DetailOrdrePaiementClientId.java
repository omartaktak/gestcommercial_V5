package tn.taktak.GestCommerciale_V1.entity;

// Generated 3 nov. 2015 16:25:43 by Hibernate Tools 3.4.0.CR1

/**
 * DetailOrdrePaiementClientId generated by hbm2java
 */
public class DetailOrdrePaiementClientId implements java.io.Serializable {

	private int nligne;
	private String cordrePaiementClient;

	public DetailOrdrePaiementClientId() {
	}

	public DetailOrdrePaiementClientId(int nligne, String cordrePaiementClient) {
		this.nligne = nligne;
		this.cordrePaiementClient = cordrePaiementClient;
	}

	public int getNligne() {
		return this.nligne;
	}

	public void setNligne(int nligne) {
		this.nligne = nligne;
	}

	public String getCordrePaiementClient() {
		return this.cordrePaiementClient;
	}

	public void setCordrePaiementClient(String cordrePaiementClient) {
		this.cordrePaiementClient = cordrePaiementClient;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DetailOrdrePaiementClientId))
			return false;
		DetailOrdrePaiementClientId castOther = (DetailOrdrePaiementClientId) other;

		return (this.getNligne() == castOther.getNligne())
				&& ((this.getCordrePaiementClient() == castOther
						.getCordrePaiementClient()) || (this
						.getCordrePaiementClient() != null
						&& castOther.getCordrePaiementClient() != null && this
						.getCordrePaiementClient().equals(
								castOther.getCordrePaiementClient())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getNligne();
		result = 37
				* result
				+ (getCordrePaiementClient() == null ? 0 : this
						.getCordrePaiementClient().hashCode());
		return result;
	}

}
