# SmartyStreets Java SDK - ProGuard/R8 rules
# Preserve all SDK classes for Jackson deserialization on Android.
# Jackson uses reflection to instantiate model classes and access fields/getters.
# R8 obfuscation breaks this without these rules.

-keep class com.smartystreets.api.** { *; }
-keepattributes *Annotation*
