# Chromecast screen mirroring integration

## Resume

It is a very specific developement.  
To start using this library you need a chromecast link to your TV and a phone, both on the same Wifi.  
(cf. see Google Home application to configure your chromecast TV)  
Then, you can start screen mirroring your phone on your chromecast TV.  

## Understand

There is 2 types of chromecast :
- *remote control mode* : you send a one time signal with an url and your chromecast TV will connect to the link through an TV app (netflix, youtube, ...) and start streaming your content from that url
- *screen mirroring mode* : you start a connection with your chromecast TV and your phone will start streaming a copy of your screen.

This library is about the screen mirroring functionnality.
But first pay attention to configuration :
- first, make sure you have a chromecast connected to your TV  
- second, configure your chromcast with Google Home application
- third, make sure your chromcast and your phone are on the same wifi
- forth, test *screen mirroring* with Google Home application (left panel > cast screen or audio > cast screen button)
- finally, implement my library to copy the functionnality

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
