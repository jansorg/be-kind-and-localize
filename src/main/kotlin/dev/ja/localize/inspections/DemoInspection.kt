package dev.ja.localize.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.progress.ProgressManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.util.elementType
import com.intellij.psi.util.siblings
import dev.ja.localize.i18n

class DemoInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                ProgressManager.checkCanceled()

                if (element is PsiFile || element.elementType == TokenType.WHITE_SPACE || element.textLength == 0) {
                    return
                }

                val name = element.elementType?.toString() ?: "no name"
                val siblings = element.siblings().count()
                holder.problem(element, i18n("inspection.demo.messageWithSiblings", name, siblings))
                    .highlight(ProblemHighlightType.WARNING)
                    .register()
            }
        }
    }
}
