package zad1;

import zad1.program.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Przechowuje parametry symulacji.
 * <p>
 * Przebieg symulacji zależy od ustawionych wartości jej parametrów:
 * <ul>
 * <li>pocz_energia - początkowa energia, którą dysponuje rob,
 * <li>pocz_ile_robów - początkowa liczba robów na planszy,
 * <li>koszt_tury - tyle energii kosztuje roba przeżycie jednej tury,
 * <li>limit_powielania - tyle razy maksymalnie rob może się powielić,
 * <li>ułamek_energii_rodzica - taką część energii zabiera rodzicowi potomek, po powieleniu
 *      nie będzie ostatniej instrukcji rodzica.
 * <li>pr_powielenia - prawdopodobieństwo powielenia roba,
 * <li>pr_dodania_instr - prawdopodobieństwo, że przy powieleniu, nowy rob będzie miał
 *  * o jedną instrukcję więcej,
 * <li>pr_zmiany_instr - prawdopodobieństwo, że nowy rob będzie miał jedną inną instrukcję niż rodzic.
 * <li>spis_instr - zbiór instrukcji, które mogą zostać dodane/zmienione w programie roba,
 * <li>pocz_progr - program robów na starcie symulacji.
 * <li>ile_daje_jedzenie - tyle energii uzyskuje rob po zjedzeniu pożywienia,
 * <li>ile_tur - liczba tur symulacji,
 * <li>co_ile_wypisz - co tyle tur ma być wypisany szczegółowy raport symulacji,
 * <li>pr_usunięcia_instr - prawdopodobieństwo, że przy powieleniu w ciągu instrukcji nowego roba,
 * <li>ile_rośnie_jedzenie - po tylu turach jedzenia odnawia się na polu żywieniowym.
 * </ul>
 * </p>
 *
 * @author Matesz Sulimowicz
 */
public class Parametry {

    private final static String[] nazwy = {
            "pocz_energia",
            "pocz_ile_robów",
            "ile_daje_jedzenie",
            "ile_rośnie_jedzenie",
            "koszt_tury",
            "limit_powielania",
            "ile_tur",
            "ułamek_energii_rodzica",
            "pr_powielenia",
            "pr_usunięcia_instr",
            "pr_dodania_instr",
            "pr_zmiany_instr",
            "spis_instr",
            "pocz_progr",
            "co_ile_wypisz"
    };

    private int liczbaTur;

    private Program poczProgr;

    private int poczEnergia;

    private int poczIleRobów;

    private int ileDajeJedzenie;

    private int ileRośnieJedzenie;

    private int kosztTury;

    private double prPowielenia;

    private double ułamekEnergiiRodzica;

    private int limitPowielania;

    private double prUsunięciaInstr;

    private double prDodaniaInstr;

    private Instrukcja[] spisInstr;

    private double prZmianyInstr;

    private int coIleWypisz;

    public Parametry(File parametry) throws FileNotFoundException {
        Scanner parametry_txt = new Scanner(parametry);
        HashSet<String> przypisane = new HashSet<>();

        try {
            int numerWiersza = 1;
            while (parametry_txt.hasNextLine()) {
                String wiersz = parametry_txt.nextLine();
                przypisz(wiersz, numerWiersza, przypisane);
                ++numerWiersza;
            }
        } catch (NiepoprawnyParametr e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        for (String s : nazwy) {
            if (!przypisane.contains(s)) {
                System.err.println("Niekompletne dane. Brakuje parametru " + s);
                System.exit(1);
            }
        }
    }

    /**
     * Sprawdza, czy podany napis
     * reprezentuje poprawny ciąg instrukcji.
     *
     * @param s napis
     * @return Czy s jest poprawnym ciągiem instrukcji?
     */
    private static boolean czyPoprawnyCiąg(String s) {
        // Możliwe oznaczenia instrukcji to {l, p, i, j, w}.
        return s.matches("[plijw]*");
    }

    /**
     * Sprawdza, czy podany napis
     * reprezentuje poprawny spis instrukcji.
     *
     * @param s napis
     * @return Czy s jest poprawnym spisem instrukcji?
     */
    private static boolean czyPoprawnySpis(String s) {
        HashSet<Character> znaki = new HashSet<>();
        for (int i = 0; i < s.length(); ++i) {
            char znak = s.charAt(i);
            if (znaki.contains(znak)) {
                return false;
            } else {
                znaki.add(znak);
            }
        }
        return true;
    }

    /**
     * Parsuje napis na ciąg instrukcji.
     *
     * @param s napis
     * @return ciąg instrukcji z s.
     */
    private static Instrukcja[] parsuj(String s) {
        Instrukcja[] spis = new Instrukcja[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            Instrukcja instr = null;
            char c = s.charAt(i);
            switch (c) {
                case 'i':
                    instr = I.getInstancja();
                    break;
                case 'l':
                    instr = L.getInstancja();
                    break;
                case 'p':
                    instr = P.getInstancja();
                    break;
                case 'w':
                    instr = W.getInstancja();
                    break;
                case 'j':
                    instr = J.getInstancja();
                    break;
                default:
                    break;
            }
            spis[i] = instr;
        }
        return spis;
    }

    /**
     * Przypisuje wartość odpowiedniego parametru symulacji
     * na podstawie wiersza tekstu o prawidłowym formacie '[nazwa_parametru] [wartość]'.
     *
     * @param wiersz     parsowany wiersz
     * @param numer      numer parsowanego wiersza.
     * @param przypisane zbiór nazw już przypisanych parametrów
     */
    private void przypisz(String wiersz, int numer, HashSet<String> przypisane) throws NiepoprawnyParametr {
        Scanner dane = new Scanner(wiersz);
        boolean poprawne = true;
        String s;
        try {
            String nazwa = dane.next();
            poprawne = wiersz.length() > nazwa.length() + 1 && wiersz.charAt(nazwa.length() + 1) != ' ';
            switch (nazwa) {
                case "pocz_energia":
                    poczEnergia = dane.nextInt();
                    poprawne &= poczEnergia >= 0;
                    break;
                case "pocz_ile_robów":
                    poczIleRobów = dane.nextInt();
                    poprawne &= poczIleRobów >= 0;
                    break;
                case "ile_daje_jedzenie":
                    ileDajeJedzenie = dane.nextInt();
                    poprawne &= ileDajeJedzenie >= 0;
                    break;
                case "ile_rośnie_jedzenie":
                    ileRośnieJedzenie = dane.nextInt();
                    poprawne &= ileRośnieJedzenie >= 0;
                    break;
                case "koszt_tury":
                    kosztTury = dane.nextInt();
                    poprawne &= kosztTury >= 0;
                    break;
                case "pr_powielenia":
                    prPowielenia = dane.nextDouble();
                    poprawne &= prPowielenia >= 0 && prPowielenia <= 1;
                    break;
                case "ułamek_energii_rodzica":
                    ułamekEnergiiRodzica = dane.nextDouble();
                    poprawne &= ułamekEnergiiRodzica >= 0 && ułamekEnergiiRodzica <= 1;
                    break;
                case "limit_powielania":
                    limitPowielania = dane.nextInt();
                    poprawne &= limitPowielania >= 0;
                    break;
                case "pr_usunięcia_instr":
                    prUsunięciaInstr = dane.nextDouble();
                    poprawne &= (prUsunięciaInstr >= 0 && prUsunięciaInstr <= 1);
                    break;
                case "pr_dodania_instr":
                    prDodaniaInstr = dane.nextDouble();
                    poprawne &= (prDodaniaInstr >= 0 && prDodaniaInstr <= 1);
                    break;
                case "pr_zmiany_instr":
                    prZmianyInstr = dane.nextDouble();
                    poprawne &= (prZmianyInstr >= 0 && prZmianyInstr <= 1);
                    break;
                case "ile_tur":
                    liczbaTur = dane.nextInt();
                    poprawne &= liczbaTur >= 0;
                    break;
                case "co_ile_wypisz":
                    coIleWypisz = dane.nextInt();
                    poprawne &= coIleWypisz > 0;
                    break;
                case "spis_instr":
                    s = dane.next();
                    poprawne &= czyPoprawnySpis(s);
                    spisInstr = parsuj(s);
                    break;
                case "pocz_progr":
                    if (dane.hasNext()) {
                        s = dane.next();
                        poprawne &= czyPoprawnyCiąg(s);
                        Instrukcja[] instrukcje = parsuj(s);
                        poczProgr = new Program(instrukcje);
                    } else {
                        poprawne = wiersz.length() == nazwa.length() + 1 && wiersz.charAt(nazwa.length()) == ' ';
                        poczProgr = new Program(new Instrukcja[0]);
                    }
                    break;
                default:
                    // W pliku z parametrami nie mogą wystąpić
                    // inne od podanych nazwy parametrów.
                    throw new NiepoprawnyParametr("Wiersz: " + numer + " Niepoprawna nazwa parametru " + nazwa);
            }
            poprawne &= !dane.hasNext();
            // W jednym wierszu może znajdować się
            // tylko jeden parametr.
            if (!poprawne) {
                throw new NiepoprawnyParametr("Wiersz: " + numer + ". Niepoprawna wartość parametru " + nazwa);
            }
            if (!przypisane.contains(nazwa)) {
                // Jeśli parametr o aktualnej nazwie
                // już został przypisany, to dane są niepoprawne.
                przypisane.add(nazwa);
            } else {
                throw new NiepoprawnyParametr("Wiersz: " + numer + ". Parametr podany wielokrotnie: " + nazwa);
            }
        } catch (NoSuchElementException e) { // Niepoprawny format wartości parametru w pliku.
            throw new NiepoprawnyParametr("Wiersz: " + numer + ". Brak lub niepoprawna wartość parametru ");
        }
    }

    public double getPrDodaniaInstr() {
        return prDodaniaInstr;
    }

    public double getPrUsunięciaInstr() {
        return prUsunięciaInstr;
    }

    public double getPrZmianyInstr() {
        return prZmianyInstr;
    }

    public double getUłamekEnergiiRodzica() {
        return ułamekEnergiiRodzica;
    }

    public Instrukcja[] getSpisInstr() {
        return spisInstr;
    }

    public int getIleDajeJedzenie() {
        return ileDajeJedzenie;
    }

    public int getIleRośnieJedzenie() {
        return ileRośnieJedzenie;
    }

    public int getKosztTury() {
        return kosztTury;
    }

    public int getLimitPowielania() {
        return limitPowielania;
    }

    public int getPoczEnergia() {
        return poczEnergia;
    }

    public int getPoczIleRobów() {
        return poczIleRobów;
    }

    public double getPrPowielenia() {
        return prPowielenia;
    }

    public Program getPoczProgr() {
        return poczProgr;
    }

    public int getIleTur() {
        return liczbaTur;
    }

    public int getCoIleWypisz() {
        return coIleWypisz;
    }

}
