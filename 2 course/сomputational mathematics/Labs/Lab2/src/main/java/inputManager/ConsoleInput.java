package inputManager;

import computation.ComputationMethods;
import computation.Equations;
import computation.system.SystemInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConsoleInput implements InputInterface {

    private final Equations systemOfEquations = new Equations();
    private final BufferedReader buffer;
    private final ArrayList<SystemInterface> systems;

    public ConsoleInput(BufferedReader buffer, ArrayList<SystemInterface> systems) {
        this.buffer = buffer;
        this.systems = systems;
    }

    @Override
    public InputObject readFromInput() throws IOException {
        System.out.println("Напишите 1, если хотите решить одно уравнение, или 2, если систему");
        InputObject inputObject = new InputObject();
        String line;
        while (true) {
            line = buffer.readLine();
            if (line != null) {
                line = line.trim();
                if (line.matches("[1-2]")) {
                    if (line.matches("1")) {
                        inputObject.setEquationType(InputObject.typeOfEquation.EQUATION);
                    } else {
                        inputObject.setEquationType(InputObject.typeOfEquation.SYSTEM_OF_EQUATIONS);
                    }
                    break;
                } else System.out.println("Ошибка! Укажите, что вы хотите посчитать (1/2)");
            } else throw new IOException();
        }
        if (inputObject.getEquationType() == InputObject.typeOfEquation.EQUATION) {
            System.out.println("Напишите номер уравнения, корень которого хотите посчитать\nДоступные уравнения:");
            for (int i = 0; i < systemOfEquations.getNumberOfEquations(); ++i)
                System.out.println((i + 1) + ") " + systemOfEquations.getEquationsForOneVar().get(i));
            while (true) {
                String regex = "[1-" + systemOfEquations.getNumberOfEquations() + "]";
                line = buffer.readLine();
                if (line != null) {
                    line = line.trim();
                    if (line.matches(regex)) {
                        inputObject.setNumberOfEquation(Integer.parseInt(line));
                        break;
                    } else
                        System.out.println("Ошибка! Укажите, корень какого уравнения вы бы хотели посчитать (1-" + systemOfEquations.getNumberOfEquations() + ")");
                } else throw new IOException();
            }
            ComputationMethods computationMethods = new ComputationMethods(inputObject, systems);
            double[] bounds;
            while (true) {
                bounds = getBounds(inputObject.getNumberOfEquation());
                boolean boundsForNewtonCorrect = computationMethods.verifyBoundsForNewtonMethod(bounds, inputObject.getNumberOfEquation());
                boolean boundsForSecantCorrect = computationMethods.verifyOneRoot(bounds, inputObject.getNumberOfEquation());
                if (boundsForNewtonCorrect | boundsForSecantCorrect) {
                    if (!boundsForNewtonCorrect)
                        System.out.println("Для функции " + inputObject.getNumberOfEquation() + " на промежутке [" + bounds[0] + ", " + bounds[1] + "] не выполняется условие f(x0)*f\"(x0)<0");
                    else if (!boundsForSecantCorrect)
                        System.out.println("Для функции " + inputObject.getNumberOfEquation() + " на промежутке [" + bounds[0] + ", " + bounds[1] + "] не выполняется условие f(a)*f(b)<0");
                    inputObject.setBoundsForSecantCorrect(boundsForSecantCorrect);
                    inputObject.setBoundsForNewtonCorrect(boundsForNewtonCorrect);
                    break;
                } else
                    System.out.println("Для функции " + inputObject.getNumberOfEquation() + " на промежутке [" + bounds[0] + ", " + bounds[1] + "] не выполняются условия f(x0)*f\"(x0)<0 и f(a)*f(b)<0");
            }
            inputObject.setBounds(bounds);
        } else {
            System.out.println("Укажите номер СНАУ, которое хотите решить\nДоступные системы:");
            for (int i = 0; i < systems.size(); ++i) {
                System.out.println((i + 1) + ".\n" + systems.get(i));
            }
            String regex = "[1-" + systems.size() + "]";
            while (true) {
                line = buffer.readLine();
                if (line != null) {
                    line = line.trim();
                    if (line.matches(regex)) {
                        inputObject.setNumberOfSystem(Integer.parseInt(line));
                        break;
                    } else System.out.println("Ошибка! Укажите номер системы, которую хотите посчитать");
                } else throw new IOException();
            }
            double[] approximation = getApproximation(inputObject.getNumberOfSystem());
            inputObject.setApproximation(approximation);
        }
        while (true) {
            System.out.println("Укажите допустимую абсолютную погрешность при расчёте корней");
            line = buffer.readLine();
            if (line != null) {
                line = line.trim();
                if (line.matches("\\d+(\\.\\d+)?")) {
                    inputObject.setError(Double.parseDouble(line));
                    break;
                } else System.out.println("Ошибка! Погрешность указана неверно");
            } else throw new IOException();
        }
        return inputObject;
    }

    private double[] getBounds(int numberOfFunction) throws IOException {
        System.out.println("Напишите границы " + numberOfFunction + " функции через пробел [a b]");
        double[] bounds = new double[2];
        String line;
        while (true) {
            line = buffer.readLine();
            if (line != null) {
                line = line.trim();
                if (line.matches("-?\\d+(\\.\\d+)? -?\\d+(\\.\\d+)?")) {
                    String[] splittedLine = line.split(" ", 2);
                    double leftBound = Double.parseDouble(splittedLine[0]);
                    double rightBound = Double.parseDouble(splittedLine[1]);
                    if (leftBound < rightBound) {
                        bounds[0] = leftBound;
                        bounds[1] = rightBound;
                        return bounds;
                    }
                }
                System.out.println("Ошибка! Границы указаны неверно. Еще раз укажите границы " + numberOfFunction + " функции [a b]");
            } else throw new IOException();
        }
    }

    private double[] getApproximation(int numberOfSystem) throws IOException {
        System.out.println("Напишите начальное приближение [x0 y0] для системы номер " + numberOfSystem + "\n" + systems.get(numberOfSystem - 1));
        double[] approximation = new double[2];
        String line;
        while (true) {
            line = buffer.readLine();
            if (line != null) {
                line = line.trim();
                if (line.matches("-?\\d+(\\.\\d+)? -?\\d+(\\.\\d+)?")) {
                    String[] splittedLine = line.split(" ", 2);
                    approximation[0] = Double.parseDouble(splittedLine[0]);
                    approximation[1] = Double.parseDouble(splittedLine[1]);
                    return approximation;
                }
                System.out.println("Ошибка! Начальное приближение указано неверно. Еще раз укажите начальное приближение [x0 y0] для системы номер " + numberOfSystem + "\n" + systems.get(numberOfSystem - 1));
            } else throw new IOException();
        }
    }
}
