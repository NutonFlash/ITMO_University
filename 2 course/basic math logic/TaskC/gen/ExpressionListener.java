// Generated from C:/Users/Алексей/Desktop/Education/2 course/basic math logic/TaskC/src/main/java\Expression.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ExpressionParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void enterDisjunction(ExpressionParser.DisjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#disjunction}.
	 * @param ctx the parse tree
	 */
	void exitDisjunction(ExpressionParser.DisjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(ExpressionParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(ExpressionParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#negation}.
	 * @param ctx the parse tree
	 */
	void enterNegation(ExpressionParser.NegationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#negation}.
	 * @param ctx the parse tree
	 */
	void exitNegation(ExpressionParser.NegationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ExpressionParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ExpressionParser.VariableContext ctx);
}