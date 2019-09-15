### API v1.0
### curl samples (application deployed in application context `(go2lunch`).
> For windows use `Git Bash`
## Working with Restaurants
#### get All Restaurants 
`curl -s http://localhost:8080/go2lunch/api/v1.0/restaurants`
#### get Restaurant 100003
`curl -s http://localhost:8080/go2lunch/api/v1.0/restaurants/100003`
#### get Restaurant not found
`curl -s -v http://localhost:8080/go2lunch/api/v1.0/restaurants/404 `
#### delete Restaurant
`curl -s -X DELETE http://localhost:8080/go2lunch/api/v1.0/restaurants/100002 --user admin@gmail.com:admin`
#### create Restaurant
`curl -s -X POST -d '{"id":null,"name":"Mama Roma","menus":null}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/go2lunch/api/v1.0/restaurants --user admin@gmail.com:admin`
#### update Restaurant
`curl -s -X PUT -d '{"name":"BurgerKing"}' -H 'Content-Type: application/json' http://localhost:8080/go2lunch/api/v1.0/restaurants/100003 --user admin@gmail.com:admin`

### validate with Error
`curl -s -X PUT -d '{"id":"404"}' -H 'Content-Type: application/json' http://localhost:8080/go2lunch/api/v1.0/restaurants/100003 --user admin@gmail.com:admin`
