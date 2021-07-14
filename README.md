# User Register service
> A simple user register service with a mongo database.

## The user properties

Mandatory for registration :  
   - firstname: String
- lastname: String
- birthDate: LocalDate 
- email : String
- password: String

Optional:  
   - region: String
   - peferredLanguage: String (FRENCH by default)



## The Endpoints

There are two endpoints:

- Register a new  user : POST `/user/register`
- Get a user's informations: GET /user/{userId}

Where `userId` represent the id of a user.

### User registration

To register a new user you have to send a POST request to `/user/register`, following these restrictions:

- firstname: not null and between 2 and 30 characters.
- lastname: not null and between 2 and 30 characters.
- password: not null, minimum 8 characters, containing at least 1 lowercase, 1 uppercase, 1 digit and 1 special character.
- birthdate: not null, a valid date in the format yyyy-mm-dd, the age of the user must be between 18 and 150.
- email: not null, not already used and a valid email format.
- preferredLanguage: null or one of the following values:
  - FRENCH,
  - ENGLISH,
  - SPANISH,
  - ITALIAN,
  - GERMAN
- region: null or one of the following values:
  - AUVERGNE_RHONE_ALPES,
  - BOURGOGNE_FRANCHE_COMTE,
  - BRETAGNE,
  - CENTRE_VAL_DE_LOIRE,
  - CORSE,
  - GRAND_EST,
  - HAUT_DE_FRANCE,
  - ILE_DE_FRANCE,
  - NORMANDIE,
  - NOUVELLE_AQUITAINE,
  - OCCITANIE,
  - PAYS_DE_LA_LOIRE,
  - PROVENCE_ALPES_COTE_D_AZUR

Example of a body to create a new user:

```json
{
	"firstname": "Jhon",
	"lastname": "DOE",
	"email": "jhon.doe@gmail.com",
	"birthDate": "1990-11-04",
	"password": "Ab1!cdef",
}
```

Possible return status:

- 201: User was correctly created
  - Return the user object containing these informations: id, firstname, lastname, email, birthDate, preferredLanguage, region.
- 400: The request was badly done
  - The name of the field with the corresponding error message.

### Retrieve user information

To retrieve a user's information send a GET request to `/user/{userId}`. Where `userId` is the id of the user.

Possible return status:

- 200: User's information retrieve
  - Return the user object containing these informations: id, firstname, lastname, email, birthDate, preferredLanguage, region.
- 404: The user was not found

## Database configuration

The database is a MongoDB instance.

The address of the server and the database to use are written in the application.properties file.

For example:

```
spring.data.mongodb.uri=mongodb://localhost:27017
spring.data.mongodb.database=user
```

On the MongoDB server side, you need to create a database, a setup a table named user.

After that you need to set one more Id, the default id is named `_id`, you need to create a regular and unique id named `email`.

