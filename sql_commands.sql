CREATE TABLE `author` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idx_author_lastName` (`firstName`),
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


insert into author (first_name,last_name) values ("Bogdan","Popa");
insert into author (first_name,last_name) values ("Cristian","Vladimirescu");
insert into book values ("ISBN 978-3-16-148410-0","Dosar permanent","Nemira",2019,"Autobiografii");
insert into book values ("ISBN 978-6-06-430912-9 ","Minerva se dezlantuie","Nemira",2019,"Fictiune");