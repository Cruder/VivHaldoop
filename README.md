# VivHaldoop [![Build Status](https://travis-ci.org/Cruder/VivHaldoop.svg?branch=master)](https://travis-ci.org/Cruder/VivHaldoop)

## Lancement des tests

Pour lancer les tests, faites la commande suivante :

```sh
sbt test
```

Vous pouvez générer le coverage du tests avec

```sh
sbt clean coverage test coverageReport
```

## Deploy de l'application sur un autre Cluster

Faites

```sh
cp app.template.properties app.properties
```

Ensuite modifier le fichier en mettant vos tokens twitter.

## Structure du projet

La classe `HelloWorld` est le main de principal de l'application.
C'est lui qui vas récupérer les tweets.

<!-- La suite a compléter avec l'équipe -->

## Décisions du projet

![Adr](/doc/adr_schema.png)


### TOC

* [1. Utilisation de Scala](0001-record-architecture-decisions.md)
* [2. Récupération tweeter](0002-r-cup-ration-tweeter.md)
* [3. Utilisation des données équipe Nicolas](0003-utilisation-des-donn-es-quipe-nicolas.md)
* [4. Nombre de jar](0004-nombre-de-jar.md)
* [5. Stockage des données](0005-stockage-des-donn-es.md)
