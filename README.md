# Android-SDK
- [概論](https://github.com/applauseadn/android-sdk/blob/master/README.md#概論) 
- [導入 SDK](https://github.com/applauseadn/android-sdk/blob/master/README.md#導入-sdk)
  - [Android Studio](https://github.com/applauseadn/android-sdk/blob/master/README.md#android-studio)
  - [Eclipse](https://github.com/applauseadn/android-sdk/blob/master/README.md#eclipse)
- [更新 AndroidManifest.xml](https://github.com/applauseadn/android-sdk/blob/master/README.md#更新-androidmanifestxml)
- [卡片型原生影音廣告](https://github.com/applauseadn/android-sdk/blob/master/README.md#卡片型原生影音廣告)
  * [Layout](https://github.com/applauseadn/android-sdk/blob/master/README.md#layout)
  * [初始化 SDK](https://github.com/applauseadn/android-sdk/blob/master/README.md#初始化sdk)
  * [載入並且展示原生影音廣告](https://github.com/applauseadn/android-sdk/blob/master/README.md#載入並且展示原生影片廣告)
* [ListView 型原生影音廣告](https://github.com/applauseadn/android-sdk/blob/master/README.md#listview-型原生影片廣告)
* [影音插頁廣告(Interstitial)](https://github.com/applauseadn/android-sdk/blob/master/README.md#影音插頁廣告interstitial)

## 概論
原生廣告沒有固定的規格大小，需要透過應用程式開發者的巧思將廣告的素材重新設計與編排後融合到使用者介面之中。

原生廣告帶來 **比傳統橫幅或是插頁廣告更友善的體驗與更好的成效**；

除此之外在原本的橫幅和插頁廣告版位外，可以在更多版位擺放廣告 **創造更多收入來源。**

<TODO - Native Video Ads Pic Example>

## 導入 SDK
#### Android Studio
---
1. 下載最新版 SDK(https://github.com/applauseadn/android-sdk/releases)
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
---

1. 下載最新版 SDK
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
2. 加入 ```Google GMS Activity``` 和 ```Meta-data```
    
    ```java
<activity
   android:name="com.google.android.gms.ads.AdActivity"
   android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
   android:hardwareAccelerated="true"/>
<meta-data
   android:name="com.google.android.gms.version"
   android:value="@integer/google_play_services_version"/>
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

## 卡片型原生影音廣告
#### Layout
---
您可以直接套用範例專案中的 ```custom_video_ad_list_item.xml``` ，但是為了使用者體驗以及廣告成效，**強烈建議您根據 app 排版自行設計適合的廣告排版**。
<TODO - Layout example>

文字和圖片等素材使用標準的 ```TextView``` 和 ```ImageView``` 呈現即可，但**用來播放影音廣告的元件請務必使用 ```com.core.adnsdk.VideoPlayer```**。

另外需要注意的是 -  CTA 文字需要設定 ```Background``` 屬性，**好提高廣告成效並取得更佳的分潤**。
例如我們的範例 App 就在 ```native_video_cta_border.xml``` 為 ```CTA Text``` 加上了邊框且指定 ```android:background="@drawable/native_video_cta_border"```。



#### 初始化SDK
---
在 ```Application``` 的進入點,呼叫 ```ADN.initialize``` 方法並且傳入 ```Context``` 和 ```API KEY``` 進行 SDK 的初始化。
**在初始化時一定要填入正確的 ```API KEY``` ，否則無法取得線上販售的廣告獲得分潤。**

```java
    public class MyApplication extends Application {
       @Override
       public void onCreate() {
           super.onCreate();
           /**
           initialize(Context context, String YOUR_APIKEY);
            * context: Application Context
            * apiKey: 向後台請求廣告需要用的字串
           */
           ADN.initialize(this, "55e7b3df0cfbf7393bb00af1");
       }
    }
```

#### 載入並且展示原生影片廣告
---
開始撰寫代碼之前，需要先引入以下的物件，完整的程式碼請參考 ```ExampleNative.java```

```java
import com.core.adnsdk.AdCustom;
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
    CardViewBinder vBinder = new CardViewBinder.Builder(R.layout.card_ad_item)
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
    CardAdRenderer vRenderer = new CardAdRenderer(vBinder);
    ```
    
3. 創建 ```AdCustom``` 物件 
    ```java
    AdCustom(Activity activity, String placeName, CardAdRenderer renderer, ViewGroup parent)
    ```
    * ```activity```：Activity context
    * ```placement```：任意字串。此字串會傳送到後台，可利用此字串在後台查詢廣告數據與收益
    * ```renderer```：CardAdRenderer 物件
    * ```parent```：要包含原生廣告 View 的 ViewGroup
    
    範例：
    ```java
    mNativeAd = new AdCustom(this, "placement(custom)", vRenderer, mainContainer);
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
  }
    ```
5. 設定測試模式

    當打開測試模式的時候，SDK 會接受到測試用的廣告。測試廣告並沒有分潤，因此**測試完成後 App 上線前請一定要關閉測試模式。(設成 false )**
    ```java
    mNativeAd.setTestMode(true)
    ```

6. 呼叫 ```loadAd``` 載入廣告
    ```java
    mNativeAd.loadAd();
    ```
7. 在 ```LifeCycle``` 的函式中，呼叫對應的 ```AdCustom``` 的 ```LifeCycle``` 方法避免內存洩漏
    ```java
    @Override
      protected void onResume() {
         mNativeAd.onResume();
         super.onResume();
      }
      
      @Override
      protected void onPause() {
         mNativeAd.onPause();
         super.onPause();
      }
      
      @Override
      protected void onDestroy() {
         mNativeAd.onDestroy();
         super.onDestroy();
      }
    ```
    
## ListView 型原生影片廣告
開始撰寫代碼之前，需要先引入以下的物件，完整的程式碼請參考 ```ExampleNative.java```

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
    CardViewBinder vBinder = new CardViewBinder.Builder(R.layout.custom_video_ad_list_item)
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
      CardAdRenderer vRenderer = new CardAdRenderer(vBinder);
    ```
3. 建立一個 ```NativeAdAdapter``` 物件

    傳入 ```Context``` ，要插入原生廣告的 ```ListView``` 和 ```Adapter``` ，以及一個任意的字串。這個字串會在後台顯示作為廣告版位的 TAG；當在很多不同的版位插入廣告的時候，就可以利用版位的 TAG 觀察與分析各個版位的廣告收益。

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
   void onAdLoaded(int index,AdObject adObject); // 廣告完成載入
   void onError(int index,ErrorMessage err); // SDK出現錯誤
   void onAdClicked(int index,); //廣告被點擊
   void onAdFinished(int index,); //廣告點擊完成跳轉後
   void onAdReleased(int index,); //廣告完成卸載並且釋放所有資源
   boolean onAdWatched(int index,); //影片播放完畢,要自動載入下一檔廣告請回傳true
   void onAdImpressed(); //廣告曝光
}
    ```

## 影音插頁廣告(Interstitial)

1. 在開始撰寫程式碼之前,請先在 ```AndroidManifest.xml``` 中宣告插頁廣告的 ```Actitivity ```
    * 直屏的 ```Activity```：
  
    ```java
    <activity
   android:name="com.core.adnsdk.InterstitialActivity"
   android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
   android:hardwareAccelerated="true">
    </activity>
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
2. 創建 ```adInterstitial``` 物件，需要傳入三個參數: Context, 一個任意字串 , 以及指定廣告類型為
 ```AdInterstitialType.INTERSTITIAL_VIDEO ```
  
  ```java
    adInterstitial = new AdInterstitial(context,
    "placement(interstitial_admob)",  AdInterstitialType.INTERSTITIAL_VIDEO) ;
    ```
2. 設定測試模式 - 在測試時請開啟測試模式，**測試完成上線前請務必設定成 false 關閉測試模式以免無法取得分潤.**
 ```adInterstitial.setTestMode(true); ```
3. 實作 ```AdListener()```，各個 ```callback``` 的定義如下：

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
4. 載入廣告，載入完成後 SDK 會呼叫 ```onAdLoaded```

 ```adInterstitial.loadAd(); ```
5. 確定廣告已經載入完成後(可用 ```onAdLoaded``` 追蹤)後，展示廣告

 ```adInterstitial.showAd(); ```
6. 處理插頁廣告的 Life Cycle，釋放資源
  ```java
  @Override
  public void onResume() {
     if (adInterstitial != null) {
         adInterstitial.onResume();
     }
  }
  
  @Override
  public void onPause() {
     if (adInterstitial != null) {
         adInterstitial.onPause();
     }
  }
  
  @Override
  public void onDestroy() {
     if (adInterstitial != null) {
         adInterstitial.onDestroy();
         adInterstitial = null;
     }
  }
    ```
