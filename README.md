# Android-SDK
- 概論
- 導入SDK
  - Android Studio
  - Eclipse
- 更新 AndroidManifest.xml
- 原生廣告Layout
- 初始化SDK
- 載入並且展示原生廣告
- 在ListView載入與展示原生廣告
- 串接影音插頁廣告(Interstitial)

## 概論
原生廣告沒有固定的規格大小，需要透過應用程式開發者的巧思將廣告的素材重新設計與編排後融合到使用者介面之中。

原生廣告帶來 **比傳統橫幅或是插頁廣告更友善的體驗與更好的成效**；

除此之外在原本的橫幅和插頁廣告版位外，可以在更多版位擺放廣告 **創造更多收入來源。**

<TODO - Native Video Ads Example>

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

## 原生廣告Layout


## 初始化SDK


## 載入並且展示原生廣告
 

## 在ListView載入與展示原生廣告


## 串接影音插頁廣告(Interstitial)


