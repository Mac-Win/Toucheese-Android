# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# stackTrace 재추적
-keepattributes LineNumberTable,SourceFile # 메서드에 위치 정보를 유지하여 스택 트레이스에 이러한 위치가 출력되도록 한다
# SoucreFile: 모든 잠재적 런타임에 실제로 위치 정보가 출력되도록 한다
-renamesourcefileattribute SourceFile # 스택 트레이스의 소스 파일 이름을 SourceFile로 설정 / 매핑 파일에 원본 소스 파일이 포함되어 있으므로 재추적할 때 실제 원본 소스 파일 이름은 필요하지 않습니다.