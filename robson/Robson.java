package robson;

import com.google.gson.*;
import robson.deserializers.InstructionDeserializer;
import robson.instructions.Instruction;
import robson.instructions.expressions.arithmetic.MyNumber;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Rozwiązanie Zadania 2. z PO -- "Robson".
 *
 * @author Mateusz Sulimowicz
 */
public class Robson {

    // Jeśli żaden program nie został jeszcze wczytany,
    // obiekt klasy Robson zawiera 'pusty' program.
    private Program program = new Program (new MyNumber(0));

    /**
     * Wczytuje program w języku Robson zapisany w formacie JSON z pliku.
     *
     * @param filename nazwa pliku, w którym jest zapisany program w JSON
     * @throws NieprawidlowyProgram jeśli program jest nieprawidłowy
     */
    public void fromJSON(String filename) throws NieprawidlowyProgram {
        try (FileReader reader = new FileReader(filename)) {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .registerTypeAdapter(Instruction.class, new InstructionDeserializer());
            Gson gson = gsonBuilder.create();
            program = new Program(gson.fromJson(reader, Instruction.class));

        } catch (JsonSyntaxException e) {
            throw new NieprawidlowyProgram(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Tworzy reprezentację program języka Robson w formacie JSON
     *
     * @param filename nazwa pliku, w którym program ma być zapisany
     */
    public void toJSON(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT)
                    .disableHtmlEscaping()
                    .create();
            gson.toJson(program.getInstruction(), writer);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Tworzy kod w Javie na podstawie programu w Robsonie.
     *
     * @param filename nazwa pliku, w którym program ma być zapisany
     */
    public void toJava(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            MyStringBuilder s = new MyStringBuilder();

            s.append("public class " + filename.split("\\.")[0] + " {\n\n");
            s.increaseIndent();

            s.indent();
            s.append("public static void main(String[] args) {\n");

            s.increaseIndent();
            program.toJava(s);

            s.decreaseIndent();
            s.append("\n");
            s.indent();
            s.append("}\n\n}\n");

            writer.write(s.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Wykonuje wczytany program.
     * @return zwracana wartość ostatniej instrukcji
     * @throws BladWykonania jeśli wykonanie nie powiodło się
     */
    public double wykonaj() throws BladWykonania {
        return program.execute();
    }

}
