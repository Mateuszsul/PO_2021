package robson.instructions.expressions.arithmetic;

import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;

/**
 * Wyrażenie arytmetyczne 'Dzielenie' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class Divide extends BinaryArithmetic {

    @SerializedName("typ")
    private static final String type = "Dzielenie";

    public Divide(Arithmetic left, Arithmetic right) {
        super(left, right);
    }

    @Override
    protected double executeBinary(Arithmetic a, Arithmetic b) throws BladWykonania {
        return a.execute() / b.execute();
    }

    @Override
    protected String toJavaOperator() {
        return " / ";
    }

}
