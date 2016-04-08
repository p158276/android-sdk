# Android-SDK
---
- 概論
- 導入SDK
  - Android Studio
  - Eclipse
- AndroidManifest.xml
- 原生廣告Layout
- 初始化SDK
- 載入並且展示原生廣告
- 在ListView載入與展示原生廣告
- 串接影音插頁廣告(Interstitial)

## 概論
---
原生廣告沒有固定的規格大小，需要透過應用程式開發者的巧思將廣告的素材重新設計與編排後融合到使用者介面之中。

原生廣告帶來 **比傳統橫幅或是插頁廣告更友善的體驗與更好的成效**；

除此之外在原本的橫幅和插頁廣告版位外，可以在更多版位擺放廣告 **創造更多收入來源。**

<TODO - Native Video Ads Example>

## 導入SDK
---

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


## AndroidManifest.xml
---

## 原生廣告Layout
---

## 初始化SDK
---

## 載入並且展示原生廣告
--- 

## 在ListView載入與展示原生廣告
---

## 串接影音插頁廣告(Interstitial)
---

