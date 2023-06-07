# ioBuilders Bank

## Run Intructions

This is a self contained maven & spring-boot project with no external dependencies so to run it, just run the command "mvn spring-boot:run" from the boot module and it should start properly.

## API

The interface provided by the service is a REST API. The operations are as follows:

### POST /api/v1/users

Creates a new user in the system.

**Body** _required_ The information about the user to create.

**Content Type** `application/json`

Sample:

```json
{
  "name": "Diego Armando",
  "email": "el10@gmail.com",
  "fiscal_address": "Fake Street 321",
  "nif": "11223344B"
}
```

Responses:

* **200 OK** When the user is created successfully.

### GET /api/v1/users/{user_id}

Finds an user by its unique identifier.

Responses:

* **200 OK** When the user is found with its information.
* **404 NOT_FOUND** When the user requested is not found.

### POST /api/v1/wallet

Creates a new wallet in the system with an initial balance.

**Body** _required_ Information about the wallet to create.

**Content Type** `application/json`

Sample:

```json
{
  "owner_id": 2,
  "balance": 1000.10
}
```

Responses:

* **200 OK** When the wallet is created successfully.
* **400 Bad Request** When the amount specified is negative or the owner does not exists in the system.

### GET /api/v1/wallet/{wallet_id}

Finds an wallet by its unique identifier. It will return the transfers associated to the wallet.

Responses:

* **200 OK** When the wallet is found with its information.
* **404 NOT_FOUND** When the wallet requested is not found.

### POST /api/v1/deposit

Creates a new deposit in the system adding the amount specified to the wallet specified.

**Body** _required_ Information about the deposit to create.

**Content Type** `application/json`

Sample:

```json
{
  "destination_wallet_id": 1,
  "amount": 50.50
}
```

Responses:

* **200 OK** When the deposit is created successfully.
* **404 Not Found** When the wallet specified does not exist in the system.
* **400 Bad Request** When the amount specified is negative.

### POST /api/v1/transference

Creates a new transfer in the system adding the amount specified to the destination wallet and subtracting it from the origin one.

**Body** _required_ Information about the transfer to create.

**Content Type** `application/json`

Sample:

```json
{
  "destination_wallet_id": 2,
  "origin_wallet_id": 1,
  "amount": 30.30
}
```

Responses:

* **200 OK** When the transfer is created successfully.
* **404 Not Found** When any of the wallets specified does not exist in the system.
* **400 Bad Request** When the amount specified is negative.