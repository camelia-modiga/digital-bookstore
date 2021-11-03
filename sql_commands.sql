CREATE TABLE `author` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fistName` varchar(45) NOT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idx_author_lastName` (`fistName`),
  KEY `idx_author_firstName` (`lastName`)
);

CREATE TABLE `book` (
  `isbn` varchar(50) NOT NULL,
  `title` varchar(45) NOT NULL,
  `publisher` varchar(45) NOT NULL,
  `year` int NOT NULL,
  `genre` varchar(45) NOT NULL,
  PRIMARY KEY (`isbn`),
  UNIQUE KEY `ISBN_UNIQUE` (`isbn`),
  UNIQUE KEY `title_UNIQUE` (`title`),
  KEY `idx_book_year` (`year`),
  KEY `idx_book_genre` (`genre`)
);

CREATE TABLE `book_author` (
  `isbn` varchar(50) NOT NULL,
  `author_id` int NOT NULL,
  `index` int NOT NULL,
  PRIMARY KEY (`index`),
  KEY `fk_id_author_idx` (`author_id`),
  KEY `fk_ISBN_idx` (`isbn`)
) ;
