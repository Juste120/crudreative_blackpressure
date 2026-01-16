# CRUD Réactif avec Backpressure - crudreativeBackpressure

Ce projet est une application CRUD (Create, Read, Update, Delete) entièrement réactive construite avec Spring Boot, Spring WebFlux et R2DBC. Elle démontre comment gérer des opérations de base de données de manière non bloquante et comment appliquer une stratégie de backpressure pour le streaming de données.

## Fonctionnalités

- Opérations CRUD complètes pour la gestion des utilisateurs.
- API RESTful entièrement réactive.
- Validation de l'unicité de l'email lors de la création d'un utilisateur.
- Streaming de données avec gestion de la backpressure.
- Base de données en mémoire H2 pour un démarrage et des tests faciles.

## Technologies utilisées

- **Java 17**
- **Spring Boot**
- **Spring WebFlux** : Framework web réactif.
- **Spring Data R2DBC** : Pour l'accès réactif aux bases de données.
- **R2DBC H2** : Driver réactif pour la base de données H2.
- **H2 Database** : Base de données en mémoire.
- **Gradle** : Outil de build.

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les éléments suivants sur votre machine :

- JDK 17 ou supérieur
- Gradle

## Installation et exécution

1.  **Clonez le dépôt :**

    ```bash
    git clone <url-du-depot>
    cd crudreativeBackpressure
    ```

2.  **Lancez l'application :**

    ```bash
    ./gradlew bootRun
    ```

L'application sera disponible à l'adresse `http://localhost:8080`.

## Points d'accès de l'API

L'API expose les points d'accès suivants sous le chemin de base `/users`.

| Méthode | Endpoint              | Description                                      |
| ------- | --------------------- | ------------------------------------------------ |
| `GET`   | `/`                   | Récupère la liste de tous les utilisateurs.      |
| `GET`   | `/{id}`               | Récupère un utilisateur par son ID.              |
| `POST`  | `/`                   | Crée un nouvel utilisateur.                      |
| `PUT`   | `/`                   | Met à jour un utilisateur existant.              |
| `DELETE`| `/{id}`               | Supprime un utilisateur par son ID.              |
| `GET`   | `/stream`             | Streame tous les utilisateurs avec backpressure. |

### Corps de la requête pour `POST` et `PUT`

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

## Comment tester l'application

Un script shell `test-curl.sh` est fourni pour tester facilement les points d'accès de l'API. Assurez-vous que le script est exécutable :

```bash
chmod +x test-curl.sh
```

Puis exécutez-le :

```bash
./test-curl.sh
```

Ce script effectuera une série de requêtes `curl` pour tester les différentes fonctionnalités de l'API.
