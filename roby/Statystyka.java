package zad1;

import zad1.plansza.Plansza;

import java.util.Collection;

/**
 * Statystyka dotycząca stanu symulacji w danej turze.
 *
 * @author Mateusz Sulimowicz
 */
public class Statystyka {

    private int numerTury;

    private int ileRobów;

    private int ilePólŻywność;

    private int minDłProgramu;

    private double śrDłProgramu;

    private int maxDłProgramu;

    private int minEnergiaRoba;

    private double śrEnergiaRoba;

    private int maxEnergiaRoba;

    private int minWiekRoba;

    private double śrWiekRoba;

    private int maxWiekRoba;

    /**
     * Sporządza statystykę danej tury symulacji.
     *
     * @param roby      zbiór robów symulacji
     * @param plansza   plansza, na której odbywa się symulacja
     * @param numerTury aktualny numer tury symulacji
     */
    public Statystyka(Collection<Rob> roby, Plansza plansza, int numerTury) {
        obliczRobInfo(roby);
        obliczPlanszaInfo(plansza);
        this.numerTury = numerTury;
    }

    /**
     * Wyznacza początkowe wartości atrybutów
     * do poprawnego liczenia minimów wartości
     *
     * @param roby zbiór robów, których dotyczy statystyka
     */
    private void inicjalizujInfo(Collection<Rob> roby) {
        if (roby.iterator().hasNext()) {
            Rob r = roby.iterator().next();
            minEnergiaRoba = r.getEnergia();
            minWiekRoba = r.getWiek();
            minDłProgramu = r.getDługośćProgramu();
            maxDłProgramu = 0;
            maxEnergiaRoba = 0;
            maxWiekRoba = 0;
        }
    }

    /**
     * Ustala statystykę dotyczącą robów w symulacji.
     *
     * @param roby zbiór robów, których dotyczy statystyka
     */
    private void obliczRobInfo(Collection<Rob> roby) {
        ileRobów = roby.size();
        inicjalizujInfo(roby);
        int wiekSuma = 0;
        int dłProgSuma = 0;
        int energiaSuma = 0;
        for (Rob r : roby) {
            int wiek = r.getWiek();
            minWiekRoba = Math.min(wiek, minWiekRoba);
            maxWiekRoba = Math.max(wiek, maxWiekRoba);
            wiekSuma += wiek;

            int energia = r.getEnergia();
            minEnergiaRoba = Math.min(energia, minEnergiaRoba);
            maxEnergiaRoba = Math.max(energia, maxEnergiaRoba);
            energiaSuma += energia;

            int dłProg = r.getDługośćProgramu();
            minDłProgramu = Math.min(dłProg, minDłProgramu);
            maxDłProgramu = Math.max(dłProg, maxDłProgramu);
            dłProgSuma += dłProg;
        }
        if (ileRobów > 0) { // żeby nie dzielić przez 0.
            śrWiekRoba = (wiekSuma * 1.0) / ileRobów;
            śrEnergiaRoba = (energiaSuma * 1.0) / ileRobów;
            śrDłProgramu = (dłProgSuma * 1.0) / ileRobów;
        }
    }

    /**
     * Ustala statystykę dotyczącą planszy.
     *
     * @param plansza plansza, na której odbywa się ewolucja robów.
     */
    private void obliczPlanszaInfo(Plansza plansza) {
        ilePólŻywność = plansza.getIleZJedzeniem();
    }

    protected void aktualizujInfo(Collection<Rob> roby, Plansza plansza, int numerTury) {
        obliczRobInfo(roby);
        obliczPlanszaInfo(plansza);
        this.numerTury = numerTury;
    }

    @Override
    public String toString() {
        String stat;
        String prgInfo = minDłProgramu + "/" + String.format("%.2f", śrDłProgramu) + "/" + maxDłProgramu;
        String energInfo = minEnergiaRoba + "/" + String.format("%.2f", śrEnergiaRoba) + "/" + maxEnergiaRoba;
        String wiekInfo = minWiekRoba + "/" + String.format("%.2f", śrWiekRoba) + "/" + maxWiekRoba;
        stat = String.valueOf(numerTury);
        stat += ", rob: " + ileRobów;
        stat += ", żyw: " + ilePólŻywność;
        stat += ", prg: " + prgInfo;
        stat += ", energ: " + energInfo;
        stat += ", wiek: " + wiekInfo;
        return stat;
    }

}
