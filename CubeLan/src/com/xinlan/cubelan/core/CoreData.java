package com.xinlan.cubelan.core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.xinlan.cubelan.Config;
import com.xinlan.cubelan.screen.GameScreen;

public class CoreData implements InputProcessor
{
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int PINK = 3;
    public static final int BLACK = 4;

    public static final int PAD = 15;
    public static final int TOP = 200;
    public static final int CUBE = 60;
    public static final int MARGIN = 5;

    public static final int WAIT_SHOT = 2;
    public static final int SHOT = 3;
    public static final int MOVE = 4;

    public int state = SHOT;

    private Random rand = new Random();
    private float times;

    // public byte[][] data = {//
    // { 0, 0, 0, 0, 0, 0, 0 },//
    // { 0, 0, 0, 0, 0, 0, 0 },//
    // { 0, 0, 0, 0, 0, 0, 0 },//
    // { 0, 0, 0, 0, 0, 0, 0 },//
    // { 0, 0, 0, 0, 0, 0, 0 }, //
    // { 0, 0, 0, 0, 0, 0, 0 },//
    // { 0, 0, 0, 0, 0, 0, 0 },//
    // { 0, 0, 0, 0, 0, 0, 0 },//
    // { 0, 0, 0, 0, 0, 0, 0 },//
    // { 0, 0, 0, 0, 0, 0, 0 } };

    public byte[][] data = {//
    { 1, 1, 1, 1, 1, 1, 1 },//
            { 0, 0, 0, 0, 1, 1, 0 },//
            { 0, 0, 0, 0, 2, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 }, //
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 },//
            { 0, 0, 0, 0, 0, 0, 0 } };

    private byte[] rowTemp = new byte[data[0].length];
    private int length = rowTemp.length;
    private GameScreen screen;
    private HashSet<Integer> setContainer = new HashSet<Integer>();
    private LinkedList<Point> track = new LinkedList<Point>();
    private Vector3 touchPos = new Vector3();
    protected byte nextCubeValue;
    private int dy = 13;
    private float addCubeX, addCubeY;
    private int curCol;
    private LinkedList<Integer> dismissList = new LinkedList<Integer>();
    private HashSet<Integer> hashSet = new HashSet<Integer>();

    public static class Point
    {
        int x, y;
    }

    public CoreData(GameScreen screen)
    {
        this.screen = screen;
        state = WAIT_SHOT;
        Gdx.input.setInputProcessor(this);
        nextCubeValue = genNormalValue();
        // genRow();
        // genRow();
    }

    public void logic(float delta)
    {
        times += delta;
        if (times >= 10)
        {
            times = 0;
            genRow();
        }

        switch (state)
        {
            case SHOT:
                screen.cam.unproject(touchPos);// 坐标转换
                addCubeX = touchPos.x;
                addCubeY = PAD;
                break;
            case MOVE:
                addCubeY += dy;
                // 找出数组中指定列 第一个非零值
                int needPos = findCubeToPos();
                if (addCubeY > needPos)
                {
                    addCubeY = needPos;
                    int pos = 0;
                    for (int i = data.length - 1; i >= 0; i--)
                    {
                        if (data[i][curCol] != 0)
                        {
                            pos = i;
                            break;
                        }
                    }// end for i
                    if (pos + 1 < data.length)
                    {
                        if (pos == 0 && data[0][curCol] == 0)
                        {
                            data[0][curCol] = nextCubeValue;
                            checkData(0, curCol);
                        }
                        else
                        {
                            data[pos + 1][curCol] = nextCubeValue;
                            checkData(pos + 1, curCol);
                        }

                        if (dismissList.size() >= 6)
                        {
                            for (int i = 0; i < dismissList.size(); i += 2)
                            {
                                data[dismissList.get(i)][dismissList.get(i + 1)] = 0;
                            }// end for i
                        }
                    }
                    state = WAIT_SHOT;
                    nextCubeValue = genNormalValue();
                }
                break;
        }// end switch
        moveCubes();
    }

    private int findCubeToPos()
    {
        int pos = 0;
        for (int i = data.length - 1; i >= 0; i--)
        {
            if (data[i][curCol] != 0)
            {
                pos = i;
                break;
            }
        }// end for i
        int marginNum = pos - 1 < 0 ? 0 : pos;
        if (pos == 0 && data[0][curCol] == 0)
        {
            return Config.SCREEN_HEIGHT - TOP;
        }
        return Config.SCREEN_HEIGHT
                - (TOP + CUBE * pos + marginNum * MARGIN + CUBE);
    }

    public void draw(ShapeRenderer shapeRenderer, float delta)
    {
        shapeRenderer.begin(ShapeType.Filled);
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0, maxCol = data[i].length; j < maxCol; j++)
            {
                int value = data[i][j];
                if (value != 0)
                {
                    shapeRenderer.setColor(mapValueToColor(value));
                    shapeRenderer.rect(PAD + j * CUBE + MARGIN * j,
                            Config.SCREEN_HEIGHT - TOP - i * CUBE - MARGIN * i,
                            CUBE, CUBE);
                }// end if
            }// end for j
        }// end for i

        switch (state)
        {
            case SHOT:
            case MOVE:
                shapeRenderer.setColor(mapValueToColor(nextCubeValue));
                shapeRenderer.rect(addCubeX, addCubeY, CUBE, CUBE);
                break;
        }// end switch

        shapeRenderer.end();
    }

    public Color mapValueToColor(int value)
    {
        switch (value)
        {
            case RED:
                return Color.RED;
            case GREEN:
                return Color.GREEN;
            case PINK:
                return Color.PINK;
            case BLACK:
                return Color.BLACK;
        }// end switch
        return Color.WHITE;
    }

    /**
     * 产生符合要求的随机值
     * 
     * @return
     */
    public byte genNormalValue()
    {
        return (byte) (1 + rand.nextInt(BLACK));
    }

    /**
     * 从底部随机产生一行
     */
    public void genRow()
    {
        for (int i = 0; i < length; i++)
        {
            rowTemp[i] = genNormalValue();
        }// end for i
        for (int i = data.length - 2; i >= 0; i--)
        {
            System.arraycopy(data[i], 0, data[i + 1], 0, length);
        }// end for i
        System.arraycopy(rowTemp, 0, data[0], 0, length);
    }

    private void checkData(int x, int y)
    {
        dismissList.clear();
        hashSet.clear();
        calPath(x, y);
    }

    private void moveCubes()
    {
        for (int i = 0; i < data[0].length; i++)
        {
            for (int j = 0; j < data.length; j++)
            {
                if (data[j][i] == 0)
                {
                    continue;
                }

                if (j > 0)
                {
                    int index = j;
                    while (data[index - 1][i] == 0)
                    {
                        data[index - 1][i] = data[index][i];
                        data[index][i] = 0;
                        index++;
                        if (index >= data.length)
                            break;
                    }// end while
                }
            }// end for j
        }// end for i
    }

    private void calPath(int startx, int starty)
    {
        int key = 7 * startx + starty;
        if (hashSet.contains(key))
        {
            return;
        }
        else
        {
            hashSet.add(key);
            dismissList.add(startx);
            dismissList.add(starty);
            int value = data[startx][starty];
            if (value == 0)
                return;

            if (starty - 1 >= 0)
            {
                if (value == data[startx][starty - 1])
                {
                    calPath(startx, starty - 1);
                }
            }

            if (startx - 1 >= 0)
            {// 上边
                if (value == data[startx - 1][starty])
                {
                    calPath(startx - 1, starty);
                }
            }

            if (starty + 1 < data[0].length)// 右
            {
                if (value == data[startx][starty + 1])
                {
                    calPath(startx, starty + 1);
                }
            }

            if (startx + 1 < data.length)// 下
            {
                if (value == data[startx + 1][starty])
                {
                    calPath(startx + 1, starty);
                }
            }

        }
    }

    // private void show()
    // {
    // System.out.println("***************************************");
    // for (int i = 0; i < data.length; i++)
    // {
    // for (int j = 0; j < data[0].length; j++)
    // {
    // System.out.print(data[i][j] + "    ");
    // }// end for j
    // System.out.println();
    // }// end for i
    // }

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        switch (state)
        {
            case WAIT_SHOT:
                touchPos.set(screenX, screenY, 0);
                state = SHOT;
                break;
        }// end switch
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        switch (state)
        {
            case WAIT_SHOT:
                break;
            case SHOT:
                touchPos.set(screenX, screenY, 0);
                break;
        }// end switch
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        switch (state)
        {
            case WAIT_SHOT:
                break;
            case SHOT:
                touchPos.set(screenX, screenY, 0);
                screen.cam.unproject(touchPos);// 坐标转换
                float x = touchPos.x + CUBE / 2;
                // TODO 可优化
                if (x < PAD + CUBE + MARGIN)
                {
                    addCubeX = PAD;
                    curCol = 0;
                }
                else if (x >= PAD + CUBE + MARGIN
                        && x < PAD + 2 * (CUBE + MARGIN))
                {
                    addCubeX = PAD + CUBE + MARGIN;
                    curCol = 1;
                }
                else if (x >= PAD + 2 * (CUBE + MARGIN)
                        && x < PAD + 3 * CUBE + 3 * MARGIN)
                {
                    addCubeX = PAD + 2 * (CUBE + MARGIN);
                    curCol = 2;
                }
                else if (x >= PAD + 3 * (CUBE + MARGIN)
                        && x < PAD + 4 * CUBE + 4 * MARGIN)
                {
                    addCubeX = PAD + 3 * (CUBE + MARGIN);
                    curCol = 3;
                }
                else if (x >= PAD + 4 * (CUBE + MARGIN)
                        && x < PAD + 5 * CUBE + 5 * MARGIN)
                {
                    addCubeX = PAD + 4 * (CUBE + MARGIN);
                    curCol = 4;
                }
                else if (x >= PAD + 5 * (CUBE + MARGIN)
                        && x < PAD + 6 * CUBE + 6 * MARGIN)
                {
                    addCubeX = PAD + 5 * (CUBE + MARGIN);
                    curCol = 5;
                }
                else if (x >= PAD + 6 * (CUBE + MARGIN))
                {
                    addCubeX = PAD + 6 * (CUBE + MARGIN);
                    curCol = 6;
                }

                state = MOVE;
                break;
        }// end switch
        return true;
    }
}// end class
