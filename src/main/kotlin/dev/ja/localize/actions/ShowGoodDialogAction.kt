package dev.ja.localize.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import dev.ja.localize.dialogs.GoodDialog

class ShowGoodDialogAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        GoodDialog(e.project!!).show()
    }
}