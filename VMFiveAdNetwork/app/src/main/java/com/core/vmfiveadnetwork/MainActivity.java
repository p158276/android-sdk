/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.core.vmfiveadnetwork;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.core.adnsdk.ADN;
import com.core.adnsdk.AdInterstitial;
import com.core.adnsdk.AdInterstitialType;
import com.core.adnsdk.AdListener;
import com.core.adnsdk.AdObject;
import com.core.adnsdk.ErrorMessage;
import com.core.adnsdk.SDKController;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
    private static final String TAG = "MainActivity";

    private static final int MENU_ABOUT = 0;
    private static final int MENU_CLEAR_RESOURCE_CACHE = 1;
    private static final int MENU_CLEAR_CONNECTION_CACHE = 2;
    private static final int MENU_EXIT = 3;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(), this);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            // Specify that the Home/Up button should not be enabled, since there is no hierarchical
            // parent.
            actionBar.setHomeButtonEnabled(false);

            // Specify that we will be displaying tabs in the action bar.
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.

                if (actionBar != null)
                    actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.

            if (actionBar != null)
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ABOUT, 0, "About");
        menu.add(0, MENU_CLEAR_RESOURCE_CACHE, 0, "Clear File Cache");
        menu.add(0, MENU_CLEAR_CONNECTION_CACHE, 0, "Clear Connection Cache");
        menu.add(0, MENU_EXIT, 0, "Exit");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case MENU_ABOUT:
                String title = "Information";
                String messages = "";
                messages += "Version Name: " + SDKController.getInstance().getSdkName() + "\n";
                messages += "Version Code: " + SDKController.getInstance().getSdkVersion() + "." + SDKController.getInstance().getSdkBuild() + "\n";
                messages += "IMEI: " + SDKController.getInstance().getDeviceInfo().getIMEI() + "\n";
                messages += "MAC addr: " + SDKController.getInstance().getDeviceInfo().getWifiMac() + "\n";
                messages += "IP addr: " + SDKController.getInstance().getDeviceInfo().getIPAddress();
                final AlertDialog alertDialog = getAlertDialog(title, messages);
                alertDialog.show();
                break;
            case MENU_CLEAR_RESOURCE_CACHE:
                ADN.clearResourceCache();
                Toast.makeText(this, "Clear resource caches.", Toast.LENGTH_LONG).show();
                break;
            case MENU_CLEAR_CONNECTION_CACHE:
                ADN.clearConnectionCache();
                Toast.makeText(this, "Clear connection caches.", Toast.LENGTH_LONG).show();
                break;
            case MENU_EXIT:
                finish();
                break;
        }
        return true;
    }

    private AlertDialog getAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return builder.create();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        private Context mContext;

        public AppSectionsPagerAdapter(FragmentManager fm, Context ctx) {
            super(fm);
            mContext = ctx;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return Banner.getInstance();
                case 1:
                    return MainActivity.Interstitial.getInstance();
                case 2:
                    return Native.getInstance();

                default:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragment = new DummySectionFragment();
                    Bundle args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                    return fragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContext.getString(R.string.banner_title);
                case 1:
                    return mContext.getString(R.string.interstitial_title);
                case 2:
                    return mContext.getString(R.string.native_title);
                default:
                    return "Section " + (position + 1);
            }
        }
    }


    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class Banner extends Fragment {

        public static Fragment getInstance() {
            return new Banner();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_banner, container, false);

            rootView.findViewById(R.id.banner_video)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ExampleBanner.class);
                            intent.putExtra("type", "video");
                            startActivity(intent);
                        }
                    });

            rootView.findViewById(R.id.card_video)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ExampleBanner.class);
                            intent.putExtra("type", "card_video");
                            startActivity(intent);
                        }
                    });

            rootView.findViewById(R.id.admob_mediation)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ExampleMediation.class);
                            intent.putExtra("type", "AdMob");
                            startActivity(intent);
                        }
                    });
            return rootView;
        }
    }

    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class Interstitial extends Fragment {

        public static Fragment getInstance() {
            return new Interstitial();
        }

        private AdInterstitial adVideoView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            adVideoView = new AdInterstitial(
                    getActivity(),
                    "placement(interstitial_video)",
                    AdInterstitialType.INTERSTITIAL_VIDEO);
            adVideoView.setTestMode(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_interstitial, container, false);

            rootView.findViewById(R.id.interstitial_video)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            /**
                             * Users are also capable of using {@link com.core.adnsdk.AdListenerAdapter}, default adapter design pattern of AdListener, to receive notification.
                             * Therefore, users can focus on specific events they care about.
                             */
                            adVideoView.setAdListener(new AdListener() {
                                @Override
                                public void onAdLoaded(AdObject obj) {
                                    Log.d(TAG, "onAdLoaded(" + obj + ")");
                                    adVideoView.showAd();
                                }

                                @Override
                                public void onError(ErrorMessage err) {
                                    Log.d(TAG, "onError : " + err + ")");
                                    Toast.makeText(view.getContext(), "Error: " + err, Toast.LENGTH_LONG).show();
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

                                }
                            });
                            adVideoView.loadAd();
                        }
                    });

            rootView.findViewById(R.id.interstitial_view)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), ExampleInterstitialView.class);
                            startActivity(intent);
                        }
                    });

            return rootView;
        }

        @Override
        public void onResume() {
            if (adVideoView != null) {
                adVideoView.onResume();
            }
            super.onResume();
        }

        @Override
        public void onPause() {
            if (adVideoView != null) {
                adVideoView.onPause();
            }
            super.onPause();
        }

        @Override
        public void onDestroy() {
            if (adVideoView != null) {
                adVideoView.onDestroy();
                adVideoView = null;
            }
            super.onDestroy();
        }
    }

    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class Native extends Fragment {

        public static Fragment getInstance() {
            return new Native();
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_native, container, false);

            rootView.findViewById(R.id.native_custom)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ExampleNative.class);
                            startActivity(intent);
                        }
                    });

            rootView.findViewById(R.id.native_listview)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ExampleListView.class);
                            startActivity(intent);
                        }
                    });

            rootView.findViewById(R.id.native_listview_with_banner)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ExampleListViewWithBanner.class);
                            startActivity(intent);
                        }
                    });

            rootView.findViewById(R.id.native_recyclerview)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ExampleRecyclerView.class);
                            startActivity(intent);
                        }
                    });

            return rootView;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_dummy, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
