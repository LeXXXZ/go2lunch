### API v1.0
### curl samples (application deployed in application context `(go2lunch`).
> For windows use `Git Bash`

## Working with Users
#### get All Users
`curl -s http://localhost:8080/go2lunch/api/v1.0/admin/users --user admin@gmail.com:admin`
#### get User 100001
`curl -s http://localhost:8080/go2lunch/api/v1.0/admin/users/100001 --user admin@gmail.com:admin`
#### get User byEmail
`curl -s http://localhost:8080/go2lunch/api/v1.0/admin/users/by?email=user@yandex.ru --user admin@gmail.com:admin`
#### create User
`curl -s -X POST -d '{"name": "NewUser", "email": "new@mail.ru", "password": "password", "roles": ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/go2lunch/api/v1.0/admin/users --user admin@gmail.com:admin`
#### update User 100000
`curl -s -X PUT -d '{"name": "User", "email": "user@yandex.ru", "password": "Newpassword", "roles": ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/go2lunch/api/v1.0/admin/users/100000 --user admin@gmail.com:admin`
#### delete User 100020
`curl -s -v -X DELETE http://localhost:8080/go2lunch/api/v1.0/admin/users/100020 --user admin@gmail.com:admin`
#### disable User 100000
`curl -v -X PATCH -H "Content-type: application/json" http://localhost:8080/go2lunch/api/v1.0/admin/users/100000/?enabled=false --user admin@gmail.com:admin`

### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/go2lunch/api/v1.0/admin/users --user admin@gmail.com:admin`