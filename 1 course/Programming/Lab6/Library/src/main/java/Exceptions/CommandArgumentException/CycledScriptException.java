package Exceptions.CommandArgumentException;

public class CycledScriptException extends Exception {
    public CycledScriptException(String filePath) {
        System.out.println("Ошибка! В исполняемом скрипте есть зацикливание на файле " + filePath);
    }
}
