package computation.exceptions;

public class DimensionMismatchException extends AbsException {
    private final String exceptionText;

    public DimensionMismatchException(String exceptionText) {
        this.exceptionText = exceptionText;
    }

    @Override
    public void writeExceptionMessage() {
        System.out.println(exceptionText);
    }
}
