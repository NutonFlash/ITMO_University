import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        long time = System.currentTimeMillis();
        ArrayList<Expression> resultProof = new ArrayList<>();
        ArrayList<Implication> proofNeeded;
        ArrayList<Expression> hypothesis;
        Comparator<Expression> expressionComparator = Comparator.comparingInt(Object::hashCode);
        // (A -> B ->C), C - key, (A -> B -> C) - value
        // allows only those elements that starts with (A -> ...). complexity(log)
        TreeMap<Expression, Expression> map_mp_first = new TreeMap<>(expressionComparator);
        // (A -> B), B - key. if key is not present than there is no element. complexity(log)
        TreeMap<Expression, Expression> map_mp_second = new TreeMap<>(expressionComparator);
        ExpressionsParser expressionsParser = new ExpressionsParser();
        ArrayList<String> theorem = expressionsParser.parseTheorem();
        String output = theorem.get(theorem.size() - 1);
        theorem.remove(theorem.size() - 1);
        hypothesis = expressionsParser.hypothesisToExpressions(theorem);
        proofNeeded = expressionsParser.parseProofs(hypothesis.get(hypothesis.size()-1));
        proofNeeded.forEach(Implication::normalize);
        hypothesis.forEach(Expression::normalize);
        for (Implication expression : proofNeeded) {
            // case 1
            if (expression.getLeft().equals(expression.getRight())) {
                Implication axiom_first = new Implication(expression.getLeft(), expression);
                Implication axiom_first2 = new Implication(expression.getLeft(), new Implication(expression, expression.getLeft()));
                Implication axiom_two = new Implication(axiom_first, new Implication(axiom_first2, expression));
                // MP axiom_first, axiom_two
                Expression mp = axiom_two.getRight();
                // MP axiom_first2, mp
                Expression mp2 = ((Implication) mp).getRight();
                // adding to output
                resultProof.add(axiom_first);
                resultProof.add(axiom_first2);
                resultProof.add(axiom_two);
                resultProof.add(mp);
                resultProof.add(mp2);

                // pushing in two maps
                Expression mp2_right = ((Implication) mp2).getRight();
                map_mp_second.put(mp2_right, mp2);
                if (mp2_right instanceof Implication) {
                    map_mp_first.put(((Implication) mp2_right).getRight(), mp2);
                }
            }
            // case 2 (removed if is_in_proof because it increases complexity
            else if (Axiom.findAxiomNumber(expression.getRight()) || hypothesis.contains(expression.getRight())) {
                Expression axiom = expression.getRight();
                Implication axiom_first = new Implication(expression.getRight(), expression);
                resultProof.add(axiom);
                resultProof.add(axiom_first);
                resultProof.add(expression);
                // pushing in two maps
                Expression mp_right = expression.getRight();
                map_mp_second.put(mp_right, expression);
                if (mp_right instanceof Implication) {
                    map_mp_first.put(((Implication) mp_right).getRight(), expression);
                }
            }
            // case else - find in loop the expression that can be proved using second axiom
            else {
                Map<Expression,Expression> sortedMap = map_mp_first.entrySet().stream().filter(entry -> entry.getKey().equals(expression.getRight())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                for (Map.Entry<Expression, Expression> entry : sortedMap.entrySet()) {
                    Implication first = (Implication) entry.getValue();
                    if (map_mp_second.entrySet().stream().anyMatch(var -> var.getKey().equals(((Implication) first.getRight()).getLeft()))) {
                        Expression foundValue = map_mp_second.entrySet().stream().filter(var -> var.getKey().equals(((Implication) first.getRight()).getLeft())).findFirst().get().getValue();
                        // resolving
                        Implication axiom_2 = new Implication(foundValue, new Implication(first, expression));
                        Expression mp1 = axiom_2.getRight();

                        // writing
                        resultProof.add(axiom_2);
                        resultProof.add(mp1);
                        resultProof.add(expression);

                        // pushing in two maps
                        Expression mp2_right = expression.getRight();
                        map_mp_second.put(mp2_right, expression);
                        if (mp2_right instanceof Implication) {
                            map_mp_first.put(((Implication) mp2_right).getRight(), expression);
                        }
                        break;
                    }
                }
            }
        }
        theorem.remove(theorem.size()-1);
        for (String hypo : theorem) {
            if (!theorem.get(theorem.size() - 1).equals(hypo))
                System.out.print(hypo + ",");
            else
                System.out.print(hypo);
        }
        System.out.println("|-" + output);
        for (int i=0; i< resultProof.size() ; ++i) {
                if (i!= resultProof.size()-1)
                    System.out.println(resultProof.get(i));
                else System.out.print(resultProof.get(i));
        }
//        System.out.println("\n"+(System.currentTimeMillis() - time));
    }
}