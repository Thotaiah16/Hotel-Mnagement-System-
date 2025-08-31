-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 27, 2025 at 07:54 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `java_hotel_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `booking_date` date NOT NULL,
  `check_in_date` date NOT NULL,
  `check_out_date` date NOT NULL,
  `status` varchar(50) DEFAULT 'CONFIRMED',
  `total_price` double NOT NULL,
  `room_no` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`id`, `user_id`, `room_id`, `booking_date`, `check_in_date`, `check_out_date`, `status`, `total_price`, `room_no`) VALUES
(1, 15, 1, '2025-08-24', '2025-08-25', '2025-08-26', 'CONFIRMED', 9500, '1'),
(2, 15, 1, '2025-08-24', '2025-08-25', '2025-08-31', 'CONFIRMED', 57000, '1'),
(3, 15, 3, '2025-08-24', '2025-08-24', '2025-08-26', 'CONFIRMED', 16000, '3'),
(4, 15, 7, '2025-08-24', '2025-08-24', '2025-08-25', 'CONFIRMED', 6500, '7'),
(5, 16, 10, '2025-08-25', '2025-08-27', '2025-08-29', 'CONFIRMED', 15000, NULL),
(6, 17, 5, '2025-08-25', '2025-08-26', '2025-08-27', 'CONFIRMED', 5500, NULL),
(7, 18, 11, '2025-08-26', '2025-08-27', '2025-08-28', 'CONFIRMED', 8000, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `Name` varchar(40) NOT NULL,
  `Address` varchar(40) NOT NULL,
  `Phone` bigint(20) NOT NULL,
  `Type` varchar(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`Name`, `Address`, `Phone`, `Type`) VALUES


-- --------------------------------------------------------

--
-- Table structure for table `food_items`
--

CREATE TABLE `food_items` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `price` int(11) NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `available` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food_items`
--

INSERT INTO `food_items` (`id`, `name`, `description`, `price`, `category`, `image_url`, `available`) VALUES
(1, 'Chicken Biryani', 'Delicious chicken biryani with aromatic spices', 250, 'MEAL', 'https://images.unsplash.com/photo-1563379091339-03246963d51a?w=400', 1),
(2, 'Masala Dosa', 'Crispy dosa with potato filling', 120, 'MEAL', 'https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400', 1),
(3, 'Mango Lassi', 'Sweet mango yogurt drink', 80, 'DRINK', 'https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400', 1);

-- --------------------------------------------------------

--
-- Table structure for table `food_orders`
--

CREATE TABLE `food_orders` (
  `id` bigint(20) NOT NULL,
  `user_id` int(11) NOT NULL,
  `food_item_id` bigint(20) NOT NULL,
  `food_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `order_date` datetime NOT NULL,
  `status` varchar(50) DEFAULT 'CONFIRMED',
  `special_instructions` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food_orders`
--

INSERT INTO `food_orders` (`id`, `user_id`, `food_item_id`, `food_name`, `quantity`, `total_price`, `order_date`, `status`, `special_instructions`) VALUES
(1, 15, 2, 'CHOCOLATE FONDUE', 1, 200, '2025-08-24 18:51:00', 'CONFIRMED', ''),
(2, 15, 1, 'SHAHI PANEER', 1, 500, '2025-08-24 18:58:28', 'CONFIRMED', ''),
(3, 15, 2, 'CHOCOLATE FONDUE', 1, 200, '2025-08-25 16:24:46', 'CONFIRMED', ''),
(4, 16, 2, 'CHOCOLATE FONDUE', 1, 200, '2025-08-25 20:56:14', 'CONFIRMED', ''),
(5, 17, 3, 'HYDERABADI MUTTON DUM BIRYANI', 1, 450, '2025-08-25 23:46:56', 'CONFIRMED', ''),
(6, 18, 11, 'HYDERABAD CHICKEN DUM  BIRYANI ', 1, 350, '2025-08-26 16:42:15', 'CONFIRMED', '');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `customer_name` varchar(100) NOT NULL,
  `customer_phone` varchar(20) DEFAULT NULL,
  `table_number` varchar(10) DEFAULT NULL,
  `special_instructions` text DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `order_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `customer_name`, `customer_phone`, `table_number`, `special_instructions`, `total_amount`, `order_date`, `status`) VALUES
(1, 'arjun', '9949472149', '07', 'manchiga cook chey ra unga bunga', 740, '2025-08-25 11:23:04', 'PENDING');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_type` varchar(20) NOT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `subtotal` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id`, `order_id`, `item_name`, `item_type`, `price`, `quantity`, `subtotal`) VALUES
(1, 1, 'Paneer Butter Masala', 'MEAL', 180, 2, 360),
(2, 1, 'CAPPUCCINO', 'DRINK', 180, 1, 180),
(3, 1, 'CHOCOLATE FONDUE', 'MEAL', 200, 1, 200);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant`
--

CREATE TABLE `restaurant` (
  `itemNo` int(11) NOT NULL,
  `itemName` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `Type` varchar(10) NOT NULL,
  `item_no` bigint(20) NOT NULL,
  `item_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `restaurant`
--

INSERT INTO `restaurant` (`itemNo`, `itemName`, `price`, `Type`, `item_no`, `item_name`) VALUES
(1, 'SHAHI PANEER', 500, 'MEAL', 0, NULL),
(2, 'CHOCOLATE FONDUE', 200, 'MEAL', 0, NULL),
(3, 'HYDERABADI MUTTON DUM BIRYANI', 450, 'MEAL', 0, NULL),
(4, 'PANEER MANCHURIAN', 350, 'MEAL', 0, NULL),
(5, 'PERI-PERI CHICKEN', 600, 'MEAL', 0, NULL),
(6, 'SPRITE', 100, 'DRINK', 0, NULL),
(7, 'VEGETABLE DUM BIRYANI', 200, 'MEAL', 0, NULL),
(8, 'CAPPUCCINO', 180, 'DRINK', 0, NULL),
(9, 'VIRGIN MOJITO', 100, 'DRINK', 0, NULL),
(10, 'MELON JUICE', 100, 'DRINK', 0, NULL),
(11, 'HYDERABAD CHICKEN DUM  BIRYANI ', 350, 'MEAL', 0, NULL),
(12, 'Chicken Biryani', 250, 'MEAL', 0, NULL),
(13, 'Paneer Butter Masala', 180, 'MEAL', 0, NULL),
(15, 'Masala Chai', 30, 'DRINK', 0, NULL),
(16, 'Fresh Lime Soda', 40, 'DRINK', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `restaurantcustomer`
--

CREATE TABLE `restaurantcustomer` (
  `custName` varchar(20) NOT NULL,
  `custAddr` varchar(20) NOT NULL,
  `Phone` bigint(20) NOT NULL,
  `Meal` varchar(20) NOT NULL,
  `Drink` varchar(20) NOT NULL,
  `Price` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `restaurantcustomer`
--

INSERT INTO `restaurantcustomer` (`custName`, `custAddr`, `Phone`, `Meal`, `Drink`, `Price`, `status`) VALUES

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `roomNo` int(11) NOT NULL,
  `roomType` varchar(20) NOT NULL,
  `bedType` varchar(20) NOT NULL,
  `Price` int(11) NOT NULL,
  `bed_type` varchar(255) NOT NULL,
  `room_no` int(11) NOT NULL,
  `room_type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `roomNo`, `roomType`, `bedType`, `Price`, `bed_type`, `room_no`, `room_type`) VALUES
(1, 1, 'SUITE', 'DOUBLE-BEDROOM', 9500, '', 0, ''),
(2, 2, 'DELUXE', 'SINGLE BEDROOM', 7500, '', 0, ''),
(3, 3, 'AC', 'DOUBLE-BEDROOM', 8000, '', 0, ''),
(4, 4, 'NON-AC', 'SINGLE BEDROOM', 3500, '', 0, ''),
(5, 5, 'SUITE', 'SINGLE BEDROOM', 5500, '', 0, ''),
(6, 6, 'DELUXE', 'DOUBLE-BEDROOM', 7000, '', 0, ''),
(7, 7, 'AC', 'DOUBLE-BEDROOM', 6500, '', 0, ''),
(8, 8, 'NON-AC', 'SINGLE BEDROOM', 5500, '', 0, ''),
(9, 101, 'SUITE', 'DOUBLE-BEDROOM', 9500, '', 0, ''),
(10, 102, 'DELUXE', 'SINGLE BEDROOM', 7500, '', 0, ''),
(11, 103, 'AC', 'DOUBLE-BEDROOM', 8000, '', 0, ''),
(12, 104, 'NON-AC', 'SINGLE BEDROOM', 3500, '', 0, ''),
(13, 105, 'SUITE', 'SINGLE BEDROOM', 5500, '', 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `roomcutomer`
--

CREATE TABLE `roomcutomer` (
  `name` varchar(20) NOT NULL,
  `address` varchar(20) NOT NULL,
  `phone` bigint(20) NOT NULL,
  `roomtype` varchar(10) NOT NULL,
  `bedtype` varchar(20) NOT NULL,
  `price` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `roomno` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roomcutomer`
--

INSERT INTO `roomcutomer` (`name`, `address`, `phone`, `roomtype`, `bedtype`, `price`, `status`, `roomno`) VALUES
('arjujn', 'something', 9949472149, 'SUITE', 'DOUBLE-BEDROOM', 57000, 1, 1),
('raju', 'adfhad', 9949472149, 'AC', 'DOUBLE-BEDROOM', 40000, 0, 103),
('dinesh', 'nekuendhukuraa', 7992224555, 'AC', 'DOUBLE-BEDROOM', 40000, 0, 3);

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `id` bigint(20) NOT NULL,
  `bed_type` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `room_no` varchar(255) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password_hash`, `email`, `mobile_number`, `role`, `is_active`, `created_at`) VALUES
(1, 'admin', '$2a$10$Pn2ElQvNdN4mBXt2fuca2uw.agaW7i4SU9FyS12u/wbh377wOC4u6', 'admin@hotel.com', NULL, 'admin', 1, '2025-08-19 11:16:44');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `food_items`
--
ALTER TABLE `food_items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `food_orders`
--
ALTER TABLE `food_orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`itemNo`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `roomNo` (`roomNo`);

--
-- Indexes for table `roomcutomer`
--
ALTER TABLE `roomcutomer`
  ADD PRIMARY KEY (`roomno`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `food_items`
--
ALTER TABLE `food_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `food_orders`
--
ALTER TABLE `food_orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `itemNo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `roomcutomer`
--
ALTER TABLE `roomcutomer`
  MODIFY `roomno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
