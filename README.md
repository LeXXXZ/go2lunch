Go2Lunch Project 
===============================

A voting system for deciding where to have lunch.

REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

### <a href="http://go2lunch.herokuapp.com/" target=_blank>Demo @heroku</a>
