package robson.instructions;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;
import robson.instructions.expressions.arithmetic.Arithmetic;

import java.util.Map;

/**
 * Instrukcja 'Zmienna' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class Variable implements Arithmetic {

    @SerializedName("typ")
    private static final String type = "Zmienna";

    @SerializedName("nazwa")
    private final String name;

    private transient Map<String, Double> variables;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double execute() throws BladWykonania {
        return variables.get(name);
    }

    @Override
    public void setVariables(Map<String, Double> variables) {
        this.variables = variables;
        if (!variables.containsKey(name)) {
            variables.put(name, 0.0);
        }
    }

    @Override
    public void toJavaArgument(MyStringBuilder s) {
        s.append(name);
    }

    @Override
    public void toJavaLast(MyStringBuilder s) {
        s.append("System.out.println(");
        s.append(name);
        s.append(");\n");
    }

    @Override
    public String toString() {
        return "Zmienna";
    }

}
