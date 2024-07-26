# List des routes de l'api des Olympe Backend


Ceci est un gestionnaire par API des épreuves associatives, à la base conçu exclusivement pour les JO, avec des tests unitaires.

Selon les droits et les rôles attribués, certaines routes ne sont pas disponibles pour tous.

Il y a beaucoup de contrôles au niveau des annotations ainsi que des contrôleurs.Ceci est un gestionnaire par API des épreuves associatives, à la base conçu exclusivement pour les JO, avec des tests unitaires.

Selon les droits et les rôles attribués, certaines routes ne sont pas disponibles pour tous.

Il y a beaucoup de contrôles au niveau des annotations ainsi que des contrôleurs.



- **GitHub Actions** : Automatisation du déploiement pour garantir que le site est toujours à jour en production.


## Public

### Inscription
Pour s'inscrire.

*POST route :*
```
http://localhost:8080/auth/signup
```
body:

```
{
    "email": "test@gmail.com",
    "password": "Test91!eeee",
    "fullName": "efrei"
}
```
### Login

Pour se connecter .

*POST route :* 

```
http://localhost:8080/auth/login
```

body:

```
{
    "email": "test@gmail.com",
    "password": "Test91!eeee",
}
```

Par default toute les personnes qui s'inscrivent sont en user

## Evenement
Pour avoir une liste d'evenement .

*route GET*
```
http://localhost:8080/evenements
```

Créer un evenement methode Post
```
http://localhost:8080/evenements
```

body exemple
```
        {
        "totalSeats": 10,  
        "availableSeats": 10,    
        "standartPrice": 2.2,
        "name": "evenement",
        "dateEvent": "2024-12-31",
        "hourBegin": "08:30:00",
        "hourEnding": "17:00:00"
    }
    
```

Update totalement un evenement methode put
```
http://localhost:8080/evenements/{uuid}
```


Supprimer un evenement methode DELETE
```
http://localhost:8080/evenements/{uuid}
```


## User

Pour le role user voici les routes disponibles.
#### Ticket

Faire attention si un evenement n'as plus de place disponible vous ne pouvez pas achetez de ticket


*method POST*

acheter un ticket
```
http://localhost:8080/evenements/{eventId}/acheterTicket
```
reserver un ticket
```
http://localhost:8080/evenements/{eventId}/bookTicket
```

body:

```
{
    "name": "name test",
    "lastName": "lastname test"
}
```




#### Profil

consulter son profil method get

```
http://localhost:8080/users/informations/{id}
```


modifier ses informations  methode put
```
http://localhost:8080/users/{id}
```

body
```
{
    "email": "10",  
    "fullName": "test"    
}
```


modifier son mot de passe   methode patch
```
http://localhost:8080/users/{id}/updatePassword
```

body
```
{
"password": "Test91450!"
}
```

consulter son historique  methode get
```
http://localhost:8080/users/tickets/{uuid}
```

## Admin

#augmenter le nombre total de billets,

Post 
```
http://localhost:8080/evenements/{eventId}/improveSeats
```
body (on augmente de 12 par exemple)
```
seats = 12
```




## SuperAdmin

TODO

