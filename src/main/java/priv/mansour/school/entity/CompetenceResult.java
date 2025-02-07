package priv.mansour.school.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;

import priv.mansour.school.enums.ResultatEnum;

public class CompetenceResult {
	@DBRef
	private Competence competence;
	private ResultatEnum resultat;

	public CompetenceResult(Competence competence, ResultatEnum resultat) {
		this.competence = competence;
		this.resultat = resultat;
	}

	public Competence getCompetence() {
		return competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}

	public ResultatEnum getResultat() {
		return resultat;
	}

	public void setResultat(ResultatEnum resultat) {
		this.resultat = resultat;
	}
}
