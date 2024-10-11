# 

## DB Setup

### Create database with UTF-8 Support
`create database client_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`
## Create db user with permissions to db
`CREATE USER 'selis_user'@'localhost' IDENTIFIED BY 'waytoosecured';`
`GRANT ALL PRIVILEGES ON client_db.* TO 'selis_user'@'localhost';`
`FLUSH PRIVILEGES;`
