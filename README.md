# Proiect: GlobalWaves - Pagination

 1. Pentru a adauga functionalitate sistemului de pagini am creat clase aferente fiecarui tip de pagina care implementeaza o interfata `Page` ce contine antetul metodei `printCurrentPage`, pentru a putea obtine la printarea paginii curente un rezultat specific fiecarui tip de pagina am folosit `Factory Design Pattern`, obtinand cu ajutorul acestuia o instanta pentru clasa corespunzatoare unui tip de pagina, care suprascrie metoda `printCurrentPage`.

2. Am adaugat clasele user-ilor noi: `Artist` si `Host`, am redenumit clasa `User` in `NormalUser`, clasa `User` devenind o clasa abstracta ce contine proprietatile comune tuturor utilizatorilor aplicatiei si tipul acestor utilizatori, fiind extinsa de `Artist`, `Host` si `NormalUser`.

La primirea comenzii de `deleteUser` se verifica tipul acestuia si pentru:

## User normal:
   - Se verifica daca unul din playlist-urile acestuia nu este ascultat de catre alti utilizatori normali.
   - Daca e posibila stergerea acestuia, se da unfollow si dislike pentru playlist-urile urmarite si cantecele apreciate. De asemenea se scot playlist-urile acestuia din lista de urmariri a fiecarui user normal.

## Artist:
   - Se verifica daca nu sunt ascultate albumele sau cantecele din aceste albume de catre alti useri
   - Se verifica daca un alt user nu se afla pe pagina artistului.
   - Inainte de stergere se parcurg utilizatorii si se scot din listele lor de likedsongs cantecele artistului.
   - La stergere se elimina din aplicatie cantecele cat si albumele acestui artist.

## Host:
   - Verific daca podcasturile acestuia nu sunt ascultate de catre alti utilizatori.
   - Verific cazul de localizare pe pagina acestuia.
   - La stergerea acestuia se elimina toate podcasturile create de acesta din aplicatie.
