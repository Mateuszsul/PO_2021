package robson.deserializers;

import com.google.gson.*;
import robson.instructions.*;
import robson.instructions.expressions.arithmetic.Arithmetic;
import robson.instructions.expressions.logical.Logical;

import java.lang.reflect.Type;

public class InstructionDeserializer implements JsonDeserializer<Instruction> {

    private final Gson gson;

    public InstructionDeserializer() {
        JsonDeserializer<Arithmetic> arithmeticDeserializer = new ArithmeticDeserializer();
        JsonDeserializer<Logical> logicalDeserializer = new LogicalDeserializer();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Arithmetic.class, arithmeticDeserializer);
        gsonBuilder.registerTypeAdapter(Logical.class, logicalDeserializer);
        gsonBuilder.registerTypeAdapter(Instruction.class, this);

        this.gson = gsonBuilder.create();
    }

    @Override
    public Instruction deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jo = jsonElement.getAsJsonObject();
        String instructionType = jo.get("typ").getAsString();

        switch (instructionType) {
            case "Blok":
                return gson.fromJson(jsonElement, Block.class);
            case "While":
                return gson.fromJson(jsonElement, While.class);
            case "If":
                return gson.fromJson(jsonElement, If.class);
            case "Przypisanie":
                return gson.fromJson(jsonElement, Assignment.class);
            case "And":
            case "Or":
            case "Not":
            case "True":
            case "False":
            case "==":
            case ">":
            case ">=":
            case "<=":
            case "<":
                return gson.fromJson(jo, Logical.class);
            case "Plus":
            case "Dzielenie":
            case "Razy":
            case "Minus":
            case "Liczba":
            case "%":
            case "Zmienna":
                return gson.fromJson(jo, Arithmetic.class);
            default:
                throw new JsonParseException("Nieprawidlowy program! "
                        + instructionType + " nie jest poprawną instrukcją!");
        }

    }

}
