package Exceptions.UserInputException;

public class NullCollectionException extends Exception {
    public NullCollectionException() {
        System.out.println("Ошибка! Команда не может быть выполнена, так как коллекция пустая.");
    }
}
