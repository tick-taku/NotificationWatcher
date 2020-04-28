import dependencies.Dep
import dependencies.Packages
import extensions.release

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dep.Kotlin.stdlibJdk)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.constraint)
    implementation(Dep.AndroidX.preference)

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.androidJunit)
    androidTestImplementation(Dep.Test.espressoCore)

    // Navigation ----------------------------------------------------
    implementation(Dep.AndroidX.Navigation.fragmentKtx)
    implementation(Dep.AndroidX.Navigation.uiKtx)
    implementation(Dep.AndroidX.Navigation.runtimeKtx)

    // Timber --------------------------------------------------------
    implementation(Dep.Timber.timber)
}
