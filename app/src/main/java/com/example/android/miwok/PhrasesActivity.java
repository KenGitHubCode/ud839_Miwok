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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    //Create a Media Player instance that will be assigned during onclick listener
    //final MediaPlayer mp = new MediaPlayer();
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        //initialize the WordsArray
        final ArrayList<Word> words = new ArrayList<Word>();

        //Add values to Words Arry
        words.add(new Word("Nice to meet you!", "hajimemashita"));
        words.add(new Word("[your name] to moshimasu", "I'm [your name]"));

        //intialize the adapter
        WordAdapter itemAdapter = new WordAdapter(this, words, R.color.category_phrases);

        //initialize the view based on XML
        ListView listView = (ListView) findViewById(R.id.list);

        //assign the adapater to the view
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create class instance to reference when assigning resource
                Word selectedWord = words.get(position);
                //Assign resource based on position of clicked item and play ie start
                mp = MediaPlayer.create(getBaseContext(), selectedWord.getItemAudio());
                mp.start();

            }
        });

    }
}
