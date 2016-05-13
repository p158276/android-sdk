package com.core.vmfiveadnetwork;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.core.adnsdk.AdObject;
import com.core.adnsdk.AdPoolListener;
import com.core.adnsdk.AdViewType;
import com.core.adnsdk.ErrorMessage;
import com.core.adnsdk.RecyclerAdapter;
import com.core.adnsdk.RecyclerCardAdRenderer;
import com.core.adnsdk.RecyclerCardViewBinder;

import java.util.ArrayList;
import java.util.List;

public class ExampleRecyclerView extends Activity {
    private static final String TAG = "ExampleListView";

    private RecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.native_recycler_view);

        // just for demo
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            data.add("Item " + i);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // native video layout builder
        RecyclerCardViewBinder binder = new RecyclerCardViewBinder.Builder(R.layout.card_ad_item)
                .loadingId(R.id.native_loading_image)
                .mainImageId(R.id.native_main_image)
                .titleId(R.id.native_title)
                .subTitleId(R.id.native_subtitle)
                .videoPlayerId(R.id.native_video_layout)
                .iconImageId(R.id.native_icon_image)
                .callToActionId(R.id.native_cta)
                .countDownId(R.id.native_count_down)
                .build();

        RecyclerCardAdRenderer renderer = new RecyclerCardAdRenderer(binder);

        final Context context = getApplication();
        // create NativeAdAdapter, and given original adapter of user
        // given a placement tag for different advertisement section
        mRecyclerAdapter = new RecyclerAdapter(this, recyclerView, new DemoAdapter(data), "placement(recycler)");
        mRecyclerAdapter.setAdRenderer(renderer, AdViewType.CARD_VIDEO); // for Video type
        mRecyclerAdapter.setTestMode(true); // for testing
        mRecyclerAdapter.setFrequency(1, 3);
        /**
         * Users are also capable of using {@link com.core.adnsdk.AdPoolListenerAdapter}, default adapter design pattern of AdPoolListener, to receive notification.
         * Therefore, users can focus on specific events they care about.
         */
        mRecyclerAdapter.setAdListener(new AdPoolListener() {
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
            public void onAdImpressed() {

            }
        });
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    protected void onPause() {
        mRecyclerAdapter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mRecyclerAdapter.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mRecyclerAdapter.onDestroy();
        mRecyclerAdapter = null;
        super.onDestroy();
    }

    // original adapter of user
    class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ItemViewHolder> {
        private final List<String> data;

        public DemoAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            ItemViewHolder holder = new ItemViewHolder(v);
            holder.text = (TextView) v;
            return holder;
        }

        @Override
        public void onBindViewHolder(ItemViewHolder itemVH, int i) {
            switch (itemVH.getItemViewType()) {
                case 0:
                    itemVH.text.setText(data.get(i));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView text;

            public ItemViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
