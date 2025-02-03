package priv.mansour.school.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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

	private int id;

	@DBRef
	private List<Competence> competences;
	@NotBlank(message = "Veillez fournir une description pour le projet.")
	private String description;
	@NotBlank(message = "Veillez fournir un libelle pour le projet.")
	private String libelle;
	@DBRef
	Teacher teacher;

}
