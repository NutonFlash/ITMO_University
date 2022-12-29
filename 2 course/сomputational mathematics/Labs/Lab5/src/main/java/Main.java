import comulation.ComputationalMethods;
import comulation.Coordinate;
import drawUtils.DrawData;
import drawUtils.Drawer;
import equation.Equation;
import equation.Equations;
import input.InputHandler;
import input.InputObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Equations equations = new Equations();
        InputHandler inputHandler = new InputHandler(new BufferedReader(new InputStreamReader(System.in)), equations);
        InputObject inputObject = inputHandler.readFromConsole();
        Equation equation = equations.getEquations().get(inputObject.getNumberOfEquation() - 1);
        ComputationalMethods computationalMethods = new ComputationalMethods(inputObject, equation);
        ArrayList<Coordinate> approximationPoints = computationalMethods.findApproximationPoints();
        ArrayList<Coordinate> analyticPoints = computationalMethods.findAnalyticPoints();
        DrawData drawData = new DrawData(approximationPoints, analyticPoints, inputObject.getInitialPoint(), "График функции "+equation);
        Drawer drawer = new Drawer(drawData);
        drawer.createGraphic();
    }
}
