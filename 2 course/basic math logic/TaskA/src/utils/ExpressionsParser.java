package utils;

import expression.Expression;
import org.antlr.v4.runtime.*;
import parser.ExpressionLexer;
import parser.ExpressionParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExpressionsParser {

    public static Expression parse(String inPath) throws IOException {
        return readFile(inPath);
    }

    private static Expression readFile(String path) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(path),2048)) {
            UnbufferedCharStream charStream = new UnbufferedCharStream(in);
            ExpressionLexer lexer = new ExpressionLexer(charStream);
            lexer.setTokenFactory(new CommonTokenFactory(true));
            TokenStream ts = new CommonTokenStream(lexer);
            ExpressionParser parser = new ExpressionParser(ts);
            Expression exp = parser.expression().expr;
            parser.reset();
//            System.out.println(parser.variable().VAR.getText());
            return exp;
        }
    }
}
