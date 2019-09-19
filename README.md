Go2Lunch Project 
===============================

A voting system for deciding where to have lunch.

REST API using Hibernate, Spring/Data-JPA/MVC/Security **without frontend**.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

### <a href="https://github.com/LeXXXZ/go2lunch/tree/master/API%20v1.0" target=_blank>API Documentation</a>
* ##### <a href="https://github.com/LeXXXZ/go2lunch/blob/master/API%20v1.0/curl_profile_administration.md" target=_blank>Working with UserProfile</a>
* ##### <a href="https://github.com/LeXXXZ/go2lunch/blob/master/API%20v1.0/curl_restaurant_administration.md" target=_blank>Restaurants administration</a>
* ##### <a href="https://github.com/LeXXXZ/go2lunch/blob/master/API%20v1.0/curl_users_administration.md" target=_blank>Users administration </a>

#### After deploying to Tomcat at http://localhost:8080/go2lunch
Today restaurants menus could be requested without authentication at:
http://localhost:8080/go2lunch/api/v1.0/today-menu

For all other actions authentication is necessary:
* for Admin role 
##### admin@gmail.com : admin
* for User role 
##### user@yandex.ru : password
