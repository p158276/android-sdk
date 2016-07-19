# Android-SDK
- [概論](https://github.com/applauseadn/android-sdk/blob/master/README.md#概論) 
- [導入 SDK](https://github.com/applauseadn/android-sdk/blob/master/README.md#導入-sdk)
  - [Android Studio](https://github.com/applauseadn/android-sdk/blob/master/README.md#android-studio)
  - [Eclipse](https://github.com/applauseadn/android-sdk/blob/master/README.md#eclipse)
- [更新 AndroidManifest.xml](https://github.com/applauseadn/android-sdk/blob/master/README.md#更新-androidmanifestxml)
- [廣告格式](https://github.com/applauseadn/android-sdk/blob/master/README.md#廣告格式)
  - [卡片型原生影音廣告](https://github.com/applauseadn/android-sdk/blob/master/README.md#卡片型原生影音廣告)
    * [Layout](https://github.com/applauseadn/android-sdk/blob/master/README.md#layout)
    * [初始化 SDK](https://github.com/applauseadn/android-sdk/blob/master/README.md#初始化sdk)
    * [載入並且展示原生影音廣告](https://github.com/applauseadn/android-sdk/blob/master/README.md#載入並且展示原生影片廣告)
  - [ListView/RecyclerView 型原生影音廣告](https://github.com/applauseadn/android-sdk/blob/master/README.md#listviewrecyclerview-型原生影片廣告)
  - [影音插頁廣告(Interstitial)](https://github.com/applauseadn/android-sdk/blob/master/README.md#影音插頁廣告interstitial)
  - [獎勵型廣告(Reward)](https://github.com/applauseadn/android-sdk/blob/master/README.md#獎勵型廣告Reward)
- [客製化 Renderer](https://github.com/applauseadn/android-sdk/blob/master/README.md#客製化-renderer)
- [輪播(Mediation)](https://github.com/applauseadn/android-sdk/blob/master/README.md#輪播mediation)
  - [AdMob](https://github.com/applauseadn/android-sdk/blob/master/README.md#admob)
  - [DFP](https://github.com/applauseadn/android-sdk/blob/master/README.md#dfp)
  - [MoPub](https://github.com/applauseadn/android-sdk/blob/master/README.md#mopub)
- [Unity](https://github.com/applauseadn/android-sdk/blob/master/README.md#unity)
- [問題排解](https://github.com/applauseadn/android-sdk/blob/master/README.md#問題排解)


## 概論
原生廣告沒有固定的規格大小，需要透過應用程式開發者的巧思將廣告的素材重新設計與編排後融合到使用者介面之中。

原生廣告帶來 **比傳統橫幅或是插頁廣告更友善的體驗與更好的成效**；

除此之外在原本的橫幅和插頁廣告版位外，可以在更多版位擺放廣告 **創造更多收入來源。**

<img src="https://github.com/applauseadn/android-sdk/blob/master/images/Video_Banner.png?raw=true" alt="Video_Banner" width="216" height="384"">
<img src="https://github.com/applauseadn/android-sdk/blob/master/images/Video_Interstitial.png?raw=true" alt="Video_Interstitial" width="216" height="384">
<img src="https://github.com/applauseadn/android-sdk/blob/master/images/Video_Card.png?raw=true" alt="Video_Card" width="216" height="384">
<img src="https://github.com/applauseadn/android-sdk/blob/master/images/Video_Native.png?raw=true" alt="Video_Native" width="216" height="384">

## 導入 SDK
#### Android Studio
----

* 自動
    1. 修改 ```build.gradle``` 引入 ```VMFiveADNSDK Maven config```, ```Google GMS```，您的 ```build.gradle``` 最後應該看起來類似這樣：
    
        ```java
        android {
            repositories {
                maven {
                    url 'https://raw.githubusercontent.com/applauseadn/android-sdk/master/VMFiveADNSDK/'
                }
            }
        }
        configurations.all {
            resolutionStrategy.cacheDynamicVersionsFor 0, 'seconds'
        }
        dependencies {
            compile fileTree(dir: 'libs', include: ['*.jar'])
            ...
            compile 'com.google.android.gms:play-services-ads:8.4.0'
            debugCompile 'com.vmfive:VMFiveADNSDK:+:debug@aar'
            releaseCompile 'com.vmfive:VMFiveADNSDK:+:release@aar'
        }
        ```
    
    > 若開發者擔心 gradle 自動更新 dependencies 會影響編譯速度, 可將 ```configurations.all``` 移除, 但因Gradle有Cache機制, 一段時間後才會更新 dependencies, 若發生底下 error message, 可使用 ```./gradlew --refresh-dependencies``` 強制更新
    
        06-21 18:18:13.568 13385-13419/com.core.vmfiveadnetwork E/CentralManager: Please update ADN SDK to the most updated version!
        06-21 18:18:13.568 13385-13419/com.core.vmfiveadnetwork E/CentralManager: Current SDK version: 2.0.1.4
        06-21 18:18:13.568 13385-13419/com.core.vmfiveadnetwork E/CentralManager: The most updated SDK version: 2.0.1.5
    
    > 也可使用[指定版本](https://github.com/applauseadn/android-sdk/tree/master/VMFiveADNSDK/com/vmfive/VMFiveADNSDK), ```'com.vmfive:VMFiveADNSDK:2.0.1.3:debug@aar' ```

* 手動
    1. [下載最新版 SDK](https://github.com/applauseadn/android-sdk/releases)
    2. 將 SDK 以新增 ```Module Dependency``` 的方式加入 ```Gradle```  <TODO -這部分可以再說清楚一點>
    3. 修改 ```build.gradle``` 引入 ```Google GMS```，您的 ```build.gradle``` 最後應該看起來類似這樣：
    
        ```java
        dependencies {
            compile fileTree(dir: 'libs', include: ['*.jar'])
            ...
            compile 'com.google.android.gms:play-services-ads:8.4.0'
        }
        ```

#### Eclipse
----

1. [下載最新版 SDK](https://github.com/applauseadn/android-sdk/releases)
2. 將 SDK 的 JAR 檔拖至 ```libs``` 目錄下
3. 加入 ```Google Play Service``` 的 ```library project```

## 更新 AndroidManifest.xml
1. 修改 ```AndroidManifest.xml``` 加入必要的權限

    ```java
    //Required permissions
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    ```

2. 加入 ```Google GMS Activity``` 和 ```Meta-data```以及因預設會有影片全屏播放功能, 需要在 AndroidManifest.xml 宣告 ```ExpandFullScreenActivity```

    ```java
    <activity
        android:name="com.google.android.gms.ads.AdActivity"
        android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
        android:hardwareAccelerated="true"/>
    <meta-data
        android:name="com.google.android.gms.version"
        android:value="@integer/google_play_services_version"/>
    <activity
            android:name="com.core.adnsdk.ExpandScreenVideoActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:hardwareAccelerated="true">
    </activity>
    ```

3. 指定 ```Application``` 入口。如以下的例子：我們指定 ```MyApplication.java``` 作為我們 ```application``` 開始執行的入口

    ```java
    <application
        android:name=".MyApplication"
        android:allowBackup="true"
        android:hardwareAccelerated="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme">
    ```
    > 若開發者請求的廣告有包含影音檔, 該 Activity / Application 需要啟用 hardwareAccelerated, 否則無法觀看影片

## 廣告格式
#### 卡片型原生影音廣告
###### Layout
------
您可以直接套用範例專案中的 [```card_ad_item.xml```](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/res/layout/card_ad_item.xml) ，但是為了使用者體驗以及廣告成效，**強烈建議您根據 app 排版自行設計適合的廣告排版**。
<TODO - Layout example>

文字和圖片等素材使用標準的 ```TextView``` 和 ```ImageView``` 呈現即可，但**用來播放影音廣告的元件請務必使用 ```com.core.adnsdk.VideoPlayer```**。

另外需要注意的是 -  CTA 文字需要設定 ```Background``` 屬性，**好提高廣告成效並取得更佳的分潤**。
例如我們的範例 App 就在 [```native_video_cta_border.xml```](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/res/drawable/native_video_cta_border.xml) 為 ```CTA Text``` 加上了邊框且指定 ```android:background="@drawable/native_video_cta_border"```。


###### 初始化SDK
------
在 ```Application``` 的進入點,呼叫 ```ADN.initialize``` 方法並且傳入 ```Context``` 和 ```API KEY``` 進行 SDK 的初始化。
**在初始化時一定要填入正確的 ```API KEY``` ，否則無法取得線上販售的廣告獲得分潤。**

```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /** 
         * initialize(Context context, String YOUR_APIKEY);
         * context: Application Context
         * apiKey: 向後台請求廣告需要用的字串
        */
        ADN.initialize(this, "55e7b3df0cfbf7393bb00af1");
    }
}
```

###### 載入並且展示原生影片廣告
---
開始撰寫代碼之前，需要先引入以下的物件，完整的程式碼請參考 [```ExampleCard.java```](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/ExampleCard.java)

```java
import com.core.adnsdk.AdView;
import com.core.adnsdk.AdListener;
import com.core.adnsdk.AdObject;
import com.core.adnsdk.CardAdRenderer;
import com.core.adnsdk.CardViewBinder;
import com.core.adnsdk.ErrorMessage;
```

1. 創建 ```CardViewBinder``` ，透過 ```CardViewBinder``` 指定廣告素材和 UI 元件的關係 <TODO -上下交換順序，然後補充是在 onCreate 裡面加入>
    * ```public final Builder loadingId(final int loadingId)```：綁定 Loading image 與 UI 元件
    * ```public final Builder titleId(final int titleId)```：綁定標題文字與 UI 元件
    * ```public final Builder subTitleId(final int subTitleId)```：綁定副標題文字與 UI 元件
    * ```public final Builder descriptionId(final int descriptionId)```：綁定描述文字與 UI 文件
    * ```public final Builder videoPlayerId(final int videoPlayerId)```：綁定影片與 Video Player
    * ```public final Builder iconImageId(final int iconImageId)```：綁定圖示與 UI 元件
    * ```public final Builder mainImageId(final int mainImageId)```：綁定圖片與 UI 元件
    * ```public final Builder callToActionId(final int callToActionId)```：綁定 CTA 文字與 UI 文件
    
    範例：
    ```java
    // native video layout builder
    CardViewBinder binder = new CardViewBinder.Builder(R.layout.card_ad_item)
        .loadingId(R.id.native_loading_image) 
        .mainImageId(R.id.native_main_image) 
        .titleId(R.id.native_title) 
        .videoPlayerId(R.id.native_video_layout) 
        .iconImageId(R.id.native_icon_image)
        .callToActionId(R.id.native_cta) 
        .build();
    ```
    
2. 創建 ```CardAdRenderer``` 物件，利用上一步的 ```CardViewBinder```

    ```java
    // set layout builder to renderer
    CardAdRenderer renderer = new CardAdRenderer(binder);
    ```
    
3. 創建 ```AdView``` 物件

    ```java
    AdView(Activity activity, String placeName, CardAdRenderer renderer, ViewGroup parent)
    ```
    * ```activity```：Activity context
    * ```placement```：任意字串。此字串會傳送到後台，可利用此字串在後台查詢廣告數據與收益
    * ```renderer```：CardAdRenderer 物件
    * ```parent```：要包含原生廣告 View 的 ViewGroup
    
    範例：
    ```java
    mNativeAd = new AdView(this, "placement(card)", renderer, mainContainer);
    ```
4. 設定並且實作 ```AdListener```：

    ```java
    public interface AdListener {
        void onAdLoaded(AdObject adObject); // 廣告完成載入
        void onError(ErrorMessage err); // SDK出現錯誤
        void onAdClicked(); //廣告被點擊
        void onAdFinished(); //廣告點擊完成跳轉後
        void onAdReleased(); //廣告完成卸載並且釋放所有資源
        boolean onAdWatched(); //影片播放完畢,要自動載入下一檔廣告請回傳true,否則回傳false
        void onAdImpressed(); //廣告曝光
    }
    ```
    > 建議使用 AdListenerAdapter, 可以只實作一部份的 event callbacks
    
5. 設定測試模式
    當打開測試模式的時候，SDK 會接受到測試用的廣告。測試廣告並沒有分潤，因此**測試完成後 App 上線前請一定要關閉測試模式。(設成 false )**

    ```java
    mNativeAd.setTestMode(true)
    ```

6. 呼叫 ```loadAd``` 載入廣告

    ```java
    mNativeAd.loadAd();
    ```

7. 在 ```LifeCycle``` 的函式中，呼叫對應的 ```AdView``` 的 ```LifeCycle``` 方法避免內存洩漏

    ```java
    @Override
    protected void onResume() {
        if (mNativeAd != null) {
            mNativeAd.onResume();
        }
        super.onResume();
    }
      
    @Override
    protected void onPause() {
        if (mNativeAd != null) {
            mNativeAd.onPause();
        }
        super.onPause();
    }
      
    @Override
    protected void onDestroy() {
        if (mNativeAd != null) {
            mNativeAd.onDestroy();
            mNativeAd = null;
        }
        super.onDestroy();
    }
    ```
    
#### ListView/RecyclerView 型原生影片廣告
開始撰寫代碼之前，需要先引入以下的物件，完整的程式碼請參考 [```ExampleListView.java```](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/ExampleListView.java), [```ExampleRecyclerView.java```](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/ExampleRecyclerView.java), 以下舉 ListView 例子說明

```java
import com.core.adnsdk.AdObject;
import com.core.adnsdk.CardAdRenderer;
import com.core.adnsdk.CardViewBinder;
import com.core.adnsdk.ErrorMessage;
import com.core.adnsdk.AdViewType;
import com.core.adnsdk.NativeAdAdapter;
import com.core.adnsdk.AdPoolListener;
```

1. 新增一個 ```CardViewBinder``` 物件，將 ```Layout``` 裡的 UI 元件 id 透過 ```CardViewBinder``` 與綁定廣告素材的關聯與規則

    範例：
    ```java
    CardViewBinder binder = new CardViewBinder.Builder(R.layout.card_ad_item)
        .loadingId(R.id.native_loading_image)
        .mainImageId(R.id.native_main_image)
        .titleId(R.id.native_title)
        .videoPlayerId(R.id.native_video_layout)
        .iconImageId(R.id.native_icon_image)
        .callToActionId(R.id.native_cta)
        .build();
    ```
2. 建立一個 ```CardAdRenderer```，並且將定義好素材與排版關聯的 ```CardViewBinder``` 傳入

    ```java
    CardAdRenderer renderer = new CardAdRenderer(binder);
    ```
    
3. 建立一個 ```NativeAdAdapter``` 物件

    傳入 ```Activity``` ，要插入原生廣告的 ```ListView``` 和 ```Adapter``` ，以及一個任意的字串。這個字串會在後台顯示作為廣告版位的 TAG；當在很多不同的版位插入廣告的時候，就可以利用版位的 TAG 觀察與分析各個版位的廣告收益。

    ```java
    public NativeAdAdapter(final Activity activity, final ListView listView, final Adapter originalAdapter, final String placement)
    ```
    * ```activity```：Activity context
    * ```listView```：要插入原生廣告的 ListView
    * ```originalAdapter```：要插入原生廣告的 adapter
    * ```placement```：任意字串；此字串會傳送到後台，可利用此字串在後台查詢廣告數據與收益。
    
    範例：
    ```java
    mAdAdapter = new NativeAdAdapter(this, listView, originalAdapter, "placement(list)")
    ```

4. 設定與實作 ```AdPoolListener```， ```AdPoolListener``` 的事件會多帶一個 ```index``` 參數表示插入廣告的位置

    ```java
    public interface AdPoolListener {
        void onAdLoaded(int index, AdObject adObject); // 廣告完成載入
        void onError(int index, ErrorMessage err); // SDK出現錯誤
        void onAdClicked(int index); //廣告被點擊
        void onAdFinished(int index); //廣告點擊完成跳轉後
        void onAdReleased(int index); //廣告完成卸載並且釋放所有資源
        boolean onAdWatched(int index); //影片播放完畢,要自動載入下一檔廣告請回傳true
        void onAdImpressed(); //廣告曝光
    }
    ```
    > 建議使用 AdPoolListenerAdapter, 可以只實作一部份的 event callbacks

5. 設定測試模式
    當打開測試模式的時候，SDK 會接受到測試用的廣告。測試廣告並沒有分潤，因此**測試完成後 App 上線前請一定要關閉測試模式。(設成 false )**

    ```java
    mAdAdapter.setTestMode(true)
    ```

6. 在 ```LifeCycle``` 的函式中，呼叫對應的 ```AdView``` 的 ```LifeCycle``` 方法避免內存洩漏

    ```java
    @Override
    public void onResume() {
        if (mAdAdapter != null) {
            mAdAdapter.onResume();
        }
        super.onResume();
    }
    
    @Override
    public void onPause() {
        if (mAdAdapter != null) {
            mAdAdapter.onPause();
        }
        super.onPause();
    }
    
    @Override
    public void onDestroy() {
        if (mAdAdapter != null) {
            mAdAdapter.onDestroy();
            mAdAdapter = null;
        }
        super.onDestroy();
    }
    ```

#### 影音插頁廣告(Interstitial)
完整的程式碼請參考 [```MainActivity.java Interstitial fragment```](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/MainActivity.java)

1. 在開始撰寫程式碼之前,請先在 ```AndroidManifest.xml``` 中宣告插頁廣告的 ```Actitivity ```
    * 直屏的 ```Activity```：
  
    ```java
    <activity
        android:name="com.core.adnsdk.InterstitialActivity"
        android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
        android:hardwareAccelerated="true"
        android:theme="@style/Theme.Transparent">
    </activity>
    ```
    
    * style/Theme.Transparent
    
    ```java
    <resources>

        <!--
            Base application theme, dependent on API level. This theme is replaced
            by AppBaseTheme from res/values-vXX/styles.xml on newer devices.
        -->
        <style name="AppBaseTheme" parent="android:Theme.Light">
            <!--
                Theme customizations available in newer API levels can go in
                res/values-vXX/styles.xml, while customizations related to
                backward-compatibility can go here.
            -->
        </style>
    
        <!-- Application theme. -->
        <style name="AppTheme" parent="AppBaseTheme">
            <!-- All customizations that are NOT specific to a particular API-level can go here. -->
        </style>
    
        <style name="Theme.Transparent" parent="android:Theme">
            <item name="android:windowIsTranslucent">true</item>
            <item name="android:windowBackground">@android:color/transparent</item>
            <item name="android:windowContentOverlay">@null</item>
            <item name="android:windowNoTitle">true</item>
            <item name="android:windowIsFloating">true</item>
            <item name="android:backgroundDimEnabled">false</item>
        </style>
    
    </resources>
    ```
    
    * 轉橫屏全螢幕播放的 ```Activity```:
  
    ```java
    <activity
        android:name="com.core.adnsdk.FullScreenVideoActivity"
        android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
        android:screenOrientation="landscape"
        android:hardwareAccelerated="true">
    </activity>
    ```
2. 創建 ```mAdInterstitial``` 物件，需要傳入三個參數: Context, 一個任意字串 , 以及指定廣告類型為

    ```AdInterstitialType.INTERSTITIAL_VIDEO ```
      
    ```java
    mAdInterstitial = new AdInterstitial(context, "placement(interstitial)",  AdInterstitialType.INTERSTITIAL_VIDEO) ;
    ```

3. 設定測試模式 - 在測試時請開啟測試模式，**測試完成上線前請務必設定成 false 關閉測試模式以免無法取得分潤.**

    ```mAdInterstitial.setTestMode(true); ```

4. 實作 ```AdListener()```，各個 ```callback``` 的定義如下：

    ```java
    public interface AdListener {
        void onAdLoaded(AdObject adObject); // 廣告完成載入
        void onError(ErrorMessage err); // SDK 出現錯誤
        void onAdClicked(); //廣告被點擊
        void onAdFinished(); //廣告點擊完成跳轉後
        void onAdReleased(); //廣告完成卸載並且釋放所有資源
        boolean onAdWatched(); //影片播放完畢，要自動載入下一檔廣告請回傳 true，否則回傳 false
        void onAdImpressed(); //廣告曝光
    }
    ```
    > 建議使用 AdListenerAdapter, 可以只實作一部份的 event callbacks

5. 載入廣告，載入完成後 SDK 會呼叫 ```onAdLoaded```

    ```mAdInterstitial.loadAd(); ```
    
6. 確定廣告已經載入完成後(可用 ```onAdLoaded``` 追蹤)後，展示廣告

    ```mAdInterstitial.showAd(); ```

7. 處理插頁廣告的 Life Cycle，釋放資源

    ```java
    @Override
    public void onResume() {
        if (mAdInterstitial != null) {
            mAdInterstitial.onResume();
        }
        super.onResume();
    }
    
    @Override
    public void onPause() {
        if (mAdInterstitial != null) {
            mAdInterstitial.onPause();
        }
        super.onPause();
    }
    
    @Override
    public void onDestroy() {
        if (mAdInterstitial != null) {
            mAdInterstitial.onDestroy();
            mAdInterstitial = null;
        }
        super.onDestroy();
    }
    ```

#### 獎勵型廣告(Reward)
完整的程式碼請參考 [```MainActivity.java Reward fragment```](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/MainActivity.java)

1. 在開始撰寫程式碼之前,請先在 ```AndroidManifest.xml``` 中宣告插頁廣告的 ```Actitivity ```
    * 橫屏的 ```Activity```：
  
    ```java
    <activity
        android:name="com.core.adnsdk.RewardActivity"
        android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
        android:screenOrientation="landscape"
        android:hardwareAccelerated="true">
    </activity>
    ```

2. 創建 ```mAdReward``` 物件，需要傳入三個參數: Context, 一個任意字串 , 以及指定廣告類型為

    ```AdInterstitialType.REWARD_VIDEO```
      
    ```java
    mAdReward = new AdReward(context, "placement(reward_video)",  AdRewardType.REWARD_VIDEO) ;
    ```

3. 設定測試模式 - 在測試時請開啟測試模式，**測試完成上線前請務必設定成 false 關閉測試模式以免無法取得分潤.**

    ```mAdReward.setTestMode(true); ```

4. 實作 ```AdListener()```，各個 ```callback``` 的定義如下：

    獎勵型廣告可透過 onAdRewarded, onAdReplayed, onAdClosed callbacks 知道獎勵結果, 被重新播放, 或是關閉
    ```java
    public interface AdRewardListener {
        void onAdLoaded(AdObject adObject); // 廣告完成載入
        void onError(ErrorMessage err); // SDK 出現錯誤
        void onAdClicked(); //廣告被點擊
        void onAdFinished(); //廣告點擊完成跳轉後
        void onAdReleased(); //廣告完成卸載並且釋放所有資源
        boolean onAdWatched(); //影片播放完畢，要自動載入下一檔廣告請回傳 true，否則回傳 false
        void onAdImpressed(); //廣告曝光
        **void onAdRewarded(String currency, double amount); //獎勵廣告**
        **void onAdReplayed(); //重新播放**
        **void onAdClosed(); //廣告被關閉**
    }
    ```
    > 建議使用 AdRewardListenerAdapter, 可以只實作一部份的 event callbacks

5. 載入廣告，載入完成後 SDK 會呼叫 ```onAdLoaded```

    ```mAdReward.loadAd(); ```
    
6. 確定廣告已經載入完成後(可用 ```onAdLoaded``` 追蹤)後，展示廣告

    ```mAdReward.showAd(); ```

7. 處理插頁廣告的 Life Cycle，釋放資源

    ```java
    @Override
    public void onResume() {
        if (mAdReward != null) {
            mAdReward.onResume();
        }
        super.onResume();
    }
    
    @Override
    public void onPause() {
        if (mAdReward != null) {
            mAdReward.onPause();
        }
        super.onPause();
    }
    
    @Override
    public void onDestroy() {
        if (mAdReward != null) {
            mAdReward.onDestroy();
            mAdReward = null;
        }
        super.onDestroy();
    }
    ```

## 客製化 Renderer

  以客製化 Card 廣告格式為例, 使用者可以修改 [CustomCardAdRenderer.java](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/renderer/CustomCardAdRenderer.java) 並將此 renderer 傳入
  
  使用者可以客製化 renderer, 例如動態創建廣告格式, 或是顯示動畫等
  
  1. 關閉全屏播放選項
  ```java
  videoLayout.setExpandEnabled(false);
  ```

  2. 關閉倒數計時選項
  ```java
  videoLayout.setCountDownEnabled(false);
  ```

## 輪播(Mediation)

  蓋版影音廣告(Video Interstitial)可支援AdMob,DFP,和Mopub輪播.原生影音廣告(Native Video Ad)目前只支援Mopub輪播. 串接前請記得在 AndroidManifest.xml 宣告權限, 及所需的 activity

#### AdMob
----
  * [Banner](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/AdMobBanner.java)
  * [Interstitial](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/AdMobInterstitial.java)

#### DFP
----
  DFP 代碼撰寫方式與 AdMob 相同
  * [Banner](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/AdMobBanner.java)
  * [Interstitial](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/AdMobInterstitial.java)

#### MoPub
----
  * [Banner](https://github.com/applauseadn/android-sdk/blob/master/VMFiveMoPubAdapter/src/VM5Banner.java)
  * [Interstitial](https://github.com/applauseadn/android-sdk/blob/master/VMFiveMoPubAdapter/src/VM5Interstitial.java)
  * [Native Static](https://github.com/applauseadn/android-sdk/blob/master/VMFiveMoPubAdapter/src/VM5NativeStatic.java)
  * [Native Video](https://github.com/applauseadn/android-sdk/blob/master/VMFiveMoPubAdapter/src/VM5NativeVideo.java)

## Unity
  串接 Card AdView 範例 [```UnityPlayerActivity```](https://github.com/applauseadn/android-sdk/blob/master/VMFiveUnity/app/src/main/java/com/vmfive/javaunitysample/UnityPlayerActivity.java)
  
  ``` java
  // 需要生成一個擺放 AdView 的 container, 最後透過 addContentView 加載到 UnityPlayer
  ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
  mUnityPlayer.currentActivity.getWindow().addContentView(mRelativeLayout, vlp);
  ```

## 問題排解
1. 影音廣告的影片, 有顯示CoverImage, 但播放影片時為黑屏

    ``` java
    /**
     * 請確認在 AndroidManifest.xml, 該版位所對應的 Activity/Application context 是否有啟用 hardwareAccelerated
    */
    android:hardwareAccelerated="true"
    ```
    
2. 廣告無法顯示素材的圖檔, 或影音廣告的影片, 無顯示CoverImage, 播放影片時也是黑屏

    ``` java
    /**
     * 請確認在 AndroidManifest.xml, 是否有增加 read/write storage permissions
    */
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    ```
    > 若開發者的 Application 為 Android M(23) 版本以上, 需要增加 request permission dialog, 可參考
    [VMFiveAdNetwork Demo App 的實現](https://github.com/applauseadn/android-sdk/blob/master/VMFiveAdNetwork/app/src/main/java/com/core/vmfiveadnetwork/MainActivity.java)
    ``` java
    // check permissions for M, if some permission denied, it would shut down activity
    checkRequiredPermissions();
    ```
    
    > 若問題仍然存在, 可能是使用者已關閉權限, 可到 Settings / Application Info / your package name / read or write permission 手動啟用

3. 如何在 release mode 印出 ADNSDK log

    ``` java
    adb logcat -c
    adb shell setprop log.tag.adnsdk VERBOSE
    adb logcat > adnsdk.log
    ```

    > 在 debug mode, 請使用 ADNSDK debug build, 預設就會印出 log
