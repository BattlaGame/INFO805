# TP Compilation : Génération de code pour un sous ensemble du langage λ-ada

Ce projet est un générateur de code qui transforme un arbre syntaxique abstrait (AST) en code assembleur.

## Fonctionnalités

- **Nœuds de base** : Représentation des constantes, variables, et opérations binaires.
- **Structures de contrôle** : Implémentation de boucles `while` et de conditions `if`.
- **Fonctions lambda** : Support pour les fonctions anonymes récursives.
- **Génération de code** : Traduction de l'AST en instructions assembleur x86.

## Structure du Projet

- **Node** : Classe de base abstraite pour tous les nœuds de l'AST.
- **NumberNode** : Nœud pour les constantes entières.
- **VariableNode** : Nœud pour les variables.
- **BinaryOpNode** : Nœud pour les opérations binaires (addition, soustraction, multiplication, division, modulo).
- **LetNode** : Nœud pour les déclarations de variables.
- **WhileNode** : Nœud pour les boucles `while`.
- **LambdaNode** : Nœud pour les fonctions lambda.
- **IfNode** : Nœud pour les structures conditionnelles.
- **CodeGenerator** : Classe principale contenant des exemples d'utilisation de l'AST.

## Exemples

### Exemple 1 : Calcul de Prix TTC

Calcule le prix TTC à partir d'un prix HT en utilisant une multiplication et une division.

### Exemple 2 : PGCD avec Boucle While

Calcule le Plus Grand Commun Diviseur (PGCD) de deux nombres en utilisant une boucle `while`.

### Exemple 3 : PGCD avec Fonction Lambda Récursive

Calcule le PGCD de manière récursive en utilisant une fonction lambda.
