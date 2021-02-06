package com.example.scafy;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.scafy.scafyFunctions.wishes;

public class MainActivity extends AppCompatActivity {

    private SpeechRecognizer recognizer;
    private TextView TvText;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Dexter.withContext(this)
//                .withPermission(Manifest.permission.RECORD_AUDIO)
//                .withListener(new PermissionListener() {
//                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
//
//                    }
//                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
//                        System.exit(0);
//                    }
//                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.INTERNET
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
//                System.exit(0);
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();

        speechToText();
        initializeTextToSpeech();
        initializeResult();
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(textToSpeech.getEngines().size()==0){
                    Toast.makeText(MainActivity.this, "TTS Engine not Available.", Toast.LENGTH_SHORT).show();
                }else {
                    speak("Hi! I'm Scafy."+wishes());
                }
            }
        });
    }

    private void speak(String s) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
        }else {
            textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void speechToText() {
        TvText = (TextView)findViewById(R.id.tv_text);
    }

    private void initializeResult() {
        if(SpeechRecognizer.isRecognitionAvailable(this)){
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int i) {

                }

                @Override
                public void onResults(Bundle bundle) {
                    ArrayList<String> result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    TvText.setText(result.get(0));
                    responseFromScafy(result.get(0));
                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });
        }
    }

    private void responseFromScafy(String s) {
        String message = s.toLowerCase();
        if(s.indexOf("hi") != -1 || s.indexOf("hello") != -1){
            speak("Hello Debeeprasad");
        }else if(s.indexOf("what is your name") != -1){
            speak("My name is Scafy.");
        }else if(s.indexOf("can you do") != -1){
            speak("I can do anything you command me. But hay, I'm still learning.");
        }else if(s.indexOf("how are you") != -1){
            speak("I'm Fine. How're you ?");
        }else if(s.indexOf("am fine") != -1){
            speak("Glad to Hear. Keep it up.");
        }else if(s.indexOf("thank") != -1){
            speak("You're Welcome");
        }else if(s.indexOf("time") != -1){
            Date date = new Date();
            String time = DateUtils.formatDateTime(this, date.getTime(), DateUtils.FORMAT_SHOW_TIME);
            speak("Now it's " + time);
        }else if(s.indexOf("day") != -1){
            Date date = new Date();
            String weekDay = DateUtils.formatDateTime(this, date.getTime(), DateUtils.FORMAT_SHOW_WEEKDAY);
            speak("It's " + weekDay);
        }else if(s.indexOf("year") != -1 || s.indexOf("date") != -1) {
            Date date = new Date();
            String year = DateUtils.formatDateTime(this, date.getTime(), DateUtils.FORMAT_SHOW_YEAR);
            speak("It's " + year);
        }
    }

    public void Microphone(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
//        speak("I'm Listening...");
        recognizer.startListening(intent);
    }
}