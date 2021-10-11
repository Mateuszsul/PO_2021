package zad1;

import zad1.plansza.Pole;
import zad1.program.Program;

import java.util.Random;

/**
 * Reprezentuje roba, czyli stworzenie, którego ewolucja jest symulowana.
 *
 * @author Mateusz Sulimowicz
 */
public class Rob {

    private static final Random random = new Random();

    private final Parametry parametry;

    private final Program program;

    private Pole pole;

    private Wektor kierunek;

    private int energia;

    private int wiek;

    private int ile_powieleń;

    protected Rob(Parametry parametry, Pole pole, Program program) {
        this.parametry = parametry;
        this.pole = pole;
        this.energia = parametry.getPoczEnergia();
        this.program = new Program(program.getInstrukcje());
        this.kierunek = Wektor.getLosowyKierunek();
    }

    /**
     * Rob może utworzyć swojego potomka przy powieleniu.
     *
     * @param parametry parametry symulacji
     * @param pole      pole, na którym znajduje się rodzic
     * @param kierunek  kierunek przeciwny do kierunku rodzica
     * @param energia   ułamek energii rodzica
     * @param program   zmodyfikowany program rodzica
     */
    private Rob(Parametry parametry, Pole pole, Wektor kierunek, int energia, Program program) {
        this.parametry = parametry;
        this.pole = pole;
        this.energia = energia;
        this.program = program;
        this.kierunek = kierunek;
    }

    /**
     * Rob wykonuje swój program dopóki
     * nie zabraknie mu energii i program się nie zakończy.
     */
    public void przeprowadźTurę() {
        ++wiek;
        program.resetuj();
        while (this.energia > 0 && !program.czyWykonany()) {
            program.wykonajNastępnąInstr(this);
            energia -= 1;
        }

        energia -= parametry.getKosztTury();
    }

    public Rob powiel() {
        if (ile_powieleń < parametry.getLimitPowielania()
                && random.nextDouble() <= parametry.getPrPowielenia()) {
            Program nowyProgr = new Program(program, parametry);
            int nowaEnergia = (int) (energia * parametry.getUłamekEnergiiRodzica());
            energia -= nowaEnergia;
            ++ile_powieleń;
            return new Rob(parametry, pole, kierunek.getPrzeciwny(), nowaEnergia, nowyProgr);
        } else {
            return null;
        }
    }

    public void obróćWPrawo() {
        kierunek = kierunek.obróc90WPrawo();
    }

    public void obróćWLewo() {
        kierunek = kierunek.obróc90WLewo();
    }

    public void idzProsto() {
        pole = pole.getSąsiednie(kierunek);
        zjedz();
    }

    /**
     * Rob próbuje zjeść pokarm z pola, na które wchodzi.
     */
    private void zjedz() {
        if (pole.zjedzPokarm()) {
            energia += parametry.getIleDajeJedzenie();
        }
    }

    /**
     * Rob jest żywy, jeśli ma nieujemną energię.
     *
     * @return Czy rob jest żywy?
     */
    public boolean czyŻywy() {
        return energia >= 0;
    }

    /**
     * @see zad1.program.W
     */
    public void wąchaj() {
        Wektor w = new Wektor(this.kierunek.getX(), this.kierunek.getY());
        for (int i = 0; i < 4; ++i) {
            if (pole.getSąsiednie(w).czyJestJedzenie()) {
                kierunek = w;
                return;
            }
            w = w.obróc90WPrawo();
        }
    }

    /**
     * @see zad1.program.J
     */
    public void jedz() {
        int kierunekX = this.kierunek.getX();
        int kierunekY = this.kierunek.getY();
        Wektor w = new Wektor(kierunekX, kierunekY);
        for (int i = 0; i < 4; ++i) {
            if (pole.getSąsiednie(w).czyJestJedzenie()) {
                this.kierunek = w;
                this.idzProsto();
                this.kierunek = new Wektor(kierunekX, kierunekY);
                return;
            }
            w = w.obróc90WPrawo();
        }
        w = new Wektor(1, 1);
        for (int i = 0; i < 4; ++i) {
            if (pole.getSąsiednie(w).czyJestJedzenie()) {
                this.kierunek = w;
                this.idzProsto();
                this.kierunek = new Wektor(kierunekX, kierunekY);
                return;
            }
            w = w.obróc90WPrawo();
        }
    }

    /**
     * Rob raportuje o swym aktualnym stanie:
     * o pozycji, wieku, energii, programie.
     *
     * @param b StringBuilder, do którego rob dołącza raport
     */
    public void raportuj(StringBuilder b) {
        String pozInfo = getPozycja().toString();
        String wiekInfo = ", wiek: " + wiek;
        String energiaInfo = ", energ: " + energia;
        String kierunekInfo = ", kierunek: " + kierunek.raportujKierunek();
        b.append(pozInfo)
                .append(wiekInfo)
                .append(energiaInfo)
                .append(kierunekInfo);
        program.raportuj(b);
    }

    public Wektor getPozycja() {
        return pole.getPozycja();
    }

    public int getWiek() {
        return wiek;
    }

    public int getEnergia() {
        return energia;
    }

    public int getDługośćProgramu() {
        return program.getDługość();
    }

}
