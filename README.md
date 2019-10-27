# Online Food Ordering System [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
The *Online Food Ordering System*(Foody) is a desktop app. It has a food ordering app for a customer and a separate app for order retrieval system for Restaurant.

## Features
- Authentication system guarding the app core.
- Customers can browse the menu, Add/remove items to cart, track the order and can make payment online.
- Customers can update password and can change the delivery address.
- Data stored in a MySQL database
- Friendly user interface with JavaFX

For screenshots [click here](https://winston-dsouza.github.io/#projects)

![login](https://github.com/winston-dsouza/winston-dsouza.github.io/blob/master/images/food/Login.png)
![menupage](https://github.com/winston-dsouza/winston-dsouza.github.io/blob/master/images/food/cart.png)

## Technologies
- [Java](https://go.java/index.html) - widely used object-oriented language, the core of our system
- [JavaFX](https://docs.oracle.com/javafx/2/overview/jfxpub-overview.htm) - Java user interface library
- [MySQL](https://www.mysql.com) - data storage solution
- [JFoenix](https://github.com/jfoenixadmin/JFoenix) - JavaFX Material Design Library

## Tools
- [Netbeans IDE 8.2](http://netbeans.apache.org/download)
- [XAMPP](https://www.apachefriends.org/download.html)
- [Scene Builder](https://gluonhq.com/products/scene-builder)

## Set Up Instructions For Running Application
1. Set up your [XAMPP](https://www.apachefriends.org/download.html) environment(Apache Server, phpMyAdmin).
2. Create the **foody** schema on the database tool, and import the database from the **SQL** file, or copy the **SQL** code.
3. Download the project of the application and open it in **NetBeans**.
4. Connect with the database in the Services tab under **MySQL Server(localhost)**. The MySQL **JDBC driver** is embedded in the project.
5. Enter your server properties(**Host Name, Port Number, Admin UserName/Password**).

*Note The [Foody](https://github.com/winston-dsouza/Online-Food-Ordering-System/tree/master/Foody) folder is Food Ordering App and 
[FoodyOrder](https://github.com/winston-dsouza/Online-Food-Ordering-System/tree/master/FoodyOrder) folder is order retrieve App code*
