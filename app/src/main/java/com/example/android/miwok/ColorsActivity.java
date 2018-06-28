/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    //Create a Media Player instance that will be assigned during onclick listener
    //final MediaPlayer mp = new MediaPlayer();
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        //initialize the WordsArray
        final ArrayList<Word> words = new ArrayList<Word>();
        //Add values to Words Arry
        words.add(new Word("black", "kuro", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("red", "aka", R.drawable.color_red, R.raw.color_red));

        //initialize the view based on XML
        ListView listView = (ListView) findViewById(R.id.list);
        //intialize the adapter
        WordAdapter itemAdapter = new WordAdapter(this, words, R.color.category_colors);
        //assign the adapater to the view
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create class instance to reference when assigning resource
                Word selectedWord = words.get(position);
                // Release media player before assigning new media
                releaseMediaPlayer();
                //Assign resource based on position of clicked item and play ie start
                mp = MediaPlayer.create(getBaseContext(), selectedWord.getItemAudio());

                //SetOnCompletionListen to ivoke release after playback to reduce memory usage
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        releaseMediaPlayer();
                    }
                });

                //Play the mediaplayer
                mp.start();
            }
        }); // END setOnItemClickListener

    } // END onCreate

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mp.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mp = null;
        }
    } // END releaseMediaPlayer


}