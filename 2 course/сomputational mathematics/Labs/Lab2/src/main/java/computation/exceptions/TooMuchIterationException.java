package computation.exceptions;

public class TooMuchIterationException extends AbsException {
    private final String exceptionText;

    public TooMuchIterationException(String exceptionText) {
        this.exceptionText = exceptionText;
    }

    @Override
    public void writeExceptionMessage() {
        System.out.println(exceptionText);
    }
}
