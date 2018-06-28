package com.example.android.miwok;

/**
 * Created by redne on 3/27/2018.
 *
 * */


/**
 * {@link Word} Stores and provides a single translation, from English to Japanese
 * */
public class Word {
    private String jWord;
    private String eWord;
    private int imageResourceID=-1;
    private int audioResourceID=-1;

    //constructor with two elemnets
    public Word(String jWordInput, String eWordInput){
        jWord = jWordInput;
        eWord = eWordInput;
    }

    //constructor with THREE elemnets
    public Word(String jWordInput, String eWordInput, int itemImageInput){
        jWord = jWordInput;
        eWord = eWordInput;
        imageResourceID = itemImageInput;
    }

    //constructor with THREE elemnets
    public Word(String jWordInput, String eWordInput, int itemImageInput, int itemAudioInput){
        jWord = jWordInput;
        eWord = eWordInput;
        imageResourceID = itemImageInput;
        audioResourceID = itemAudioInput;
    }

    //Sets the Japanese Word
    public void setjWord(String jWordInput){
        jWord = jWordInput;
    }
    //Sets the English Word
    public void setWord(String eWordInput){
        eWord = eWordInput;
    }
    //sets the image
    public void setItemImage(int itemImageInput) { imageResourceID = itemImageInput; }
    //sets the audio
    public void setItemAudio(int itemAudioInput) { audioResourceID = itemAudioInput; }

    //Gets the Japanese Word
    public String getjWord(){
        return jWord;
    }
    //Gets the English word
    public String getWord(){
        return eWord;
    }
    //get image
    public int getItemImage(){return imageResourceID; }
    //get image
    public int getItemAudio(){return audioResourceID; }

    //Checks if a resource has been assigned to the variable
    public boolean hasImage() {
        if (imageResourceID == -1)
            return false;
        else
            return true;
    }

    //Checks if a resource has been assigned to the variable
    public boolean hasAudio() {
        if (audioResourceID == -1)
            return false;
        else
            return true;
    }

    @Override
    public String toString() {
        return "Word{" +
                "jWord='" + jWord + '\'' +
                ", eWord='" + eWord + '\'' +
                ", imageResourceID=" + imageResourceID +
                ", audioResourceID=" + audioResourceID +
                '}';
    }
}
