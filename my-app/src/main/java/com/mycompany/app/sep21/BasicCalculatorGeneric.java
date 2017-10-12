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
 * Besides the priorities, there is another thing we need to pay attention to: 5-4+2 != 5-(4+2)
 * To get around of this, what we can do is make op1-op2 --> op1+(-op2), we do not need that "()",
 * just need to replace '-' with "+(0-1)*", then the original logic will take care of everything.
 * Even with this trick, still cannot take negative operands, like "-3 * 2".
 * The passing logic will be more complicated, basically you have to look at the left side of the '-',
 * if the left side is operand, you do "+(0-1)*", else you do "(0-1)*":
 * "-3 * 2" --> "(0-1)*3*2"
 * "-3 * -2" --> "(0-1)*3*(0-1)*2"
 * "--3 * -2" --> "(0-1)*(0-1)*3*(0-1)*2"
 *
 * How to recursively solve it?
 * After the generalization phase, you no longer need to worry about priorities.
 * All you need is just to to calculate from left to right.
 * You can use the suffix as the subproblem.
 * You solve a prefix, and combine with the remaining suffix problem, you are done.
 * Just like the case in "BasicCalculator", there you also do not have association issues.
 *
 * With the same approach I can write code to convert normal expression strings into reverse polished format.
 */
public class BasicCalculatorGeneric {
    public int calculate(String s) {
        //Need to Normalize the expression first: add parenthesis and remove all spaces
        String normStr = normalize(s);
        System.out.println(normStr);
        return helper(normStr, 0, normStr.length()-1);
    }

    /**
     * Remove all spaces and add parenthesis as needed.
     * @param s
     * @return return normalized String.
     */
    private String normalize(String s){
        StringBuilder buf1 = new StringBuilder();
        //first step is to remove all spaces
        int ptr = 0;
        while(ptr<s.length()){
            if(s.charAt(ptr)!=' '){
                if(s.charAt(ptr)=='-'){
                    buf1.append("+(0-1)*");
                }
                else{
                    buf1.append(s.charAt(ptr));
                }
            }
            ptr++;
        }
        String tmpString = buf1.toString();
        //you should normally avoid looping and modifying the same sequence at the same time.
        ptr = 0;
        StringBuilder buf2 = new StringBuilder();
        while(ptr<tmpString.length()){
            Operand operand = getOperand(tmpString, ptr, tmpString.length()-1);
            int checkValue;
            if(tmpString.charAt(ptr)=='('){
                checkValue = check(tmpString, operand.left-1, operand.right+1);
                addParenthesis(tmpString, buf2, checkValue, operand.left-1, operand.right+1);
            }
            else{
                checkValue = check(tmpString, operand.left, operand.right);
                addParenthesis(tmpString, buf2, checkValue, operand.left, operand.right);
            }
            ptr = operand.next;
            //check if need to take operator.
            if(ptr<tmpString.length()){
                buf2.append(tmpString.substring(ptr, ptr+1));
                ptr++;
            }
        }
        return buf2.toString();
    }

    /**
     * @param src
     * @param buf
     * @param checkValue
     * @param left: inclusive.
     * @param right: inclusive.
     */
    private void addParenthesis(String src, StringBuilder buf, int checkValue, int left, int right){
        if(checkValue==1){
            buf.append('(');
            buf.append(src.substring(left, right+1));
        }
        else if(checkValue==2){
            buf.append(src.substring(left, right+1));
            buf.append(')');
        }
        else{
            buf.append(src.substring(left, right+1));
        }
    }

    /**
     * @param s
     * @param start: inclusive; the start of an operand
     * @param end: inclusive; the end of an operand
     * @return return 1 if add '(', return 2 if add ')', return 0 if nothing.
     */
    private int check(String s, int start, int end){
        boolean leftHigh = false;
        boolean rightHigh = false;
        if(start-1>=0 && (s.charAt(start-1)=='*' || s.charAt(start-1)=='/')){
            leftHigh = true;
        }
        if(end+1<s.length() && (s.charAt(end+1)=='*' || s.charAt(end+1)=='/')){
            rightHigh = true;
        }
        if(leftHigh&&!rightHigh){
            return 2;
        }
        else if(rightHigh&&!leftHigh){
            return 1;
        }
        else{
            return 0;
        }
    }

    /**
     * Assume all needed "()" added and all spaces removed.
     * @param s
     * @param start: inclusive
     * @param end: inclusive
     * @return return the expression value
     */
    private int helper(String s, int start, int end){
        //base case
        BaseValue baseValue = checkBase(s, start, end);
        if(baseValue.base){
            return baseValue.value;
        }

        //recursive case
        Operand operand = getOperand(s, start, end);
        //this if check is for the case when the whole expression is wrapped with "()"
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

    /**
     * Check for the base case: where it is a numeric operand.
     * Assuming the expression is already normalized.
     * @param s
     * @param start: inclusive
     * @param end: inclusive
     * @return
     */
    private BaseValue checkBase(String s, int start, int end){
        //when not base case, do not really care the value assigned.
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
     * @param start: inclusive; start is pointing to the current '('
     * @param end: inclusive
     * @return return the pos of the pair ')' of the current '('.
     */
    private int findPair(String s, int start, int end){
        int left = 1;
        int ptr = start+1;
        while(ptr<=end && left>0){
            if(s.charAt(ptr)=='('){
                left++;
            }
            else if(s.charAt(ptr)==')'){
                left--;
            }
            ptr++;
        }
        return --ptr;
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
