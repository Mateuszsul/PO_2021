package robson;

/**
 * Pomocniczy StringBuilder z możliwością ustawienia wcięcia tekstu.
 *
 * @author Mateusz Sulimowicz
 */
public class MyStringBuilder {

    private int indentCount;

    private String indentation = "";

    private boolean addIndentation = false;

    private final StringBuilder stringBuilder = new StringBuilder();

    public void append(String str) {
        if (addIndentation) {
            stringBuilder.append(indentation);
            addIndentation = false;
        }
        stringBuilder.append(str);
    }

    public void increaseIndent() {
        ++indentCount;
        indentation = "\t".repeat(indentCount);
    }

    public void decreaseIndent() {
        --indentCount;
        indentation = "\t".repeat(indentCount);
    }

    public void indent() {
        addIndentation = true;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
