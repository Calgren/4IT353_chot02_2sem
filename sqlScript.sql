CREATE DATABASE  IF NOT EXISTS `tickets` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tickets`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: tickets
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `login` varchar(100) NOT NULL,
  `type` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `birthDate` date NOT NULL,
  `firstName` varchar(80) NOT NULL,
  `lastName` varchar(80) NOT NULL,
  `email` varchar(80) NOT NULL,
  `registerDate` date NOT NULL,
  `phone` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`login`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('Calgren','Customer','123','1997-08-26','Tomáš','Chour','chot02@vse.cz','2017-01-20',NULL),('TEST1','Customer','213','2020-01-16','TEST12','TEST13','TEST1@TEST1.com','2020-01-18','223131341');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customerticket`
--

DROP TABLE IF EXISTS `customerticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customerticket` (
  `customerId` varchar(100) NOT NULL,
  `ticketId` int NOT NULL,
  `purchaseDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`customerId`,`ticketId`),
  KEY `customerTicket_seasonticket_idx` (`ticketId`),
  CONSTRAINT `customerTicket_customer` FOREIGN KEY (`customerId`) REFERENCES `customer` (`login`),
  CONSTRAINT `customerTicket_seasonticket` FOREIGN KEY (`ticketId`) REFERENCES `seasonticket` (`idseasonTicket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerticket`
--

LOCK TABLES `customerticket` WRITE;
/*!40000 ALTER TABLE `customerticket` DISABLE KEYS */;
INSERT INTO `customerticket` VALUES ('Calgren',1,'2020-02-02 17:18:01'),('Calgren',6,'2020-02-02 17:54:01');
/*!40000 ALTER TABLE `customerticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `season`
--

DROP TABLE IF EXISTS `season`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `season` (
  `seasonId` int NOT NULL AUTO_INCREMENT,
  `start` date NOT NULL,
  `end` date NOT NULL,
  PRIMARY KEY (`seasonId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `season`
--

LOCK TABLES `season` WRITE;
/*!40000 ALTER TABLE `season` DISABLE KEYS */;
INSERT INTO `season` VALUES (1,'2020-02-09','2020-05-26'),(2,'2020-07-21','2020-12-16'),(3,'2021-02-09','2020-05-26');
/*!40000 ALTER TABLE `season` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seasonticket`
--

DROP TABLE IF EXISTS `seasonticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seasonticket` (
  `idseasonTicket` int NOT NULL AUTO_INCREMENT,
  `typeId` varchar(20) NOT NULL,
  `seasonId` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `sectorId` varchar(3) NOT NULL,
  PRIMARY KEY (`idseasonTicket`),
  KEY `seasonTicket_season_idx` (`seasonId`),
  KEY `seasonTicket_sector_idx` (`sectorId`),
  KEY `seasonTicket_Type_idx` (`typeId`),
  CONSTRAINT `seasonTicket_season` FOREIGN KEY (`seasonId`) REFERENCES `season` (`seasonId`),
  CONSTRAINT `seasonTicket_sector` FOREIGN KEY (`sectorId`) REFERENCES `sector` (`sectorId`),
  CONSTRAINT `seasonTicket_Type` FOREIGN KEY (`typeId`) REFERENCES `tickettype` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seasonticket`
--

LOCK TABLES `seasonticket` WRITE;
/*!40000 ALTER TABLE `seasonticket` DISABLE KEYS */;
INSERT INTO `seasonticket` VALUES (1,'Adult',1,2000.00,'A1'),(3,'Adult',2,1600.00,'B3'),(6,'Kid',1,800.00,'A1');
/*!40000 ALTER TABLE `seasonticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sector`
--

DROP TABLE IF EXISTS `sector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sector` (
  `sectorId` varchar(3) NOT NULL,
  `pricePoint` decimal(3,2) NOT NULL,
  `adultOnly` tinyint DEFAULT '0',
  PRIMARY KEY (`sectorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sector`
--

LOCK TABLES `sector` WRITE;
/*!40000 ALTER TABLE `sector` DISABLE KEYS */;
INSERT INTO `sector` VALUES ('A1',1.00,0),('A2',1.00,0),('A3',1.00,0),('B1',0.80,0),('B2',0.80,0),('B3',0.80,0),('C1',0.60,0),('C2',0.60,0),('C3',0.60,1);
/*!40000 ALTER TABLE `sector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickettype`
--

DROP TABLE IF EXISTS `tickettype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickettype` (
  `name` varchar(20) NOT NULL,
  `defaultPrice` float NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickettype`
--

LOCK TABLES `tickettype` WRITE;
/*!40000 ALTER TABLE `tickettype` DISABLE KEYS */;
INSERT INTO `tickettype` VALUES ('Adult',2000),('Junior',1200),('Kid',800);
/*!40000 ALTER TABLE `tickettype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-02 22:26:55
