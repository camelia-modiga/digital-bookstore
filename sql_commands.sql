CREATE TABLE author (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  CONSTRAINT id_pk PRIMARY KEY (`id`),
  CONSTRAINT id_unique UNIQUE KEY (`id`)
);
CREATE INDEX first_name_idx on author(`first_name`);
CREATE INDEX last_name_idx on author(`last_name`);

insert into author (first_name,last_name) values ("Bogdan","Popa");
insert into author (first_name,last_name) values ("Cristian","Vladimirescu");

CREATE TABLE `book` (
  `isbn` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  `publisher` varchar(50) NOT NULL,
  `year` int NOT NULL,
  `genre` varchar(50) NOT NULL,
  CONSTRAINT isbn_pk PRIMARY KEY (`isbn`),
  CONSTRAINT isbn_unique UNIQUE KEY (`isbn`)
);
CREATE INDEX year_idx on book(`year`);
CREATE INDEX genre_idx on book(`genre`);

insert into book values ("ISBN 978-3-16-148410-0","Dosar permanent","Nemira",2019,"Autobiografii");
insert into book values ("ISBN 978-6-06-430912-9 ","Minerva se dezlantuie","Nemira",2019,"Fictiune");

CREATE TABLE `book_author` (
  `isbn` varchar(50) NOT NULL,
  `author_id` int NOT NULL,
  `index` int NOT NULL,
  PRIMARY KEY (`index`),
  CONSTRAINT isbn_fk FOREIGN KEY (`isbn`) REFERENCES book(`isbn`),
  CONSTRAINT author_if_fk FOREIGN KEY (`author_id`) REFERENCES author(`id`)
) ;

insert into book_author values("ISBN 978-3-16-148410-0",1,1);
insert into book_author values("ISBN 978-3-16-148410-0",2,2);

select last_name,first_name,book.isbn from author,book_author,book where book_author.author_id=author.id and book_author.isbn=book.isbn;



