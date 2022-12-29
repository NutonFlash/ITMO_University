import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;


public class ExpressionsParser {

    public static ArrayList<Variable> variables;

    public Expression parse() {
        return readExpression();
    }

    private Expression readExpression() {
        byte[] inArr = new byte[256];
        String statement = "";
        try {
            System.in.read(inArr);
            statement = new String(compressArray(inArr), StandardCharsets.UTF_8);
            statement = statement.replaceAll(" ", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "").toUpperCase();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Expression expression = null;
        if (!statement.equals("")) {
            CharStream is = CharStreams.fromString(statement);
            ExpressionLexer lexer = new ExpressionLexer(is);
            TokenStream ts = new CommonTokenStream(lexer);
            ExpressionParser parser = new ExpressionParser(ts);
            expression = parser.expression().expr;
        }
        return expression;
    }

    public byte[] compressArray(byte[] array) {
        int pos = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != 0)
                pos++;
            else
                break;
        }
        if (pos == 0)
            return new byte[1];
        return Arrays.copyOf(array, pos);
    }
}