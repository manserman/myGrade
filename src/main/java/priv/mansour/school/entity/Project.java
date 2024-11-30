package priv.mansour.school.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
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
	private String description;
	private String libelle;

	@DBRef
	Teacher teacher;

}
