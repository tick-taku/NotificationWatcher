import dependencies.Packages
import dependencies.Dep
import extensions.debug
import extensions.release
import java.util.Properties
import java.io.FileInputStream
import kotlin.collections.mapOf

// Loads the keystore.properties file into the keystoreProperties object.
val credentialProperties = Properties().apply {
    val credentialPropertiesFile = rootProject.file("credential.properties")
    load(FileInputStream(credentialPropertiesFile))
}
val lineChannelId: Map<String, String> by extra {
    mapOf(
        "lineChannelIdDebug".let { it to credentialProperties[it].toString() },
        "lineChannelIdRelease".let { it to credentialProperties[it].toString() }
    )
}

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        debug {
            buildConfigField("String", "LINE_CHANNEL_ID", lineChannelId["lineChannelIdDebug"])
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "LINE_CHANNEL_ID", lineChannelId["lineChannelIdRelease"])
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

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.androidJunit)
    androidTestImplementation(Dep.Test.espressoCore)

    // Dagger --------------------------------------------------------
    implementation(Dep.Dagger.core)
    implementation(Dep.Dagger.android)
    implementation(Dep.Dagger.androidSupport)
    kapt(Dep.Dagger.compiler)
    kapt(Dep.Dagger.androidProcessor)

    // Coroutine -----------------------------------------------------
    implementation(Dep.Kotlin.coroutines)
    implementation(Dep.Kotlin.coroutinesCommon)

    // Timber --------------------------------------------------------
    implementation(Dep.Timber.timber)

    // Line ----------------------------------------------------------
    implementation(Dep.Line.sdk)
}
