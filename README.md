# Android-SDK
- 概論
- 導入SDK
  - Android Studio
  - Eclipse
- 更新 AndroidManifest.xml
- 卡片型原生影音廣告
  - Layout
  - 初始化SDK
  - 載入並且展示原生影音廣告
* ListView 型原生影音廣告
* 影音插頁廣告(Interstitial)

## 概論
原生廣告沒有固定的規格大小，需要透過應用程式開發者的巧思將廣告的素材重新設計與編排後融合到使用者介面之中。

原生廣告帶來 **比傳統橫幅或是插頁廣告更友善的體驗與更好的成效**；

除此之外在原本的橫幅和插頁廣告版位外，可以在更多版位擺放廣告 **創造更多收入來源。**

<TODO - Native Video Ads Pic Example>

## 導入SDK
### Android Studio
---
1. 下載最新版 SDK
2. 將 SDK 以新增 Module Dependency 的方式加入Gradle 
3. 修改 build.gradle 引入Google GMS，您的 build.gradle 最後應該看起來類似這樣：
```java
dependencies {
   compile fileTree(dir: 'libs', include: ['*.jar'])
   ...
   compile 'com.google.android.gms:play-services-ads:8.4.0'
}
```

### Eclipse
---
**目前SDK是以Library Project的方式提供**

1. 下載最新版 SDK
2. 將 SDK 的 Library Project 加入正在開發的專案
3. 依照上述的方法加入 Google Play Service 的 Library Project

## 更新 AndroidManifest.xml
1. 修改 AndroidManifest.xml 加入必要的權限
   
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
2. 加入 Google GMS Activity 和 Meta-data.
    
    ```java
<activity
   android:name="com.google.android.gms.ads.AdActivity"
   android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
   android:hardwareAccelerated="true"/>
<meta-data
   android:name="com.google.android.gms.version"
   android:value="@integer/google_play_services_version"/>
```
3. 指定 Application 入口。如以下的例子：我們指定 MyApplication.java 作為我們 application 開始執行的入口

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
### Layout
---
您可以直接套用範例專案中的 custom_video_ad_list_item.xml ，但是為了使用者體驗以及廣告成效，**強烈建議您根據 app 排版自行設計適合的廣告排版**。
```java
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
   xmlns:android="http://schemas.android.com/apk/res/android"
   android:id="@+id/custom_out_layout"
   android:layout_width="match_parent"
   android:layout_height="wrap_content">
   <ImageView android:id="@+id/native_main_image"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:background="@null"
       android:layout_alignParentLeft="true"
       android:layout_alignParentTop="true"
       android:layout_marginTop="10dp"
       android:layout_marginLeft="10dp"
       android:layout_marginRight="10dp"
       android:scaleType="fitXY"
       android:adjustViewBounds="true" />
   <com.core.adnsdk.VideoPlayer
       android:id="@+id/native_video_layout"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:layout_marginLeft="10dp"
       android:layout_marginRight="10dp"
       android:layout_marginBottom="10dp"
       android:layout_below="@id/native_main_image"
       android:layout_centerHorizontal="true" />
   <RelativeLayout
       android:id="@+id/native_video_title_block"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:layout_marginLeft="10dp"
       android:layout_marginRight="10dp"
       android:layout_alignBottom="@id/native_video_layout"
       android:background="#af000000" >
       <ImageView
           android:id="@+id/native_icon_image"
           android:layout_width="40dp"
           android:layout_height="40dp"
           android:scaleType="centerCrop" />
       <TextView
           android:id="@+id/native_title"
           android:layout_width="match_parent"
           android:layout_height="40dp"
           android:layout_centerVertical="true"
           android:gravity="center"
           android:singleLine="true"
           android:textColor="#ffffff"
           android:textSize="15dp"
           android:layout_toRightOf="@id/native_icon_image" />
   </RelativeLayout>
   <TextView android:id="@+id/native_cta"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_marginRight="3dp"
       android:layout_marginBottom="3dp"
       android:gravity="center_horizontal"
       android:layout_above="@id/native_video_title_block"
       android:layout_alignRight="@id/native_video_layout" />
   <ImageView android:id="@+id/native_loading_image"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:layout_alignParentRight="true"
       android:layout_alignParentTop="true"
       android:scaleType="centerCrop"
       android:src="@drawable/native_loading_image"/>
</RelativeLayout>
```
  文字和圖片等素材使用標準的 TextView 和 ImageView 呈現即可，但**用來播放影音廣告的元件請務必使用 com.core.adnsdk.VideoPlayer**。

<TODO - Layout example>

### 初始化SDK
---
在 Application 的進入點,呼叫 ADN.initialize 方法並且傳入 Context 和 API KEY 進行SDK的初始化。**在初始化時一定要填入正確的 API KEY ，否則無法取得線上販售的廣告獲得分潤。**

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


### 載入並且展示原生影片廣告
---

 

## ListView 型原生影片廣告


## 串接影音插頁廣告(Interstitial)


