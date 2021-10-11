package robson;

import com.google.gson.annotations.SerializedName;
import robson.instructions.Instruction;

import java.util.HashMap;
import java.util.Map;

public class Program {

    @SerializedName("instrukcja")
    private final Instruction instruction;

    private transient final Map<String, Double> variables;

    public Program(Instruction instruction) {
        this.instruction = instruction;
        variables = new HashMap<>();
        instruction.setVariables(variables);
    }

    private void initializeVariables() {
        for (String s : variables.keySet()) {
            variables.put(s, 0.0);
        }
    }

    public double execute() throws BladWykonania {
        initializeVariables();
        return instruction.execute();
    }

    /**
     * Tworzy kod w Javie inicjujący zmienne programu.
     * @param s StringBuilder, który tworzy kod w Javie z Robsona.
     */
    private void toJavaInitialization(MyStringBuilder s) {
        for (String name : variables.keySet()) {
            s.indent();
            s.append("double " + name + ";\n");
        }
    }

    public void toJava(MyStringBuilder s) {
        toJavaInitialization(s);
        s.indent();
        instruction.toJavaLast(s);
    }

    public Instruction getInstruction() {
        return instruction;
    }

}
