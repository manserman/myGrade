package priv.mansour.school.entity;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class Project {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@OneToMany
	@JoinColumn(name= "id")
	private List<Competence> competences;
	private String description;
	private String libelle;
	@ManyToOne
	@JoinColumn(name= "id")
	private Teacher teacher;
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return getLibelle().equals(((Project)obj).getLibelle());
		
	}
	public String getLibelle() {
		return this.libelle;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Competence> getCompetences() {
		return competences;
	}
	public void setCompetences(List<Competence> competences) {
		this.competences = competences;
	}

}
