# Chromecast screen mirroring integration

## Import library in your dependencies

## Code snippet


```java
   public class MainActivity extends AppCompatCastScreenActivity {
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button buttonCast = (Button) findViewById(R.id.start);
        buttonCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start cast
                startScreenCast();
            }
        });

        Button stopCast = (Button) findViewById(R.id.stop);
        stopCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop cast
                stopScreenCast();
            }
        });
    }
}
```
