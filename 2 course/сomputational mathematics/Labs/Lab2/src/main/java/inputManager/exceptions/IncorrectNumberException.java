package inputManager.exceptions;

import computation.exceptions.AbsException;

public class IncorrectNumberException extends AbsException {

    private int numberOfLineWithException;

    public int getNumberOfLineWithException() {
        return numberOfLineWithException;
    }

    public void setNumberOfLineWithException(int numberOfLineWithException) {
        this.numberOfLineWithException = numberOfLineWithException;
    }

    @Override
    public void writeExceptionMessage() {
        System.out.println("В строке " + this.numberOfLineWithException + " некорректно указано число");
    }

}
