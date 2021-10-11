package zad1.program;

import zad1.Rob;

/**
 * Instrukcja l (lewo) - "obróć się o 90 stopni w lewo".
 *
 * @author Mateusz Sulimowicz
 */
public class L implements Instrukcja {

    private static L instancja = null;

    private L() { }

    public static L getInstancja() {
        if (instancja == null) {
            instancja = new L();
        }
        return instancja;
    }

    @Override
    public void wykonaj(Rob r) {
        r.obróćWLewo();
    }

    public String toString() {
        return "L";
    }

}
