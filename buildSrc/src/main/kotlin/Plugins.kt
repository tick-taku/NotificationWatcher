import dependencies.Dep

object Plugins {
    const val android = "com.android.tools.build:gradle:3.6.0"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dep.Kotlin.version}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Dep.AndroidX.Navigation.version}"
}
