package dev.ja.localize.actions

import com.intellij.ide.nls.NlsMessages
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.text.Formats
import com.intellij.util.text.DateFormatUtil
import com.jetbrains.rd.util.Date
import dev.ja.localize.i18n
import java.time.Instant
import java.util.concurrent.TimeUnit.HOURS
import java.util.concurrent.TimeUnit.MINUTES
import java.util.concurrent.TimeUnit.SECONDS
import java.util.concurrent.atomic.AtomicInteger

private val callCounter = AtomicInteger(0)

/**
 * The presentation (text, description, etc.) is defined in the plugin.xml file.
 */
class GoodAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val aboutTwoHoursAgo = Date(Instant.now().minusSeconds(120).toEpochMilli())
        val dateFormatted = DateFormatUtil.formatDate(aboutTwoHoursAgo)
        val dateTimeFormatted = DateFormatUtil.formatPrettyDateTime(aboutTwoHoursAgo)
        val ordinal = callCounter.incrementAndGet()

        val duration = HOURS.toMillis(2) + MINUTES.toMillis(5) + SECONDS.toMillis(10);
        val formattedDuration = NlsMessages.formatDuration(duration)
        val englishDuration = Formats.formatDuration(duration)

        Messages.showMessageDialog(
            e.project,
            i18n("goodAction.message.contentWithDateTime", aboutTwoHoursAgo, dateFormatted, dateTimeFormatted, ordinal, formattedDuration, englishDuration),
            i18n("goodAction.message.title"),
            null
        )
    }
}
