package robson.instructions;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;
import robson.instructions.expressions.arithmetic.Arithmetic;

import java.util.Map;

/**
 * Instrukcja 'Przypisanie' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class Assignment implements Instruction {

    @SerializedName("typ")
    private static final String type = "Przypisanie";

    @SerializedName("nazwa")
    private final String name;

    @SerializedName("wartosc")
    private final Arithmetic expression;

    private transient Map<String, Double> variables;

    public Assignment(String name, Arithmetic expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void setVariables(Map<String, Double> variables) {
        this.variables = variables;
        expression.setVariables(variables);
        if (!variables.containsKey(name)) {
            variables.put(name, 0.0);
        }
    }

    @Override
    public double execute() throws BladWykonania {
        double value = expression.execute();
        variables.put(name, value);
        return value;

    }

    @Override
    public void toJava(MyStringBuilder s) {
        s.append(name);
        s.append(" = ");
        expression.toJavaArgument(s);
        s.append(";\n");
    }

    @Override
    public void toJavaLast(MyStringBuilder s) {
        s.append(name);
        s.append(" = ");
        expression.toJavaArgument(s);
        s.append(";\n");

        s.indent();
        s.append("System.out.println(" + name + ");");
    }

    @Override
    public String toString() {
        return "Przypisanie";
    }

}
