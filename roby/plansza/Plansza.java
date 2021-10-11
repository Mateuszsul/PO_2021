package zad1.plansza;

import zad1.Parametry;
import zad1.Wektor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reprezentuje planszę, na której jest przeprowadzana symulacja.
 *
 * @author Mateusz Sulimowicz
 */
public class Plansza {

    private static final char PUSTE = ' ';

    private static final char ŻYWIENIOWE = 'x';

    private final Parametry parametry;

    private final Wektor rozmiar;

    private final Pole[][] pola;

    private int ileŻywieniowych;

    public Plansza(File plansza, Parametry parametry) throws FileNotFoundException {
        Scanner plansza_txt = new Scanner(plansza);

        Wektor w = null;
        Pole[][] p = null;

        try {
            w = getRozmiar(plansza_txt);
            int x = w.getX();
            int y = w.getY();

            plansza_txt = new Scanner(plansza);
            p = getPola(x, y, plansza_txt);
        } catch (NiepoprawnaPlansza e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        this.parametry = parametry;
        this.rozmiar = w;
        pola = p;
    }

    /**
     * Zczytuje z pliku `plansza.txt` rozmiar wejściowej planszy
     * @param plansza_txt Scanner po pliku `plansza.txt`
     * @return Wektor wyznaczający rozmiary planszy
     * @throws NiepoprawnaPlansza jeśli plansza nie jest prostokątem, sygnalizuje to wyjątkiem
     */
    private Wektor getRozmiar(Scanner plansza_txt) throws NiepoprawnaPlansza {
        int x = 0;
        int y = 0;
        String wiersz;
        boolean poprawna = plansza_txt.hasNextLine();

        while (poprawna && plansza_txt.hasNextLine()) { // Sprawdza czy każdy wiersz jest takiej samej długości.
            wiersz = plansza_txt.nextLine();
            poprawna = (wiersz.length() != 0 && (x == 0 || wiersz.length() == x));
            x = wiersz.length();
            ++y;
        }
        if (!poprawna) {
            throw new NiepoprawnaPlansza("Niepoprawny kształt planszy.");
        }
        return new Wektor(x, y);
    }

    /**
     * Zczytuje z pliku `plansza.txt` strukturę wejściowej planszy
     * @param x rozmiar planszy w poziomie
     * @param y rozmiar planszy w pionie
     * @param plansza_txt Scanner po pliku `plansza.txt`
     * @return dwuwymiarowa tablica pól reprezentująca plansze symulacji
     * @throws NiepoprawnaPlansza jeśli w pliku wejściowym znajdują się oznaczenia pól inne niż ` ` lub `x`,
     *         to jest to niepoprawna plansza
     */
    private Pole[][] getPola(int x, int y, Scanner plansza_txt) throws NiepoprawnaPlansza {
        int i = 0;
        Pole[][] pola = new Pole[x][y];

        while (plansza_txt.hasNextLine()) {
            String wiersz = plansza_txt.nextLine();
            for (int j = 0; j < wiersz.length(); ++j) {
                char c = wiersz.charAt(j);
                switch (c) {
                    case PUSTE:
                        pola[j][i] = new Pole(j, i, this);
                        break;
                    case ŻYWIENIOWE:
                        pola[j][i] = new Żywieniowe(j, i, this);
                        ++ileŻywieniowych;
                        break;
                    default:
                        throw new NiepoprawnaPlansza(
                                "Niepoprawny znak. Oznaczenia: ' ' - pole puste, 'x' - pole żywieniowe.");
                }
            }
            ++i;
        }
        return pola;
    }

    /**
     * Przeprowadza jedną turę planszy - na polach żywieniowych rośnie pożywienie.
     */
    public void przeprowadźTurę() {
        for (Pole[] pw : pola) {
            for (Pole p : pw)
                p.przeprowadźTurę();
        }
    }

    public Pole getPole(Wektor pozycja) {
        int x = pozycja.getX();
        int y = pozycja.getY();
        if (x < 0) {
            x = getRozmiarX() - 1;
        }
        if (y < 0) {
            y = getRozmiarY() - 1;
        }
        return pola[x % getRozmiarX()][y % getRozmiarY()];
    }

    public Parametry getUstawienia() {
        return parametry;
    }

    public int getRozmiarX() {
        return rozmiar.getX();
    }

    public int getRozmiarY() {
        return rozmiar.getY();
    }

    /**
     * @return na ilu polach aktualnie jest jedzenie
     */
    public int getIleZJedzeniem() {
        int ile = 0;
        for (Pole[] kolumna : pola) {
            for (Pole p : kolumna) {
                if (p.czyJestJedzenie()) {
                    ++ile;
                }
            }
        }
        return ile;
    }

}
