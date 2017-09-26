package finished;

/**
 * Question:
 * Given two strings S and T, determine if they are both one edit distance apart.
 */

/**
 * Analysis:
 * This one is a simplified version of the EditDistance problem.
 * The first problem is what's the definition of "one edit distance apart"?
 * 1. insert; 2. replace; 3. delete
 *
 * One naive way of doing this is to compute the edit distance of these two strings,
 * and this will take O(n^2) time.
 *
 * The smarter way would be realizing that they only one distance away, we can linearly
 * compare these two strings, with maximum 1 shift.
 */


class OneEditDistance {
    public boolean isOneEditDistance(String s, String t) {
        if(s==null || t==null){
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        int ls = s.length();
        int lt = t.length();
        if(Math.abs(ls-lt)>1) return false;

        if(ls==lt) return checkReplace(s, t);
        else if(ls<lt) return checkDelete(s, t);
        else return checkDelete(t, s);//Delete and Insert is essentially the same thing.
    }

    private boolean checkReplace(String s, String t){
        int diff = 0;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)!=t.charAt(i)){
                diff++;
                if(diff>1) return false;
            }
        }
        return true;
    }

    //Assume s is short
    private boolean checkDelete(String s, String t){
        boolean found = false;
        for(int i=0; i<s.length(); i++){
            if(found){
                if(s.charAt(i)!=t.charAt(i+1)){
                    return false;
                }
            }
            else{
                if(s.charAt(i)!=t.charAt(i)){
                    found = true;
                    i--;//default i++, but for this case we want to hold i,so i-- first.
                }
            }
        }
        return true;
    }
}
