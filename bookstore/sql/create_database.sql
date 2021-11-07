CREATE TABLE author (
  id int NOT NULL AUTO_INCREMENT,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  CONSTRAINT id_pk PRIMARY KEY (id),
  CONSTRAINT author_name_uk UNIQUE (first_name,last_name)
);
CREATE INDEX first_name_idx on author(first_name);
CREATE INDEX last_name_idx on author(last_name);

CREATE TABLE book (
  isbn varchar(50) NOT NULL,
  title varchar(50) NOT NULL,
  publisher varchar(50) NOT NULL,
  year int NOT NULL,
  genre varchar(50) NOT NULL,
  CONSTRAINT isbn_pk PRIMARY KEY (isbn)
);
CREATE INDEX year_idx on book(year);
CREATE INDEX genre_idx on book(genre);

CREATE TABLE book_author (
  isbn varchar(50) NOT NULL,
  author_id int NOT NULL,
  author_index int NOT NULL,
  CONSTRAINT book_author_pk PRIMARY KEY (author_index,isbn),
  CONSTRAINT isbn_fk FOREIGN KEY (isbn) REFERENCES book(isbn),
  CONSTRAINT author_if_fk FOREIGN KEY (author_id) REFERENCES author(id)
) ;






