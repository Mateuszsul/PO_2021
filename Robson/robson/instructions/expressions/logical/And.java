package robson.instructions.expressions.logical;

import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;

/**
 * Wyrażenie logiczne 'And' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class And extends BinaryLogical implements Logical {

    @SerializedName("typ")
    private static final String type = "And";

    public And(Logical left, Logical right) {
        super(left, right);
    }

    @Override
    protected double executeBinary(Logical left, Logical right) throws BladWykonania {
        if (left.execute() == 1 && right.execute() == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected String toJavaOperator() {
        return " && ";
    }

}
