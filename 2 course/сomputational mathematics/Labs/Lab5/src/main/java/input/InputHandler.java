package input;

import comulation.Coordinate;
import equation.Equations;

import java.io.BufferedReader;
import java.io.IOException;

public class InputHandler {
    private final BufferedReader buffer;
    private final Equations equations;

    public InputHandler(BufferedReader buffer, Equations equations) {
        this.buffer = buffer;
        this.equations = equations;
    }

    public InputObject readFromConsole() throws IOException {
        int numberOfEquation = readNumberOfEquation();
        Coordinate initialPoint = readInitialPoint();
        double[] bounds = readBounds(initialPoint.getX());
        double accuracy = readAccuracy(bounds);
        return new InputObject(numberOfEquation, initialPoint, bounds, accuracy);
    }

    private int readNumberOfEquation() throws IOException {
        String req = "[1-" + equations.getSize() + "]";
        String entryMsg = "Выберите уравнение, которое хотите решить\n" + "Доступные уравнения:\n" + equations;
        String errorMsg = "Ошибка! Укажите номер уравнения " + req;
        return Integer.parseInt(getCorrectLine(req, entryMsg, errorMsg));
    }

    private Coordinate readInitialPoint() throws IOException {
        String req = "-?\\d+(\\.\\d+)? -?\\d+(\\.\\d+)?";
        String entryMsg = "Введите начальную точку для аппроксимации методом Рунге-Кутты:";
        String errorMsg = "Ошибка! Укажите два числа";
        String[] point = getCorrectLine(req, entryMsg, errorMsg).split(" ",2);
        double x0 = Double.parseDouble(point[0]);
        double y0 = Double.parseDouble(point[1]);
        return new Coordinate(x0, y0);
    }

    private double[] readBounds(double x0) throws IOException {
        String reg = "-?\\d+(\\.\\d+)?";
        String entryMsg = "Введите правую границу интервала для аппроксимации:";
        String errorMsg = "Ошибка! Укажите число";
        while (true) {
            String rightBorderStr = getCorrectLine(reg, entryMsg, errorMsg);
            double[] bounds = new double[2];
            bounds[0] = x0;
            bounds[1] = Double.parseDouble(rightBorderStr);
            if (bounds[1] < bounds[0])
                System.out.println("Ошибка! Правая граница меньше левой");
            else return bounds;
        }
    }

    private double readAccuracy(double[] bounds) throws IOException {
        String reg = "-?\\d+(\\.\\d+)?";
        String entryMsg = "Введите точность, с которой хотите аппроксимировать уравнение:";
        String errorMsg = "Ошибка! Укажите число";
        double len = Math.abs(bounds[1] - bounds[0]);
        while (true) {
            double accuracy = Double.parseDouble(getCorrectLine(reg, entryMsg, errorMsg));
            if (accuracy >= Math.pow((len / 1000), 4))
                return accuracy;
            else System.out.println("Ошибка! Вы указали слишком высокую точность");
        }
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
}
