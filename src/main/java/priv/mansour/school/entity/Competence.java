package priv.mansour.school.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Document
@AllArgsConstructor // ✅ Required to set all final fields
@Getter // ✅ Only getters, no setters (immutability)
@EqualsAndHashCode
@ToString(exclude = "id")
public class Competence {

	@Id
	private final String id;

	@NotBlank(message = "Veuillez fournir une description pour le projet.")
	private final String description;

	@Indexed(unique = true)
	@NotBlank(message = "Veuillez fournir un libellé pour le projet.")
	private final String libelle;

}
