package zad1.plansza;

import zad1.Parametry;
import zad1.Wektor;

/**
 * Reprezentuje puste pole - nie ma na nim pożywienia i
 * nigdy nie wyrośnie.
 * @author Mateusz Sulimowicz
 */
public class Pole {

    private final Plansza plansza;

    private final Wektor pozycja;

    protected Pole(int pozX, int pozY, Plansza plansza) {
        this.pozycja = new Wektor(pozX, pozY);
        this.plansza = plansza;
    }

    protected void przeprowadźTurę() {
        // nic nie robimy.
    }

    public boolean czyJestJedzenie() {
        return false;
    }

    public Parametry getUstawienia() {
        return plansza.getUstawienia();
    }

    public boolean zjedzPokarm() {
        return false;
    }

    public Pole getSąsiednie(Wektor kierunek) {
        Wektor suma = new Wektor(pozycja, kierunek);
        return plansza.getPole(suma);
    }

    public Wektor getPozycja() {
        return pozycja;
    }

}
