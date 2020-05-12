import dependencies.Dep
import dependencies.Packages
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
    id("com.android.application")

    kotlin("android")

    kotlin("android.extensions")

    kotlin("kapt")

    id("androidx.navigation.safeargs.kotlin")
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
    dataBinding.isEnabled = true
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        debug {
            applicationIdSuffix = Packages.debugIdSuffix
            buildConfigField("String", "LINE_CHANNEL_ID", lineChannelId["lineChannelIdDebug"])
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "LINE_CHANNEL_ID", lineChannelId["lineChannelIdRelease"])
        }
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/*.version")
        exclude("META-INF/proguard/*.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":corecomponent"))
    implementation(project(":feature:message"))
    implementation(project(":feature:preference"))
    implementation(project(":domain:api"))
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

    // LifeCycle -----------------------------------------------------
    implementation(Dep.AndroidX.LifeCycle.liveDataExtension)
    implementation(Dep.AndroidX.LifeCycle.liveDataKtx)

    // Navigation ----------------------------------------------------
    implementation(Dep.AndroidX.Navigation.fragmentKtx)
    implementation(Dep.AndroidX.Navigation.uiKtx)
    implementation(Dep.AndroidX.Navigation.runtimeKtx)

    // WorkManager ----------------------------------------------------
    implementation(Dep.AndroidX.WorkManager.runtimeKtx)

    // OnActivityResult ----------------------------------------------
    implementation(Dep.OnActivityResult.core)
    kapt(Dep.OnActivityResult.compiler)

    // Coil ----------------------------------------------------------
    implementation(Dep.Coil.common)

    // Timber  -------------------------------------------------------
    implementation(Dep.Timber.timber)

    // Line ----------------------------------------------------------
    implementation(Dep.Line.sdk)
}
