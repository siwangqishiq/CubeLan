package com.xinlan.cubelan.core;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class CoreData
{
    public static final int RED=1;
    public static final int GREEN=2;
    public static final int YELLOW=3;
    public static final int ORANGE=4;
    public static final int BLUE=5;
    public static final int PURPLE=6;
    public static final int GRAY=7;
    
    private Random rand = new Random();
    

    public byte[][] data = {//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 }, //
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 } };

    private byte[] rowTemp = new byte[data[0].length];
    private int length = rowTemp.length;

    private HashSet<Integer> setContainer = new HashSet<Integer>();
    private LinkedList<Point> track = new LinkedList<Point>();

    public static class Point
    {
        int x, y;
    }

    public void genRow()
    {
        for (int i = 0; i < length; i++)
        {
            rowTemp[i] = (byte) (1+rand.nextInt(GRAY+1));
        }// end for i
        for (int i = data.length - 2; i >= 0; i--)
        {
            System.arraycopy(data[i], 0, data[i + 1], 0, length);
        }// end for i
        System.arraycopy(rowTemp, 0, data[0], 0, length);
    }

    public LinkedList<Point> calPath(int startx, int starty)
    {
        return null;
    }

    public void show()
    {
        System.out.println("***************************************");
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[0].length; j++)
            {
                System.out.print(data[i][j] + "    ");
            }// end for j
            System.out.println();
        }// end for i
    }

    public static void main(String[] args)
    {
        CoreData d = new CoreData();
        d.genRow();
        d.genRow();
        d.genRow();
        d.genRow();
        d.genRow();
        d.genRow();
        d.genRow();
        d.genRow();
        d.genRow();
        d.show();
    }
}// end class
