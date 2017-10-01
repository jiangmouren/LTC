package com.mycompany.app.sep21;

/**
 * Question:
 * This scope for this one is to write a generic solution for this Calculator thing.
 * When I sai "generic", I mean it can contain "()", "+", "-", "*" and "/".
 * And it can be arbitrarily nested.
 *
 * The approach I followed is:
 * 1. Add parenthesis(Generalization)
 * 2. Do recursion(Calculation)
 *
 * How to add parenthesis?
 * The idea is you loop though all the operands from left to right and every time you decide
 * if you need to add "(" / ")" / Nothing.
 * Rules:
 * 1. empty side == "+" == "-", has the same lowest priority;
 * 2. "*" == "/", has higher priority;
 * 3. if(priority_left<priority_right){add "(" on left side}; else if(priority_left>priority_right){add ")" on right side}
 * eg.
 * 2*3-2 --> (2*3-2 --> (2*3)-2
 * 2+3*2 --> 2+(3*2 --> 2+(3*2)
 *
 * How to recursively solve it?
 * After the generalization phase, you no longer need to worry about priorities.
 * All you need is just to to calculate from left to right.
 * You can use the suffix as the subproblem.
 * You solve a prefix, and combine with the remaining suffix problem, you are done.
 * Just like the case in "BasicCalculator", there you also do not have association issues.
 *
 * With the same approach I can write code to convert normal expression strings into reverse polished format.
 * TODO:
 */
public class BasicCalculatorIII {
    public int calculate(String s) {
        //Need to Normalize the expression first: add parenthesis and remove all spaces
        return helper(s, 0, s.length()-1);
    }

    /**
     * Assume all needed "()" added and all spaces removed.
     * @param s
     * @param start
     * @param end
     * @return
     */
    private int helper(String s, int start, int end){
        //base case
        BaseValue baseValue = checkBase(s, start, end);
        if(baseValue.base){
            return baseValue.value;
        }

        //recursive case
        Operand operand = getOperand(s, start, end);
        //this if check is only for the initial iteration when the whole expression is wrapped with "()"
        if(operand.next>end){
            return helper(s, operand.left, operand.right);
        }
        else{
            Operator operator = getOperator(s, operand.next, end);
            int rightSub = helper(s, operator.next, end);
            int leftSub = helper(s, operand.left, operand.right);
            return evaluate(leftSub, operator.operator, rightSub);
        }
    }

    private BaseValue checkBase(String s, int start, int end){
        //check for invalid expression
        BaseValue baseValue = new BaseValue(false, 0);
        for(int i=start; i<=end; i++){
            if(!Character.isDigit(s.charAt(i))){
                return baseValue;
            }
        }
        baseValue.base = true;
        baseValue.value = Integer.valueOf(s.substring(start, end+1));
        return baseValue;
    }

    private class BaseValue{
        boolean base;
        int value;
        BaseValue(boolean base, int value){
            this.base = base;
            this.value = value;
        }
    }

    private int evaluate(int leftSub, char operator, int rightSub){
        if(operator=='+'){
            return leftSub + rightSub;
        }
        else if(operator=='-'){
            return leftSub - rightSub;
        }
        else if(operator=='*'){
            return leftSub * rightSub;
        }
        else{
            return leftSub/rightSub;
        }
    }

    /**
     * @param s
     * @param start
     * @param end
     * @return return next Operand if there exist one, or return null.
     */
    private Operand getOperand(String s, int start, int end){
        if(s.charAt(start)=='('){
            int pair = findPair(s, start, end);
            return new Operand(start+1, pair-1, pair+1);
        }
        else{
            int ptr = start;
            while(ptr<=end && Character.isDigit(s.charAt(ptr))){
                ptr++;
            }
            return new Operand(start, ptr-1, ptr);
        }
    }

    /**
     * @param s
     * @param start start is pointing to the current '('
     * @param end
     * @return return the pos of the pair ')' of the current '('.
     */
    private int findPair(String s, int start, int end){
        int left = 1;
        int ptr = start;
        while(ptr<=end && left>0){
            if(s.charAt(ptr)=='('){
                left++;
            }
            else if(s.charAt(ptr)==')'){
                left--;
            }
            ptr++;
        }
        return ptr--;
    }

    private class Operand{
        int left;
        int right;
        int next;
        Operand(int left, int right, int next){
            this.left = left;
            this.right = right;
            this.next = next;
        }
    }

    private Operator getOperator(String s, int start, int end){
        if(s.charAt(start)=='+'){
            return new Operator('+', start+1);
        }
        else if(s.charAt(start)=='-'){
            return new Operator('-', start+1);
        }
        else if(s.charAt(start)=='*'){
            return new Operator('*', start+1);
        }
        else{
            return new Operator('/', start+1);
        }
    }

    private class Operator{
        char operator;
        int next;
        Operator(char operator, int next){
            this.operator = operator;
            this.next = next;
        }
    }
}
