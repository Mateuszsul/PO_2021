package zad1.program;

import zad1.Rob;

/**
 * Instrukcja p (prawo) - "obróć się o 90 stopni w prawo".
 *
 * @author Mateusz Sulimowicz
 */
public class P implements Instrukcja {

    private static P instancja = null;

    private P() { }

    public static P getInstancja() {
        if (instancja == null) {
            instancja = new P();
        }
        return instancja;
    }

    @Override
    public void wykonaj(Rob r) {
        r.obróćWPrawo();
    }

    public String toString() {
        return "P";
    }


}
