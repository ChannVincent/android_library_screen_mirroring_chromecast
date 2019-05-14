package fr.vincent.chann.screencast;

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteChooserDialogFragment;
import android.support.v7.app.MediaRouteDialogFactory;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.util.DisplayMetrics;

import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;

/**
 * Created by vincentchann on 30/01/2018.
 */

public class AppCompatCastScreenActivity extends AppCompatActivity {

    public static final int SCREEN_CAPTURE_REQUEST = 9000;
    private static final String YOUR_APP_ID = "B6C540F3";
    private static final String CHOOSER_TAG = "android.support.v7.mediarouter:MediaRouteChooserDialogFragment";
    private static final String CONTROLLER_TAG = "android.support.v7.mediarouter:MediaRouteControllerDialogFragment";
    private String mAppId;
    private MediaRouteSelector mSelector;
    private MediaRouter mRouter;
    private CastScreenMediaRouteActionProvider mProvider;
    private int mPermissionsResultCode;
    private Intent mPermissionsData;
    private MediaRouterCallback mCallback;
    private MediaRouteDialogFactory mDialogFactory = MediaRouteDialogFactory.getDefault();

    /**
     * Main methods
     */

    protected void startChromeCast() {
        if (mSelector == null) {
            prepareCast();
        }
        try {
            MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), SCREEN_CAPTURE_REQUEST);
        }
        catch (Exception e) {

        }
    }

    protected void stopChromeCast() {
        try {
            CastScreenService.stop();
            onChromeCastEnabled(false);
        }
        catch (Exception e) {

        }
    }

    protected void onChromeCastEnabled(boolean enabled) {
        // TODO callback
    }

    /**
     * Life Cycle
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCREEN_CAPTURE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                mPermissionsResultCode = resultCode;
                mPermissionsData = data;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialogFactory();
                    }
                }, 500);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRouter != null && mCallback != null) {
            mRouter.removeCallback(mCallback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRouter != null && mCallback != null && mSelector != null) {
            mRouter.addCallback(mSelector, mCallback, MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
        }
    }

    /**
     * 1 - PREPARE CAST BUTTON
     */

    private void prepareCast() {
        mSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast(YOUR_APP_ID)).build();

        mProvider = new CastScreenMediaRouteActionProvider(getApplication());
        mProvider.setRouteSelector(mSelector);
        mAppId = YOUR_APP_ID;

        mRouter = MediaRouter.getInstance(getApplicationContext());

        // Remove existing callback if present
        if(mCallback != null) {
            mRouter.removeCallback(mCallback);
        }
        mCallback = new MediaRouterCallback();
        mRouter.addCallback(mSelector, mCallback, MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    /**
     * 2 - START DIALOG ROUTE CHOOSER
     */

    private void showDialogFactory() {
        MediaRouteChooserDialogFragment chooser = mDialogFactory.onCreateChooserDialogFragment();
        chooser.setRouteSelector(mSelector);
        FragmentManager fm = getSupportFragmentManager();
        chooser.show(fm, CHOOSER_TAG);
    }

    /**
     * 3 - ROUTE IS SELECTED => START SCREEN CAST SERVICE
     */

    private class MediaRouterCallback extends MediaRouter.Callback {
        @Override
        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo route) {
            // start casting when the user selects a media route
            CastDevice device = CastDevice.getFromBundle(route.getExtras());
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            CastScreenService.start(getApplicationContext(),
                    mAppId,
                    metrics,
                    mPermissionsResultCode,
                    mPermissionsData,
                    device,
                    mRouter,
                    CastScreenService.makeNotification(AppCompatCastScreenActivity.this, device)
            );
            onChromeCastEnabled(true);
        }

        @Override
        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo route) {
            CastScreenService.stop();
            onChromeCastEnabled(false);
        }
    }
}
