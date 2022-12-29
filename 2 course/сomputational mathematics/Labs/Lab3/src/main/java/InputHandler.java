import java.io.BufferedReader;
import java.io.IOException;

public class InputHandler {
    private BufferedReader buffer;
    private Equations equations = new Equations();

    public InputHandler() {}

    public InputHandler(BufferedReader bufferedReader) {
        this.buffer = bufferedReader;
    }

    public InputObject readFromInput() {
        InputObject inputObject = null;
        try {
            int numberOfEquation = readNumberOfEquation();
            double[] bounds = readBounds();
            double step = readStep(bounds);
            inputObject = new InputObject(numberOfEquation, bounds, step);
        } catch (IOException exception) {
            System.out.println("Произошла ошибка чтения из консоли...\nЗавершение программы");
            System.exit(1);
        }
        return inputObject;
    }

    private int readNumberOfEquation() throws IOException {
        String reg = "[1-"+equations.getSize()+"]";
        String entryMsg = "Введите номер подынтегральной функции, которую хотите посчитать\nДоступные функции:\n"+equations;
        String errorMsg = "Ошибка! Укажите число от 1 до "+equations.getSize();
        return Integer.parseInt(getCorrectLine(reg, entryMsg, errorMsg));
    }

    private double[] readBounds() throws IOException {
        String reg = "-?\\d+(\\.\\d+)? -?\\d+(\\.\\d+)?";
        String entryMsg = "Введите границы интеграла [a, b]";
        String errorMsg = "Ошибка! Укажите границы интеграла через пробел";
        while (true) {
            String line = getCorrectLine(reg, entryMsg, errorMsg);
            double[] bounds = new double[2];
            String[] splittedLine = line.split(" ", 2);
            bounds[0] = Double.parseDouble(splittedLine[0]);
            bounds[1] = Double.parseDouble(splittedLine[1]);
            if (bounds[1] < bounds[0])
                System.out.println("Ошибка! Верхняя граница меньше нижней");
            else return bounds;
        }
    }

    private double readStep(double[] bounds) throws IOException {
        String reg = "\\d+(\\.\\d+)?";
        String entryMsg = "Введите шаг разбиения, с которым хотите посчитать интеграл";
        String errorMsg = "Ошибка! Укажите h>0";
        double step;
        while (true) {
            step = Double.parseDouble(getCorrectLine(reg,entryMsg,errorMsg));
            if (Math.abs(bounds[1] - bounds[0]) / step < 100000000)
                break;
            else System.out.println("Вы указали слишком маленький шаг разбиения, введите шаг побольше");
        }
        return step;
    }

    private String getCorrectLine(String regex, String entryMsg, String errorMsg) throws IOException {
        System.out.println(entryMsg);
        String line = "";
        while (true) {
            line = buffer.readLine();
            if (line != null) {
                line = line.trim().replaceAll(",", ".");
                if (line.matches(regex))
                    return line;
                else System.out.println(errorMsg);
            } else throw new IOException();
        }
    }

    public void setBuffer(BufferedReader buffer) {
        this.buffer = buffer;
    }

    public BufferedReader getBuffer() {
        return buffer;
    }
}



