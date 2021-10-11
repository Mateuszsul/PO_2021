package robson.deserializers;

import com.google.gson.*;
import robson.instructions.expressions.arithmetic.Arithmetic;
import robson.instructions.expressions.comparators.*;
import robson.instructions.expressions.logical.*;

import java.lang.reflect.Type;

public class LogicalDeserializer implements JsonDeserializer<Logical> {

    private final Gson gson;

    public LogicalDeserializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Logical.class, this);
        gsonBuilder.registerTypeAdapter(Arithmetic.class, new ArithmeticDeserializer());
        this.gson = gsonBuilder.create();
    }

    @Override
    public Logical deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jo = jsonElement.getAsJsonObject();
        String instructionType = jo.get("typ").getAsString();

        switch (instructionType) {
            case "And":
                return gson.fromJson(jo, And.class);
            case "Or":
                return gson.fromJson(jo, Or.class);
            case "Not":
                return gson.fromJson(jo, Not.class);
            case "True":
                return gson.fromJson(jo, True.class);
            case "False":
                return gson.fromJson(jo, False.class);
            case "==":
                return gson.fromJson(jo, Equal.class);
            case ">":
                return gson.fromJson(jo, Greater.class);
            case ">=":
                return gson.fromJson(jo, GreaterEqual.class);
            case "<=":
                return gson.fromJson(jo, LessEqual.class);
            case "<":
                return gson.fromJson(jo, Less.class);
            default:
                throw new JsonParseException("Nieprawidlowy program: "
                        + instructionType + " nie jest wyrażeniem logicznym!");
        }
    }

}
