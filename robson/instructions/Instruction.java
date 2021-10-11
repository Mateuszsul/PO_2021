package robson.instructions;

import com.google.gson.JsonObject;
import robson.BladWykonania;
import robson.MyStringBuilder;

import java.util.Map;

/**
 * Interfejs instrukcji języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public interface Instruction {

    /**
     * Wykonuje instrukcję.
     * @return zwracana wartość instrukcji
     * @throws BladWykonania jeśli wykonanie programu się nie powiodło
     */
    double execute() throws BladWykonania;

    /**
     * Dodaje do StringBuildera s reprezentację instrukcji w języku Java
     * @param s StringBuilder, który konwertuje program w Robsonie do kodu w Javie
     */
    default void toJava(MyStringBuilder s) {
        // nic nie robimy.
    }

    /**
     * Dodaje do StringBuildera s reprezentację instrukcji w języku Java
     * jako ostatniej instrukcji programu.
     * @param s StringBuilder, który konwertuje program w Robsonie do kodu w Javie
     */
    void toJavaLast(MyStringBuilder s);

    /**
     * Przekazuje instrukcji zmienne programu, które ma do dyspozycji
     * @param variables : słownik globalnych zmiennych programu
     */
    default void setVariables(Map<String, Double> variables) {
        // nic nie robimy.
    }

}
