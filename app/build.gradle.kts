import dependencies.Dep
import dependencies.Packages
import extensions.debug
import extensions.release

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

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.incremental" to "true")
            }
        }
    }
    dataBinding.isEnabled = true
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        debug {
            applicationIdSuffix = Packages.debugIdSuffix
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/*.version")
        exclude("META-INF/proguard/*.pro")
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
    implementation(project(":corecomponent"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dep.Kotlin.stdlibJdk)
    implementation(Dep.AndroidX.appCompat)
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.constraint)
    implementation(Dep.AndroidX.design)

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.androidJunit)
    androidTestImplementation(Dep.Test.espressoCore)

    // coroutines
    implementation(Dep.Kotlin.coroutines)
    implementation(Dep.Kotlin.coroutinesCommon)
    implementation(Dep.Kotlin.androidCoroutinesDispatcher)

    // LifeCycle -----------------------------------------------------
    implementation(Dep.AndroidX.LifeCycle.liveDataExtension)
    implementation(Dep.AndroidX.LifeCycle.liveDataKtx)

    // Room
    implementation(Dep.AndroidX.Room.runtime)
    implementation(Dep.AndroidX.Room.coroutine)
    kapt(Dep.AndroidX.Room.compiler)

    // for Navigation
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

    // for Timber
    implementation(Dep.Timber.timber)
}
