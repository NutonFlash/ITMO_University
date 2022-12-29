import computation.ComputationMethods;
import computation.Vector;
import computation.exceptions.AbsException;
import computation.system.FirstSystem;
import computation.system.SecondSystem;
import computation.system.SystemInterface;
import computation.system.ThirdSystem;
import inputManager.InputHandler;
import inputManager.InputObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        InputHandler inputHandler = new InputHandler(getSystem());
        inputHandler.setBuffer(new BufferedReader(new InputStreamReader(System.in)));
        InputObject inputObject = inputHandler.readInputData();
        ComputationMethods computationMethods = new ComputationMethods(inputObject, getSystem());
        if (inputObject.getEquationType() == InputObject.typeOfEquation.EQUATION) {
                if (inputObject.isBoundsForSecantCorrect() | inputObject.isBoundsForNewtonCorrect())
                    System.out.println("Корень функции " + inputObject.getNumberOfEquation() + " посчитанный");
                if (inputObject.isBoundsForSecantCorrect()) {
                    try {
                        double x1 = computationMethods.solveBySecantMethod(inputObject.getNumberOfEquation());
                        System.out.println(" +методом хорд: " + x1);
                        System.out.println(" +количество итераций: " + computationMethods.getItrCounter1());
                    } catch (AbsException exception) {exception.writeExceptionMessage();}
                }
                if (inputObject.isBoundsForNewtonCorrect()) {
                    try {
                        double x2 = computationMethods.solveByNewtonMethod(inputObject.getNumberOfEquation());
                        System.out.println(" -методом касательных: " + x2);
                        System.out.println(" -количество итераций: " + computationMethods.getItrCounter2());
                    } catch (AbsException exception) {exception.writeExceptionMessage();}
                }
        } else {
            try {
                double[] approximation = inputObject.getApproximation();
                Vector resultVector = computationMethods.findRootsBySimpleIterationMethod(approximation);
                System.out.println("Корни СНАУ:");
                System.out.println("x = " + resultVector.get(0) + "\ny = " + resultVector.get(1));
                System.out.println("количество итераций: " + computationMethods.getItrCounter3());
            } catch (AbsException exception) {
                exception.writeExceptionMessage();
            }
        }
    }

    public static ArrayList<SystemInterface> getSystem() {
        ArrayList<SystemInterface> systems = new ArrayList<>();
        systems.add(new FirstSystem());
        systems.add(new SecondSystem());
        systems.add(new ThirdSystem());
        return systems;
    }
}
