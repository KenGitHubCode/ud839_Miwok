package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by redne on 3/27/2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {


    private static final String LOG_TAG = WordAdapter.class.getSimpleName();
    private int viewBGColor;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param Words   A List of Word objects to display in a list
     */
    public WordAdapter(Activity context, ArrayList<Word> Words, int myBGColor) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, Words);
        viewBGColor = myBGColor;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        /*  JAPANESE WORD ***********************************/
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView jTextView = (TextView) listItemView.findViewById(R.id.listItemJapan);
        // Get the version name from the current Word object and set this text on the name TextView
        jTextView.setText(currentWord.getjWord());


        /* ENGLISH WORD ***********************************/
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView eTextView = (TextView) listItemView.findViewById(R.id.listItemEn);
        // Get the version number from the current Word object and set this text on the number TextView
        eTextView.setText(currentWord.getWord());

        /* IMAGE FOR ITEM ***********************************/
        // Find the imageview in list_item.xml
        ImageView myImageView = (ImageView) listItemView.findViewById(R.id.myImage);
        if (currentWord.hasImage()) {
            // Get the image resource and set it to the image view
            myImageView.setImageResource(currentWord.getItemImage());
        } else {
            //if there is no valid image ID
            myImageView.setVisibility(View.GONE);
        }
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView


        //backgorund
        View textContainer = listItemView.findViewById(R.id.wordsLayout);
        View playButtonContainter = listItemView.findViewById(R.id.play_button);
        //find the value of the color through below single line
        int color = ContextCompat.getColor(getContext(), viewBGColor);
        textContainer.setBackgroundColor(color);
        playButtonContainter.setBackgroundColor(color);
        return listItemView;
    }
}
