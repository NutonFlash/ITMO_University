package computation.exceptions;

public class MethodDivergesException extends AbsException {
    private final String exceptionText;

    public MethodDivergesException(String exceptionText) {
        this.exceptionText = exceptionText;
    }

    @Override
    public void writeExceptionMessage() {
        System.out.println(exceptionText);
    }
}
