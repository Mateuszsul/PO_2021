package robson.instructions.expressions.logical;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;

import java.util.Map;

/**
 * Binarne wyrażenie logiczne języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public abstract class BinaryLogical implements Logical {

    @SerializedName("argument1")
    private final Logical left;

    @SerializedName("argument2")
    private final Logical right;

    protected BinaryLogical(Logical left, Logical right) {
        this.left = left;
        this.right = right;
    }

    protected abstract double executeBinary(Logical left, Logical right) throws BladWykonania;

    @Override
    public double execute() throws BladWykonania {
        return executeBinary(left, right);
    }

    @Override
    public void setVariables(Map<String, Double> variables) {
        left.setVariables(variables);
        right.setVariables(variables);
    }

    protected abstract String toJavaOperator();

    @Override
    public void toJavaArgument(MyStringBuilder s) {
        left.toJavaArgument(s);
        s.append(toJavaOperator());
        right.toJavaArgument(s);
    }

    @Override
    public void toJavaLast(MyStringBuilder s) {
        s.append("System.out.println(");
        left.toJavaArgument(s);
        s.append(toJavaOperator());
        right.toJavaArgument(s);
        s.append(");\n");
    }

}
