package com.mycompany.app.companies.cruise;
import java.util.*;
/**
 * 第二轮 1h
 * coding
 * 从头实现一个sparse matrix类
 * get
 * set
 * add
 * 我写得比较慢 可能之后还有multiply
 * 我的解法是nested hashmap: Map<Integer, Map<Integer, Integer>>
 * follow up
 * 如何泛化 比如 List<Integer> List<String>
 * 中间有个bug没处理好 感觉是挂了 哈哈哈
 */
public class SparseMatrix {
    int rowCnt;
    int colCnt;
    Map<String, Integer> map;

    public SparseMatrix(SparseMatrix mat){
        this.rowCnt = mat.rowCnt;
        this.colCnt = mat.colCnt;
        this.map = new HashMap<>();
        for(String key : mat.map.keySet()){
            this.map.put(key, mat.map.get(key));
        }
    }

    public SparseMatrix(int[][] mat){
        this.rowCnt = mat.length;
        this.colCnt = mat[0].length;
        this.map = new HashMap<>();
        for(int i=0; i<mat.length; i++){
            for(int j=0; j<mat[0].length; j++){
                if(mat[i][j]!=0){
                    String key = i + "-" + j;
                    map.put(key, mat[i][j]);
                }
            }
        }
    }

    public SparseMatrix(int rowCnt, int colCnt){
        this.rowCnt = rowCnt;
        this.colCnt = colCnt;
        this.map = new HashMap<>();
    }

    public int get(int row, int col){
        String key = row+"-"+col;
        if(this.map.containsKey(key)){
            return this.map.get(key);
        }
        return 0;
    }

    public void set(int row, int col, int val){
        String key = row + "-" + col;
        this.map.put(key, val);
    }

    public static SparseMatrix add(SparseMatrix mat0, SparseMatrix mat1){
        if(mat0.rowCnt!=mat1.rowCnt || mat0.colCnt!=mat1.colCnt){
            throw new IllegalArgumentException("inputs matrices should have the same size");
        }
        SparseMatrix res = new SparseMatrix(mat0);
        for(String key : mat1.map.keySet()){
            int sum = mat1.map.get(key);
            if(res.map.containsKey(key)){
                sum += res.map.get(key);
            }
            if(sum==0){
                res.map.remove(key);
            }
            else{
                res.map.put(key, sum);
            }
        }
        return res;
    }

    public static SparseMatrix mul(SparseMatrix mat0, SparseMatrix mat1){
        if(mat0.colCnt!=mat1.rowCnt){
            throw new IllegalArgumentException("mat0 col_cnt should equal to mat1 row_cnt");
        }

        SparseMatrix res = new SparseMatrix(mat0.rowCnt, mat1.colCnt);

        for(int i=0; i<mat0.rowCnt; i++){
            for(int j=0; j<mat1.colCnt; j++){
                int sum = 0;
                for(int k=0; k<mat0.colCnt; k++){
                    String key0 = i+"-"+k;
                    String key1 = k+"-"+j;
                    if(mat0.map.containsKey(key0) && mat1.map.containsKey(key1)){
                        int temp = mat0.map.get(key0) * mat1.map.get(key1);
                        sum += temp;
                    }
                }
                if(sum!=0){
                    String key = i + "-" + j;
                    res.map.put(key, sum);
                }
            }
        }
        return res;
    }
}
