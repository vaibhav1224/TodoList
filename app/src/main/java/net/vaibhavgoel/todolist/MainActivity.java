package net.vaibhavgoel.todolist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.vaibhavgoel.todolist.database.TaskContract;
import net.vaibhavgoel.todolist.database.TaskdbHelper;

import java.util.ArrayList;

import static android.R.attr.checked;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TaskdbHelper mHelper;
    private ListView mTaskListView;
    private CustomArray mAdapter;

    private void updateUI() {
        ArrayList<TDClass> taskList = new ArrayList<TDClass>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sortOrder =
                TaskContract.TaskEntry.COL_TASK_COLOR + " DESC";
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_COLOR},
                null, null, null,null, sortOrder);
        while (cursor.moveToNext()) {
            //int idx =
            String task = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE));
            int prioriy = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_COLOR)));
           TDClass newtask = new TDClass(task,prioriy);

            taskList.add(newtask);
        }

        if (mAdapter == null) {
            mAdapter = new CustomArray(this,taskList);

            mTaskListView.setAdapter(mAdapter);


        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mHelper = new TaskdbHelper(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sortOrder =
                TaskContract.TaskEntry.COL_TASK_COLOR + " DESC";
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.COL_TASK_COLOR},
                null, null, null, null, sortOrder);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
             Log.d(TAG, "Task: " + cursor.getString(idx));
            updateUI();
        }
        cursor.close();
        db.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
        Toast.makeText(getApplicationContext(),"Task Deleted successfully!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);

              final RadioGroup RAD= new RadioGroup(this);


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.prioriy, null))
//

                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String task = String.valueOf(taskEditText.getText());

                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                int selectedId = RAD.getCheckedRadioButtonId();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                if(selectedId == R.id.high)
                                {Log.d(TAG, "Task: " + "3");
                                    values.put(TaskContract.TaskEntry.COL_TASK_COLOR, "3");
                                }
                                else if(selectedId == R.id.med)
                                {Log.d(TAG, "Task: " + "2");
                                    values.put(TaskContract.TaskEntry.COL_TASK_COLOR, "2");
                                }
                                else { Log.d(TAG, "Task: " + "3");
                                    values.put(TaskContract.TaskEntry.COL_TASK_COLOR, "1");
                                }

                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                          updateUI();
                                Toast.makeText(getApplicationContext(),"Task added successfully!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                builder.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
