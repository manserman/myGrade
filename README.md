 API de Notation par compétence- Backend avec Spring Boot, Spring Data JPA, et MongoDB

Cette application est une API backend construite avec Spring Boot, intégrant Spring Data JPA pour la gestion des données et MongoDB comme base de données NoSQL.

- **Spring Boot** : Framework Java facilitant le développement rapide d'applications en offrant des configurations automatiques et une architecture modulaire.
- **Spring Data JPA** : Module de Spring permettant une gestion simplifiée des bases de données grâce au JPA (Java Persistence API), avec un support pour les entités, requêtes et transactions.
- **MongoDB** : Base de données NoSQL orientée documents, idéale pour gérer des données non structurées ou évolutives.

---

## Instructions d'utilisation

### 1. Cloner le projet :
   bash
   git clone [<URL_DU_DEPOT>](https://github.com/manserman/myGrade/)
  

2. Configurer MongoDB :
   Le projet est déjà lié à une base de données distante.
   SI vous voulez utiliser une autre bd vous poucez changer le lien de connexion dans application.properties
3. Lancer l'application :
Compilez et exécutez l'application avec Maven :
bash

mvn clean install
mvn spring-boot:run
L'API sera disponible à l'adresse : http://localhost:8080.
Liste des Endpoints
Compétences
Créer une compétence :
Méthode : POST
URL : /competences
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "id": 1,
  "libelle": "Java",
  "description": "Langage de programmation orienté objet"
}

Lister toutes les compétences :
Méthode : GET
URL : /competences

Récupérer une compétence par ID :
Méthode : GET
URL : /competences/{id}

Mettre à jour une compétence par ID :
Méthode : PUT
URL : /competences/{id}
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "libelle": "Spring Boot",
  "description": "Framework de développement rapide pour Java"
}

Supprimer une compétence par ID :
Méthode : DELETE
URL : /competences/{id}
Étudiants
Créer un étudiant :

Méthode : POST
URL : /students
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "id": 1,
  "nom": "Doe",
  "prenom": "John",
  "projets": [],
  "competences": []
}
Lister tous les étudiants :
Méthode : GET
URL : /students

Récupérer un étudiant par ID :
Méthode : GET
URL : /students/{id}
Mettre à jour un étudiant par ID :

Méthode : PUT
URL : /students/{id}
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "nom": "Smith",
  "prenom": "Jane"
}

Supprimer un étudiant par ID :
Méthode : DELETE
URL : /students/{id}
Ajouter un projet à un étudiant :

Méthode : POST
URL : /students/{id}/projects
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "id": 1,
  "libelle": "Gestion de l'école",
  "description": "Un projet de gestion"
}

Ajouter une compétence à un étudiant :
Méthode : POST
URL : /students/{id}/competences
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "id": 1,
  "libelle": "Java"
}

Enseignants

Créer un enseignant :

Méthode : POST
URL : /teachers
Exemple de Body (JSON) :
json
Always show details
Copy code
{
  "id": 1,
  "nom": "Smith",
  "prenom": "Alice",
  "projets": []
}

Lister tous les enseignants :
Méthode : GET
URL : /teachers

Récupérer un enseignant par ID :
Méthode : GET
URL : /teachers/{id}

Mettre à jour un enseignant par ID :
Méthode : PUT
URL : /teachers/{id}
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "nom": "Johnson",
  "prenom": "Bob"
}

Supprimer un enseignant par ID :
Méthode : DELETE
URL : /teachers/{id}

Ajouter un projet à un enseignant :
Méthode : POST
URL : /teachers/{id}/projects
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "id": 1,
  "libelle": "Gestion de l'école",
  "description": "Un projet de gestion"
}

Projets
Créer un projet :
Méthode : POST
URL : /projects
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "id": 1,
  "libelle": "Gestion de l'école",
  "description": "Projet de gestion d'une école",
  "competences": [
    {
      "id": 1,
      "libelle": "Java",
      "description": "Langage de programmation orienté objet"
    }
  ],
  "teacher": {
    "id": 1,
    "nom": "Smith",
    "prenom": "Alice"
  }
}

Lister tous les projets :
Méthode : GET
URL : /projects

Récupérer un projet par ID :
Méthode : GET
URL : /projects/{id}

Mettre à jour un projet par ID :
Méthode : PUT
URL : /projects/{id}
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "libelle": "Mise à jour projet",
  "description": "Description mise à jour"
}

Supprimer un projet par ID :
Méthode : DELETE
URL : /projects/{id}

Ajouter une compétence à un projet :
Méthode : POST
URL : /projects/{id}/competences
Exemple de Body (JSON) :
json
Always show details

Copy code
{
  "id": 2,
  "libelle": "Spring Boot",
  "description": "Framework de développement rapide pour Java"
}

Structure du projet
Controller : Gère les requêtes HTTP et les réponses.
Service : Contient la logique métier.
Repository : Interagit avec la base de données MongoDB.
Entity : Définit les modèles de données.
Vous pouvez maintenant tester tous les endpoints en suivant ces instructions simples !
