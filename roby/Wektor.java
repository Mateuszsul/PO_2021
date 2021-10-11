package zad1;

import java.util.Random;

/**
 * Reprezentuje wektor.
 *
 * @author Mateusz Sulimowicz
 */
public class Wektor {

    private static final Random random = new Random();

    private final int x;

    private final int y;

    /**
     * Tworzy wektor na podstawie 2 współrzędnych.
     *
     * @param x współrzędna x wektora
     * @param y współrzędna y wektora
     */
    public Wektor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Tworzy wektor będący sumą u + v.
     *
     * @param u wektor
     * @param v wektor
     */
    public Wektor(Wektor u, Wektor v) {
        this.x = u.getX() + v.getX();
        this.y = u.getY() + v.getY();
    }

    public static Wektor getLosowy(int maxX, int maxY) {
        int x = random.nextInt(maxX);
        int y = random.nextInt(maxY);
        return new Wektor(x, y);
    }

    public static Wektor getLosowyKierunek() {
        int x = random.nextInt(4);
        Wektor w = new Wektor(0, 1);
        for (int i = 0; i < x; ++i) {
            w = w.obróc90WPrawo();
        }
        return w;
    }

    public Wektor obróc90WPrawo() {
        return new Wektor(y, -x);
    }

    public Wektor obróc90WLewo() {
        return new Wektor(-y, x);
    }

    public Wektor getPrzeciwny() {
        return new Wektor(-x, -y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String raportujKierunek() {
        if (x == 0) {
            if (y > 0) {
                return "GÓRA";
            } else if (y < 0) {
                return "DÓŁ";
            }
        } else if (x > 0) {
            return "PRAWO";
        } else {
            return "LEWO";
        }
        return "NIEPOPRAWNY";
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
