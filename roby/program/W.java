package zad1.program;

import zad1.Rob;

/**
 * Instrukcja w (wąchaj) - "sprawdź, czy któraś z
 * (czterech) sąsiednich komórek ma pożywienie,
 * jeśli tak, to zwróć się w jej stronę
 * (bez przechodzenia)".
 *
 * @author Mateusz Sulimowicz
 */
public class W implements Instrukcja {

    private static W instancja = null;

    private W() { }

    public static W getInstancja() {
        if (instancja == null) {
            instancja = new W();
        }
        return instancja;
    }

    @Override
    public void wykonaj(Rob r) {
        r.wąchaj();
    }

    public String toString() {
        return "W";
    }

}
