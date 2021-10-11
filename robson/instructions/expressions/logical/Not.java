package robson.instructions.expressions.logical;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;

import java.util.Map;

/**
 * Wyrażenie logiczne 'Not' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class Not implements Logical {

    @SerializedName("typ")
    private static final String type = "Not";

    @SerializedName("argument")
    private final Logical argument;

    public Not(Logical argument) {
        this.argument = argument;
    }

    @Override
    public double execute() throws BladWykonania {
        return argument.execute() + 1 % 2;
    }

    @Override
    public void toJavaArgument(MyStringBuilder s) {
        s.append("!(");
        argument.toJavaArgument(s);
        s.append(")");
    }

    @Override
    public void toJavaLast(MyStringBuilder s) {
        s.append("!(");
        argument.toJavaLast(s);
        s.append(")");
    }

    @Override
    public void setVariables(Map<String, Double> variables) {
        argument.setVariables(variables);
    }

}
