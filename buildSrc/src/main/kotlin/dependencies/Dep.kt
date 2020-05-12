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

        const val coroutinesVersion = "1.3.3"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesCommon = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion"
        const val androidCoroutinesDispatcher = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.2.0-rc01"
        const val activityKtx = "androidx.activity:activity-ktx:1.1.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta2"
        const val design = "com.google.android.material:material:1.1.0-rc01"
        const val preference = "androidx.preference:preference:1.1.0"

        object LifeCycle {
            const val version = "2.2.0"
            const val liveDataExtension = "androidx.lifecycle:lifecycle-livedata:$version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        }

        object Room {
            const val version = "2.2.3"
            const val compiler = "androidx.room:room-compiler:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val coroutine = "androidx.room:room-ktx:$version"
        }

        object Navigation {
            const val version = "2.2.0"
            const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:$version"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object WorkManager {
            const val version = "2.3.4"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
        }
    }

    object Dagger {
        const val version = "2.26"
        const val core = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val androidSupport = "com.google.dagger:dagger-android-support:$version"
        const val android = "com.google.dagger:dagger-android:$version"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
        object AssistedInject {
            const val version = "0.5.2"
            const val annotations = "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
            const val processor = "com.squareup.inject:assisted-inject-processor-dagger2:$version"
        }
    }

    object OnActivityResult {
        const val version = "0.7.0"
        const val core = "com.vanniktech:onactivityresult:$version"
        const val compiler = "com.vanniktech:onactivityresult-compiler:$version"
    }

    object Groupie {
        const val version = "2.7.2"
        const val common = "com.xwray:groupie:$version"
        const val dataBinding = "com.xwray:groupie-databinding:$version"
        const val extensions = "com.xwray:groupie-kotlin-android-extensions:$version"
    }

    object Coil {
        const val version = "0.9.5"
        const val common = "io.coil-kt:coil:$version"
    }

    object Klock {
        const val version = "1.8.7"
        const val common = "com.soywiz.korlibs.klock:klock:$version"
    }

    object Timber {
        const val version = "4.7.1"
        const val timber = "com.jakewharton.timber:timber:$version"
    }

    object Line {
        const val version = "5.0.1"
        const val sdk = "com.linecorp:linesdk:$version"
    }
}
