// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Plugins.android)
        classpath(Plugins.kotlin)

        // for Navigation safe args
        classpath(Plugins.safeArgs)
    }
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}