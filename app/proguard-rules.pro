# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/liuzeren/Library/Android/sdk/tools/proguard/proguard-android.txt
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
-optimizationpasses 7  #指定代码的压缩级别 0 - 7
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontoptimize
-dontpreverify
-ignorewarnings

-keep class com.tencent.mm.sdk.** {
   *;
}

-keep class com.cloudwise.agent.app.** {*;}
-dontwarn com.cloudwise.agent.app.**
-keepattributes Exceptions, Signature, InnerClasses

-keepattributes *Annotation*
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.database.sqlite.SQLiteOpenHelper { * ; }
-keep class **.R$* { *; }  #保持R文件不被混淆，否则，你的反射是获取不到资源id的

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**


-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepclassmembers class * implements android.os.Parcelable {
      public static final android.os.Parcelable$Creator *;
}

-dontwarn java.util.**
-keep class java.util.** {*; }

-dontwarn org.apache.http.**
-keep class org.apache.http.** {*; }

-dontwarn com.tencent.**
-keep class com.tencent.** {*;}

-dontwarn com.qq.**
-keep class com.qq.** {*;}

-dontwarn com.google.**
-keep class com.google.** { *; }

-dontwarn com.android.**
-keep class com.android.** { *; }

-dontwarn android.support.**
-keep class android.support.** { *; }

-dontwarn org.apache.**
-keep class org.apache.** { *; }



##---------------Begin: proguard configuration for Gson  ----------

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature


# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
#-keep class com.google.gson.stream.** { *; }

##---------------End: proguard configuration for Gson  ----------

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}
-keep public class * implements com.coremedia.iso.boxes.Box {*;}
-keep class com.media.ffmpeg.** { *; }

#weixin
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}

# webview + js
-keepattributes *JavascriptInterface*
-keep class **.Webview2JsInterface { *; }  #保护WebView对HTML页面的API不被混淆
-keepclassmembers class * extends android.webkit.WebViewClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
     public boolean *(android.webkit.WebView,java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {  #如果你的项目中用到了webview的复杂操作 ，最好加入
     public void *(android.webkit.WebView,java.lang.String);
}

#==================gson==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}

#==================protobuf======================
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}


# # -------------------------------------------
# #  ######## glide混淆  ##########
# # -------------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keep class com.cx.pretend.**{*;}
-keep class com.cx.pluginlib.**{*;}
-keep class android.accounts.**{*;}
-keep class android.content.pm.**{*;}
-keep class android.content.IIntentReceiver.**{*;}
-keep class android.app.**{*;}

-keep public class * extends com.lidroid.xutils.**
-keep public interface org.xutils.** {*;}
-dontwarn org.xutils.**
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.os.Binder
-keep public class * extends android.os.BinderProxy
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider

-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.os.Binder
-keep public class * extends android.os.BinderProxy
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider

-keep class android.accounts.** {*;}
-keep class android.app.** {*;}
-keep class android.content.** {*;}
-keep class android.location.** {*;}
-keep class android.net.** {*;}

-keep  class cn.vszone.ko.plugin.sdk.**{*;}
-keep  class com.union.android.vsp.**{*;}
-keep  class com.vsp.framework.**{*;}
-keep  class vspmirror.**{*;}
-ignorewarnings

# okhttp
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** {*;}
-dontwarn okio.**
# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* {*;}
-keep public class java.nio.* {*;}

-dontwarn cn.emagsoftware.gamehall.okhttp.request.**
-keep class cn.emagsoftware.gamehall.okhttp.request.** {*;}

-keep class com.e9where.analysis.sdk.ApiAgent
-keep class com.e9where.analysis.sdk.common.HttpsUtils
-keep class com.e9where.analysis.sdk.CommonUtil {
    public protected <fields>;
    public protected <methods>;
}


#shareSDK
	-keep class cn.sharesdk.**{*;}
	-keep class com.sina.**{*;}
	-keep class **.R$* {*;}
	-keep class **.R{*;}
	-keep class com.mob.**{*;}
	-dontwarn com.mob.**
	-dontwarn cn.sharesdk.**
	-dontwarn **.R$*

#抛异常时保留行号
-keepattributes SourceFile,LineNumberTable


#bean
-dontwarn com.jinghan.app.mvp.model.bean.**
-keep class com.jinghan.app.mvp.model.bean.** { *; }
-dontwarn com.jinghan.app.mvp.model.request.**
-keep class com.jinghan.app.mvp.model.request.** { *; }
-dontwarn com.jinghan.app.mvp.model.response.**
-keep com.jinghan.app.mvp.model.response.** { *; }


-dontwarn cn.emagsoftware.gamehall.config.**
-keep class cn.emagsoftware.gamehall.config.** { *; }

