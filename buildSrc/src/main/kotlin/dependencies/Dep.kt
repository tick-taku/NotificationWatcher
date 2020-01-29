package dependencies

object Dep {
    object Test {
        const val junit = "junit:junit:4.12"
        const val androidJunit = "androidx.test.ext:junit:1.1.1"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
    }

    object Kotlin {
        const val version = "1.3.61"
        const val stdlibJdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.2.0-rc01"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta2"

        object Navigation {
            const val version = "2.2.0"
            const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:$version"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }
    }

    object Timber {
        const val version = "4.7.1"
        const val timber = "com.jakewharton.timber:timber:$version"
    }
}