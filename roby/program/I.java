package zad1.program;

import zad1.Rob;

/**
 * Instrukcja i (idź) - "idź do przodu o jedno pole
 * (jeśli tam jest pożywienie, to je zjedz)".
 *
 * @author Mateusz Sulimowicz
 */
public class I implements Instrukcja {

    private static I instancja = null;

    private I() { }

    public static I getInstancja() {
        if (instancja == null) {
            instancja = new I();
        }
        return instancja;
    }

    @Override
    public void wykonaj(Rob r) {
        r.idzProsto();
    }

    public String toString() {
        return "I";
    }


}
