package robson.instructions.expressions.arithmetic;

import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;

/**
 * Wyrażenie arytmetyczne '%' (modulo) języka Robson.
 * (rozszerzenie)
 *
 * @author Mateusz Sulimowicz
 */
public class Modulo extends BinaryArithmetic {

    @SerializedName("typ")
    private static final String type = "%";

    public Modulo(Arithmetic left, Arithmetic right) {
        super(left, right);
    }

    @Override
    protected double executeBinary(Arithmetic a, Arithmetic b) throws BladWykonania {
        return Math.floor(Math.abs(a.execute())) %  Math.floor(Math.abs(b.execute()));
    }

    @Override
    protected String toJavaOperator() {
        return " % ";
    }

}
