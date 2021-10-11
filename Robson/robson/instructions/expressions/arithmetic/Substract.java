package robson.instructions.expressions.arithmetic;

import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;

/**
 * Wyrażenie arytmetyczne 'Minus' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class Substract extends BinaryArithmetic {

    @SerializedName("typ")
    private static final String type = "Minus";

    public Substract(Arithmetic left, Arithmetic right) {
        super(left, right);
    }

    @Override
    protected double executeBinary(Arithmetic a, Arithmetic b) throws BladWykonania {
        return a.execute() - b.execute();
    }

    @Override
    protected String toJavaOperator() {
        return " - ";
    }

}
