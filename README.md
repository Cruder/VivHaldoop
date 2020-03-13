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

Faites un

```sh
sbt package
ssh <user>@<domain> -t "mkdir -p app"
scp target/scala-2.12/vivhaldoop_2.12-0.1.jar <user>@<domain>:app/vivhaldoop.jar
ssh <user>@<domain> -t "
hdfs dfs -rm vivhaldoop.jar;
hdfs dfs -rm app.properties;
hdfs dfs -copyFromLocal app/vivhaldoop.jar vivhaldoop.jar;
hdfs dfs -copyFromLocal app/app.properties app.properties
"
```

Ceci vas générer, copier, et mettre sur le hdfs l'exécutable et sa configuration.

### Deploy travis CD

Il faudra ajouter un clef privé pour faire fonctionner travis avec le nouveau cluster.

https://oncletom.io/2016/travis-ssh-deploy/


## Structure du projet

La classe `HelloWorld` est le main de principal de l'application.
C'est lui qui vas récupérer les tweets.

La classe `KMeans` est celle qui vas appliquer l'algorithme des K-Means sur les données
collectée.

## Décisions du projet

![Adr](/doc/adr_schema.png)


### TOC

* [1. Utilisation de Scala](/doc/adr/0001-record-architecture-decisions.md)
* [2. Récupération tweeter](/doc/adr/0002-r-cup-ration-tweeter.md)
* [3. Utilisation des données équipe Nicolas](/doc/adr/0003-utilisation-des-donn-es-quipe-nicolas.md)
* [4. Nombre de jar](/doc/adr/0004-nombre-de-jar.md)
* [5. Stockage des données](/doc/adr/0005-stockage-des-donn-es.md)
