import java.util.ArrayList;

public class ParserListener extends ExpressionBaseListener {

    private ArrayList<Variable> variables = new ArrayList<>();

    @Override
    public void exitVariable(ExpressionParser.VariableContext ctx) {
        if (ctx.expr instanceof Variable) {
            if (!variables.contains((Variable) ctx.expr)) {
                Variable var = (Variable) ctx.expr;
                var.setIndex(variables.size());
                variables.add(var);
            } else
                ctx.expr = variables.stream().filter(var -> var.getName().equals(((Variable) ctx.expr).getName())).findFirst().get();
        }
    }

    public ArrayList<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

}
