package net.vaibhavgoel.todolist;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by v on 22-03-2017.
 */

public class CustomArray extends ArrayAdapter<TDClass> {
private TextView nameTextView;
    public CustomArray(Activity context, ArrayList<TDClass> tasklist) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, tasklist);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_todo, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        TDClass todotask = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
       //View v = new View(Context);

    nameTextView = (TextView) listItemView.findViewById(R.id.task_title);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(todotask.getTask());

        int priority = todotask.getColor();
        if(priority==3) {
            listItemView.setBackgroundColor(0xff8a80);
        }
        else if(priority==2)
        {
            listItemView.setBackgroundColor(0x80d8ff);
        }
        else
        {
            listItemView.setBackgroundColor(0xccff90);
        }

        // Find the TextView in the list_item.xml layout with the ID version_number

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
