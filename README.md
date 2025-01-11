🛍️ Gestion du Cahier de Dette d’une Boutique

📋 Description

Cette application console en Java permet de gérer efficacement les dettes d’une boutique. Elle inclut des fonctionnalités organisées en fonction des rôles des utilisateurs et offre trois versions différentes selon les besoins techniques :

📂 Version Liste : Gestion des données en mémoire via des collections Java.

🗄️ Version JDBC : Connexion avec MySQL (phpMyAdmin) ou PostgreSQL (pgAdmin).

🌐 Version JPA : Gestion avancée avec Hibernate et JPA.

🗂️  Version JavaFX : Interface graphique avec JavaFX 

🔀 Basculer entre les versions et bases de données :

Les configurations sont centralisées dans le fichier /ressources/META-INF/persistence.xml, permettant de changer facilement entre MySQL et PostgreSQL ou entre les modes LIST, JDBC et JPA.

Exemple de configuration :

yaml

persistence:

  unit: MYSQLDETTES  # Ou POSTGRESDETTES pour PostgreSQL

client:

  type: JPA          # Options: JPA, DB, LIST

user:

  type: JPA          # Options: JPA, DB, LIST

article:

  type: JPA          # Options: JPA, DB, LIST  

dept:
 
  type: JPA          # Options: JPA, DB, LIST  

detail:

  type: JPA          # Options: JPA, DB, LIST  

paiement:

  type: JPA          # Options: JPA, DB, LIST  

💡 L’application respecte les principes SOLID et adopte une structure de code claire et modulaire, avec des controllers, facilitant l'extension vers une interface graphique (JavaFX).

✨ Fonctionnalités

🔑 Utilisateur de rôle Boutiquier

Gestion des clients :

➕ Créer un client (nom, téléphone, adresse) avec ou sans compte utilisateur (email, login, password).

📋 Lister les clients avec des filtres (clients avec ou sans compte utilisateur).

🔍 Rechercher un client via son téléphone pour afficher ses informations détaillées.

Gestion des dettes :

➕ Créer une dette pour un client avec :

📅 Date, 💰 Montant total, 💵 Montant versé, 🧾 Montant restant, 🛒 Articles associés et paiements éventuels.

📋 Lister les dettes non soldées d’un client avec options :

Voir les articles associés.

Voir les paiements effectués.

Gestion des demandes de dette :

📋 Lister les demandes de dette avec filtres :

Par état (En cours, Annulé).

Voir les articles disponibles.

✅ Valider ou ❌ Refuser une demande de dette.

Paiements :

➕ Enregistrer un paiement pour une dette non soldée (montant restant > 0).

👨‍💻 Utilisateur de rôle Admin

Gestion des utilisateurs :

➕ Créer un compte utilisateur pour un client sans compte.

🔄 Créer ou modifier un compte utilisateur avec rôle : Boutiquier ou Admin.

🚫 Désactiver ou ✅ Réactiver un compte utilisateur.

📋 Lister les comptes utilisateurs par rôle ou actif uniquement.

Gestion des articles :

➕ Créer ou 📋 Lister les articles avec un filtre sur la disponibilité (quantité en stock > 0).

🔄 Mettre à jour la quantité en stock d’un article.

Gestion des dettes :

📦 Archiver les dettes soldées.

👤 Utilisateur de rôle Client

Gestion de ses dettes :

📋 Lister ses dettes non soldées avec options :

Voir les articles associés.

Voir les paiements effectués.

Demandes de dette :

➕ Créer une nouvelle demande de dette.

📋 Lister ses demandes de dette avec filtres :

Par état (En cours, Annulé).

✉️ Envoyer une relance pour une demande annulée.

🔒 Règles de Gestion

Un client peut avoir plusieurs dettes.

Une dette doit inclure au moins un article disponible.

Les paiements ne doivent pas dépasser le montant restant (sauf pour les clients fidèles).

⚙️ Prérequis Techniques

Backend

☕ Langage : Java (Java 8 ou plus récent).

🗄️ Base de données : MySQL ou PostgreSQL.

📦 Frameworks et bibliothèques :

Lombok : Pour simplifier le code.

JDBC : Pour une gestion directe de la base de données.

JPA (Hibernate) : Pour une gestion avancée des entités.

🚀 Installation

1️⃣ Clonez le projet

git clone <url_du_dépôt>

2️⃣ Configurer les dépendances avec Maven

Le projet utilise Maven. Vérifiez que Lombok est activé dans votre IDE, puis installez les dépendances :


mvn clean install

3️⃣ Configurer la base de données

Importez le fichier database.sql dans phpMyAdmin (MySQL) ou pgAdmin (PostgreSQL).

Modifiez les paramètres dans /ressources/META-INF/persistence.xml pour configurer les bases de données et version.
Et dans le config.Yaml pour configurer la version LIST,JDBC ou JPA ou choisir la base de données a choisir Postgresdettes OU MYSQLDETTES

4️⃣ Exécuter l’application

Compilez et exécutez depuis votre IDE ou via la ligne de commande :

OU dans Main.java

mvn exec:java

📂 Structure du Projet

/Entity : Gestion des entités (utilisateurs, clients, articles, dettes, paiements).

/repositories : Implémentation des accès aux données (LIST, JDBC, JPA).

/enum : Gestion des énumérations (états, types).

/core : Interfaces et configuration (Yaml, JPA).

/services : Règles métiers.

/Factory : Configuration 

/views : Interface console.

/controllers : Gestion des interactions avec XML

/META-INF: Interface JAVAFX

👨‍💻 Auteur

Ameth BA

🎓 Étudiant en Génie Logiciel

💡 Application conçue selon les meilleures pratiques pour une maintenance et une évolutivité optimales.
