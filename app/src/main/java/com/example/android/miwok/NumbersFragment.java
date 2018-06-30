package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    /**
     * global variables
     */
    //MEDIA PLAYER and AUDIO MANAGER CREATE: instance that will be assigned during onclick listener
    private MediaPlayer mp;
    private AudioManager mAudioManager;
    //CREATE AUDIO FOCUS STATUS  and set to default false
    private boolean mAudioFocusGranted = false;
    //context for Audio Focus methods
    private Context mContext;

    /**
     * REQUIRED empty public constructor
     */
    public NumbersFragment() {
        // REQUIRED empty public constructor
    }

    /**
     * Audio Focus management to ensure app reacts with other apps' audio appropriately
     * Code base from : https://medium.com/google-developers/how-to-properly-handle-audio-interruptions-3a13540d18fa
     */
    private AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        // Pause playback because your Audio Focus was
                        // temporarily stolen, but will be back soon.
                        // i.e. for a phone call
                        Toast.makeText(mContext, "Audio Focus LOSS and PAUSED", Toast.LENGTH_SHORT).show();
                        if (mp != null && mp.isPlaying() == true) {
                            mp.pause();
                        }
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback, because you lost the Audio Focus.
                        // i.e. the user started some other playback app
                        // Remember to unregister your controls/buttons here.
                        // And release the kra — Audio Focus!
                        // You’re done.

                        Toast.makeText(mContext, "Audio Focus LOSS and ABANDONED", Toast.LENGTH_SHORT).show();
                        releaseMediaPlayer();
                    } else if (focusChange ==
                            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Lower the volume, because something else is also
                        // playing audio over you.
                        // i.e. for notifications or navigation directions
                        // Depending on your audio playback, you may prefer to
                        // pause playback here instead. You do you.
                        if (mp != null) {
                            mp.setVolume(0.1f, 0.1f);
                        }
                        Toast.makeText(mContext, "Audio Focus LOSS and VOLUME LOWERED", Toast.LENGTH_SHORT).show();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback, because you hold the Audio Focus
                        // again!
                        // i.e. the phone call ended or the nav directions
                        // are finished
                        // If you implement ducking and lower the volume, be
                        // sure to return it to normal here, as well.
                        if (mp != null) {
                            mp.setVolume(1f, 1f);
                        }
                        requestAudioFocus();
                        Toast.makeText(mContext, "Audio Focus GAINED and VOLUME returned to normal", Toast.LENGTH_SHORT).show();
                        mp.start();
                    }
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //setcontentview not needed in fragment per instructions FYI
//        setContentView(R.layout.activity_numbers);

        //Set parent for naivation in master detail format
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // assign the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //set context
        mContext = getActivity().getApplicationContext();

        // words ArrayList initialize, using ArrayList for variable size array
        final ArrayList<Word> words = new ArrayList<Word>();
        //Add values to the ArrayList Word for each
        words.add(new Word("one", "一", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "二", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "三", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "四", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "五", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "六", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "七", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "八", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "九", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "十", R.drawable.number_ten, R.raw.number_ten));

        //initialize itemsAdapter using words ArrayList
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);
        //Initialize listView as the list View from the applicable xml file
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        //set the adapter for listView (which is "list" view in the applicable xml) to itemsView using words
        listView.setAdapter(adapter);


        /**
         *  set on item click listener block
         *  creates Variable of clicked item, releases media player, requests Audio focus,
         *  assigned media resource to player and starts
         * listens for completion of media player and then releases
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create class instance to reference when assigning resource
                Word selectedWord = words.get(position);

                // RELEASE media player before assigning new media
                releaseMediaPlayer();

                //request Audio FOCUS before playing
                if (requestAudioFocus()) {
                    //ASSIGN RESOURCE based on position of clicked item and play ie start
                    mp = MediaPlayer.create(getActivity(), selectedWord.getItemAudio());
                    //PLAY the mediaplayer
                    mp.start();
                    //RELEASE: SetOnCompletionListen to ivoke RELEASE after playback to reduce memory usage
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });
                }

            }
        }); // END setOnItemClickListener

        // Inflate the layout for this fragment
        return rootView;
    } // End OnCreateView

    /**
     * Audio Focus request
     * Code base from : https://gist.github.com/kuccello/5816882
     */
    private boolean requestAudioFocus() {
        if (!mAudioFocusGranted) {

            // Request audio focus for play back
            int result = mAudioManager.requestAudioFocus(afChangeListener,
                    // Use the music stream.
                    AudioManager.STREAM_MUSIC,
                    // Request permanent focus.
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mAudioFocusGranted = true;
            } else {
                // FAILED
                Log.e("Error", ">>>>>>>>>>>>> FAILED TO GET AUDIO FOCUS <<<<<<<<<<<<<<<<<<<<<<<<");
            }
        }
        return mAudioFocusGranted;
    }

    /**
     * Audio Focus abandon for when focus is lost
     * Code base from : https://gist.github.com/kuccello/5816882
     */
    private void abandonAudioFocus() {
        AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int result = am.abandonAudioFocus(afChangeListener);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mAudioFocusGranted = false;
        } else {
            // FAILED
            Log.e("Error",
                    ">>>>>>>>>>>>> FAILED TO ABANDON AUDIO FOCUS <<<<<<<<<<<<<<<<<<<<<<<<");
        }
        afChangeListener = null;
    }

    /**
     * OnStop now only Cleans up the media player by releasing its resources.
     */
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

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
        abandonAudioFocus();
    } // END releaseMediaPlayer
}

