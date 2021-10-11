package robson.instructions;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;
import robson.instructions.expressions.logical.Logical;

import java.util.ArrayList;
import java.util.Map;

/**
 * Instrukcja 'If' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class If implements Instruction {

    @SerializedName("typ")
    private static final String type = "If";

    @SerializedName("warunek")
    private final Logical condition;

    @SerializedName("blok_prawda")
    private final Block blockTrue;

    @SerializedName("blok_falsz")
    private final Block blockFalse;

    public If(Logical condition, Block BlockTrue, Block blockFalse) {
        this.condition = condition;
        this.blockTrue = BlockTrue;
        this.blockFalse = blockFalse;
    }

    @Override
    public double execute() throws BladWykonania {
        double value = 0;
        if (condition.execute() == 1) {
            value = blockTrue.execute();
        } else if (blockFalse != null) {
            value = blockFalse.execute();
        }
        return value;
    }

    @Override
    public void setVariables(Map<String, Double> variables) {
        condition.setVariables(variables);
        blockTrue.setVariables(variables);
        if (blockFalse != null) {
            blockFalse.setVariables(variables);
        }
    }

    @Override
    public void toJava(MyStringBuilder s) {
        s.append("if (");
        condition.toJavaArgument(s);
        s.append(") ");

        blockTrue.toJava(s);

        if (blockFalse != null) {
            s.append(" else ");
            blockFalse.toJava(s);
        }
        s.append("\n");
    }

    @Override
    public void toJavaLast(MyStringBuilder s) {
        s.append("if (");
        condition.toJavaArgument(s);
        s.append(") ");

        blockTrue.toJavaLast(s);

        s.append(" else ");
        if (blockFalse != null) {
            blockFalse.toJavaLast(s);
        } else {
            new Block(new ArrayList<>()).toJavaLast(s);
        }
        s.append("\n");
    }

    @Override
    public String toString() {
        return "If";
    }


}
