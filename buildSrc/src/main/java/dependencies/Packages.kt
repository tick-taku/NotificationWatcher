package dependencies

object Packages {
    const val id = "com.tick.taku.notificationwatcher"
    const val debugIdSuffix = ".debug"

    object SdkVersion {
        val target = 29
        val compile = 29
        val min = 21
    }

    object Version {
        private val major = 0
        private val minor = 0
        private val patch = 0

        val code = (major * 100 + minor * 10 + patch)
        val name = "$major.$minor.$patch"
    }
}