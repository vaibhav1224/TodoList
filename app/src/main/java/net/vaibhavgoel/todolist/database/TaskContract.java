package net.vaibhavgoel.todolist.database;

import android.provider.BaseColumns;

/**
 * Created by v on 20-03-2017.
 */

public class TaskContract {
    public static final String DB_NAME = "com.vaibhav2.todolist.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";


        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_COLOR = "Color";
    }
}