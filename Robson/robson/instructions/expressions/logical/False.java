package robson.instructions.expressions.logical;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import robson.BladWykonania;
import robson.MyStringBuilder;

/**
 * Wyrażenie logiczne 'False' języka Robson.
 *
 * @author Mateusz Sulimowicz
 */
public class False implements Logical {

    @SerializedName("typ")
    private static final String type = "False";

    @Override
    public double execute() throws BladWykonania {
        return 0;
    }

    @Override
    public void toJavaArgument(MyStringBuilder s) {
        s.append("false");
    }

    @Override
    public void toJavaLast(MyStringBuilder s) {
        s.append("System.out.println(false);\n");
    }

}
