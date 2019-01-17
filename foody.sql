-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 12, 2018 at 03:13 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `foody`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `order_list` ()  NO SQL
SELECT o.order_id ,o.customer_id,m.menu_name,p.payment_type,CONCAT(c.phone_no,' ,',c.state,' ,',c.city,' ,',c.landmark,' ,',c.pincode) AS Address,o.quantity as Qnt FROM orders o INNER JOIN menu m ON m.menu_id=o.menu_id INNER JOIN payment p ON p.order_id=o.order_id INNER JOIN customer c ON c.customer_id=o.customer_id WHERE o.order_status='PAYMENT_CONFIRMED' ORDER BY p.time_stamp ASC$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email_id` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_no` varchar(10) NOT NULL,
  `state` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `landmark` varchar(255) NOT NULL,
  `pincode` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `first_name`, `last_name`, `email_id`, `password`, `phone_no`, `state`, `city`, `landmark`, `pincode`) VALUES
(1, 'mark', 'berg', 'berg11@gmail.com', '12345', '9111111111', 'karnataka', 'mangalore', 'state bank', 574154),
(2, 'winston', 'dsouza', 'winstonds12@gmail.com', '123456', '9764316497', 'karnataka', 'mangalore', 'urva store', 574154),
(3, 'Sheldon', 'Sam', 'sam12@gmail.com', '2345', '9888888856', 'karnataka', 'mangalore', 'urva store', 574154),
(4, 'kishore', 'kumar', 'kumar45@gmail.com', '12345', '9865326598', 'karnataka', 'Bangalore', 'hsr layout', 50004),
(5, 'bob', 'sin', 'bob14@gmail.com', '45698', '9081649731', 'karnataka', 'bangalore', 'bda complex hbr layout', 560102),
(6, 'meril', 'dsouza', 'meril11@gmail.com', '123456', '9632895563', 'karnataka', 'bangalore', 'vijayanagar vijaya bank layout', 560040),
(7, 'max', 'dsouza', 'max12@gmail.com', '123456', '9741628856', 'karnataka', 'mangalore', 'urva store', 574154),
(8, 'maxton', 'mosses', 'mos12@gmail.com', '123', '9741628856', 'karnataka', 'bangalore', 'city centre mall mg road', 574154),
(9, 'Feona', 'Melisa', 'melisa@gmail.com', '123', '9101928856', 'karnataka', 'mangalore', 'near state bank circle', 574154);

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `menu_id` int(255) NOT NULL,
  `menu_name` varchar(255) NOT NULL,
  `price` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`menu_id`, `menu_name`, `price`) VALUES
(1, 'American Pizza', 350),
(2, 'Veg Pizza', 250),
(3, 'Chicken Pizza', 400),
(4, 'Pepperroni Pizza', 450),
(5, 'Veg Burger', 50),
(6, 'Chicken Burger', 80),
(7, 'Power Burger', 300),
(8, 'Sandwich Burger', 180),
(9, 'Gulab Jamun', 200),
(10, 'Chocholate Moose', 250),
(11, 'Naugat Moose', 300),
(12, 'Belgium Waffle', 150);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(255) NOT NULL,
  `customer_id` int(255) NOT NULL,
  `menu_id` int(255) NOT NULL,
  `quantity` int(255) NOT NULL DEFAULT '1',
  `order_status` enum('ADDED_TO_CART','CONFIRMED','PAYMENT_CONFIRMED','DELIVERED') DEFAULT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `customer_id`, `menu_id`, `quantity`, `order_status`, `time_stamp`) VALUES
(138, 2, 8, 2, 'DELIVERED', '2018-11-06 12:58:42'),
(139, 2, 4, 2, 'DELIVERED', '2018-11-06 12:58:37'),
(140, 2, 2, 2, 'DELIVERED', '2018-11-06 12:58:53'),
(141, 2, 1, 2, 'DELIVERED', '2018-11-06 12:58:47'),
(143, 2, 1, 1, 'DELIVERED', '2018-11-07 04:21:26'),
(146, 2, 12, 1, 'DELIVERED', '2018-11-07 05:43:38'),
(147, 2, 11, 1, 'DELIVERED', '2018-11-07 05:43:42'),
(148, 2, 1, 1, 'DELIVERED', '2018-11-07 14:12:03'),
(149, 2, 8, 2, 'DELIVERED', '2018-11-12 09:55:38'),
(150, 2, 7, 2, 'DELIVERED', '2018-11-10 15:28:08'),
(151, 2, 9, 2, 'PAYMENT_CONFIRMED', '2018-11-11 04:36:31'),
(152, 2, 10, 1, 'PAYMENT_CONFIRMED', '2018-11-11 04:36:31'),
(153, 2, 1, 2, 'DELIVERED', '2018-11-12 09:55:47'),
(156, 2, 6, 2, 'DELIVERED', '2018-11-12 04:31:09'),
(159, 9, 1, 1, 'ADDED_TO_CART', '2018-11-11 07:59:28'),
(160, 2, 1, 2, 'DELIVERED', '2018-11-12 04:16:04'),
(162, 2, 1, 2, 'PAYMENT_CONFIRMED', '2018-11-12 09:54:04'),
(165, 2, 1, 1, 'DELIVERED', '2018-11-12 10:58:53');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` int(255) NOT NULL,
  `order_id` int(255) NOT NULL,
  `payment_type` enum('CASH_ON_DELIVERY','ONLINE_PAYMENT') NOT NULL DEFAULT 'CASH_ON_DELIVERY',
  `payment_status` enum('NOT_CONFIRMED','CONFIRMED') NOT NULL DEFAULT 'NOT_CONFIRMED',
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id`, `order_id`, `payment_type`, `payment_status`, `time_stamp`) VALUES
(209, 138, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-06 12:53:56'),
(210, 139, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-06 12:53:56'),
(212, 140, 'ONLINE_PAYMENT', 'CONFIRMED', '2018-11-06 12:57:37'),
(213, 141, 'ONLINE_PAYMENT', 'CONFIRMED', '2018-11-06 12:57:37'),
(215, 143, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-06 17:43:49'),
(216, 146, 'ONLINE_PAYMENT', 'CONFIRMED', '2018-11-07 04:20:01'),
(217, 147, 'ONLINE_PAYMENT', 'CONFIRMED', '2018-11-07 04:20:01'),
(219, 148, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-07 05:44:28'),
(220, 149, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-09 07:54:10'),
(221, 150, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-09 07:54:10'),
(223, 151, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-11 04:36:31'),
(224, 152, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-11 04:36:31'),
(225, 153, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-11 04:36:31'),
(237, 156, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-11 06:29:11'),
(238, 160, 'CASH_ON_DELIVERY', 'CONFIRMED', '2018-11-12 04:15:10'),
(239, 162, 'ONLINE_PAYMENT', 'CONFIRMED', '2018-11-12 09:54:04'),
(240, 165, 'ONLINE_PAYMENT', 'CONFIRMED', '2018-11-12 10:57:53');

-- --------------------------------------------------------

--
-- Table structure for table `payment_details`
--

CREATE TABLE `payment_details` (
  `payment_id` int(255) NOT NULL,
  `customer_id` int(255) NOT NULL,
  `card_number` varchar(16) NOT NULL,
  `card_holder_name` varchar(255) NOT NULL,
  `cvv` int(3) NOT NULL,
  `exp_month` int(2) NOT NULL,
  `exp_year` int(4) NOT NULL,
  `time_stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment_details`
--

INSERT INTO `payment_details` (`payment_id`, `customer_id`, `card_number`, `card_holder_name`, `cvv`, `exp_month`, `exp_year`, `time_stamp`) VALUES
(8, 2, '1234123412341234', 'winston', 123, 10, 25, '2018-11-04 04:21:29'),
(9, 2, '1234123412341234', 'winston', 123, 12, 34, '2018-11-06 09:21:19'),
(10, 2, '1234123412341234', 'winston', 123, 3, 19, '2018-11-06 12:57:36'),
(11, 2, '1234123412341234', 'winston', 122, 12, 22, '2018-11-07 04:20:01'),
(12, 2, '1234123412341234', 'winston', 123, 12, 22, '2018-11-12 09:54:04'),
(13, 2, '1234123412341234', 'wins', 123, 12, 22, '2018-11-12 10:57:53');

-- --------------------------------------------------------

--
-- Table structure for table `restaurant`
--

CREATE TABLE `restaurant` (
  `restaurant_id` int(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `designation` enum('EMPLOYEE','ADMIN') NOT NULL DEFAULT 'EMPLOYEE'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `restaurant`
--

INSERT INTO `restaurant` (`restaurant_id`, `password`, `first_name`, `last_name`, `designation`) VALUES
(101, '123456', 'Winston', 'Dsouza', 'ADMIN'),
(1001, '1234', 'Sham', 'ram', 'EMPLOYEE'),
(1005, '12345', 'Mahesh', 'Kanth', 'EMPLOYEE'),
(1009, '1234', 'kiran', 'nath', 'EMPLOYEE');

--
-- Triggers `restaurant`
--
DELIMITER $$
CREATE TRIGGER `res_id` BEFORE INSERT ON `restaurant` FOR EACH ROW BEGIN
 SET NEW.restaurant_id = (SELECT MAX(restaurant_id) + 4 FROM restaurant);
 END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `email_id` (`email_id`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`menu_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `menu_id` (`menu_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `payment_details`
--
ALTER TABLE `payment_details`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`restaurant_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `menu_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=166;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=241;

--
-- AUTO_INCREMENT for table `payment_details`
--
ALTER TABLE `payment_details`
  MODIFY `payment_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `restaurant_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1010;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Constraints for table `payment_details`
--
ALTER TABLE `payment_details`
  ADD CONSTRAINT `payment_details_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
