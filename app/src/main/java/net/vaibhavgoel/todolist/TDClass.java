package net.vaibhavgoel.todolist;

/**
 * Created by v on 22-03-2017.
 */

public class TDClass {

    private String task;
    private int color;
    public TDClass( String Task , int Color)
    {
        task = Task;
        color = Color;
    }

    public String getTask()
    {
        return task;
    }
    public int getColor()
    {
        return color;
    }
    public void setTask(String s)
    {
        task = s;
    }
}
