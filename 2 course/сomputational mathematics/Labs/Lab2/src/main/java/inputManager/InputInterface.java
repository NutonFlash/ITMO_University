package inputManager;

import computation.exceptions.WrongConditionException;
import inputManager.exceptions.BoundException;
import inputManager.exceptions.IncorrectNumberException;

import java.io.IOException;

public interface InputInterface {
    InputObject readFromInput() throws IOException, IncorrectNumberException, BoundException, WrongConditionException;
}
