package com.mycompany.app.companies.rippling;
import java.util.*;

/**
 * Problem
 * We want to build a very basic app that let artists draw shapes on a blank canvas.
 * Think of this app like the most rudimentary version of Microsoft Paint or Adobe Photoshop.
 * We want this app to eventually support the following operations:
 *
 * add shapes to the canvas
 *
 * move shapes around the canvas.
 *
 * Part 1
 * create a MxN blank canvas
 *
 * display the canvas to the user
 *
 * Example:
 * A canvas of 10x25 would look like this. String “0” means a white space.
 *
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 *
 * Part 2
 * draw a rectangle of size LxW and place it at some coordinate (i, j)
 *
 * supports multiple shapes where the shapes can be overlapping. If they are overlapping, then the last shape that was drawn gets displayed
 *
 * Example:
 *
 * Drawing the shapes ‘a', ‘b’ and 'c’ where
 *
 * 'a' is 4x6 at (0,0)
 *
 * 'b' is 6x4 at (4,21)
 *
 * 'c' is 5x5 at (2,5)
 *
 * Notice ‘c' is overlapping with ‘a’ and since ‘c’ was added last, we show ‘c’ on top of 'a’
 *
 * a a a a a a 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * a a a a a a 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * a a a a a c c c c c 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * a a a a a c c c c c 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 c c c c c 0 0 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 c c c c c 0 0 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 c c c c c 0 0 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 b b b b
 *
 * Part 3
 * Move the shape to any new location within the canvas.
 *
 * Example:
 * Moving the shape ‘c' to (2, 7). Notice how 'a’ is being displayed to the user.
 *
 * a a a a a a 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * a a a a a a 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * a a a a a a 0 c c c c c 0 0 0 0 0 0 0 0 0 0 0 0 0
 * a a a a a a 0 c c c c c 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 c c c c c 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 0 0 c c c c c 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 0 0 c c c c c 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 b b b b
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 b b b b
 */

/**
 * 网上看到有一些讨论说用per pixel的list或者stack之类的数据结构，建模，这根本不scalable.
 * 操作起来太复杂，
 * 还是得用layer的方式建模。每次render的时候进行覆盖。
 */
public class Canvas {
    class Shape{
        char color;
        int x;
        int y;
        int width;
        int height;
        public Shape(char color, int x, int y, int width, int height){
            this.color = color;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public boolean contains(int x, int y){
            //add logic to determine if given (x, y) is within the boundary of the shape
            if(x>=this.x && x<this.x+this.height && y>=this.y && y<this.y+this.height){
                return true;
            }
            return false;
        }
    }

    int width;
    int height;
    List<Shape> layers;
    List<List<Character>> canvas;

    public Canvas(int width, int height){
        this.width = width;
        this.height = height;
        this.layers = new ArrayList<>();
        this.canvas = new ArrayList<>();
        for(int i=0; i<height; i++){
            List<Character> temp = new ArrayList<>();
            for(int j=0; j<width; j++){
                temp.add('0');
            }
            this.canvas.add(temp);
        }
    }

    private void clearCanvas(){
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                this.canvas.get(i).set(j, '0');
            }
        }
    }

    public Shape select(int x, int y){
        //add logic to select shap, given click position (x, y)
        for(int i=this.layers.size()-1; i>=0; i--){
            if(this.layers.get(i).contains(x, y)){
                return this.layers.get(i);
            }
        }
        return null;
    }

    public void draw(int x, int y, int width, int height, char color){
        //TODO: add logic to check if the shape is within the canvas
        Shape shape = new Shape(color, x, y, width, height);
        this.layers.add(shape);
        this.updateCanvas();
    }

    //assume all shapes within the canvas
    //if shapes are allowed to move out of canvas, then we can allow corresponding (x, y)
    //to go out of boundary, then we need to calculate the overlap area between the shape and the canvas
    //before apply the shape
    private void updateCanvas(){
        this.clearCanvas();
        for(Shape shape : this.layers){
            for(int i=shape.x; i<shape.x+shape.height; i++){
                for(int j=shape.y; j<shape.y+shape.width; j++){
                    this.canvas.get(i).set(j, shape.color);
                }
            }
        }
    }

    //Assume select method is called and shape got selected
    public void move(int x, int y, int toX, int toY){
        //adjust the toX and toY to make sure the shape is still within the Canvas
        Shape src = this.select(x, y);
        if(src!=null){
            if(toX+src.height>=this.height){
                toX -= (toX+src.height - this.height+1);
            }
            if(toY+src.width>=this.width){
                toY -= (toY+src.width - this.width+1);
            }
            if(toX<0){
                toX = 0;
            }
            if(toY<0){
                toY = 0;
            }
            src.x = toX;
            src.y = toY;
            this.updateCanvas();
        }
    }

    public void display(){
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                System.out.print(this.canvas.get(i).get(j));
            }
            System.out.println("");
        }
    }

    public static void main(String[] args){
        Canvas instance = new Canvas(16, 10);
        System.out.println("empty canvas");
        instance.display();
        instance.draw(0, 0, 3, 4, 'a');
        System.out.println("a 3X4 at (0, 0)");
        instance.display();
        instance.draw(4, 4, 3, 4, 'b');
        System.out.println("b 3X4 at (4, 4)");
        instance.display();
        instance.draw(2, 2, 4, 3, 'c');
        System.out.println("c 4X3 at (2, 2)");
        instance.display();
        System.out.println("move c");
        instance.move(2, 3, 2, 8);
        instance.display();
    }
}
