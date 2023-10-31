-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 24, 2022 at 10:38 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hy351`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `admin_id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(32) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`admin_id`, `username`, `email`, `password`, `firstname`, `lastname`) VALUES
(1, 'admin', 'admin@gmail.com', 'admin23', 'Mike', 'Chaniotakis');

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `doctor_id` int(11) NOT NULL,
  `hospital_id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(32) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `birthdate` date NOT NULL,
  `gender` varchar(7) NOT NULL,
  `telephone` varchar(14) NOT NULL,
  `specialty` varchar(30) NOT NULL,
  `doctor_info` varchar(500) NOT NULL,
  `certified` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`doctor_id`, `hospital_id`, `username`, `email`, `password`, `firstname`, `lastname`, `birthdate`, `gender`, `telephone`, `specialty`, `doctor_info`, `certified`) VALUES
(1, 1, 'papadakis', 'papadakis@doctor.gr', '123456', 'NIKOS', 'PAPADAKIS', '1980-02-01', 'Male', '2810569874', 'GeneralDoctor', 'Exei megali empiria se axiologisi emvoliwn covid.', 1),
(2, 1, 'peristeris', 'peristeris@gmail.com', 'asd@dsfdsf3', 'MARINOS', 'PERISTERIS', '1990-01-01', 'Male', '2810564574', 'Pathologist', 'Foveros sta panda', 0),
(3, 1, 'giannis', 'giannis@doctor.gr', '123456', 'IOANNIS', 'PAPADOPOULOS', '1980-02-01', 'Male', '2810569874', 'GeneralDoctor', 'Poly kalos ston tomea tou', 0);

-- --------------------------------------------------------

--
-- Table structure for table `hospital`
--

CREATE TABLE `hospital` (
  `hospital_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `address` varchar(50) NOT NULL,
  `addr_no` int(11) NOT NULL,
  `city` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hospital`
--

INSERT INTO `hospital` (`hospital_id`, `name`, `address`, `addr_no`, `city`) VALUES
(1, 'PAGNI', 'Panepistimiou', 80, 'Heraklion'),
(2, 'Vanizeleio', 'Knossou', 44, 'Heraklion'),
(3, 'Asklipieion', 'Zografou', 8, 'Heraklion');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `message_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `message` varchar(1000) NOT NULL,
  `sender` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`message_id`, `user_id`, `date_time`, `message`, `sender`) VALUES
(1, 1, '2022-05-12 16:00:35', 'Den eimai kala', 'user'),
(2, 2, '2022-05-23 16:34:24', 'To epwnymo mou thelei y oxi i', 'user'),
(3, 2, '2022-05-23 16:34:28', 'Mporeis na mou allaxeis to amka mou?', 'user');

-- --------------------------------------------------------

--
-- Table structure for table `nurse`
--

CREATE TABLE `nurse` (
  `nurse_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `hospital_id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(32) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `birthdate` date NOT NULL,
  `gender` varchar(7) NOT NULL,
  `telephone` varchar(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `nurse`
--

INSERT INTO `nurse` (`nurse_id`, `doctor_id`, `hospital_id`, `username`, `email`, `password`, `firstname`, `lastname`, `birthdate`, `gender`, `telephone`) VALUES
(1, 1, 1, 'anagnostou', 'anagnostou@yahoo.gr', 'nurse87', 'AMALIA', 'ANAGNOSTOU', '1976-05-05', 'Female', '2810123456'),
(2, 1, 1, 'nurse233', 'nurse@nurse.com', 'nurse12', 'MARINA', 'TSELEPI', '1990-01-01', 'Female', '2851336656');

-- --------------------------------------------------------

--
-- Table structure for table `randevouz`
--

CREATE TABLE `randevouz` (
  `randevouz_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `hospital_id` int(11) NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(15) NOT NULL,
  `vaccine` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `randevouz`
--

INSERT INTO `randevouz` (`randevouz_id`, `doctor_id`, `user_id`, `hospital_id`, `date_time`, `status`, `vaccine`) VALUES
(1, 1, 1, 1, '2022-03-01 14:52:16', 'done', 'Pfizer'),
(4, 1, 1, 1, '2022-03-23 14:29:06', 'done', 'Pfizer'),
(5, 1, 1, 1, '2022-04-13 13:28:20', 'done', 'Pfizer'),
(6, 0, 2, 2, '2022-05-24 12:44:14', 'done', 'Moderna'),
(7, 0, 2, 2, '2022-06-13 16:10:52', 'selected', 'Moderna'),
(8, 0, 2, 2, '2022-07-03 15:57:09', 'selected', 'Moderna');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `gender` varchar(7) DEFAULT NULL,
  `amka` varchar(11) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `addr_no` int(11) DEFAULT NULL,
  `telephone` varchar(14) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `email`, `password`, `firstname`, `lastname`, `birthdate`, `gender`, `amka`, `city`, `address`, `addr_no`, `telephone`, `status`) VALUES
(1, 'eva_chris', 'eva@gmail.com', '123456', 'EVA', 'CHRYSOSTOMAKI', '2001-04-27', 'Female', '03069200003', 'Heraklion', 'Kainouria Porta', 50, '1234567890', 'semi_vaccinated'),
(2, 'kalgenitsa1', 'kalgenitsa@sch.gr', 'kal123', 'KALLIOPI', 'GENITSARIDOU', '1973-02-17', 'Female', '17027365987', 'Heraklion', 'Louka Petraki', 5, '2810285133', 'unvaccinated'),
(3, 'katerinalouka', 'katerinalouka@gmail.com', '83$sadjh28', 'KATERINA', 'LOUKA', '1999-05-05', 'Female', '05059945698', 'Heraklion', 'Chanioporta', 43, '2812859563', 'unvaccinated');

-- --------------------------------------------------------

--
-- Table structure for table `vaccine`
--

CREATE TABLE `vaccine` (
  `vaccine_id` int(11) NOT NULL,
  `manufacturer` varchar(30) NOT NULL,
  `doses` int(11) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `vaccine`
--

INSERT INTO `vaccine` (`vaccine_id`, `manufacturer`, `doses`, `stock`) VALUES
(1, 'Pfizer', 3, 1000),
(2, 'Johnson', 3, 1000),
(3, 'Moderna', 3, 1000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`doctor_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `hospital_id` (`hospital_id`);

--
-- Indexes for table `hospital`
--
ALTER TABLE `hospital`
  ADD PRIMARY KEY (`hospital_id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `nurse`
--
ALTER TABLE `nurse`
  ADD PRIMARY KEY (`nurse_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `doctor_id` (`doctor_id`),
  ADD KEY `hospital_id` (`hospital_id`);

--
-- Indexes for table `randevouz`
--
ALTER TABLE `randevouz`
  ADD PRIMARY KEY (`randevouz_id`),
  ADD KEY `hospital_id` (`hospital_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `vaccine`
--
ALTER TABLE `vaccine`
  ADD PRIMARY KEY (`vaccine_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `doctors`
--
ALTER TABLE `doctors`
  MODIFY `doctor_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `hospital`
--
ALTER TABLE `hospital`
  MODIFY `hospital_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `nurse`
--
ALTER TABLE `nurse`
  MODIFY `nurse_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `randevouz`
--
ALTER TABLE `randevouz`
  MODIFY `randevouz_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `vaccine`
--
ALTER TABLE `vaccine`
  MODIFY `vaccine_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `doctors`
--
ALTER TABLE `doctors`
  ADD CONSTRAINT `doctors_ibfk_1` FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`hospital_id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `nurse`
--
ALTER TABLE `nurse`
  ADD CONSTRAINT `nurse_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`doctor_id`),
  ADD CONSTRAINT `nurse_ibfk_2` FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`hospital_id`);

--
-- Constraints for table `randevouz`
--
ALTER TABLE `randevouz`
  ADD CONSTRAINT `randevouz_ibfk_1` FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`hospital_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
