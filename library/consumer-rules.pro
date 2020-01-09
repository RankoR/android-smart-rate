-dontobfuscate

-keepattributes MethodParameters
-keepattributes Signature

-keep class com.g2pdev.smartrate.** { *; }
-keepnames class com.g2pdev.smartrate.** { *; }

-dontwarn java.util.concurrent.Flow*