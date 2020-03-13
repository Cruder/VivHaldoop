# 2. Récupération tweeter

Date: 2020-03-13

## Status

Accepted

Superceded by [3. Utilisation des données équipe Nicolas](0003-utilisation-des-donn-es-quipe-nicolas.md)

## Context

Il faut trouver un moyen de récupérer les tweets sur tweeter.

Il y a le choix entre faire une requete toute les heures avec un filtre, ou bien
les récupérer en streaming.

## Decision

Le streaming est le plus optimal pour récupérer les tweets.

## Consequences

Utilisation de la lib Hadoop tweets streaming.
