package extensions

import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.NamedDomainObjectContainer

fun NamedDomainObjectContainer<BuildType>.debug(block: BuildType.() -> Unit) {
    getByName("debug") { block(this) }
}

fun NamedDomainObjectContainer<BuildType>.release(block: BuildType.() -> Unit) {
    getByName("release") { block(this) }
}