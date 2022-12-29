import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class ExpressionsParser {

    public static ArrayList<Variable> variables;
    private BufferedReader bufferedReader;
    public ExpressionsParser() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }
    public ArrayList<String> parseTheorem() {
        String firstLine = "";
        ArrayList<String> hypothesisList = new ArrayList<>();
        try {
            firstLine = bufferedReader.readLine();
        } catch (IOException exception) {
            System.out.println("Не удалось считать теорему.");
            System.exit(1);
        }
        if (firstLine != null) {
            String[] separatedTheoremLine = firstLine.split("\\|-", 2);
            String left = separatedTheoremLine[0].trim().toUpperCase();
            String right = separatedTheoremLine[1].trim().toUpperCase();
            int lastCommaIndex = left.lastIndexOf(",");
            if (lastCommaIndex != -1) {
                int pos;
                while (left.length()>0) {
                    pos = left.indexOf(",");
                    if (pos==-1) {
                        hypothesisList.add(left);
                        left = "";
                    }
                    else {
                        hypothesisList.add(left.substring(0, pos));
                        left = left.substring(pos + 1);
                    }
                }
            } else hypothesisList.add(left);
            hypothesisList.add(hypothesisList.get(hypothesisList.size() - 1) + "->" + right);
        }
        return hypothesisList;
    }


    public ArrayList<Expression> hypothesisToExpressions(ArrayList<String> hypothesisListString) {
        ArrayList<Expression> hypothesisListExpression = new ArrayList<>();
        hypothesisListString.forEach(expression -> {
            CharStream is = CharStreams.fromString(expression);
            ExpressionLexer lexer = new ExpressionLexer(is);
            TokenStream ts = new CommonTokenStream(lexer);
            ExpressionParser parser = new ExpressionParser(ts);
            hypothesisListExpression.add(parser.expression().expr);
        });
        return hypothesisListExpression;
    }

    public ArrayList<Implication> parseProofs(Expression lastHypothesis) {
        ArrayList<Implication> proofList = new ArrayList<>();
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    CharStream is = CharStreams.fromString(line.toUpperCase().trim());
                    ExpressionLexer lexer = new ExpressionLexer(is);
                    TokenStream ts = new CommonTokenStream(lexer);
                    ExpressionParser parser = new ExpressionParser(ts);
                    ExpressionParser.ExpressionContext expressionContext = parser.expression();
                    ParseTreeWalker walker = new ParseTreeWalker();
                    ParserListener parserListener = new ParserListener();
                    walker.walk(parserListener, expressionContext);
                    variables = parserListener.getVariables();
                    proofList.add(new Implication(lastHypothesis, expressionContext.expr));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return proofList;
    }
}