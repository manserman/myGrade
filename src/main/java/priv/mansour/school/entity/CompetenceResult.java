package priv.mansour.school.entity;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import priv.mansour.school.enums.ResultatEnum;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CompetenceResult {

	@DBRef(lazy = true)
	private final Competence competence;

	private final ResultatEnum resultat;

}
