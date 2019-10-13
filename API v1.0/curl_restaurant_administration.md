### API v1.0
### curl samples (application deployed in application context `(go2lunch`).
> For windows use `Git Bash`
## Working with Restaurants
#### get All Restaurants 
`curl -s http://localhost:8080/go2lunch/api/v1.0/admin/restaurants --user admin@gmail.com:admin`
#### get Restaurant 100010
`curl -s http://localhost:8080/go2lunch/api/v1.0/admin/restaurants/100010 --user admin@gmail.com:admin`
#### get Restaurant not found
`curl -s -v http://localhost:8080/go2lunch/api/v1.0/admin/restaurants/404 --user admin@gmail.com:admin`
#### delete Restaurant
`curl -s -X DELETE http://localhost:8080/go2lunch/api/v1.0/admin/restaurants/100011 --user admin@gmail.com:admin`
#### create Restaurant
`curl -s -X POST -d '{"id":null,"name":"Mama Roma"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/go2lunch/api/v1.0/admin/restaurants --user admin@gmail.com:admin`
#### update Restaurant
`curl -s -X PUT -d '{"id":100010,"name":"BurgerKing"}' -H 'Content-Type: application/json' http://localhost:8080/go2lunch/api/v1.0/admin/restaurants/100010 --user admin@gmail.com:admin`

## Working with Menus
#### get All Menus of Restaurant 100010
`curl -s http://localhost:8080/go2lunch/api/v1.0/admin/restaurants/100010/menus --user admin@gmail.com:admin`
#### get Menu 100026 of Restaurant 100010
`curl -s http://localhost:8080/go2lunch/api/v1.0/admin/restaurants/100010/menus/100026 --user admin@gmail.com:admin`
#### update Menu 100026 of Restaurant 100010
`curl -s -X PUT -d '{"id":100026,"name":"updatedBigMac","price":2222,"date":"2019-10-13"}' -H 'Content-Type: application/json' http://localhost:8080/go2lunch/api/v1.0/admin/restaurants/100010/menus/100026 --user admin@gmail.com:admin`
#### create Menu of Restaurant 100010
`curl -s -X POST -d '{"name":"Cola","price":1111,"date":"2019-10-13"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/go2lunch/api/v1.0/admin/restaurants/100010/menus --user admin@gmail.com:admin`
