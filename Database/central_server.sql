-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 30, 2019 at 03:03 AM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `central_server`
--

-- --------------------------------------------------------

--
-- Table structure for table `jobs_incomming`
--

CREATE TABLE `jobs_incomming` (
  `job_id` varchar(45) NOT NULL,
  `job_name` varchar(45) NOT NULL,
  `resources_allocated` int(11) DEFAULT NULL,
  `job_status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Table related to Jobs';

--
-- Dumping data for table `jobs_incomming`
--

INSERT INTO `jobs_incomming` (`job_id`, `job_name`, `resources_allocated`, `job_status`) VALUES
('j001', 'asd', 10, 'Busy'),
('j002', 'pq', 15, 'Busy'),
('j003', 'my', 10, 'Busy'),
('j004', 'apk', 12, 'Busy');

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `task_id` varchar(45) NOT NULL,
  `task_job_id` varchar(45) NOT NULL,
  `t_resource_allocated` int(11) NOT NULL,
  `task_name` varchar(45) DEFAULT NULL,
  `task_type` varchar(45) NOT NULL,
  `task_status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Contains task related information';

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`task_id`, `task_job_id`, `t_resource_allocated`, `task_name`, `task_type`, `task_status`) VALUES
('t001', 'j003', 7, 'msg', 'single', NULL),
('t002', 'j003', 8, 'ppt', 'sequential', 0),
('t003', 'j003', 6, 'asdf', 'sequential', 1),
('t004', 'j003', 2, 'jkld', 'single', 2),
('t005', 'j004', 1, 'mnbg', 'single', 3),
('t006', 'j004', 5, 'qwer', 'sequential', NULL),
('t007', 'j004', 7, 'qwers', 'single', 0),
('t008', 'j004', 10, 'asdfg', 'sequential', 1),
('t009', 'j001', 4, 'poiu', 'single', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jobs_incomming`
--
ALTER TABLE `jobs_incomming`
  ADD PRIMARY KEY (`job_id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`task_id`),
  ADD KEY `task_job_id_idx` (`task_job_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `task_job_id` FOREIGN KEY (`task_job_id`) REFERENCES `jobs_incomming` (`job_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
