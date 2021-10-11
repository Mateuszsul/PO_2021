package robson.deserializers;

import com.google.gson.*;
import robson.instructions.Variable;
import robson.instructions.expressions.arithmetic.*;

import java.lang.reflect.Type;

public class ArithmeticDeserializer implements JsonDeserializer<Arithmetic> {

    private final Gson gson;

    public ArithmeticDeserializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Arithmetic.class, this);
        this.gson = gsonBuilder.create();
    }

    @Override
    public Arithmetic deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jo = jsonElement.getAsJsonObject();
        String instructionType = jo.get("typ").getAsString();

        switch (instructionType) {
            case "Zmienna":
                return gson.fromJson(jsonElement, Variable.class);
            case "Plus":
                return gson.fromJson(jsonElement, Add.class);
            case "Dzielenie":
                return gson.fromJson(jsonElement, Divide.class);
            case "Razy":
                return gson.fromJson(jsonElement, Multiply.class);
            case "Minus":
                return gson.fromJson(jsonElement, Substract.class);
            case "Liczba":
                return gson.fromJson(jsonElement, MyNumber.class);
            case "%":
                return gson.fromJson(jsonElement, Modulo.class);
            default:
                throw new JsonParseException("Nieprawidlowy program: "
                        + instructionType + " nie jest wyrażeniem arytmetycznym!");
        }
    }

}
