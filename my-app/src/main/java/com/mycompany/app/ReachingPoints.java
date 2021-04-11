package com.mycompany.app;

/**
 * https://leetcode.com/problems/reaching-points/submissions/
 * A move consists of taking a point (x, y) and transforming it to either (x, x+y) or (x+y, y).
 *
 * Given a starting point (sx, sy) and a target point (tx, ty), return True if and only if a sequence of moves exists to transform the point (sx, sy) to (tx, ty). Otherwise, return False.
 *
 * Examples:
 * Input: sx = 1, sy = 1, tx = 3, ty = 5
 * Output: True
 * Explanation:
 * One series of moves that transforms the starting point to the target is:
 * (1, 1) -> (1, 2)
 * (1, 2) -> (3, 2)
 * (3, 2) -> (3, 5)
 *
 * Input: sx = 1, sy = 1, tx = 2, ty = 2
 * Output: False
 *
 * Input: sx = 1, sy = 1, tx = 1, ty = 1
 * Output: True
 *
 * Note:
 * sx, sy, tx, ty will all be integers in the range [1, 10^9].
 */
public class ReachingPoints {
    //最直观的想法是从(sx, sy)往(tx, ty)的方向做遍历，这种解法要访问整个decision tree
    //再细想之下会发现这个也可以反着写，tx>ty，就用tx-ty，反之ty-tx，一直减下去，知道跟sx,sy相同，或者比他们更小。
    //这种解法，避免了遍历整个decision tree，而是从终点直接沿着唯一返程的path回到起点。
    //这种解法对于sx=1, sy=1, tx=1000000000, ty=1的情况极为耗时
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while(tx>sx || ty>sy){
            if(tx>ty){
                tx -= ty;
            }
            else{
                ty -=tx;
            }
        }
        return tx==sx && ty==sy;
    }

    //作为对上面解法更进一步的优化，如果tx需要连续数次的减ty，与其一次一次的减，不如直接tx%ty
    //但是这种操作要避免tx%ty<sx的情况，相当于，tx不停减ty，到了比sx还小的地步。
    //避免这种情况方法就是diff=tx-sx，这把多出来的部分做%ty的操作（也就是连续减ty）
    //边界条件就是当diff本身就小于ty的时候，那么这时候diff%ty就等于它本身
    //于是diff+sx又变回到tx，这种情况减不下去了，不停止就会死循环了。
    //test case1:sx=9, sy=5, tx=12, ty=8
    //test case2:sx=9, sy=10, tx=9, ty=19
    public boolean reachingPointsSln2(int sx, int sy, int tx, int ty){
        while(tx>sx || ty>sy){
            if(tx>ty){
                int diff = tx - sx;
                diff %= ty;
                diff += sx;
                if(diff==tx){//减到最后的位置的条件
                    break;//tx已经无法再往下减了，但是tx>ty，所以只能结束整个过程
                }
                else{
                    tx = diff;
                }
            }
            else{
                int diff = ty - sy;
                diff %= tx;
                diff += sy;
                if(diff==ty){
                    break;
                }
                else{
                    ty = diff;
                }
            }
        }
        return tx==sx && ty==sy;
    }
}
