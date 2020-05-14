import dependencies.Dep
import dependencies.Packages
import extensions.release

plugins {
    id("com.android.library")

    kotlin("android")

    kotlin("android.extensions")

    kotlin("kapt")

    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Packages.SdkVersion.compile)
    defaultConfig {
        minSdkVersion(Packages.SdkVersion.min)
        targetSdkVersion(Packages.SdkVersion.target)
        versionCode = Packages.Version.code
        versionName = Packages.Version.name
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    dataBinding.isEnabled = true
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":corecomponent"))
    implementation(project(":domain:db"))
    implementation(project(":domain:repository"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dep.Kotlin.stdlibJdk)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.constraint)
    implementation(Dep.AndroidX.design)

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.androidJunit)
    androidTestImplementation(Dep.Test.espressoCore)

    // Dagger --------------------------------------------------------
    implementation(Dep.Dagger.core)
    implementation(Dep.Dagger.android)
    implementation(Dep.Dagger.androidSupport)
    kapt(Dep.Dagger.compiler)
    kapt(Dep.Dagger.androidProcessor)
    compileOnly(Dep.Dagger.AssistedInject.annotations)
    kapt(Dep.Dagger.AssistedInject.processor)

    // Coroutines ----------------------------------------------------
    implementation(Dep.Kotlin.coroutines)
    implementation(Dep.Kotlin.coroutinesCommon)
    implementation(Dep.Kotlin.androidCoroutinesDispatcher)

    // LifeCycle -----------------------------------------------------
    implementation(Dep.AndroidX.LifeCycle.liveDataExtension)
    implementation(Dep.AndroidX.LifeCycle.liveDataKtx)

    // Navigation ----------------------------------------------------
    implementation(Dep.AndroidX.Navigation.fragmentKtx)
    implementation(Dep.AndroidX.Navigation.uiKtx)
    implementation(Dep.AndroidX.Navigation.runtimeKtx)

    // Groupie -------------------------------------------------------
    implementation(Dep.Groupie.common)
    implementation(Dep.Groupie.dataBinding)
    implementation(Dep.Groupie.extensions)

    // Coil ----------------------------------------------------------
    implementation(Dep.Coil.common)

    // Klock ---------------------------------------------------------
    implementation(Dep.Klock.common)

    // Timber --------------------------------------------------------
    implementation(Dep.Timber.timber)

    // Jsoup ---------------------------------------------------------
    implementation(Dep.Jsoup.common)
}