import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        InputHandler inputHandler = new InputHandler(new BufferedReader(new InputStreamReader(System.in)));
        InputObject input = inputHandler.readFromInput();
        ComputationMethods computationMethods = new ComputationMethods(input);
    }
}
