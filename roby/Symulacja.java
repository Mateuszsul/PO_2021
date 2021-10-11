package zad1;

import zad1.plansza.Plansza;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Symulacja ewolucji robów.
 *
 * @author Mateusz Sulimowicz
 */
public class Symulacja {

    private final Parametry parametry;

    private final List<Rob> roby;

    private final Plansza plansza;

    private int nrTury = 1;

    private final Statystyka statystyka;

    /**
     * Tworzy nową symulację na podstawie planszy i parametrów.
     *
     * @param parametry parametry nowej symulacji
     * @param plansza   plansza symulacji
     */
    public Symulacja(Parametry parametry, Plansza plansza) {
        this.parametry = parametry;
        this.roby = new ArrayList<>();
        int maxX = plansza.getRozmiarX();
        int maxY = plansza.getRozmiarY();
        for (int i = 0; i < parametry.getPoczIleRobów(); ++i) {
            Wektor w = Wektor.getLosowy(maxX, maxY);
            Rob nowy = new Rob(parametry, plansza.getPole(w), parametry.getPoczProgr());
            roby.add(nowy);
        }
        this.plansza = plansza;
        this.statystyka = new Statystyka(roby, plansza, nrTury);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Niepoprawna liczba parametrów programu!");
            System.exit(1);
        }

        File parametry_txt = new File(args[1]);
        File plansza_txt = new File(args[0]);

        Parametry parametry = null;
        Plansza plansza = null;
        try {
            parametry = new Parametry(parametry_txt);
            plansza = new Plansza(plansza_txt, parametry);
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono któregoś z parametrów programu.");
            System.exit(1);
        }

        Symulacja symulacja = new Symulacja(parametry, plansza);
        symulacja.przeprowadź();
    }

    /**
     * Usuwa z planszy roby, których energia jest ujemna.
     */
    private void usuńMartweRoby() {
        ListIterator<Rob> it = roby.listIterator();
        while (it.hasNext()) {
            Rob r = it.next();
            if (!r.czyŻywy()) {
                it.remove();
            }
        }
    }

    /**
     * Przeprowadza jedną turę symulacji.
     */
    private void przeprowadźTurę() {
        ListIterator<Rob> it = roby.listIterator();
        while (it.hasNext()) {
            Rob r = it.next();
            if (r.czyŻywy()) {
                r.przeprowadźTurę();
                Rob nowy = r.powiel();
                if (nowy != null) {
                    it.add(nowy);
                }
            }
        }
        usuńMartweRoby();
        plansza.przeprowadźTurę();
        System.out.println(raport());
        ++nrTury;
    }

    /**
     * Przeprowadza całą symulację.
     */
    public void przeprowadź() {
        System.out.println(this);
        for (int i = 0; i < parametry.getIleTur(); ++i) {
            przeprowadźTurę();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        String s = "-------------- Stan symulacji --------------\nŻywe roby:\n";
        b.append(s);

        for (Rob r : roby) {
            r.raportuj(b);
        }
        s = "Pól z pożywieniem: " + plansza.getIleZJedzeniem() + "\n";
        b.append(s);
        b.append("--------------------------------------------\n");
        return b.toString();
    }

    /**
     * Tworzy, wypisywany co `co_ile_wypisz` tur,
     * szczegółowy raport symulacji.
     *
     * @return raport symulacji
     */
    public String raport() {
        String info = "";
        statystyka.aktualizujInfo(roby, plansza, nrTury);
        int coIle = parametry.getCoIleWypisz();
        if (nrTury % coIle == 0) {
            info += this;
        }
        info += statystyka;
        return info;
    }

}
