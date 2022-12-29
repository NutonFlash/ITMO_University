package inputManager;

import computation.exceptions.AbsException;
import computation.system.SystemInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputHandler {

    private final ArrayList<SystemInterface> systems;
    private BufferedReader buffer;

    public InputHandler(ArrayList<SystemInterface> systems) {
        this.systems = systems;
    }

    public InputObject readInputData() throws IOException {
        InputObject inputObject = new InputObject();
        String line;
        try {
            while (true) {
                System.out.println("Как бы вы хотели ввести данные?\n$ - через консоль\n+ - через файл");
                line = buffer.readLine();
                if (line != null) {
                    line = line.trim();
                    if (line.matches("[$+]")) {
                        if (line.matches("[$]")) {
                            ConsoleInput consoleInput = new ConsoleInput(buffer, systems);
                            inputObject = consoleInput.readFromInput();
                            break;
                        } else {
                            FileInput fileInput = new FileInput(systems);
                            try {
                                inputObject = fileInput.readFromInput();
                                break;
                            } catch (AbsException exception1) {
                                exception1.writeExceptionMessage();
                            } catch (IOException exception2) {
                                System.out.println("""
                                        Не удается корректно прочесть файл
                                        Должна быть соблюдена следующая структура:
                                          1)Решить одно нелинейное уравнение или систему (1/2)
                                          2)Указать границы для уравнений(-ия)
                                          3)Указать абсолютную погрешность для вычисления уравнений(-ия)""");
                            }
                        }
                    } else System.out.println("Ошибка! Попробуйте еще раз");
                } else throw new IOException();
            }
            buffer.close();
        } catch (IOException exception) {
            System.out.println("Не удалось прочесть входные данные, так как было встречено EOF\nЗавершение программы...");
            buffer.close();
            System.exit(1);
        }
        return inputObject;
    }

    public void setBuffer(BufferedReader buffer) {
        this.buffer = buffer;
    }
}
