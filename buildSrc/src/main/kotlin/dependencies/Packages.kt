package dependencies

object Packages {
    const val id = "com.tick.taku.notificationwatcher"
    const val debugIdSuffix = ".debug"

    object SdkVersion {
        const val min = 23
        const val target = 29
        const val compile = 29
    }

    object Version {
        private const val major = 0
        private const val minor = 1
        private const val patch = 0

        val code = (major * 100 + minor * 10 + patch)
        val name = "$major.$minor.$patch"
    }
}