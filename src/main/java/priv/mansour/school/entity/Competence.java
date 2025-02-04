package priv.mansour.school.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString(exclude = "id")
public class Competence {
	@Id
	private int id;
	@NotBlank(message = "Veillez fournir une description pour le projet.")
	private String description;
	@Indexed(unique = true)
	@NotBlank(message = "Veillez fournir un libelle pour le projet.")
	private String libelle;

}
