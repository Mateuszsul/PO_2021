package zad1.program;

import zad1.Parametry;
import zad1.Rob;

import java.util.Random;

/**
 * Kod programu robów składa się z ciągu instrukcji.
 * W jednej turze, rob wykonuje jedną instrukcję.
 *
 * @author Mateusz Sulimowicz
 */
public interface Instrukcja {

    Random random = new Random();

    static Instrukcja getLosowąInstrukcję(Parametry parametry) {
        int zakres = parametry.getSpisInstr().length;
        return parametry.getSpisInstr()[random.nextInt(zakres)];
    }

    void wykonaj(Rob r);

}
