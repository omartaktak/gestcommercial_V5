package tn.taktak.GestCommerciale_V1.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

// Generated 23 avr. 2015 18:41:00 by Hibernate Tools 3.4.0.CR1

/**
 * Unite generated by hbm2java
 */
@Entity
@Getter
@Setter
public class Unite {

	@Id
	private String cunite;
	private String desUnite;

	public Unite() {
	}

	public Unite(String cunite, String desUnite) {
		this.cunite = cunite;
		this.desUnite = desUnite;
	}
}