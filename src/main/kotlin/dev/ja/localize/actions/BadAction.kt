package dev.ja.localize.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

@Suppress("ActionPresentationInstantiatedInCtor")
class BadAction : AnAction("Bad Action", "Demo how NOT to define an action", null) {
    override fun actionPerformed(e: AnActionEvent) {
        Messages.showMessageDialog(e.project, "This action is not localized!", "Not Localized!", null)
    }
}
