# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/hjd/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#忽略警告
-ignorewarning
#####################记录生成的日志数据,gradle build时在本项目根目录输出################
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
#####################记录生成的日志数据，gradle build时 在本项目根目录输出-end################
################<span></span>混淆保护自己项目的部分代码以及引用的第三方jar包library#########################
#-libraryjars libs/umeng-analytics-v5.2.4.jar
#-libraryjars libs/alipaysd<span></span>k.jar
#<span></span>-libraryjars libs/alipaysecsdk.jar
#-libraryjars libs/alipayutdid.jar
#-libraryjars libs/wup-1.0.0-SNAPSHOT.jar
#-libraryjars libs/weibosdkcore.jar
#三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
#-libraryjars libs/sdk-v1.0.0.jar
#-libraryjars libs/look-v1.0.1.jar

#如果引用了v4或者v7包
-dontwarn android.support.**
############<span></span>混淆保护自己项目的部分代码以及引用的第三方jar包library-end##################
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
#避免混淆泛型 如果混淆报错建议关掉
#-keepattributes Signature






#############################################
#
# 项目中特殊处理部分
#
#############################################

#-----------处理反射类---------------



#-----------处理js交互---------------



#-----------处理实体类---------------
 #在开发的时候我们可以将所有的实体类放在一个包内，这样我们写一次混淆就行了。
-keep public class com.dh.yunsale.entity.** {
    public void set*(***);
    public *** get*();
    public *** is*();
}


#add by superxz
#maven库
-dontwarn com.umeng.**
-keep class com.umeng.** { *; }
-keep interface com.umeng.** { *; }
-keep public class * extends com.umeng.**

-dontwarn u.aly.**
-keep class u.aly.** { *; }
-keep interface u.aly.** { *; }
-keep public class * extends u.aly.**

-dontwarn com.facebook.**
-keep class com.facebook.** { *; }
-keep interface com.facebook.** { *; }
-keep public class * extends com.facebook.**

-dontwarn android.support.**
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keep public class * extends android.support.**

-dontwarn bolts.**
-keep class bolts.** { *; }
-keep interface bolts.** { *; }
-keep public class * extends bolts.**

-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }
-keep interface com.nineoldandroids.** { *; }
-keep public class * extends com.nineoldandroids.**


-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep public class * extends okhttp3.**



-dontwarn okio.**
-keep class okio.** { *; }
-keep interface okio.** { *; }
-keep public class * extends okio.**


-dontwarn com.google.**
-keep class com.google.** { *; }
-keep interface com.google.** { *; }
-keep public class * extends com.google.**


-dontwarn com.orangegangsters.**
-keep class com.orangegangsters.** { *; }
-keep interface com.orangegangsters.** { *; }
-keep public class * extends com.orangegangsters.**

-dontwarn com.readystatesoftware.**
-keep class com.readystatesoftware.** { *; }
-keep interface com.readystatesoftware.** { *; }
-keep public class * extends com.readystatesoftware.**

-dontwarn pl.droidsonroids.gif.**
-keep class pl.droidsonroids.gif.** { *; }
-keep interface pl.droidsonroids.gif.** { *; }
-keep public class * extends pl.droidsonroids.gif.**


#maven库----end

#lib库
-dontwarn com.igexin.**
-keep class com.igexin.** { *; }
-keep interface com.igexin.** { *; }
-keep public class * extends com.igexin.**


-dontwarn com.tencent.**
-keep class com.tencent.** { *; }
-keep interface com.tencent.** { *; }
-keep public class * extends com.tencent.**


-dontwarn com.hp.hpl.sparta.**
-keep  class com.hp.hpl.sparta.** { *; }
-keep interface com.hp.hpl.sparta.** { *; }
-keep public class * extends com.hp.hpl.sparta.**

-dontwarn demo.**
-keep class demo.** { *; }
-keep interface demo.** { *; }
-keep public class * extends demo.**

-dontwarn net.sourceforge.pinyin4j.**
-keep class net.sourceforge.pinyin4j.** { *; }
-keep interface net.sourceforge.pinyin4j.** { *; }
-keep public class * extends net.sourceforge.pinyin4j.**

-dontwarn pinyindb.**
-keep class pinyindb.** { *; }
-keep interface pinyindb.** { *; }
-keep public class * extends pinyindb.**
#lib库----end
#本地反射库屏蔽
-keep public class com.dh.yunsale.R$*{
   public static final int *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}
-keepclassmembers class ** {
     public void onEvent*(**);
     void onEvent*(**);
 }

 -keepclassmembers class ** {
     public void ViewUtils*(**);
     void ViewUtils*(**);
 }

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable


-keep public class * extends com.dh.yunsale.framework.net.fgview.BaseParser
-keep class * implements java.io.Serializable
-keep class com.dh.yunsale.entity.** { *; }
-keep public class * extends com.dh.yunsale.framework.net.fgview.BaseParser { *; }
-keep class com.dh.yunsale.framework.net.fgview.ReqResBean { *; }
-keep class com.dh.yunsale.fragment.FragmentTabHost { *; }

-keep class com.dh.yunsale.xutils.view.** { *; }
-keep interface com.dh.yunsale.xutils.view.** { *; }
-keep public class * extends com.dh.yunsale.xutils.view.**

 -keep class com.dh.yunsale.event.** { *; }
-keep interface com.dh.yunsale.event.** { *; }
-keep public class * extends com.dh.yunsale.event.**

-keep class com.dh.yunsale.alibaba.fastjson.** { *; }
-keep interface com.dh.yunsale.alibaba.fastjson.** { *; }
-keep public class * extends com.dh.yunsale.alibaba.fastjson.**

-keep class org.apache.http.entity.mime.** { *; }
-keep interface org.apache.http.entity.mime.** { *; }
-keep public class * extends org.apache.http.entity.mime.**


-keepclassmembers class com.dh.yunsale.activity.ProductDetailsActivity$JsObject {
  public *;
}
-keepclassmembers class com.dh.yunsale.activity.PubWebViewActivity$JsObject {
  public *;
}
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
