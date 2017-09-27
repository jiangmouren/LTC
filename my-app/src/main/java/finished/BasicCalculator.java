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
    public int calculate(String s) {
        int sum = 0;
        int op = 1;
        int i = 0;
        while(i<s.length()){
            if(Character.isDigit(s.charAt(i))){
                int j = i;
                //Be aware num can have multiple digits
                while(j<s.length() && Character.isDigit(s.charAt(j))){
                    j++;
                }
                int num = Integer.valueOf(s.substring(i, j));
                //System.out.println(num);
                sum += op * num;
                i = j;
            }
            else{
                if(s.charAt(i)=='-'){
                    op = -1;
                }
                else if(s.charAt(i)=='+'){
                    op = 1;
                }
                i++;
            }
        }
        return sum;
    }
    **/
    /**
     * The above logic will not work, because of the following case:
     * "2-(5-6)"
     * It can be more nested with minus, basically the parentheses cannot be ignored.
     * need to use recursion.
     */

    /**
    public int calculate(String s) {

    }

    private int helper(String s, int start, int end){
        //base case
        BaseValue baseValue = checkBase();
        if(baseValue.base){
            return baseValue.value;
        }

        //recursive case
        Operand operand = getOperand(s, start, end);
        Operator operator = getOperator(s, operand.right +1, end);


    }

    private BaseValue checkBase(){

    }

    private class BaseValue{
        boolean base;
        int value;
        BaseValue(boolean base, int value){
            this.base = base;
            this.value = value;
        }
    }

    private Operand getOperand(String s, int start, int end){

    }

    private class Operand{
        int left;
        int right;
    }

    private Operator getOperator(String s, int start, int end){

    }

    private class Operator{
        char operator;
        int next;
    }

    **/



}
