start transaction;

create database `Acme-Barter`;

use `Acme-Barter`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `Acme-Barter`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `Acme-Barter`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Barter
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (12,0,'Miguel','666777888','Rodriguez',1);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_img3kaubb62u0h0jujkh1tejo` (`userAccount_id`),
  KEY `UK_avdstpil73shpg9jh6od6wvci` (`name`),
  KEY `UK_ffq9bbqe5yv7qn1njy8k6cbpr` (`surname`),
  CONSTRAINT `FK_img3kaubb62u0h0jujkh1tejo` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
INSERT INTO `auditor` VALUES (13,0,'Pablo','652148745','López',6),(14,0,'Antonio','630157841','Serrano',7),(15,0,'Jose','632112358','Ruiz',8),(16,0,'Rocío','674185293','Cuesta',9),(17,0,'Verónica','693852741','Gil',10),(18,0,'Carlos','622852741','Mata',11);
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `barter`
--

DROP TABLE IF EXISTS `barter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `barter` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelled` bit(1) NOT NULL,
  `registerMoment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `offered_id` int(11) NOT NULL,
  `requested_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nqjsw6o9p0r779e595c8mksmr` (`offered_id`),
  UNIQUE KEY `UK_gkaec90s2mtteti23so9di9dm` (`requested_id`),
  KEY `UK_nhmhxf9yweyocubkbdvufgr89` (`cancelled`),
  KEY `FK_i3x9l14dpct1ps9l8cpe176ov` (`user_id`),
  CONSTRAINT `FK_i3x9l14dpct1ps9l8cpe176ov` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_gkaec90s2mtteti23so9di9dm` FOREIGN KEY (`requested_id`) REFERENCES `item` (`id`),
  CONSTRAINT `FK_nqjsw6o9p0r779e595c8mksmr` FOREIGN KEY (`offered_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barter`
--

LOCK TABLES `barter` WRITE;
/*!40000 ALTER TABLE `barter` DISABLE KEYS */;
INSERT INTO `barter` VALUES (102,0,'\0','2016-01-12 12:30:00','Te lo cambio',75,76,21),(103,0,'\0','2016-03-10 11:00:00','¡¡Intercambio urgente!!',77,78,19),(104,0,'\0','2016-03-01 17:15:00','Acepto ofertas',79,80,20),(105,0,'','2015-12-03 12:00:00','¡Que me lo quitan de las manos!',81,82,21),(106,0,'\0','2016-02-02 02:00:00','Lo cambio por este o cualquier otro portatil de las mismas características',83,84,19),(107,0,'\0','2016-03-11 16:10:00','Solo hasta Junio o lo vendo a mi vecino',85,86,20),(108,0,'\0','2016-01-12 12:30:00','No lo cambio',87,88,19),(109,0,'\0','2016-01-12 12:30:00','Quiero otro PC',89,90,21),(110,0,'\0','2016-01-12 12:30:00','Quiero otro Portátil',92,91,19),(111,0,'\0','2016-01-12 12:30:00','Quiero otro Móvil',94,93,19),(112,0,'\0','2016-01-12 12:30:00','Busco guitarra',96,95,21),(113,0,'\0','2016-01-12 12:30:00','Busco otro bajo',97,98,19);
/*!40000 ALTER TABLE `barter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `barter_barter`
--

DROP TABLE IF EXISTS `barter_barter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `barter_barter` (
  `Barter_id` int(11) NOT NULL,
  `relatedBarter_id` int(11) NOT NULL,
  KEY `FK_jdddda5iuepxoso7fy0be6lvg` (`relatedBarter_id`),
  KEY `FK_bhi2aehwte3r1vxv6543xp4c6` (`Barter_id`),
  CONSTRAINT `FK_bhi2aehwte3r1vxv6543xp4c6` FOREIGN KEY (`Barter_id`) REFERENCES `barter` (`id`),
  CONSTRAINT `FK_jdddda5iuepxoso7fy0be6lvg` FOREIGN KEY (`relatedBarter_id`) REFERENCES `barter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barter_barter`
--

LOCK TABLES `barter_barter` WRITE;
/*!40000 ALTER TABLE `barter_barter` DISABLE KEYS */;
INSERT INTO `barter_barter` VALUES (103,102),(106,105),(107,106);
/*!40000 ALTER TABLE `barter_barter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isSystem` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `actor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_l1kp977466ddsv762wign7kdh` (`name`),
  KEY `UK_a5prtxr1c0vwoxbpixtnse2nq` (`isSystem`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (27,0,'','InBox',12),(28,0,'','OutBox',12),(29,0,'','TrashBox',12),(30,0,'','SpamBox',12),(31,0,'','InBox',21),(32,0,'','OutBox',21),(33,0,'','TrashBox',21),(34,0,'','SpamBox',21),(35,0,'','InBox',19),(36,0,'','OutBox',19),(37,0,'','TrashBox',19),(38,0,'','SpamBox',19),(39,0,'','InBox',20),(40,0,'','OutBox',20),(41,0,'','TrashBox',20),(42,0,'','SpamBox',20),(43,0,'','InBox',22),(44,0,'','OutBox',22),(45,0,'','TrashBox',22),(46,0,'','SpamBox',22),(47,0,'','InBox',13),(48,0,'','OutBox',13),(49,0,'','TrashBox',13),(50,0,'','SpamBox',13),(51,0,'','InBox',14),(52,0,'','OutBox',14),(53,0,'','TrashBox',14),(54,0,'','SpamBox',14),(55,0,'','InBox',15),(56,0,'','OutBox',15),(57,0,'','TrashBox',15),(58,0,'','SpamBox',15),(59,0,'','InBox',16),(60,0,'','OutBox',16),(61,0,'','TrashBox',16),(62,0,'','SpamBox',16),(63,0,'','InBox',17),(64,0,'','OutBox',17),(65,0,'','TrashBox',17),(66,0,'','SpamBox',17),(67,0,'','InBox',18),(68,0,'','OutBox',18),(69,0,'','TrashBox',18),(70,0,'','SpamBox',18),(71,0,'\0','MyBox',21);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder_message`
--

DROP TABLE IF EXISTS `folder_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder_message` (
  `folders_id` int(11) NOT NULL,
  `messages_id` int(11) NOT NULL,
  KEY `FK_5nh3mwey9bw25ansh2thcbcdh` (`messages_id`),
  KEY `FK_o1e2m8i8khapsgpd6jg28dr7e` (`folders_id`),
  CONSTRAINT `FK_o1e2m8i8khapsgpd6jg28dr7e` FOREIGN KEY (`folders_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `FK_5nh3mwey9bw25ansh2thcbcdh` FOREIGN KEY (`messages_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder_message`
--

LOCK TABLES `folder_message` WRITE;
/*!40000 ALTER TABLE `folder_message` DISABLE KEYS */;
INSERT INTO `folder_message` VALUES (28,23),(31,23),(33,23),(34,24),(34,25),(35,23),(36,26),(39,23),(42,26),(43,23),(44,24),(44,25);
/*!40000 ALTER TABLE `folder_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (75,0,'Es normalillo, pero corre bien el Minecraft.','Portatil HP'),(76,0,'El PORTÁTIL más versatil del mercado.','Surface Pro 4'),(77,0,'Bueno... es lo que tengo.','iPhone'),(78,0,'Cualquier me vale si se pueden ver vídeo de YouTube en 1080p.','Tablet Android'),(79,0,'Para dormir las siestas está de lujo, si te cabe en el coche yo me la llevo hasta al campo.','Cama plegable'),(80,0,'Ahora con los niños necesito una litera, con protector para la cama de arriba a ser posible.','Litera'),(81,0,'Del 2001, como nuevo. El maletero no cierra bien, por lo demás perfecto.','Ford Fiesta'),(82,0,'A ser posible negro, pago la diferencia en efectivo en el momento del intercambio.','Seat León'),(83,0,'Rapido! Te he propuesto emparejamiento para que lo veas! Antes de que me pillen los Auditores!!','VENDO GATOS'),(84,0,'Rapido! Te he propuesto emparejamiento para que lo veas!  Antes de que me pillen los Auditores!!','VENDO GATOS'),(85,0,'Clean your house in only 1 hour!','Amazing Turbo Cleaner'),(86,0,'Any laptop bought in 2015 is fine for me.','Any laptop bought in 2015'),(87,0,'Es normalillo, pero corre bien el Minecraft.','Portatil HP'),(88,0,'El PORTÁTIL más versatil del mercado.','Surface Pro 4'),(89,0,'Portátil marca HP','PC HP'),(90,0,'Portátil marca Asus','PC Asus'),(91,0,'Portátil marca HP','Portátil HP'),(92,0,'Portátil marca Asus','Portátil Asus'),(93,0,'Quiero un iPhone','iPhone'),(94,0,'Ofrezco un nexus','Nexus'),(95,0,'Guitarra eléctrica','Guitarra'),(96,0,'Bajo en buenas condiciones','Bajo'),(97,0,'Guitarra eléctrica bastante buena','Guitarra'),(98,0,'Quiero un bajo que vaya bien','Bajo');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_pictures`
--

DROP TABLE IF EXISTS `item_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_pictures` (
  `Item_id` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_qo3ea4pf194lddsr8jitxod28` (`Item_id`),
  CONSTRAINT `FK_qo3ea4pf194lddsr8jitxod28` FOREIGN KEY (`Item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_pictures`
--

LOCK TABLES `item_pictures` WRITE;
/*!40000 ALTER TABLE `item_pictures` DISABLE KEYS */;
INSERT INTO `item_pictures` VALUES (75,'http://imagenes.pccomponentes.com/hp_g62_b67es_p6100_4gb_500gb_ati_hd5470_15_6__1.jpg'),(76,'http://dri1.img.digitalrivercontent.net/Storefront/Company/msintl/images/English/en-INTL-Surface-Pro4-SU3-00001/PDP/en-INTL-PDP0-Surface-Pro4-SU3-00001-P1.jpg'),(76,'http://dri1.img.digitalrivercontent.net/Storefront/Company/msintl/images/English/en-INTL-Surface-Pro4-SU3-00001/en-INTL-L-Surface-Pro4-SU3-00001-RM3-mnco.jpg'),(77,'http://laseptimaentrevista.com/wp-content/uploads/2014/09/pantalla-completa-iphone-5-blanca-lcd-digitalizador-home-cam-8764-MLM20007595312_112013-O.jpg'),(79,'http://www.practiletto.com/33-thickbox_default/cama-plegable.jpg'),(79,'http://www.decoestilo.com/wp-content/uploads/2013/09/camas_invitados_3.jpg'),(87,'http://imagenes.pccomponentes.com/hp_g62_b67es_p6100_4gb_500gb_ati_hd5470_15_6__1.jpg'),(88,'http://dri1.img.digitalrivercontent.net/Storefront/Company/msintl/images/English/en-INTL-Surface-Pro4-SU3-00001/PDP/en-INTL-PDP0-Surface-Pro4-SU3-00001-P1.jpg'),(88,'http://dri1.img.digitalrivercontent.net/Storefront/Company/msintl/images/English/en-INTL-Surface-Pro4-SU3-00001/en-INTL-L-Surface-Pro4-SU3-00001-RM3-mnco.jpg');
/*!40000 ALTER TABLE `item_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legaltext`
--

DROP TABLE IF EXISTS `legaltext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legaltext` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legaltext`
--

LOCK TABLES `legaltext` WRITE;
/*!40000 ALTER TABLE `legaltext` DISABLE KEYS */;
INSERT INTO `legaltext` VALUES (99,0,'Queda sujeto a las condiciones del creador del Emparejamiento, aunque Acme Barter Inc. (y cualquiera de sus auditores) se reserva el derecho de controlar y/o cancelar cualquier intercambio sin previo aviso.'),(100,0,'Queda sujeto a las condiciones del receptor del Emparejamiento, aunque Acme Barter Inc. (y cualquiera de sus auditores) se reserva el derecho de controlar y/o cancelar cualquier intercambio sin previo aviso.'),(101,0,'Queda sujeto a las condiciones del auditor del Emparejamiento, Acme Barter Inc. (y cualquiera de sus auditores) se reserva el derecho de controlar y/o cancelar cualquier intercambio sin previo aviso.');
/*!40000 ALTER TABLE `legaltext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matchtable`
--

DROP TABLE IF EXISTS `matchtable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matchtable` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelled` bit(1) NOT NULL,
  `creationMoment` datetime DEFAULT NULL,
  `offerSignsDate` datetime DEFAULT NULL,
  `report` varchar(255) DEFAULT NULL,
  `requestSignsDate` datetime DEFAULT NULL,
  `auditor_id` int(11) DEFAULT NULL,
  `creatorBarter_id` int(11) NOT NULL,
  `legalText_id` int(11) NOT NULL,
  `receiverBarter_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jt2ge3spcdoxqgh059m99kfmj` (`auditor_id`),
  KEY `FK_74whxtgpokd7xu1w4w2squbxq` (`creatorBarter_id`),
  KEY `FK_ej5aiqrpwye7pq0fpkdb9sjw5` (`legalText_id`),
  KEY `FK_f6n74hxs4liwyueaeuec7mwwv` (`receiverBarter_id`),
  CONSTRAINT `FK_f6n74hxs4liwyueaeuec7mwwv` FOREIGN KEY (`receiverBarter_id`) REFERENCES `barter` (`id`),
  CONSTRAINT `FK_74whxtgpokd7xu1w4w2squbxq` FOREIGN KEY (`creatorBarter_id`) REFERENCES `barter` (`id`),
  CONSTRAINT `FK_ej5aiqrpwye7pq0fpkdb9sjw5` FOREIGN KEY (`legalText_id`) REFERENCES `legaltext` (`id`),
  CONSTRAINT `FK_jt2ge3spcdoxqgh059m99kfmj` FOREIGN KEY (`auditor_id`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matchtable`
--

LOCK TABLES `matchtable` WRITE;
/*!40000 ALTER TABLE `matchtable` DISABLE KEYS */;
INSERT INTO `matchtable` VALUES (114,0,'\0','2016-04-01 12:00:00','2016-04-02 13:30:00','Me parece apropiado.','2016-04-02 18:30:00',13,102,99,103),(115,0,'\0','2016-03-02 10:00:00','2016-04-03 14:30:00',NULL,NULL,NULL,104,100,102),(116,0,'\0','2016-04-03 12:00:00',NULL,'Posiblemente inadecuado, estar pendiente de sus Matchs.','2016-04-03 16:13:00',15,106,99,107),(117,0,'','2016-04-05 14:00:00','2016-04-06 13:30:00','Quitar de inmediato.','2016-04-07 20:00:00',15,106,99,107),(118,0,'','2016-04-05 14:00:00',NULL,NULL,NULL,NULL,112,99,113),(119,0,'\0','2016-04-05 14:00:00',NULL,NULL,NULL,NULL,112,99,113);
/*!40000 ALTER TABLE `matchtable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `sentMoment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (23,0,'Registrado con exito, bienvenido a Acme-Barter',0,'2015-10-13 22:15:00','Bienvenido',12),(24,0,'Compra viagra',0,'2016-02-05 22:15:00','Hola',22),(25,0,'Esto es spam',0,'2016-02-05 22:15:00','Hola',22),(26,0,'Esto es spam',0,'2016-02-05 22:15:00','Hola',19);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_actor`
--

DROP TABLE IF EXISTS `message_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_actor` (
  `received_id` int(11) NOT NULL,
  `recipients_id` int(11) NOT NULL,
  KEY `FK_s15b8cpmjbq3qqa55fool5tp7` (`received_id`),
  CONSTRAINT `FK_s15b8cpmjbq3qqa55fool5tp7` FOREIGN KEY (`received_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_actor`
--

LOCK TABLES `message_actor` WRITE;
/*!40000 ALTER TABLE `message_actor` DISABLE KEYS */;
INSERT INTO `message_actor` VALUES (23,21),(23,19),(23,20),(23,22),(24,21),(25,21),(26,20);
/*!40000 ALTER TABLE `message_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialidentity`
--

DROP TABLE IF EXISTS `socialidentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialidentity` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `homePage` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_adc08eqomstl4mtqaecc3c9as` (`user_id`),
  CONSTRAINT `FK_adc08eqomstl4mtqaecc3c9as` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialidentity`
--

LOCK TABLES `socialidentity` WRITE;
/*!40000 ALTER TABLE `socialidentity` DISABLE KEYS */;
INSERT INTO `socialidentity` VALUES (72,0,'http://www.twitter.com/Manolo','Manolo','@Manolo','http://1.bp.blogspot.com/-BB4biccPxuM/VquRIDFbBRI/AAAAAAAAJX8/v0sfOPr3R40/s1600-r/yo%2B2.png',21),(73,0,'http://www.facebook.com/ManoloLopez','Manolo López','ManoloLopez','http://www.codigo.pe/wp-content/uploads/2012/11/Minigote.jpg',21),(74,0,'http://www.instagram.com/RubenSanchez','Ruben','RubenSanchez','https://pbs.twimg.com/profile_images/1361012701/monigote_400x400.jpg',19);
/*!40000 ALTER TABLE `socialidentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o6s94d43co03sx067ili5760c` (`userAccount_id`),
  CONSTRAINT `FK_o6s94d43co03sx067ili5760c` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (19,0,'Ruben','612342289','Sanchez',3),(20,0,'Guillermo','633422897','Alcala',4),(21,0,'Manolo','612345789','Lopez',2),(22,0,'Mónica','626667791','Ordóñez',5);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_user`
--

DROP TABLE IF EXISTS `user_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_user` (
  `User_id` int(11) NOT NULL,
  `followed_id` int(11) NOT NULL,
  KEY `FK_fkljans6a6pux89xnqbfcw3ho` (`followed_id`),
  KEY `FK_nlnx78x3m38aq2r86t1d5eio1` (`User_id`),
  CONSTRAINT `FK_nlnx78x3m38aq2r86t1d5eio1` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_fkljans6a6pux89xnqbfcw3ho` FOREIGN KEY (`followed_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_user`
--

LOCK TABLES `user_user` WRITE;
/*!40000 ALTER TABLE `user_user` DISABLE KEYS */;
INSERT INTO `user_user` VALUES (21,19),(21,20),(22,19),(22,21),(22,20);
/*!40000 ALTER TABLE `user_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (1,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(2,0,'24c9e15e52afc47c225b757e7bee1f9d','user1'),(3,0,'7e58d63b60197ceb55a1c487989a3720','user2'),(4,0,'92877af70a45fd6a2ed7fe81e1236b78','user3'),(5,0,'3f02ebe3d7929b091e3d8ccfde2f3bc6','user4'),(6,0,'175d2e7a63f386554a4dd66e865c3e14','auditor1'),(7,0,'04dd94ba3212ac52ad3a1f4cbfb52d4f','auditor2'),(8,0,'fc2073dbe4f65dfd71e46602f8e6a5f3','auditor3'),(9,0,'6cc8affcba51a854192a33af68c08f49','auditor4'),(10,0,'3775bf00262284e83013c9cea5f41431','auditor5'),(11,0,'5d78d53d65022ce05a759cd3c057782e','auditor6');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (1,'ADMIN'),(2,'USER'),(3,'USER'),(4,'USER'),(5,'USER'),(6,'AUDITOR'),(7,'AUDITOR'),(8,'AUDITOR'),(9,'AUDITOR'),(10,'AUDITOR'),(11,'AUDITOR');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-20 17:41:37

commit;