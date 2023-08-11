-- MySQL dump 10.13  Distrib 8.0.33, for macos12.6 (arm64)
--
-- Host: localhost    Database: community
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` varchar(500) NOT NULL,
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`board_id`,`user_id`),
  KEY `FK_users_TO_board_1` (`user_id`),
  CONSTRAINT `FK_users_TO_board_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,15,'수정된 제목','수정된 내용','2023-08-07 21:49:56','2023-08-08 08:36:33'),(2,15,'제목','글 내용','2023-08-08 06:21:19','2023-08-08 06:21:19'),(3,15,'제목','글 내용','2023-08-08 06:21:44','2023-08-08 06:21:44'),(4,15,'제목','글 내용','2023-08-08 06:49:23','2023-08-08 06:49:23'),(5,15,'제목','글 내용','2023-08-08 06:49:27','2023-08-08 06:49:27'),(6,15,'제목','글 내용','2023-08-08 06:49:28','2023-08-08 06:49:28'),(7,15,'제목','글 내용','2023-08-08 06:49:29','2023-08-08 06:49:29'),(8,15,'제목','글 내용','2023-08-08 06:49:30','2023-08-08 06:49:30'),(9,15,'제목','글 내용','2023-08-08 06:49:30','2023-08-08 06:49:30'),(10,15,'제목','글 내용','2023-08-08 06:49:32','2023-08-08 06:49:32'),(11,15,'제목','글 내용','2023-08-08 06:49:32','2023-08-08 06:49:32'),(12,15,'제목','글 내용','2023-08-08 06:49:33','2023-08-08 06:49:33'),(13,15,'제목','글 내용','2023-08-08 06:49:33','2023-08-08 06:49:33'),(14,15,'제목','글 내용','2023-08-08 06:49:35','2023-08-08 06:49:35'),(15,15,'제목','글 내용','2023-08-08 06:49:36','2023-08-08 06:49:36'),(16,15,'제목','글 내용','2023-08-08 06:49:36','2023-08-08 06:49:36'),(17,15,'제목','글 내용','2023-08-08 06:49:37','2023-08-08 06:49:37'),(18,15,'제목','글 내용','2023-08-08 06:49:38','2023-08-08 06:49:38'),(19,15,'제목','글 내용','2023-08-08 06:49:38','2023-08-08 06:49:38'),(20,15,'제목','글 내용','2023-08-08 08:11:09','2023-08-08 08:11:09'),(21,15,'제목','글 내용','2023-08-08 08:25:06','2023-08-08 08:25:06'),(23,19,'제목222','글 내용222','2023-08-08 09:00:10','2023-08-08 09:00:10'),(24,19,'제목222','글 내용222','2023-08-08 09:00:12','2023-08-08 09:00:12'),(25,19,'제목222','글 내용222','2023-08-08 09:00:13','2023-08-08 09:00:13'),(26,19,'제목222','글 내용222','2023-08-08 09:00:13','2023-08-08 09:00:13');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (14,'user2344@asdsdd.com','$2a$10$P/kJ82/FG7VmxPG3zI3ht.i1eZVyXm0qAgQsyvjDElMBPP3dPxW4m'),(15,'test@test.com','$2a$10$pRDksnbddaoGnZJ5fVBSneCfuLoLON14tNv05ZO0g.dq7TIfkUs5O'),(16,'test@testaasd.cosssm','$2a$10$MavHERxszT0qYsYH32etP.A3/ITr3uiLrl4jJEp44vSW1AAcKalsG'),(17,'test1@test.com','$2a$10$Ry1XqgezyzOR0JaL/ouRneGTArXBDx8W84BBwSp9m195D5zkp5Ima'),(18,'test1@22','$2a$10$oe8Tu8cHvQXTPyWYhT7aueRi2QXck8ROzvDKhcrEyodOKyem0MlES'),(19,'test2@test.com','$2a$10$x1FzNR6rDt6Iqy3YV3tMh.Y2BSCP40RmkHfJUuHUbSfhRxgWqp7Wa'),(20,'test122@ㅁ','$2a$10$GGnmYR.kDS3GwltnU7EaNeAHph3aBEQ2eMyKLOKFzArezG5SbcOgO'),(21,'test3@test.com','$2a$10$y8VDI3wQts5o0Sw1sU4.pOPguMAjdEJpbdN1yb6pjP93PUypgQvry');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-11 16:39:40
