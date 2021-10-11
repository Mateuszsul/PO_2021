package robson;

public class NieprawidlowyProgram extends Exception {

    public NieprawidlowyProgram() {
        super("Program jest nieprawidłowy!");
    }

    public NieprawidlowyProgram(String message) {
        super(message);
    }

}
