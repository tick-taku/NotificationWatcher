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
    buildToolsVersion = Packages.SdkVersion.compile.toString()

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
    dataBinding.isEnabled = true
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    androidExtensions { isExperimental = true }
    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xuse-experimental=kotlin.Experimental")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin.stdlibJdk)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.activityKtx)
    implementation(Dep.AndroidX.fragmentKtx)
    implementation(Dep.AndroidX.design)

    implementation(Dep.AndroidX.Navigation.uiKtx)

    implementation(Dep.Test.junit)
    implementation(Dep.Test.coroutines)
    implementation(Dep.Test.archCore)
    androidTestImplementation(Dep.Test.androidJunit)
    androidTestImplementation(Dep.Test.espressoCore)

    // LifeCycle -----------------------------------------------------
    implementation(Dep.AndroidX.LifeCycle.liveDataExtension)
    implementation(Dep.AndroidX.LifeCycle.liveDataKtx)

    // Dagger --------------------------------------------------------
    implementation(Dep.Dagger.core)

    // Coil ----------------------------------------------------------
    implementation(Dep.Coil.common)

    // Timber --------------------------------------------------------
    implementation(Dep.Timber.timber)
}
