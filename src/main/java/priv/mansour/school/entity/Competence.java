package priv.mansour.school.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int Id;
	private String libelle;
	private String description;
	public Competence(String libelle) {
		this.libelle=libelle;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return ((Project)obj).getLibelle().equals(getLibelle());
	}
	public String getLibelle() {
		return this.libelle;
	}
	public void setLibelle(String lib) {
		this.libelle=lib;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
