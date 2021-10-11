package robson.instructions;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;
import robson.instructions.expressions.arithmetic.MyNumber;
import robson.instructions.expressions.logical.Logical;

import java.util.Map;

/**
 * Instrukcja 'While' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class While implements Instruction {

    @SerializedName("typ")
    private static final String type = "While";

    @SerializedName("warunek")
    private final Logical condition;

    @SerializedName("blok")
    private final Block block;

    public While(Logical condition, Block block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public double execute() throws BladWykonania {
        double value = 0;
        while (condition.execute() == 1) {
            block.execute();
        }
        return value;
    }

    @Override
    public void toJava(MyStringBuilder s) {
        s.append("while (");
        condition.toJavaArgument(s);
        s.append(") ");

        block.toJava(s);
        s.append("\n");
    }

    @Override
    public void toJavaLast(MyStringBuilder s) {
        s.append("while (");
        condition.toJavaArgument(s);
        s.append(") ");

        block.toJava(s);
        s.append("\n");
        s.indent();
        new MyNumber(0.0).toJavaLast(s);
    }

    @Override
    public void setVariables(Map<String, Double> variables) {
        this.condition.setVariables(variables);
        this.block.setVariables(variables);
    }

    @Override
    public String toString() {
        return "While";
    }

}
