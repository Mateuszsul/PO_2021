package robson.instructions.expressions;

import robson.MyStringBuilder;
import robson.instructions.Instruction;

/**
 * Interfejs wyrażenia języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public interface Expression extends Instruction {

    /**
     * Dodaje do StringBuildera s reprezentację wyrażenia jako argumentu instrukcji.
     * @param s StringBuilder, który konwertuje program do kodu w Javie
     */
    void toJavaArgument(MyStringBuilder s);

}
