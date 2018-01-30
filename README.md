# Chromecast screen mirroring integration

## Resume

It is a very specific developement.
To start using this library you need a chromecast link to your TV and a phone, both on the same Wifi.
(cf. see Google Home application to configure your chromecast TV)
Then, you can start screen mirroring your phone on your chromecast TV.

## Import library 

```xml
   dependencies {
      compile 'fr.vincent.chann:screencast:1.0.0'
   }
```

## Integration

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
