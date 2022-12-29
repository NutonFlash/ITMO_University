package Exceptions.UserInputException;

public class NoSuchCommandException extends Exception {
    public NoSuchCommandException() {
        System.out.println("Ошибка! Такой команды не существует.\nЧтобы посмотреть список доступных команд, используйте help.");
    }
}
