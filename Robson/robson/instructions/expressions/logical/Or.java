package robson.instructions.expressions.logical;

import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;

/**
 * Wyrażenie logiczne 'Or' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class Or extends BinaryLogical implements Logical {

    @SerializedName("typ")
    private static final String type = "Or";

    public Or(Logical left, Logical right) {
        super(left, right);
    }

    @Override
    protected double executeBinary(Logical lewy, Logical prawy) throws BladWykonania {
        if (lewy.execute() == 1 || prawy.execute() == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected String toJavaOperator() {
        return " || ";
    }


}
