## Visits API

A microservice to manager data about visits of users.

## API

`POST https://visits-api.herokuapp.com/api/v1/user`
* Creates a user in the db
* `name` should be a non-empty, alphanumeric value

*Example JSON body*
```json
{
	"name": "Randy Marsh"
}
```

`POST https://visits-api.herokuapp.com/api/v1/visit`
* Creates a visit for the specified user
* `location` should be a non-empty, alphanumeric value
* `userId` is the ID provided to you when the user record was created via the `POST /user` endpoint

*Example JSON body*
```json
{
	"location": "Whole Foods, Columbus Circle",
	"userId": 1
}
```

`GET https://visits-api.herokuapp.com/api/v1/visit/:visitId`
* Required path parameter: `visitId`
* Returns exactly one Visit object in a JSON array pertainin to the visitId

*Example response*
```json
[
    {
        "visitId": 4,
        "userId": 1,
        "location": "Trader Joe's, DUMBO"
    }
]
```

`GET https://visits-api.herokuapp.com/api/v1/visit?userId={value}&searchString={value}`
* Required query parameters: `userId` & `searchString`
* Returns JSON array of visit objects. These objects are queried using fuzzy matching.

*Example response*
```json
[
    {
        "visitId": 12,
        "userId": 1,
        "location": "wasHingTon STATE"
    },
    {
        "visitId": 11,
        "userId": 1,
        "location": "WASHINGTON DC"
    },
    {
        "visitId": 10,
        "userId": 1,
        "location": "WASHINGTON HEIGHTS"
    },
    {
        "visitId": 5,
        "userId": 1,
        "location": "washington square park"
    }
]
```