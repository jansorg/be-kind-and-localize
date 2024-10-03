package dev.ja.localize.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import dev.ja.localize.dialogs.BadDialog

class ShowBadDialogAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        BadDialog(e.project!!).show()
    }
}