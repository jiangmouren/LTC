package finished.math;

/**
 * Question:
 * Given a positive integer, return its corresponding column title as appear in an Excel sheet.
 * For example:
 *
 *     1 -> A
 *     2 -> B
 *     3 -> C
 *     ...
 *     26 -> Z
 *     27 -> AA
 *     28 -> AB
 */

/**
 * Analysis:
 * Typical Radix conversion problem.
 * There are 2 catches:
 * one is that the 26 radix is from 1 to 26 not 0 to 25,
 * so we cannot just use divide by radix to eliminate the last digit, use (A-1)/26 will work.
 * The second one is in the end need to reverse the string.
 *
 */

public class ExcelSheetColumnTitle{
    public String title(int num){
        if(num<1) throw new IllegalArgumentException("Input must be positive integer");
        StringBuilder buffer = new StringBuilder();
        while(num>0){
            int remainder = num%26;
            char tmp = getChar(remainder);
            buffer.append(tmp);
            num = (num-1)/26;
        }
        buffer.reverse();
        return buffer.toString();
    }

    private char getChar(int input){
        if(input==1) return 'A';
        if(input==2) return 'B';
        if(input==3) return 'C';
        if(input==4) return 'D';
        if(input==5) return 'E';
        if(input==6) return 'F';
        if(input==7) return 'G';
        if(input==8) return 'H';
        if(input==9) return 'I';
        if(input==10) return 'J';
        if(input==11) return 'K';
        if(input==12) return 'L';
        if(input==13) return 'M';
        if(input==14) return 'N';
        if(input==15) return 'O';
        if(input==16) return 'P';
        if(input==17) return 'Q';
        if(input==18) return 'R';
        if(input==19) return 'S';
        if(input==20) return 'T';
        if(input==21) return 'U';
        if(input==22) return 'V';
        if(input==23) return 'W';
        if(input==24) return 'X';
        if(input==25) return 'Y';
        else return 'Z';
    }

}
