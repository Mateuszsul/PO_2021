package robson.instructions.expressions.comparators;

import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.instructions.expressions.logical.Logical;
import robson.instructions.expressions.arithmetic.Arithmetic;
import robson.instructions.expressions.arithmetic.BinaryArithmetic;

/**
 * Wyrażenie logiczne '>' (Większe) języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class Greater extends BinaryArithmetic implements Logical {

    @SerializedName("typ")
    private static final String type = ">";

    public Greater(Arithmetic left, Arithmetic right) {
        super(left, right);
    }

    @Override
    protected double executeBinary(Arithmetic lewy, Arithmetic prawy) throws BladWykonania {
        if (lewy.execute() > prawy.execute()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected String toJavaOperator() {
        return " > ";
    }

}