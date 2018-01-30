package fr.vincent.chann.screencast;

import android.content.Context;
import android.support.v7.app.MediaRouteActionProvider;

public class CastScreenMediaRouteActionProvider extends MediaRouteActionProvider {

    public CastScreenMediaRouteActionProvider(Context context) {
        super(context);
    }

    @Override
    public CastScreenMediaRouteButton onCreateMediaRouteButton() {
        return new CastScreenMediaRouteButton(getContext());
    }
}
