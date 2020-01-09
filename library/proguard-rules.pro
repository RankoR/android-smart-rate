-dontobfuscate

-keepattributes Signature
-keepattributes *Annotation*
-keepattributes MethodParameters
-dontwarn sun.misc.**

-dontwarn java.util.concurrent.Flow*

-keep class com.g2pdev.smartrate.** { *; }
-keepnames class com.g2pdev.smartrate.** { *; }

-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}