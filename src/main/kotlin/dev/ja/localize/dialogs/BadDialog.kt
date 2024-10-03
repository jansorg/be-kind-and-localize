package dev.ja.localize.dialogs

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.util.Disposer
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.ui.components.JBList
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import java.time.LocalDateTime
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.ListSelectionModel
import kotlin.concurrent.timer

class BadDialog(project: Project) : DialogWrapper(project) {
    private lateinit var timeLabel: JLabel
    private lateinit var dateLabel: JLabel
    private lateinit var statusLabel: JLabel

    init {
        title = "What Could Possibly Go Wrong?"
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

    private fun timeLabel(): String = "It's now %tT".format(LocalDateTime.now())

    private fun dateLabel(): String = "Today is %tD".format(LocalDateTime.now())

    private fun statusText(selected: List<Int>): String {
        val fileSizeMB = selected.sum().toDouble() / 1024
        return "You selected %d files. Total size: %.2f MB. Expected duration: %.2f min.".format(
            selected.size,
            fileSizeMB,
            fileSizeMB / 10 / 60 // 10 MB per second
        )
    }
}