package com.mycompany.app.tree;

import java.util.*;

/**
 * https://leetcode.com/problems/design-in-memory-file-system/
 * Design an in-memory file system to simulate the following functions:
 * ls: Given a path in string format. If it is a file path, return a list that only contains this file's name. If it is a directory path, return the list of file and directory names in this directory. Your output (file and directory names together) should in lexicographic order.
 *
 * mkdir: Given a directory path that does not exist, you should make a new directory according to the path. If the middle directories in the path don't exist either, you should create them as well. This function has void return type.
 *
 * addContentToFile: Given a file path and file content in string format. If the file doesn't exist, you need to create that file containing given content. If the file already exists, you need to append given content to original content. This function has void return type.
 *
 * readContentFromFile: Given a file path, return its content in string format.
 *
 * Example:
 * Input:
 * ["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
 * [[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
 * Output:
 * [null,[],null,null,["a"],"hello"]
 *
 * Note:
 *
 * You can assume all file or directory paths are absolute paths which begin with / and do not end with / except that the path is just "/".
 * You can assume that all operations will be passed valid parameters and users will not attempt to retrieve file content or list a directory or file that does not exist.
 * You can assume that all directory names and file names only contain lower-case letters, and same names won't exist in the same directory.
 */
public class DesignInMemoryFileSystem {
    public static void main(String[] args){
        DesignInMemoryFileSystem instance = new DesignInMemoryFileSystem();
        instance.mkdir("/goowmfn");
        System.out.println(instance.ls("/goowmfn"));
        System.out.println(instance.ls("/"));
        instance.mkdir("/z");
        System.out.println(instance.ls("/"));
        System.out.println(instance.ls("/"));
        instance.addContentToFile("/goowmfn/c", "shetopcy");
        System.out.println(instance.ls("/z"));
        System.out.println(instance.ls("/goowmfn/c"));
    }

    class Node{
        String name;
        boolean isFile;
        List<Node> subDir;
        StringBuilder content;
        public Node(String name, boolean isFile){
            this.name = name;
            if(isFile){
                this.isFile = true;
                this.content = new StringBuilder();
            }
            else{
                this.subDir = new ArrayList<>();
            }
        }
    }

    Node root;

    public DesignInMemoryFileSystem() {
        this.root = new Node("", false);
    }

    public List<String> ls(String path) {
        List<String> pathTokens = tokenize(path);
        List<String> res = new ArrayList<>();
        traverse(this.root, pathTokens, 0, res);
        Collections.sort(res);
        return res;
    }

    public void mkdir(String path) {
        List<String> pathTokens = tokenize(path);
        this.mkdir(pathTokens, this.root, 0);
    }

    public void addContentToFile(String filePath, String content) {
        List<String> pathTokens = tokenize(filePath);
        String fileName = pathTokens.get(pathTokens.size()-1);
        pathTokens.remove(pathTokens.size()-1);
        Node lastDir = this.mkdir(pathTokens, this.root, 0);
        for(Node sub : lastDir.subDir){
            if(sub.name.equals(fileName)){
                sub.content.append(content);
                return;
            }
        }
        Node file = new Node(fileName, true);
        file.content.append(content);
        lastDir.subDir.add(file);
    }

    public String readContentFromFile(String filePath) {
        List<String> pathTokens = tokenize(filePath);
        //String fileName = pathTokens.get(pathTokens.size()-1);
        //pathTokens.remove(pathTokens.size()-1);
        Node file = this.mkdir(pathTokens, this.root, 0);
        return file.content.toString();
    }

    private Node mkdir(List<String> path, Node cur, int pos){
        //termination condition
        if(pos>=path.size()){
            return cur;
        }

        for(Node sub : cur.subDir){
            if(sub.name.equals(path.get(pos))){
                return mkdir(path, sub, pos+1);
            }
        }
        Node node = new Node(path.get(pos), false);
        cur.subDir.add(node);
        return mkdir(path, node, pos+1);
    }

    private List<String> tokenize(String path){
        List<String> res = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        int ptr = 1;
        while(ptr<path.length()){
            char c = path.charAt(ptr);
            if(c=='/'){
                res.add(buf.toString());
                buf.setLength(0);
            }
            else{
                buf.append(path.charAt(ptr));
            }
            ptr++;
        }
        //add the last section
        if(buf.length()>0){
            res.add(buf.toString());
        }
        return res;
    }

    private void traverse(Node cur, List<String> path, int pos, List<String> res){
        //termination condition
        if(pos>=path.size()){
            if(cur.isFile){
                res.add(cur.name);
                return;
            }
            else{
                for(Node sub : cur.subDir){
                    res.add(sub.name);
                }
                return;
            }
        }

        for(Node sub : cur.subDir){
            if(sub.name.equals(path.get(pos))){
                traverse(sub, path, pos+1, res);
                return;
            }
        }
    }

}
