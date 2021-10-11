package robson;

/**
 * Klasa testująca działanie
 * metod klasy Robson na przykładowym programie.
 *
 * @author Mateusz Sulimowicz
 */
public class RobsonTest {

    public static void main(String[] args) {
        Robson robson = new Robson();
        String filename = args[0];
        try {
            robson.fromJSON(filename);
            System.out.println(robson.wykonaj());
            // Zapisuje do pliku RobsonInJava.java
            // reprezentację wczytanego programu jako kompilowalny program w Javie.
            robson.toJava("RobsonInJava.java");
            // Zapisuje do pliku Robson.json reprezentację wczytanego programu w JSON.
            robson.toJSON("Robson.json");
        } catch (NieprawidlowyProgram | BladWykonania e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
