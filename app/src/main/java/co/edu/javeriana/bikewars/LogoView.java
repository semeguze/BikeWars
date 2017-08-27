package co.edu.javeriana.bikewars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class LogoView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_view);
        Log.v("Logger", "hola");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Log.v("Logger", "Si entro");
                lanzarApp();
            }
        }, 2000);
    }
    public void lanzarApp(){
        Intent intent = new Intent(this, LoginView.class);
        startActivity(intent);
    }
}
