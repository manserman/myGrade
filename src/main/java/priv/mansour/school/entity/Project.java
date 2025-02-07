package priv.mansour.school.entity;

import java.util.Objects;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

	@Id
	private String id;

	@Pattern(regexp = "\\d{4}", message = "L'année doit être au format YYYY (ex: 2024).")
	private final String annee = "";

	@NotBlank(message = "Veuillez fournir une promotion pour le projet.")
	private final String promotion = "";

	@NotBlank(message = "Veuillez fournir une description pour le projet.")
	private final String description = "";

	@NotBlank(message = "Veuillez fournir un libellé pour le projet.")
	private final String libelle = "";

	@DBRef
	private final Teacher teacher = null;

	@DBRef
	private List<Competence> competences;

	public boolean addCompetence(Competence competence) {
		if (competences.contains(competence)) {
			return false;
		}
		competences.add(competence);
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Project project = (Project) obj;
		return Objects.equals(id, project.id) && Objects.equals(annee, project.annee)
				&& Objects.equals(promotion, project.promotion) && Objects.equals(description, project.description)
				&& Objects.equals(libelle, project.libelle) && Objects.equals(teacher, project.teacher);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, annee, promotion, description, libelle, teacher);
	}
}
