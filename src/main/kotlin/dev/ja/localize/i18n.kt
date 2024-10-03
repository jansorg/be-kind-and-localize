package dev.ja.localize

import com.intellij.DynamicBundle
import org.jetbrains.annotations.PropertyKey

private object LocalizeBundle {
    val Bundle = DynamicBundle(this::class.java, "messages.LocalizeBundle")
}

fun i18n(@PropertyKey(resourceBundle = "messages.LocalizeBundle") key: String): String {
    return LocalizeBundle.Bundle.getMessage(key)
}

fun i18n(@PropertyKey(resourceBundle = "messages.LocalizeBundle") key: String, vararg params: Any?): String {
    return LocalizeBundle.Bundle.getMessage(key, *params)
}
