package com.example.magic;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.appsearch.AppSearchManager;
import android.app.appsearch.SetSchemaRequest;
import android.database.Cursor;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.service.autofill.OnClickAction;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

        private static final int REQ_CODE_SPEECH_INPUT = 100;
        private TextView mVoiceInputTv;
        private ImageView mSpeakBtn;
    private TextToSpeech textToSpeech;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
                mSpeakBtn = (ImageView) findViewById(R.id.imageview);
                mSpeakBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                                startVoiceInput();
                        }
                });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {

                    // if No error is found then only it will run
                    if(i!=TextToSpeech.ERROR){
                        // To Choose language of speech
                        textToSpeech.setLanguage(Locale.ENGLISH);
                    }
                }
            });
        }

        private void startVoiceInput() {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");


                try {
                        startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {

                }


        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);



                   switch (requestCode) {
                        case REQ_CODE_SPEECH_INPUT: {
                                if (resultCode == RESULT_OK && null != data) {

                                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                                        mVoiceInputTv.setText(result.get(0));
                                }
                                if (mVoiceInputTv.getText().toString().equals("activity")) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                        startActivity(intent);
                                }

                                if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open camera")) {
                                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivity(camera);


                                }
                                if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open Google")) {

                                        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);

                                        startActivity(search);
                                    String text = "    WELCOME TO GOOGLE";
                                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);



                                }
                                if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open video")) {


                                        Intent intent = new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
                                        if (intent.resolveActivity(getPackageManager()) != null) {
                                                startActivity(intent);
                                        }
                                }
                                if (mVoiceInputTv.getText().toString().equalsIgnoreCase("play song")) {

                                        String title = ("song");

                                        Intent intent = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
                                        intent.putExtra(MediaStore.EXTRA_MEDIA_FOCUS,
                                                MediaStore.Audio.Artists.ENTRY_CONTENT_TYPE);
                                        intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE, title);
                                        intent.putExtra(SearchManager.QUERY, title);
                                        if (intent.resolveActivity(getPackageManager()) != null) {
                                                startActivity(intent);
                                                Intent search = new Intent("android.intent.extra.genre");
                                                startActivity(search);
                                        }
                                }

                        }

                        if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open gallery")) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setType("image/*");
                                startActivity(intent);
                            String text = "    WELCOME TO GALLERY";
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);



                        }
                   }
                     if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open YouTube")) {


                        Intent search = new Intent(MediaStore.INTENT_ACTION_MEDIA_SEARCH);

                        startActivity(search);
                         String text = "    WELCOME TO YOUTUBE";
                         textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);



                     }
                    String url = "https://www.youtube.com/watch?v=_B6T8O15Ohk&list=RDCLAK5uy_n9Fbdw7e6ap-98_A-8JYBmPv64v-Uaq1g";

                    if (mVoiceInputTv.getText().toString().equalsIgnoreCase("play music")) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);

                        }
                   }
                   String ur = "https://uni.dbatuapps.in/itxlogin";

                    if (mVoiceInputTv.getText().toString().equalsIgnoreCase("University login")) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(ur));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            String text = "    WELCOME TO DBATU UNIVERSITY";
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);


                        }


                    }


                       if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open contact")) {


                        Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);

                        startActivityForResult(intent, 1);

                     }


                      if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open instagram")) {

                        String u = "http://instagram.com";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(u));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            String text = "    WELCOME TO INSTAGRAM";
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);


                        }
                       }

                      if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open facebook")) {

                        String u = "http://facebook.com";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(u));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            String text = "    WELCOME TO FACEBOOK";
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);


                        }
                    }
                      if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open Snapchat")) {

                        String u =  "https://snapchat.com/add";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(u));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            String text = "    WELCOME TO SNAPCHAT";
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);


                        }
                    }
                      if (mVoiceInputTv.getText().toString().equalsIgnoreCase("open Whatsapp")) {

                        String u =  "https://wa.me/WH";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(u));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            String text = "    WELCOME TO WHATSAPP";
                            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);


                         }
                      }
                     if (mVoiceInputTv.getText().toString().equalsIgnoreCase("HELLO")) {

                         String text = "    HOW ARE YOU";
                         textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

                      }
                      if (mVoiceInputTv.getText().toString().equalsIgnoreCase("BHAVIK")) {

                          String text = "    WISH YOU MANY MANY HAPPY RETURN OF THE DAY GOD BLESS YOU HAPPY BIRTHDAY BHAVIK ";
                         textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                     }




    }
}




