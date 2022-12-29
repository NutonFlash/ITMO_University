package inputManager;

import computation.ComputationMethods;
import computation.Equations;
import computation.exceptions.WrongConditionException;
import computation.system.SystemInterface;
import inputManager.exceptions.BoundException;
import inputManager.exceptions.IncorrectNumberException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileInput implements InputInterface {

    private final String filePath = "src/main/resources/input.txt";
    private final Equations equations = new Equations();
    private final ArrayList<SystemInterface> systems;

    public FileInput(ArrayList<SystemInterface> systems) {
        this.systems = systems;
    }

    @Override
    public InputObject readFromInput() throws IOException, IncorrectNumberException, BoundException, WrongConditionException {
        InputObject inputObject = new InputObject();
        int lineNumber = 0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath))) {
            //считываем число (1 - решить одно уравнение, 2 - решить систему)
            String line = buffer.readLine();
            if (line != null) {
                if (line.trim().matches("[1-2]")) {
                    lineNumber++;
                    if (line.trim().matches("1"))
                        inputObject.setEquationType(InputObject.typeOfEquation.EQUATION);
                    else
                        inputObject.setEquationType(InputObject.typeOfEquation.SYSTEM_OF_EQUATIONS);
                } else {
                    lineNumber++;
                    IncorrectNumberException exception = new IncorrectNumberException();
                    exception.setNumberOfLineWithException(lineNumber);
                    throw exception;
                }
            } else throw new IOException();
            if (inputObject.getEquationType() == InputObject.typeOfEquation.EQUATION) {
                //если выбрали решить уравнение, то считываем номер уравнения, которое требуется решить
                line = buffer.readLine();
                String regex = "[1-" + equations.getNumberOfEquations() + "]";
                if (line != null) {
                    if (line.trim().matches(regex)) {
                        inputObject.setNumberOfEquation(Integer.parseInt(line));
                        lineNumber++;
                    } else {
                        lineNumber++;
                        IncorrectNumberException exception = new IncorrectNumberException();
                        exception.setNumberOfLineWithException(lineNumber);
                        throw exception;
                    }
                } else throw new IOException();
                //считываем границы функции, в которых ищем корень
                line = buffer.readLine();
                if (line != null) {
                    lineNumber++;
                    double[] bounds = getBounds(line, lineNumber);
                    ComputationMethods computationMethods = new ComputationMethods(inputObject,systems);
                    boolean boundsForNewtonCorrect = computationMethods.verifyBoundsForNewtonMethod(bounds, inputObject.getNumberOfEquation());
                    boolean boundsForSecantCorrect = computationMethods.verifyOneRoot(bounds, inputObject.getNumberOfEquation());
                    if (boundsForNewtonCorrect | boundsForSecantCorrect) {
                        if (!boundsForNewtonCorrect)
                            System.out.println("Для функции " + inputObject.getNumberOfEquation() + " на промежутке [" + bounds[0] + ", " + bounds[1] + "] не выполняется условие f(x0)*f\"(x0)<0");
                        else if (!boundsForSecantCorrect)
                            System.out.println("Для функции " + inputObject.getNumberOfEquation() + " на промежутке [" + bounds[0] + ", " + bounds[1] + "] не выполняется условие f(a)*f(b)<0");
                        inputObject.setBoundsForSecantCorrect(boundsForSecantCorrect);
                        inputObject.setBoundsForNewtonCorrect(boundsForNewtonCorrect);
                    } else
                        System.out.println("Для функции " + inputObject.getNumberOfEquation() + " на промежутке [" + bounds[0] + ", " + bounds[1] + "] не выполняются условия f(x0)*f\"(x0)<0 и f(a)*f(b)<0");
                    inputObject.setBounds(bounds);
                } else throw new IOException();

            } else {
                String regex = "[1-" + systems.size() + "]";
                line = buffer.readLine();
                if (line != null) {
                    lineNumber++;
                    if (line.matches(regex)) {
                        inputObject.setNumberOfSystem(Integer.parseInt(line));
                    } else
                        throw new WrongConditionException("Ошибка! Номер СНАУ в строке " + lineNumber + " указан неверно");
                }
                //если выбрано решить систему уравнений, то считываем начальные приближения
                line = buffer.readLine();
                if (line != null) {
                    lineNumber++;
                    double[] approximation = getApproximation(line, lineNumber);
                    inputObject.setApproximation(approximation);
                } else throw new IOException();
            }
            //считываем абсолютную погрешность, до которой численные методы будут искать приближенное значение корней
            line = buffer.readLine();
            if (line != null) {
                lineNumber++;
                if (line.trim().matches("\\d+(\\.\\d+)?")) {
                    inputObject.setError(Double.parseDouble(line));
                } else {
                    IncorrectNumberException exception = new IncorrectNumberException();
                    exception.setNumberOfLineWithException(lineNumber);
                    throw exception;
                }
            } else throw new IOException();
        }
        return inputObject;
    }

    private double[] getBounds(String str, int numberOfLine) throws BoundException, IncorrectNumberException {
        double[] bounds = new double[2];
        if (str.trim().matches("-?\\d+(\\.\\d+)? -?\\d+(\\.\\d+)?")) {
            String[] splittedString = str.split(" ", 2);
            double leftBound = Double.parseDouble(splittedString[0]);
            double rightBound = Double.parseDouble(splittedString[1]);
            if (leftBound >= rightBound) {
                BoundException boundException = new BoundException();
                boundException.setNumberOfLineWithException(numberOfLine);
                throw boundException;
            }
            bounds[0] = leftBound;
            bounds[1] = rightBound;
        } else {
            IncorrectNumberException exception = new IncorrectNumberException();
            exception.setNumberOfLineWithException(numberOfLine);
            throw exception;
        }
        return bounds;
    }

    private double[] getApproximation(String str, int numberOfLine) throws WrongConditionException {
        double[] approximation = new double[2];
        if (str.trim().matches("-?\\d+(\\.\\d+)? -?\\d+(\\.\\d+)?")) {
            String[] splittedLine = str.split(" ", 2);
            approximation[0] = Double.parseDouble(splittedLine[0]);
            approximation[1] = Double.parseDouble(splittedLine[1]);
            return approximation;
        } else
            throw new WrongConditionException("Ошибка! Начальное приближение [x0 y0] в строке " + numberOfLine + " указано неверно");
    }
}
