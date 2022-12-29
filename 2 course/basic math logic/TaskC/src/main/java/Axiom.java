import java.util.ArrayList;

public class Axiom implements Expression {

    public static final String[] axiomStrings = {
            "A->(B->A)", //1
            "(A->B)->((A->B->C)->(A->C))", //2
            "A->(B->(A&B))", //3
            "(A&B)->A", //4
            "(A&B)->B", //5
            "A->(A|B)", //6
            "B->(A|B)", //7
            "(A->C)->((B->C)->((A|B)->C))", //8
            "(A->B)->((A->(!B))->(!C))", //9
            "(!!A)->A" //10
    };
    private final Expression expression;
    private final int number;

    public Axiom(Expression expression, int number) {
        this.expression = expression;
        this.number = number;
    }

    public static boolean findAxiomNumber(Expression expression) {
        boolean isFound = false;
        for (int number = 1; !isFound && number < 11; ++number)
            isFound = isAxiomNumber(expression, number);
        return isFound;
    }

    private static boolean isAxiomNumber(Expression expression, int number) {
        switch (number) {

            case 1: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getRight() instanceof Implication)
                        return ((Implication) implication.getRight()).getRight().equals(implication.getLeft());
                }
                return false;
            }

            case 2: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getRight() instanceof Implication) {
                        Implication implicationRight = (Implication) implication.getRight();
                        if (!(implication.getLeft() instanceof Implication)
                                || !(implicationRight.getLeft() instanceof Implication)
                                || !(implicationRight.getRight() instanceof Implication))
                            return false;
                        Implication firstBracket = (Implication) implication.getLeft();
                        Implication secondBracket = (Implication) implicationRight.getLeft();
                        Implication thirdBracket = (Implication) implicationRight.getRight();
                        if (secondBracket.getRight() instanceof Implication) {
                            Implication secondInnerRight = (Implication) secondBracket.getRight();
                            Expression leftExpOfFirstBracket = firstBracket.getLeft();
                            Expression leftExpOfSecondBracket = secondBracket.getLeft();
                            Expression leftExpOfThirdBracket = thirdBracket.getLeft();
                            if (!leftExpOfFirstBracket.equals(leftExpOfSecondBracket)
                                    || !leftExpOfSecondBracket.equals(leftExpOfThirdBracket))
                                return false;
                            Expression rightExpOfFirstBracket = firstBracket.getRight();
                            Expression leftExpOfSecondInnerRight = secondInnerRight.getLeft();
                            if (!rightExpOfFirstBracket.equals(leftExpOfSecondInnerRight))
                                return false;
                            Expression rightExpOfSecondInnerRight = secondInnerRight.getRight();
                            Expression rightExpOfThirdBracket = thirdBracket.getRight();
                            return rightExpOfSecondInnerRight.equals(rightExpOfThirdBracket);
                        }
                    }
                }
                return false;
            }

            case 3: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getRight() instanceof Implication) {
                        Implication implicationRight = (Implication) implication.getRight();
                        if (implicationRight.getRight() instanceof Conjunction) {
                            Conjunction rightOfImplicationRight = (Conjunction) (implicationRight.getRight()); //Сомнительная хуйня (почему не конъюнкция?)
                            Implication implicationLeft = (Implication) implication.getLeft();
                            Expression leftOfRightOfImplicationRight = rightOfImplicationRight.getLeft();
                            if (implicationLeft.equals(leftOfRightOfImplicationRight))
                                return implicationRight.equals(rightOfImplicationRight);
                        }
                    }
                }
                return false;
            }

            case 4: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getLeft() instanceof Conjunction) {
                        Conjunction implicationLeft = (Conjunction) implication.getLeft();
                        Expression leftOfImplicationLeft = implicationLeft.getLeft();
                        Expression implicationRight = implication.getRight();
                        return leftOfImplicationLeft.equals(implicationRight);
                    }
                }
                return false;
            }

            case 5: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getLeft() instanceof Conjunction) {
                        Conjunction implicationLeft = (Conjunction) implication.getLeft();
                        Expression rightOfImplicationLeft = implicationLeft.getLeft();
                        Expression implicationRight = implication.getRight();
                        return rightOfImplicationLeft.equals(implicationRight);
                    }
                }
                return false;
            }

            case 6: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getRight() instanceof Disjunction) {
                        Disjunction implicationRight = (Disjunction) implication.getRight();
                        return implication.getLeft().equals(implicationRight.getLeft());
                    }
                }
                return false;
            }

            case 7: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;

                    if (implication.getRight() instanceof Disjunction) {
                        Disjunction implicationRight = (Disjunction) implication.getRight();
                        return implication.getLeft().equals(implicationRight.getRight());
                    }
                }
                return false;
            }

            case 8: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getRight() instanceof Implication) {
                        Implication implicationRight = (Implication) implication.getRight();
                        if (!(implication.getLeft() instanceof Implication)
                                || !(implicationRight.getLeft() instanceof Implication)
                                || !(implicationRight.getRight() instanceof Implication))
                            return false;
                        Implication firstBracket = (Implication) implication.getLeft();
                        Implication secondBracket = (Implication) implicationRight.getLeft();
                        Implication thirdBracket = (Implication) implicationRight.getRight();
                        if (thirdBracket.getLeft() instanceof Disjunction) {
                            Disjunction thirdBracketLeft = (Disjunction) thirdBracket.getLeft();
                            Expression firstBracketLeft = firstBracket.getLeft();
                            Expression leftOfThirdBracketLeft = thirdBracketLeft.getLeft();
                            if (firstBracketLeft.equals(leftOfThirdBracketLeft)) {
                                Expression secondBracketLeft = secondBracket.getLeft();
                                Expression rightOfThirdBracketLeft = thirdBracketLeft.getRight();
                                if (secondBracketLeft.equals(rightOfThirdBracketLeft)) {
                                    Expression firstBracketRight = firstBracket.getRight();
                                    Expression secondBracketRight = secondBracket.getRight();
                                    Expression thirdBracketRight = thirdBracket.getRight();
                                    return firstBracketRight.equals(secondBracketRight) && secondBracketRight.equals(thirdBracketRight);
                                }
                            }
                        }
                    }
                }
                return false;
            }

            case 9: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getRight() instanceof Implication) {
                        Implication implicationRight = (Implication) implication.getRight();
                        if (!(implication.getLeft() instanceof Implication)
                                || !(implicationRight.getLeft() instanceof Implication)
                                || !(implicationRight.getRight() instanceof Negation))
                            return false;
                        Implication firstBracket = (Implication) implication.getLeft();
                        Implication secondBracket = (Implication) implicationRight.getLeft();
                        //Negation thirdBracket = (Negation) implicationRight.getRight(); что це за хуйня?
                        Expression firstBracketLeft = firstBracket.getLeft();
                        Expression secondBracketLeft = secondBracket.getLeft();
                        if (firstBracketLeft.equals(secondBracketLeft)) {
                            if (secondBracket.getRight() instanceof Negation) {
                                Negation secondBracketRight = (Negation) secondBracket.getRight();
                                Expression firstBracketRight = firstBracket.getRight();
                                Expression secondBracketRightNegated = secondBracketRight.getNegation();
                                return firstBracketRight.equals(secondBracketRightNegated);
                            }
                        }
                    }
                }
                return false;
            }

            case 10: {
                if (expression instanceof Implication) {
                    Implication implication = (Implication) expression;
                    if (implication.getLeft() instanceof Negation) {
                        Negation implicationLeft = (Negation) implication.getLeft();
                        if (implicationLeft.getNegation() instanceof Negation) {
                            Negation implicationLeftNegation = (Negation) implicationLeft.getNegation();
                            return implicationLeftNegation.equals(implication.getRight());
                        }
                    }
                }
                return false;
            }
            default:
                return false;
        }
    }

    public Axiom createAxiomNumber(ArrayList<Expression> expressions, int number) {
        Axiom axiom;

        Expression a = expressions.get(0);
        Expression b = expressions.get(1);
        Expression c = expressions.get(2);

        switch (number) {
            case (1): {
                axiom = new Axiom(new Implication(a, new Implication(b, a)), number);
                break;
            }
            case (2): {
                axiom = new Axiom(
                        new Implication(
                                new Implication(a, b),
                                new Implication(
                                        new Implication(a, new Implication(b, c)),
                                        new Implication(a, c)
                                )
                        ), number);
                break;
            }
            case (3): {
                axiom = new Axiom(new Implication(a, new Implication(b, new Conjunction(a, b))), number);
                break;
            }
            case (4): {
                axiom = new Axiom(new Implication(new Conjunction(a, b), a), number);
                break;
            }
            case (5): {
                axiom = new Axiom(new Implication(new Conjunction(a, b), b), number);
                break;
            }
            case (6): {
                axiom = new Axiom(new Implication(a, new Disjunction(a, b)), number);
                break;
            }
            case (7): {
                axiom = new Axiom(new Implication(b, new Disjunction(a, b)), number);
                break;
            }
            case (8): {
                axiom = new Axiom(
                        new Implication(
                                new Implication(a, c),
                                new Implication(
                                        new Implication(b, c),
                                        new Implication(new Disjunction(a, b), c)
                                )
                        ), number);
                break;
            }
            case (9): {
                axiom = new Axiom(
                        new Implication(
                                new Implication(a, b),
                                new Implication(
                                        new Implication(a, new Negation(b)),
                                        new Negation(c)
                                )
                        ), number);
                break;
            }
            case (10): {
                axiom = new Axiom(new Implication(new Negation(new Negation(a)), a), number);
                break;
            }
            default: {
                axiom = null;
                break;
            }
        }
        return axiom;
    }

    public Expression getExpression() {
        return expression;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toTree() {
        return expression.toTree();
    }

    @Override
    public boolean[] calculate(int varValue1, int varValue2) {
        return new boolean[0];
    }

    @Override
    public void normalize() {
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
