package component

import react.*
import react.dom.*
import org.w3c.dom.events.Event
import data.*


interface StudentFullProps : RProps {
    var lessons: Array<Lesson>
    var student: Student
    var presents: Array<Boolean>
    var onClick: (Int) -> (Event) -> Unit
}

val fStudentFull =
    functionalComponent<StudentFullProps> {
        h3 { +"${it.student.firstname} ${it.student.surname}" }
        ul {
            it.lessons.mapIndexed { index, lesson ->
                li {
                    lesson(lesson, it.presents[index], it.onClick(index))
                }
            }
        }
    }

fun RBuilder.studentFull(
    lessons: Array<Lesson>,
    student: Student,
    presents: Array<Boolean>,
    onClick: (Int) -> (Event) -> Unit
) = child(fStudentFull){
    attrs.lessons = lessons
    attrs.student = student
    attrs.presents = presents
    attrs.onClick = onClick
}
