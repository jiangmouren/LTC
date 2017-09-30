package finished;

/**
 * Question:
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string may contain open ( and closing parentheses ),
 * the plus + or minus sign -, non-negative integers and empty spaces .
 * You may assume that the given expression is always valid.
 * Some examples:
 * "1 + 1" = 2
 * " 2-1 + 2 " = 3
 * "(1+(4+5+2)-3)+(6+8)" = 23
 * Note: Do not use the eval built-in library function.
 */

/**
 * Analysis:
 * The normal way to solve this kind of Calculator problem is to use "recursion".
 * Because the problem solving process is essentially a "recursive process".
 * You recursively evaluate expression values, and with all the sub-expression values, original one is solved.
 *
 * But for this specific problem, we can do it easier because we only have "+" and "-".
 * The order does not really matters, we can just sum from left to right.
 * Just be careful with the ptr manipulation.
 */
class BasicCalculator {
    /**
     * The above logic will not work, because of the following case:
     * "2-(5-6)"
     * It can be more nested with minus, basically the parentheses cannot be ignored.
     * need to use recursion.
     */

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
