package com.core.vmfiveadnetwork;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.core.adnsdk.AdListener;
import com.core.adnsdk.AdObject;
import com.core.adnsdk.ErrorMessage;
import com.core.adnsdk.AdViewType;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner;
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener;
import com.core.adnsdk.TimeUnit;
import com.core.adnsdk.AdView;

/**
 * Created by yangmingyi on 15/10/16.
 */
public class AdMobBanner implements CustomEventBanner {
    private final static String TAG = "AdMobBanner";

    private AdView mAdView;

    @Override
    public void requestBannerAd(final Context context, final CustomEventBannerListener customEventBannerListener,
                                String serverParameter, com.google.android.gms.ads.AdSize adSize, MediationAdRequest mediationAdRequest, Bundle bundle) {

        AdViewType adViewType = AdViewType.BANNER_VIDEO;

        if (mAdView == null) {
            mAdView = new AdView((Activity)context, "placement(banner_admob)", adViewType);
        }

        mAdView.setTestMode(true);
        mAdView.setRotationTimeUnit(TimeUnit.STOP);
        /**
         * Users are also capable of using {@link com.core.adnsdk.AdListenerAdapter}, default adapter design pattern of AdListener, to receive notification.
         * Therefore, users can focus on specific events they care about.
         */
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded(AdObject obj) {
                Log.i(TAG, "onAdLoaded(" + obj + ")");
                customEventBannerListener.onAdLoaded(mAdView);
            }

            @Override
            public void onError(ErrorMessage err) {
                Log.i(TAG, "onError : " + err);
                switch (err) {
                    case GENERIC:
                        customEventBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                        break;
                    case NOADS:
                        customEventBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                        break;
                    case RESOURCES_DOWNLOAD_FAIL:
                        customEventBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NETWORK_ERROR);
                        break;
                    case NOTVISIBLE:
                        customEventBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                        break;
                    default:
                        customEventBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
                }
            }

            @Override
            public void onAdClicked() {
                Log.i(TAG, "onAdClicked!");
                customEventBannerListener.onAdClicked();
            }

            @Override
            public void onAdFinished() {
                customEventBannerListener.onAdOpened();
            }

            @Override
            public void onAdReleased() {
                customEventBannerListener.onAdClosed();
            }

            @Override
            public boolean onAdWatched() {
                Log.d(TAG, "onAdWatched.");
                return false;
            }

            @Override
            public void onAdImpressed() {

            }
        });
        mAdView.loadAd();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.onDestroy();
            mAdView = null;
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.onPause();
        }
    }

    @Override
    public void onResume() {
        if (mAdView != null) {
            mAdView.onResume();
        }
    }
}
