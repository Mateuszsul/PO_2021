"ROBSON" simple programming language interpreter

W rozwiązaniu została użyta biblioteka com.google.code.gson:gson:2.8.52.

Program kompiluje się poleceniem:
$ javac -cp .:jars/gson-2.8.5.jar robson/RobsonTest.java 

Natomiast uruchamia się poleceniem:
$ java -cp .:jars/gson-2.8.5.jar robson/RobsonTest dane.json

gdzie plik dane.json zawiera program zapisany w języku Robson w formacie JSON.

Do rozwiązania dołączony jest w pliku NWD.json przykładowy program w języku Robson. Wczytany program oblicza NWD(5,10).

