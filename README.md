# List des routes de l'api des jo

Par Asâad M.
Ceci est un gestionaire par api des epreuves des jo avec en bonus des tests unitaires.


Selon les droits et les roles attribué certaines routes ne sont pas disponible pour tous

faire aussi attention j'ai fait beaucoup de controles au niveau des creations et mises a jours

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

Supprimer un evenement methode DELETE
```
http://localhost:8080/evenements/{uuid}
```


## User

Pour le role user voici les routes disponibles.
#### Ticket

faire attention si un evenement n'as plus de place disponible vous ne pouvez pas achetez de ticket


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

*method PATCH*

payer un ticket reserver
```
http://localhost:8080/tickets/{ticketId}/paidBookTicket
```

*method POST*


acheter un lot de ticket avec different promotion en fonction du nombre de place
```
http://localhost:8080/evenements/{eventId}/acheterLotTicket
```

body:

```
[
{
    "name": "name test",
    "lastName": "lastname test"
},
{
    "name": "name test 2",
    "lastName": "lastname test 2"
},
.
.
.
{
    "name": "name test n ",
    "lastName": "lastname test n"
}
]
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

ceci en bas sont ceux que je n'ai aps eu le temps de faire serieusement ni tester

annuler un evenement  #fait a testet


créer un user # fait a tester

changer les information d'un user #fait a tester
changer le role d'un user a admin #fait a tester



## SuperAdmin

herite de admin sauf qu'il peux en plus changer le role d'un admin et supprimer un admin


