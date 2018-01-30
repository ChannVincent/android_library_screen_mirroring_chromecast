package fr.vincent.chann.screen_mirroring;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.vincent.chann.screencast.AppCompatCastScreenActivity;

public class MainActivity extends AppCompatCastScreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonCast = (Button) findViewById(R.id.screencast);
        buttonCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScreenCast();
            }
        });

        Button stopCast = (Button) findViewById(R.id.stop);
        stopCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopScreenCast();
            }
        });
    }
}
