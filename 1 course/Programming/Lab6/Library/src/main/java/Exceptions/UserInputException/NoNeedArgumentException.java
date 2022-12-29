package Exceptions.UserInputException;

public class NoNeedArgumentException extends Exception {
    public NoNeedArgumentException(String typeOfCommand) {
        System.out.println("Ошибка! Команда " + typeOfCommand + " используется без аргументов.");
    }
}
