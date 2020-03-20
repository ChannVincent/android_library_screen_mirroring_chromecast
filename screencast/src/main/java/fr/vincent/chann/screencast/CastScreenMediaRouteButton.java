package fr.vincent.chann.screencast;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.mediarouter.app.MediaRouteButton;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class CastScreenMediaRouteButton extends MediaRouteButton {
    private static final String TAG = "MediaRouteButton";
    public static final String CHOOSER_TAG =
            "android.support.v7.mediarouter:MediaRouteChooserDialogFragment";
    private static final String CONTROLLER_TAG =
            "android.support.v7.mediarouter:MediaRouteControllerDialogFragment";
    private MediaRouteSelector mSelector;

    public CastScreenMediaRouteButton(Context context) {
        this(context, null);
    }

    public CastScreenMediaRouteButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.mediaRouteButtonStyle);
    }

    public CastScreenMediaRouteButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean showDialog() {
        try {
            Activity currentActivity = getActivity();
            final FragmentManager fm = ((FragmentActivity) currentActivity).getSupportFragmentManager();
            if (!isAttachedToWindow()
                    || (fm.findFragmentByTag(CONTROLLER_TAG) != null)
                    || (fm.findFragmentByTag(CHOOSER_TAG) != null) ) {
                return false;
            }

            MediaRouter.RouteInfo route = getMediaRouter().getSelectedRoute();
            if (route.isDefault() || !route.matchesSelector(mSelector)) { // route chooser
                Intent intent = ((MediaProjectionManager)
                        currentActivity.getSystemService(Context.MEDIA_PROJECTION_SERVICE)).createScreenCaptureIntent();
                currentActivity.startActivityForResult(intent, AppCompatCastScreenActivity.SCREEN_CAPTURE_REQUEST);

                return true;
            } else { // route controller
                return super.showDialog();
            }
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e(TAG, "MediaRouteButton implementation changed ");
        }
        return false;
    }

    @Override
    public void setRouteSelector(MediaRouteSelector selector) {
        super.setRouteSelector(selector);
        mSelector = selector;
    }

    private MediaRouter getMediaRouter() throws NoSuchFieldException, IllegalAccessException {
        Field routerField =  getClass().getSuperclass().getDeclaredField("mRouter");
        routerField.setAccessible(true);
        return (MediaRouter) routerField.get(this);
    }

    private Activity getActivity() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method getActivityMethod = getClass().getSuperclass().getDeclaredMethod("getActivity");
        getActivityMethod.setAccessible(true);
        return (Activity) getActivityMethod.invoke(this);
    }

}