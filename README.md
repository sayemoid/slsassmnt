# Installation Instruction

## Database Setup
- Download mysql and install in your system
- Or if you have docker,

  `docker run --name selis-mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306  -d mysql:lts`
- now, login to docker instance and create database

  `docker exec -it selis-mysql bash`
    `mysql -uroot -p` then use root password.

### Create database with UTF-8 Support

`create database client_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`

## Create db user with permissions to db
`CREATE USER 'selis_user'@'localhost' IDENTIFIED BY 'waytoosecured';`
`GRANT ALL PRIVILEGES ON client_db.* TO 'selis_user'@'localhost';`
`FLUSH PRIVILEGES;`

### When project runs, it'll automatically run the migration script containing table schema and seed data located in `src/main/resources/db/migration`. 



# Application

- clone project from github
    `git clone git@github.com:sayemoid/slsassmnt.git`
- cd into your project directory
- run `./gradlew bootRun`. This will directly run as a java application in port 8080 in localhost.
- Or to build jar file run `./gradlew clean test build assemble`. This will create jar files inside build/libs
- Run `java -jar build/libs/selis-0.0.1-SNAPSHOT.jar` to run application


# API Definitions

## Create API for account
Endpoint: `/api/v1/accounts`

Req payload:
```json
{
    "account_number": "XYZANK02857335",
    "full_name": "John Doe",
    "birth_date": "1990-10-11T15:32:48.942Z",
    "account_type": "PREMIUM"
}
```
Success Response:
```json
    "account_number": "XYZANK02857335",
    "full_name": "John Doe",
    "birth_date": "1990-10-11T15:32:48.942Z",
    "account_type": "PREMIUM",
    "account_status": "INACTIVE",
    "balance": 0,
    "last_tnx_date": null
}
```
Example Error Response ()
```json
{
    "code": 400,
    "status": "BAD_REQUEST",
    "message": "Account number must be unique",
    "exception": {...} // only throws in debug mode
```
## Activate Account
Endpoint: `/accounts/{accountNumber}/activate?status={ACTIVE/INACTIVE}`
Response: 
```json
{
    "account_number": "XYZANK02857335",
    "full_name": "John Doe",
    "birth_date": "1990-10-11T15:32:48.942Z",
    "account_type": "PREMIUM",
    "account_status": "ACTIVE",
    "balance": 0.0000,
    "last_tnx_date": null
}
```

## Get Account Details
Endpoint: `/api/v1/accounts/XYZANK02857335`
Response:
```json
{
    "account_number": "XYZANK02857335",
    "full_name": "John Doe",
    "birth_date": "1990-10-11T15:32:48.942Z",
    "account_type": "PREMIUM",
    "account_status": "INACTIVE",
    "balance": 0.0000,
    "last_tnx_date": null
}
```

## Transfer Balance
Endpoint: `/api/v1/accounts/transfer`
Payload:
```json
{
    "sender_account": "XYZANK02857335",
    "receiver_account": "XYZANK02857332", // receivers account number
    "amount": 50
}
```
Response:
```json
{
    "tnx_id": "103935"
}
```
