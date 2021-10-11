package zad1.program;

import zad1.Rob;

/**
 * Instrukcja j (jedz) - "sprawdź, czy któraś z (ośmiu)
 * sąsiednich komórek (także te na ukos) ma pożywienie,
 * jeśli tak, to przejdź tam i zjedz, wpp nic nie rób."
 *
 * @author Mateusz Sulimowicz
 */
public class J implements Instrukcja {

    private static J instancja = null;

    private J() { }

    public static J getInstancja() {
        if (instancja == null) {
            instancja = new J();
        }
        return instancja;
    }

    @Override
    public void wykonaj(Rob r) {
        r.jedz();
    }

    public String toString() {
        return "J";
    }

}
