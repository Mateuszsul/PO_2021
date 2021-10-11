package robson.instructions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;
import robson.instructions.expressions.arithmetic.MyNumber;

import java.util.*;

/**
 * Instrukcja 'Blok' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class Block implements Instruction {

    @SerializedName("typ")
    private static final String type = "Blok";

    @SerializedName("instrukcje")
    private Collection<Instruction> instructions;

    public Block(Collection<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public double execute() throws BladWykonania {
        double value = 0;

        for (Instruction i : instructions) {
            value = i.execute();
        }

        return value;
     }

    @Override
    public void setVariables(Map<String, Double> variables) {
        for (Instruction i : instructions) {
            i.setVariables(variables);
        }
    }

    @Override
    public void toJava(MyStringBuilder s) {
        s.append("{\n");
        s.increaseIndent();

        for (Instruction i : instructions) {
            s.indent();
            i.toJava(s);
        }

        s.decreaseIndent();
        s.indent();
        s.append("}");
    }

    @Override
    public void toJavaLast(MyStringBuilder s) {
        Instruction last = null;
        s.append("{\n");
        s.increaseIndent();

        Iterator<Instruction> it = instructions.iterator();
        while (it.hasNext()) {
            last = it.next();
            if (!it.hasNext()) {
                break;
            }
            s.indent();
            last.toJava(s);
        }

        s.indent();
        if (last != null) {
            last.toJavaLast(s);
        } else {
            new MyNumber(0).toJavaLast(s);
        }

        s.decreaseIndent();
        s.indent();
        s.append("}");
    }

    @Override
    public String toString() {
        return "Blok";
    }

}
