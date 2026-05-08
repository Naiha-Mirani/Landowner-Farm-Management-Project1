-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: farm_database
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `assignment`
--

DROP TABLE IF EXISTS `assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignment` (
  `Assignment_ID` varchar(10) NOT NULL,
  `Worker_ID` varchar(10) NOT NULL,
  `Field_ID` varchar(10) NOT NULL,
  `Start_Date` date NOT NULL,
  `End_Date` date DEFAULT NULL,
  PRIMARY KEY (`Assignment_ID`),
  UNIQUE KEY `uq_active_assignment` (`Worker_ID`,`Field_ID`,`Start_Date`),
  KEY `fk_assignment_field` (`Field_ID`),
  CONSTRAINT `fk_assignment_field` FOREIGN KEY (`Field_ID`) REFERENCES `field` (`Field_ID`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_assignment_worker` FOREIGN KEY (`Worker_ID`) REFERENCES `worker` (`Worker_ID`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_end_after_start` CHECK (((`End_Date` > `Start_Date`) or (`End_Date` is null)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment`
--

LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop`
--

DROP TABLE IF EXISTS `crop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop` (
  `Crop_ID` varchar(10) NOT NULL,
  `Field_ID` varchar(10) NOT NULL,
  `Crop_Name` varchar(50) NOT NULL,
  `Season_Type` varchar(10) NOT NULL,
  `Planting_Date` date NOT NULL,
  `Harvest_Date` date DEFAULT NULL,
  `Yield_Kg` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`Crop_ID`),
  KEY `fk_crop_field` (`Field_ID`),
  CONSTRAINT `fk_crop_field` FOREIGN KEY (`Field_ID`) REFERENCES `field` (`Field_ID`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_harvest_after_planting` CHECK (((`Harvest_Date` > `Planting_Date`) or (`Harvest_Date` is null))),
  CONSTRAINT `chk_season` CHECK ((`Season_Type` in (_utf8mb4'Rabi',_utf8mb4'Kharif')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop`
--

LOCK TABLES `crop` WRITE;
/*!40000 ALTER TABLE `crop` DISABLE KEYS */;
/*!40000 ALTER TABLE `crop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field`
--

DROP TABLE IF EXISTS `field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field` (
  `Field_ID` varchar(10) NOT NULL,
  `Landowner_ID` varchar(10) NOT NULL,
  `Location` varchar(100) NOT NULL,
  `Area_Acres` decimal(8,2) NOT NULL,
  `Soil_Type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Field_ID`),
  KEY `fk_field_landowner` (`Landowner_ID`),
  CONSTRAINT `fk_field_landowner` FOREIGN KEY (`Landowner_ID`) REFERENCES `landowner` (`Landowner_ID`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_area` CHECK ((`Area_Acres` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field`
--

LOCK TABLES `field` WRITE;
/*!40000 ALTER TABLE `field` DISABLE KEYS */;
INSERT INTO `field` VALUES ('101','1','North Ghotki Block-A',120.50,'Alluvial'),('102','1','South Ghotki Block-B',85.00,'Clay'),('103','2','Kandhkot East Farm',200.75,'Loam'),('104','3','Naudero Field-1',95.00,'Sandy Loam');
/*!40000 ALTER TABLE `field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `landowner`
--

DROP TABLE IF EXISTS `landowner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `landowner` (
  `Landowner_ID` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `CNIC` varchar(15) NOT NULL,
  `Phone` varchar(15) DEFAULT NULL,
  `Village` varchar(50) NOT NULL,
  `District` varchar(50) NOT NULL,
  PRIMARY KEY (`Landowner_ID`),
  UNIQUE KEY `CNIC` (`CNIC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `landowner`
--

LOCK TABLES `landowner` WRITE;
/*!40000 ALTER TABLE `landowner` DISABLE KEYS */;
INSERT INTO `landowner` VALUES ('1','Ali Nawaz Bhutto','4210112345671','0300-1234567','Ghotki Town','Ghotki'),('1234','Naiha','123-345','03-00000','Lakhi','Sukkur'),('2','Sardar Mehar Khan','4210198765432','0333-9876543','Kandhkot','Kashmore'),('2675','Noreen','098-99999999','0302-8888888','Bachal Shah Miani','Sukkur'),('3','Mumtaz Ali Talpur','4210154321098','0315-5556666','Naudero','Larkana');
/*!40000 ALTER TABLE `landowner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `Payment_ID` varchar(10) NOT NULL,
  `Worker_ID` varchar(10) NOT NULL,
  `Amount` decimal(12,2) NOT NULL,
  `Payment_Type` varchar(20) NOT NULL,
  `Payment_Date` date NOT NULL DEFAULT (curdate()),
  `Notes` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Payment_ID`),
  KEY `fk_payment_worker` (`Worker_ID`),
  CONSTRAINT `fk_payment_worker` FOREIGN KEY (`Worker_ID`) REFERENCES `worker` (`Worker_ID`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_amount_positive` CHECK ((`Amount` > 0)),
  CONSTRAINT `chk_payment_type` CHECK ((`Payment_Type` in (_utf8mb4'Advance',_utf8mb4'Monthly',_utf8mb4'Bonus')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES ('2','2',2000.00,'Monthly','2026-05-21','Nope');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worker`
--

DROP TABLE IF EXISTS `worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `worker` (
  `Worker_ID` varchar(10) NOT NULL,
  `Landowner_ID` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `CNIC` varchar(15) NOT NULL,
  `Skill_Type` varchar(50) NOT NULL,
  `Daily_Wage` decimal(10,2) NOT NULL,
  `Hire_Date` date NOT NULL DEFAULT (curdate()),
  PRIMARY KEY (`Worker_ID`),
  UNIQUE KEY `CNIC` (`CNIC`),
  KEY `fk_worker_landowner` (`Landowner_ID`),
  CONSTRAINT `fk_worker_landowner` FOREIGN KEY (`Landowner_ID`) REFERENCES `landowner` (`Landowner_ID`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_wage` CHECK ((`Daily_Wage` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worker`
--

LOCK TABLES `worker` WRITE;
/*!40000 ALTER TABLE `worker` DISABLE KEYS */;
INSERT INTO `worker` VALUES ('1','1234','Abdullah','45-44444444','Harvesting',3000.00,'2026-05-08'),('2','3','Ahad','34-33333333','Ploughing',2000.00,'2026-05-10'),('3456','1234','Abdul Shakoor','87-77777777777','Harvesting',3000.00,'2026-05-07');
/*!40000 ALTER TABLE `worker` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-08 20:26:29
