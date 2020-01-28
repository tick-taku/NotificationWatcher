import dependencies.Dep
import dependencies.Packages

plugins {
    id("com.android.application")

    kotlin("android")

    kotlin("android.extensions")

    id("androidx.navigation.safeargs")
}

android {
    compileSdkVersion(Packages.SdkVersion.compile)
    buildToolsVersion = Packages.SdkVersion.compile.toString()
    defaultConfig {
        applicationId = Packages.id
        minSdkVersion(Packages.SdkVersion.min)
        targetSdkVersion(Packages.SdkVersion.target)
        versionCode = Packages.Version.code
        versionName = Packages.Version.name
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    androidExtensions { isExperimental = true }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin.stdlibJdk)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.constraint)

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.androidJunit)
    androidTestImplementation(Dep.Test.espressoCore)

    // for Navigation
    implementation(Dep.AndroidX.Navigation.fragmentKtx)
    implementation(Dep.AndroidX.Navigation.uiKtx)
    implementation(Dep.AndroidX.Navigation.runtimeKtx)

//    implementation("androidx.navigation:navigation-fragment:2.2.0")
//    implementation("androidx.navigation:navigation-ui:2.2.0")

    // for Timber
    implementation(Dep.Timber.timber)
}
