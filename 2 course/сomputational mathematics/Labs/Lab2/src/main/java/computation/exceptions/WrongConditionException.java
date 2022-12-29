package computation.exceptions;

public class WrongConditionException extends AbsException {
    private final String errorText;

    public WrongConditionException(String errorText) {
        this.errorText = errorText;
    }

    @Override
    public void writeExceptionMessage() {
        System.out.println(errorText);
    }
}
