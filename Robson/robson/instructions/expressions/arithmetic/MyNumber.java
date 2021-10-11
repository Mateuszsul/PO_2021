package robson.instructions.expressions.arithmetic;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;

/**
 * Wyrażenie arytmetyczne 'Liczba' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class MyNumber implements Arithmetic {

    @SerializedName("typ")
    private static final String type = "Liczba";

    @SerializedName("wartosc")
    private final double value;

    public MyNumber(double value) {
        this.value = value;
    }

    @Override
    public double execute() throws BladWykonania {
        return value;
    }

    @Override
    public void toJavaArgument(MyStringBuilder s) {
        s.append(Double.toString(value));
    }

    @Override
    public void toJavaLast(MyStringBuilder s) {
        s.append("System.out.println(");
        s.append(Double.toString(value));
        s.append(");\n");
    }

}
