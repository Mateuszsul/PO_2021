package robson.instructions.expressions.arithmetic;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;

import java.util.Map;

/**
 * Binarne wyrażenie arytmetyczne języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public abstract class BinaryArithmetic implements Arithmetic {

    @SerializedName("argument1")
    private final Arithmetic left;

    @SerializedName("argument2")
    private final Arithmetic right;

    protected BinaryArithmetic(Arithmetic left, Arithmetic right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Wykonuje instrukcję typu binarne wyrażenie arytmetyczne
     * @param left lewy argument wyrażenia
     * @param right prawy argument wyrażenia
     * @return wynik wyrażenia
     * @throws BladWykonania w przypadku niepowodzenia wykonania instrukcji.
     */
    protected abstract double executeBinary(Arithmetic left, Arithmetic right) throws BladWykonania;

    @Override
    public double execute() throws BladWykonania {
        return executeBinary(left, right);
    }

    protected abstract String toJavaOperator();

    @Override
    public void setVariables(Map<String, Double> variables) {
        left.setVariables(variables);
        right.setVariables(variables);
    }

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
