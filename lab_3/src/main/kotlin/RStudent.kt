import data.Student
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.span
import react.functionalComponent

interface RStudentProps : RProps {
    var student: Student
    var present: Boolean
    var onClick: (Event) -> Unit
}

val RFStudent =
    functionalComponent<RStudentProps> {
        span(
            if (it.present) "present" else "absent"
        ) {
            +"${it.student.firstname} ${it.student.surname}"
            attrs.onClickFunction = it.onClick
        }
    }

fun RBuilder.rstudent(student: Student, present: Boolean, onClick: (Event)->Unit) =
    child(RFStudent) {
        attrs.student = student
        attrs.present = present
        attrs.onClick = onClick
    }