package priv.mansour.school.entity;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document
@AllArgsConstructor

@Setter
@Getter
public class Competence {
	@Id
	private int Id;
	private String libelle;
	private String description;

	public Competence(String libelle) {
		this.libelle = libelle;

	}
	 // Égalité et hashcode pour permettre l'utilisation comme clé dans une HashMap
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Competence that = (Competence) obj;
        return Id == that.getId() && Objects.equals(libelle, that.libelle) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, libelle, description);
    }

    // toString pour un affichage facile
    @Override
    public String toString() {
        return "Competence{" +
               "id=" + Id +
               ", libelle='" + libelle + '\'' +
               ", description='" + description + '\'' +
               '}';
    }

}
