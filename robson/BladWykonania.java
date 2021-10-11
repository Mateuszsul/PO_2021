package robson;

public class BladWykonania extends Exception {

    public BladWykonania() {
        super("Wykonanie programu nie powiodło się!");
    }

    public BladWykonania(String message) {
        super(message);
    }

}
