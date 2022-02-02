insert into author (first_name,last_name) values ("Edward","Snowden");
insert into author (first_name,last_name) values ("Cristian","Vladimirescu");
insert into author (first_name,last_name) values ("James","Clear");
insert into author (first_name,last_name) values ("Kevin","Dutton");
insert into author (first_name,last_name) values ("Daniel","Goleman");
insert into author (first_name,last_name) values ("Markus","Zusak");

insert into book values ("ISBN978-3-16-148410-0","Dosar permanent","Nemira",2019,"Autobiografii", 20.0, 40);
insert into book values ("ISBN978-3-16-148410-1","Stilet cu sampanie","Nemira",2020,"Fictiune", 15.5, 45);
insert into book values ("ISBN978-6-06-430912-2","Atomic Habits","Lifestyle",2019,"Lifestyle", 19, 23);
insert into book values ("ISBN978-6-06-430912-3","Arta manipularii","Globo",2019,"Dezvoltare personala", 14.5, 2);
insert into book values ("ISBN978-6-06-430912-4","Inteligenta emotionala","Curtea Veche",2018,"Dezvoltare personala", 10, 4);
insert into book values ("ISBN978-6-06-430912-9 ","Minerva se dezlantuie","Nemira",2019,"Fictiune", 34, 9);
insert into book values ("ISBN978-6-06-430912-5","Hotul de carti","RAO",2011,"SF", 10, 10);

insert into book_author values("ISBN978-3-16-148410-0",1,1);
insert into book_author values("ISBN978-3-16-148410-0",2,2);
insert into book_author values ("ISBN978-6-06-430912-2",3,3);
insert into book_author values ("ISBN978-6-06-430912-2",4,4);
insert into book_author values ("ISBN978-6-06-430912-4",5,5);
insert into book_author values ("ISBN978-6-06-430912-5",6,6);