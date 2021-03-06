package com.core.vmfiveadnetwork;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.core.adnsdk.AdViewType;
import com.core.adnsdk.AdObject;
import com.core.adnsdk.CardAdRenderer;
import com.core.adnsdk.CardViewBinder;
import com.core.adnsdk.ErrorMessage;
import com.core.adnsdk.AdListener;
import com.core.adnsdk.AdView;
import com.core.adnsdk.NativeAdAdapter;
import com.core.adnsdk.AdPoolListener;

public class ExampleListViewWithBanner extends Activity {
    private static final String TAG = "ExampleListViewWithBanner";

    private NativeAdAdapter mNativeAdAdapter;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview_with_banner);

        final ListView listView = (ListView) findViewById(R.id.native_list_view);

        // original adapter of user
        final ArrayAdapter<String> originalAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);
        // just for demo
        for (int i = 0; i < 100; ++i) {
            originalAdapter.add("Item " + i);
        }

        // native video layout builder
        CardViewBinder binder = new CardViewBinder.Builder(R.layout.card_ad_item)
                .loadingId(R.id.native_loading_image)
                .mainImageId(R.id.native_main_image)
                .titleId(R.id.native_title)
                .subTitleId(R.id.native_subtitle)
                .videoPlayerId(R.id.native_video_layout)
                .iconImageId(R.id.native_icon_image)
                .callToActionId(R.id.native_cta)
                .countDownId(R.id.native_count_down)
                .build();
        // set layout builder to renderer
        CardAdRenderer renderer = new CardAdRenderer(binder);

        final Context context = getApplication();
        // create NativeAdAdapter, and given original adapter of user
        // given a placement tag for different advertisement section
        mNativeAdAdapter = new NativeAdAdapter(this, listView, originalAdapter, "placement(list_banner)");
        mNativeAdAdapter.setAdRenderer(renderer, AdViewType.CARD_VIDEO); // for Video type
        mNativeAdAdapter.setTestMode(true); // for testing
        mNativeAdAdapter.setFrequency(1, 3);
        /**
         * Users are also capable of using {@link com.core.adnsdk.AdPoolListenerAdapter}, default adapter design pattern of AdPoolListener, to receive notification.
         * Therefore, users can focus on specific events they care about.
         */
        mNativeAdAdapter.setAdListener(new AdPoolListener() {
            @Override
            public void onError(int index, ErrorMessage err) {
                Log.d(TAG, "onError : " + err);
                if (err != ErrorMessage.NOTREADY) {
                    Toast.makeText(context, "Error: " + err, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAdLoaded(int index, AdObject obj) {
                Log.d(TAG, "onAdLoaded!");
            }

            @Override
            public void onAdClicked(int index) {
                Log.d(TAG, "onAdClicked!");
            }

            @Override
            public void onAdFinished(int index) {
                Log.d(TAG, "onAdFinished.");
            }

            @Override
            public void onAdReleased(int index) {
                Log.d(TAG, "onAdReleased.");
            }

            @Override
            public boolean onAdWatched(int index) {
                Log.d(TAG, "onAdWatched.");
                return false;
            }

            @Override
            public void onAdImpressed(int index) {
                Log.d(TAG, "onAdImpressed.");
            }
        });
        // (Optional) sdk already set listener to ListView, if user want to set listener of ListView,
        // please set to NativeAdAdapter
        mNativeAdAdapter.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        // (Optional) sdk already set listener to ListView, if user want to set listener of ListView,
        // please set to NativeAdAdapter
        mNativeAdAdapter.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {

            }
        });
        // (Optional) sdk already set listener to ListView, if user want to set listener of ListView,
        // please set to NativeAdAdapter
        mNativeAdAdapter.setOnItemClickListener(new AbsListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setAdapter(mNativeAdAdapter);

        ViewGroup adLayout = (ViewGroup) findViewById(R.id.example_adlayout);

        final AdViewType type;
        type = AdViewType.BANNER_VIDEO;
        mAdView = new AdView(this, "placement(banner_video)", type, adLayout);
        /**
         * Users are also capable of using {@link com.core.adnsdk.AdListenerAdapter}, default adapter design pattern of AdListener, to receive notification.
         * Therefore, users can focus on specific events they care about.
         */
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded(AdObject obj) {
                Log.d(TAG, "onAdLoaded(" + obj + ")");
            }

            @Override
            public void onError(ErrorMessage err) {
                Log.d(TAG, "onError : " + err);
                if (err != ErrorMessage.NOTREADY) {
                    Toast.makeText(getApplication(), "Error: " + err, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "onAdClicked!");
            }

            @Override
            public void onAdFinished() {
                Log.d(TAG, "onAdFinished.");
            }

            @Override
            public void onAdReleased() {
                Log.d(TAG, "onAdReleased.");
            }

            @Override
            public boolean onAdWatched() {
                Log.d(TAG, "onAdWatched.");
                return false;
            }

            @Override
            public void onAdImpressed() {
                Log.d(TAG, "onAdImpressed.");
            }
        });

        mAdView.setTestMode(true);
        mAdView.loadAd();
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.onPause();
        }
        if (mNativeAdAdapter != null) {
            mNativeAdAdapter.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mAdView != null) {
            mAdView.onResume();
        }
        if (mNativeAdAdapter != null) {
            mNativeAdAdapter.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.onDestroy();
            mAdView = null;
        }
        if (mNativeAdAdapter != null) {
            mNativeAdAdapter.onDestroy();
            mNativeAdAdapter = null;
        }
        super.onDestroy();
    }
}
