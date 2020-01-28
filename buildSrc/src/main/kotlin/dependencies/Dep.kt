package dependencies

object Dep {
    object Plugin {
        val android = "com.android.tools.build:gradle:3.5.3"
        val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
        val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${AndroidX.Navigation.version}"
    }

    object Test {
        val junit = "junit:junit:4.12"
        val androidJunit = "androidx.test.ext:junit:1.1.1"
        val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
    }

    object Kotlin {
        val version = "1.3.61"
        val stdlibJdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
    }

    object AndroidX {
        val appCompat = "androidx.appcompat:appcompat:1.1.0"
        val coreKtx = "androidx.core:core-ktx:1.2.0-rc01"
        val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta2"

        object Navigation {
            val version = "2.2.0"
            val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:$version"
            val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }
    }

    object Timber {
        val version = "4.7.1"
        val timber = "com.jakewharton.timber:timber:$version"
    }
}
