package dev.ja.localize.dialogs

import com.intellij.ide.nls.NlsMessages
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.util.Disposer
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.ui.components.JBList
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.text.DateFormatUtil
import dev.ja.localize.LocalizedStringUtil
import dev.ja.localize.i18n
import java.time.Duration
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.ListSelectionModel
import kotlin.concurrent.timer

class GoodDialog(project: Project) : DialogWrapper(project, false, IdeModalityType.IDE) {
    private lateinit var timeLabel: JLabel
    private lateinit var dateLabel: JLabel
    private lateinit var statusLabel: JLabel

    init {
        title = i18n("goodDialog.title")
        setSize(800, 450)
        init()
        pack()
    }

    override fun createCenterPanel(): JComponent? {
        return panel {
            row {
                JBList((1..10000).toList()).also { list ->
                    scrollCell(list).align(Align.FILL)

                    list.cellRenderer = SimpleListCellRenderer.create("") { "/home/user/$it.txt" }
                    list.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
                    list.addListSelectionListener {
                        statusLabel.text = statusText(list.selectedValuesList)
                    }
                }
            }.resizableRow()
        }
    }

    override fun createNorthPanel(): JComponent? {
        return panel {
            row {
                label("").align(AlignX.LEFT).apply { dateLabel = component }
                label("").align(AlignX.RIGHT).apply { timeLabel = component }
            }
        }
    }

    override fun createSouthAdditionalPanel(): JPanel? {
        return panel {
            row {
                label("").apply { statusLabel = component }
            }
        }
    }

    override fun init() {
        super.init()

        val timer = timer(period = 1000) {
            dateLabel.text = dateLabel()
            timeLabel.text = timeLabel()
        }
        Disposer.register(disposable, timer::cancel)
    }

    private fun timeLabel(): String {
        return i18n("goodDialog.timeLabel.text", DateFormatUtil.formatTimeWithSeconds(System.currentTimeMillis()))
    }

    private fun dateLabel(): String {
        return i18n("goodDialog.dateLabel.text", DateFormatUtil.formatDate(System.currentTimeMillis()))
    }

    private fun statusText(selected: List<Int>): String {
        val kilobytes = selected.sum()
        val fileSize = LocalizedStringUtil.formatFileSize(kilobytes * 1024L, " ", -1, true)
        val seconds = kilobytes.toDouble() / 1024 / 10 // 10 MB / second
        val duration = NlsMessages.formatDurationApproximate(Duration.ofSeconds(seconds.toLong()).toMillis())
        return i18n("goodDialog.selectedStatus", selected.size, fileSize, duration)
    }
}