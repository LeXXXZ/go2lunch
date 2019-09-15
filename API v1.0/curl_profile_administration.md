### API v1.0
### curl samples (application deployed in application context `(go2lunch`).
> For windows use `Git Bash`

## Working with UserProfile
#### get User Profile
`curl -s http://localhost:8080/go2lunch/api/v1.0/profile/ --user user@yandex.ru:password`
#### register new User
`curl -s -X POST -d '{"name": "NewUser", "email": "new@mail.ru", "password": "password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/go2lunch/api/v1.0/profile/register`
#### update User Profile
`curl -s -X PUT -d '{"name": "UpdatedUser", "email": "user@yandex.ru", "password": "password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/go2lunch/api/v1.0/profile --user user@yandex.ru:password`
#### delete User Profile
`curl -s -v -X DELETE http://localhost:8080/go2lunch/api/v1.0/profile/  --user user@yandex.ru:password`

#### get All Restaurants with votes
`curl -s http://localhost:8080/go2lunch/api/v1.0/profile/restaurants --user user@yandex.ru:password`
#### check restaurant 100003 is voted
`curl -s http://localhost:8080/go2lunch/api/v1.0/profile/restaurants/100003/vote --user user@yandex.ru:password`
#### get Restaurant not found
`curl -s -v http://localhost:8080/go2lunch/api/v1.0/profile/restaurants/404/vote --user user@yandex.ru:password`
#### vote for Restaurant 100003
`curl -s -X POST -d '{}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/go2lunch/api/v1.0/profile/restaurants/100003/vote --user user@yandex.ru:password`