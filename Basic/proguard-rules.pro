# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/android-sdk-macosx/tools/proguard/proguard-android.txt
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


# This example uses Picasso, see: https://github.com/square/picasso/blob/master/README.md
-dontwarn com.squareup.okhttp.**

-dontwarn org.apache.http.**
-dontwarn android.net.http.**
-dontnote android.net.http.**
-dontnote org.apache.http.**
#-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.android.volley.toolbox.**

# Required by IndoorAtlas SDK
-keep public class com.indooratlas.algorithm.ClientProcessingManager { *; }
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
