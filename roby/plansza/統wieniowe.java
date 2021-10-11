package zad1.plansza;

/**
 * Pole żywieniowe - początkowo znajduje się na nim pożywienie,
 * po zjedzeniu, pożywienie odnawia się po ile_rośnie_jedzenie turach.
 * @author Mateusz Sulimowicz
 */
public class Żywieniowe extends Pole {

    private boolean jest_pokarm;

    private int ile_rośnie;

    public Żywieniowe(int pozX, int pozY, Plansza plansza) {
        super(pozX, pozY, plansza);
        this.jest_pokarm = true;
    }

    @Override
    public void przeprowadźTurę() {
        if (!jest_pokarm) {
            if (ile_rośnie < this.getUstawienia().getIleRośnieJedzenie()) {
                ++ile_rośnie;
            } else {
                ile_rośnie = 0;
                jest_pokarm = true;
            }
        }
    }

    @Override
    public boolean czyJestJedzenie() {
        return jest_pokarm;
    }

    @Override
    public boolean zjedzPokarm() {
        if (this.czyJestJedzenie()) {
            jest_pokarm = false;
            return true;
        } else {
            return false;
        }
    }

}
