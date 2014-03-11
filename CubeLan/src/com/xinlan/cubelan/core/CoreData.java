package com.xinlan.cubelan.core;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.xinlan.cubelan.Config;

public class CoreData
{
    public static final int RED=1;
    public static final int GREEN=2;
    public static final int YELLOW=3;
    public static final int ORANGE=4;
    public static final int BLUE=5;
    public static final int PINK=6;
    public static final int GRAY=7;
    
    public static final int PAD=20;
    public static final int TOP=200;
    public static final int CUBE=60;
    
    private Random rand = new Random();
    private float times;

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
    
    public CoreData()
    {
    }
    
    public void logic(float delta)
    {
        times+=delta;
        if(times>=1){
            times=0;
            genRow();
        }
    }
    
    public void draw(ShapeRenderer shapeRenderer,float delta)
    {
        shapeRenderer.begin(ShapeType.Filled);
        for(int i=0;i<length;i++)
        {
            for(int j=0,maxCol = data[i].length;j<maxCol;j++)
            {
                int value = data[i][j];
                if(value!=0)
                {
                    switch(value)
                    {
                        case RED:
                            shapeRenderer.setColor(Color.RED);
                            break;
                        case GREEN:
                            shapeRenderer.setColor(Color.GREEN);
                            break;
                        case YELLOW:
                            shapeRenderer.setColor(Color.YELLOW);
                            break;
                        case ORANGE:
                            shapeRenderer.setColor(Color.ORANGE);
                            break;
                        case BLUE:
                            shapeRenderer.setColor(Color.BLUE);
                            break;
                        case PINK:
                            shapeRenderer.setColor(Color.PINK);
                            break;
                        case GRAY:
                            shapeRenderer.setColor(Color.GRAY);
                            break;
                    }//end switch
                    shapeRenderer.rect(PAD+j*CUBE+5*j, Config.SCREEN_HEIGHT-TOP-i*CUBE-5*i, CUBE, CUBE);
                }//end if
                
            }//end for j
        }//end for i
        
        shapeRenderer.end();
    }

    /**
     * 从底部随机产生一行
     */
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

    private void show()
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
