package zad1.program;

import zad1.Parametry;
import zad1.Rob;

import java.util.Random;

/**
 * W symulacji, każdy rob ma swój program.
 * Program składa się z ciągu instrukcji,
 * które są wykonywane po jednej na turę.
 *
 * @author Mateusz Sulimowicz
 */
public class Program {

    private final static Random random = new Random(123);

    private final Instrukcja[] instrukcje;

    private int ileWykonanych;

    /**
     * Standardowo program tworzymy podając instrukcje,
     * z których ma się on składać.
     *
     * @param instrukcje ciąg instrukcji programu.
     */
    public Program(Instrukcja[] instrukcje) {
        this.instrukcje = instrukcje;
    }

    /**
     * Program może być stworzony na podstawie innego programu.
     * W zależności od ustawień symulacji,
     * nowy program może różnić się od programu, na którym bazuje.
     * Może nie mieć ostatniej istrukcji, mieć zmienioną
     * jakąś instrukcję lub dodaną nową.
     *
     * @param p program, na którym bazuje nowy program
     * @param u ustawienia symulacji
     */
    public Program(Program p, Parametry u) {
        Instrukcja[] baza = p.getInstrukcje();
        this.instrukcje = getZmodyfikowane(baza, u);
    }

    /**
     * Tworzy ciąg instrukcji, który bazuje na pewnym ciągu,
     * ale może być względem niego zmodyfikowany,
     * w zależności od ustawień symulacji.
     *
     * @param baza bazowy ciąg instrukcji
     * @param u    ustawienia symulacji
     * @return zmodyfikowany ciąg instrukcji
     */
    private static Instrukcja[] getZmodyfikowane(Instrukcja[] baza, Parametry u) {
        Instrukcja losowa = Instrukcja.getLosowąInstrukcję(u);
        int ile = baza.length;
        int nowyRozmiar;
        boolean usuń_ostatnią =
                random.nextDouble() <= u.getPrUsunięciaInstr();
        boolean dodaj_na_koniec =
                random.nextDouble() <= u.getPrDodaniaInstr();

        if (usuń_ostatnią && !dodaj_na_koniec) {
            nowyRozmiar = ile - 1;
        } else if (usuń_ostatnią || !dodaj_na_koniec) {
            nowyRozmiar = ile;
        } else {
            nowyRozmiar = ile + 1;
        }

        if (nowyRozmiar > 0) {
            Instrukcja[] nowe = new Instrukcja[nowyRozmiar];
            int r = Math.min(nowyRozmiar, ile);
            System.arraycopy(baza, 0, nowe, 0, r);

            if (dodaj_na_koniec) {
                nowe[nowyRozmiar - 1] = losowa;
            }

            boolean zmień_losową = (random.nextDouble() <= u.getPrZmianyInstr());
            if (zmień_losową) {
                int i = random.nextInt(nowe.length);
                losowa = Instrukcja.getLosowąInstrukcję(u);
                nowe[i] = losowa;
            }
            return nowe;
        } else { // Nowy ciąg instrukcji może być pusty.
            return new Instrukcja[0];
        }
    }

    /**
     * Program ma być wykonywany od początku.
     */
    public void resetuj() {
        this.ileWykonanych = 0;
    }

    /**
     * Sprawdza, czy program został w całości wykonany.
     *
     * @return Czy program został wykonany?
     */
    public boolean czyWykonany() {
        return ileWykonanych == instrukcje.length;
    }

    /**
     * Wykonuje następną instrukcję roba.
     *
     * @param r rob, na którym następna instrukcja ma być wykonana
     */
    public void wykonajNastępnąInstr(Rob r) {
        if (ileWykonanych < instrukcje.length) {
            instrukcje[ileWykonanych++].wykonaj(r);
        }
    }

    /**
     * Raportuje o ciągu instrukcji programu
     * oraz o instrukcjach, jakie jeszcze nie zostały wykonane
     *
     * @param b StringBuilder, do którego program dołącza swój raport
     */
    public void raportuj(StringBuilder b) {
        b.append(", program: ");
        for (Instrukcja i : instrukcje) {
            b.append(i);
        }
        b.append("\n");
    }

    public Instrukcja[] getInstrukcje() {
        return instrukcje;
    }

    public int getDługość() {
        return instrukcje.length;
    }

}
