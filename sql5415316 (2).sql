-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2021 at 01:22 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sql5415316`
--

-- --------------------------------------------------------

--
-- Table structure for table `wtaxy_address`
--

CREATE TABLE `wtaxy_address` (
  `address_id` int(10) UNSIGNED NOT NULL,
  `address_line_one` varchar(64) DEFAULT NULL,
  `address_line_two` varchar(64) DEFAULT NULL,
  `address_region` varchar(32) DEFAULT NULL,
  `address_country` char(2) DEFAULT NULL,
  `user_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `wtaxy_admin`
--

CREATE TABLE `wtaxy_admin` (
  `admin_id` int(10) UNSIGNED NOT NULL,
  `admin_level` int(10) UNSIGNED NOT NULL DEFAULT 404,
  `user_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `wtaxy_chat`
--

CREATE TABLE `wtaxy_chat` (
  `chat_sender_id` int(10) UNSIGNED NOT NULL,
  `chat_reciever_id` int(10) UNSIGNED NOT NULL,
  `chat_message` varchar(512) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `wtaxy_contestant`
--

CREATE TABLE `wtaxy_contestant` (
  `contestant_id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `contestant_image_dir` varchar(128) DEFAULT NULL,
  `contestant_verified` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `wtaxy_contestant`
--

INSERT INTO `wtaxy_contestant` (`contestant_id`, `user_id`, `contestant_image_dir`, `contestant_verified`) VALUES
(1, 1, 'dir', 0);

-- --------------------------------------------------------

--
-- Table structure for table `wtaxy_grade`
--

CREATE TABLE `wtaxy_grade` (
  `submission_id` int(10) UNSIGNED NOT NULL,
  `judge_id` int(10) UNSIGNED NOT NULL,
  `grade_value` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `wtaxy_grade`
--

INSERT INTO `wtaxy_grade` (`submission_id`, `judge_id`, `grade_value`) VALUES
(1, 1, 34),
(2, 1, 45);

-- --------------------------------------------------------

--
-- Table structure for table `wtaxy_judge`
--

CREATE TABLE `wtaxy_judge` (
  `judge_id` int(10) UNSIGNED NOT NULL,
  `judge_level` int(10) UNSIGNED NOT NULL DEFAULT 404,
  `user_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `wtaxy_judge`
--

INSERT INTO `wtaxy_judge` (`judge_id`, `judge_level`, `user_id`) VALUES
(1, 404, 2),
(2, 404, 3),
(3, 404, 4);

-- --------------------------------------------------------

--
-- Table structure for table `wtaxy_submission`
--

CREATE TABLE `wtaxy_submission` (
  `submission_id` int(10) UNSIGNED NOT NULL,
  `submission_date` date NOT NULL,
  `submission_poem_english` text NOT NULL,
  `submission_poem_kom` text NOT NULL DEFAULT '',
  `contestant_id` int(10) UNSIGNED NOT NULL,
  `submission_final_grade` double DEFAULT 0,
  `submission_poem_title` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `wtaxy_submission`
--

INSERT INTO `wtaxy_submission` (`submission_id`, `submission_date`, `submission_poem_english`, `submission_poem_kom`, `contestant_id`, `submission_final_grade`, `submission_poem_title`) VALUES
(1, '2021-05-31', 'sdf', 'sdfs', 1, 11.333333333333334, 'sdfsd'),
(2, '2021-05-31', 'The poem in english', 'The poem in itangi-kom', 1, 15, 'My tenth test poem');

-- --------------------------------------------------------

--
-- Table structure for table `wtaxy_user`
--

CREATE TABLE `wtaxy_user` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `user_name` varchar(32) NOT NULL,
  `user_email` varchar(32) NOT NULL,
  `user_pass` varchar(128) NOT NULL,
  `user_first_name` varchar(32) NOT NULL,
  `user_last_name` varchar(32) NOT NULL,
  `user_gender` varchar(1) DEFAULT NULL,
  `user_dob` date DEFAULT NULL,
  `user_verified` tinyint(1) DEFAULT 0,
  `user_role` int(10) UNSIGNED NOT NULL DEFAULT 444,
  `user_joined_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `wtaxy_user`
--

INSERT INTO `wtaxy_user` (`user_id`, `user_name`, `user_email`, `user_pass`, `user_first_name`, `user_last_name`, `user_gender`, `user_dob`, `user_verified`, `user_role`, `user_joined_date`) VALUES
(1, 'elroykanye', 'elroykanye@gmail.com', '12345678', 'Elroy', 'Yonghabichia', 'M', '2001-04-21', 0, 333, '2021-05-31'),
(2, 'judge1', 'judge1@gmail.com', '12345678', 'First', 'Judge', 'F', '2000-01-01', 0, 222, '2021-05-31'),
(3, 'judge2', 'judge2@gmail.com', '12345678', 'Second', 'Judge', 'M', '2000-01-01', 0, 222, '2021-05-31'),
(4, 'judge3', 'judge3@gmail.com', '12345678', 'Third', 'Judge', 'F', '2000-01-01', 0, 222, '2021-05-31');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `wtaxy_address`
--
ALTER TABLE `wtaxy_address`
  ADD PRIMARY KEY (`address_id`),
  ADD KEY `fk_address_wtaxy_user` (`user_id`);

--
-- Indexes for table `wtaxy_admin`
--
ALTER TABLE `wtaxy_admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD KEY `fk_wtaxy_admin_wtaxy_user` (`user_id`);

--
-- Indexes for table `wtaxy_chat`
--
ALTER TABLE `wtaxy_chat`
  ADD UNIQUE KEY `unq_wtaxy_chat_chat_reciever_id` (`chat_reciever_id`),
  ADD UNIQUE KEY `unq_wtaxy_chat_chat_sender_id` (`chat_sender_id`);

--
-- Indexes for table `wtaxy_contestant`
--
ALTER TABLE `wtaxy_contestant`
  ADD PRIMARY KEY (`contestant_id`);

--
-- Indexes for table `wtaxy_grade`
--
ALTER TABLE `wtaxy_grade`
  ADD PRIMARY KEY (`submission_id`,`judge_id`),
  ADD KEY `fk_wtaxy_grade_wtaxy_judge` (`judge_id`);

--
-- Indexes for table `wtaxy_judge`
--
ALTER TABLE `wtaxy_judge`
  ADD PRIMARY KEY (`judge_id`),
  ADD KEY `fk_wtaxy_judge_wtaxy_user` (`user_id`);

--
-- Indexes for table `wtaxy_submission`
--
ALTER TABLE `wtaxy_submission`
  ADD PRIMARY KEY (`submission_id`);

--
-- Indexes for table `wtaxy_user`
--
ALTER TABLE `wtaxy_user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `wtaxy_address`
--
ALTER TABLE `wtaxy_address`
  MODIFY `address_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wtaxy_admin`
--
ALTER TABLE `wtaxy_admin`
  MODIFY `admin_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wtaxy_contestant`
--
ALTER TABLE `wtaxy_contestant`
  MODIFY `contestant_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `wtaxy_judge`
--
ALTER TABLE `wtaxy_judge`
  MODIFY `judge_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `wtaxy_submission`
--
ALTER TABLE `wtaxy_submission`
  MODIFY `submission_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `wtaxy_user`
--
ALTER TABLE `wtaxy_user`
  MODIFY `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `wtaxy_address`
--
ALTER TABLE `wtaxy_address`
  ADD CONSTRAINT `fk_address_wtaxy_user` FOREIGN KEY (`user_id`) REFERENCES `wtaxy_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `wtaxy_admin`
--
ALTER TABLE `wtaxy_admin`
  ADD CONSTRAINT `fk_wtaxy_admin_wtaxy_user` FOREIGN KEY (`user_id`) REFERENCES `wtaxy_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `wtaxy_chat`
--
ALTER TABLE `wtaxy_chat`
  ADD CONSTRAINT `fk_wtaxy_chat_wtaxy_judge_receiver` FOREIGN KEY (`chat_reciever_id`) REFERENCES `wtaxy_judge` (`judge_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_wtaxy_chat_wtaxy_judge_sender` FOREIGN KEY (`chat_sender_id`) REFERENCES `wtaxy_judge` (`judge_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `wtaxy_contestant`
--
ALTER TABLE `wtaxy_contestant`
  ADD CONSTRAINT `fk_wtaxy_contestant_wtaxy_user` FOREIGN KEY (`user_id`) REFERENCES `wtaxy_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `wtaxy_grade`
--
ALTER TABLE `wtaxy_grade`
  ADD CONSTRAINT `fk_wtaxy_grade_wtaxy_judge` FOREIGN KEY (`judge_id`) REFERENCES `wtaxy_judge` (`judge_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_wtaxy_grade_wtaxy_submission` FOREIGN KEY (`submission_id`) REFERENCES `wtaxy_submission` (`submission_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `wtaxy_judge`
--
ALTER TABLE `wtaxy_judge`
  ADD CONSTRAINT `fk_wtaxy_judge_wtaxy_user` FOREIGN KEY (`user_id`) REFERENCES `wtaxy_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `wtaxy_submission`
--
ALTER TABLE `wtaxy_submission`
  ADD CONSTRAINT `fk_wtaxy_submission` FOREIGN KEY (`contestant_id`) REFERENCES `wtaxy_contestant` (`contestant_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
