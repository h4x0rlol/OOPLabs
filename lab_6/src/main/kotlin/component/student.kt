package component

import react.*
import react.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import data.*
import hoc.withDisplayName

interface StudentProps : RProps {
    var student: Student
    var present: Boolean
    var onClick: (Event)->Unit
}

val fStudent =
    functionalComponent<StudentProps> {
        span (
            if(it.present) "present" else "absent"
        ){
            +"${it.student.firstname} ${it.student.surname}"
            attrs.onClickFunction = it.onClick
        }
    }

fun RBuilder.student(
    student: Student,
    present: Boolean,
    onClick: (Event)->Unit
) = child(
    withDisplayName(student.firstname, fStudent)
) {
        attrs.student = student
        attrs.present = present
        attrs.onClick = onClick
    }