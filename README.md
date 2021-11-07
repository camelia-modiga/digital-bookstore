# Digital bookstore
Proiectul urmarește dezvoltarea unei aplicații de tip magazin virtual pentru o librărie digitală utilizând Spring Boot. <br >
Funcționalitățile urmărite includ:
* gestiunea unei colecții de cărți oferite spre vânzare; această colecție va fi organizată pe genuri literare și pe ani, și trebuie să permită operații de căutare după criterii precum titlu, gen literar, autori sau an de apariției/publicare;
* gestiunea utilizatorilor și a sesiunilor de lucru ale acestora; utilizatorii aplicației vor fi grupați, în funcție de drepturile pe care le dețin, în următoarele trei categorii:<br >
  + **administrator** controlează conturile utilizatorilor aplicației;<br >
  + **manager** gestionează colecția de cărți oferite spre vânzare, inclusiv prețurile și stocurile cărților;<br>
  + **client** poate naviga prin colecția de cărți, poate construi colectții de cărți dorite/urmăarite și poate cumpăra cărți.

* gestiunea comenzilor și a facturilor corespunzătoare fiecărei comenzi în parte
* gestiunea datelor de tip profil personal pentru utilizatorii aplicației și a celor necesare facturării comenzilor lansate de către clienți;

Elementele decrise anterior vor fi implementate prin intermediul unor servicii Web. Pentru a putea realiza acțiuni complexe (de exemplu, plasarea unei comenzi pentru cărți și emiterea facturii de plată), toate serviciile Web identificate vor fi disponibile prin intermediul unei componente de tip gateway. Utilizatorii finali ai unei astfel de aplicații vor interacționa cu acest gateway prin intermediul unei aplicații Web dezvoltate în React JS.
