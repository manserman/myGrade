package priv.mansour.school.entity;

import java.util.List;

public class Project {
	private List<Competence> competences;
	private String libelle;
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return getLibelle().equals(((Project)obj).getLibelle());
		
	}
	public String getLibelle() {
		return this.libelle;
	}

}
