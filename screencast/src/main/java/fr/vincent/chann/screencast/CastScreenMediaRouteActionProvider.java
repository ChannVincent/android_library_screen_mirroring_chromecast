package fr.vincent.chann.screencast;

import android.content.Context;
import androidx.mediarouter.app.MediaRouteActionProvider;

public class CastScreenMediaRouteActionProvider extends MediaRouteActionProvider {

    public CastScreenMediaRouteActionProvider(Context context) {
        super(context);
    }

    @Override
    public CastScreenMediaRouteButton onCreateMediaRouteButton() {
        return new CastScreenMediaRouteButton(getContext());
    }
}
