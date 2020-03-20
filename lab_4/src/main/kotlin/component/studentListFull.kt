package component

import react.*
import react.dom.*
import org.w3c.dom.events.Event
import data.*

interface StudentListFullProps : RProps {
    var lessons: Array<Lesson>
    var students: Array<Student>
    var presents: Array<Array<Boolean>>
    var onClick: (Int) -> (Int) -> (Event) -> Unit
}

val fStudentListFull =
    functionalComponent<StudentListFullProps> {
        h2 { +"Students" }
        it.students.mapIndexed{index, student ->
            studentFull(
                it.lessons,
                student,
                it.presents[index],
                it.onClick(index))
        }
    }

fun RBuilder.studentListFull(
    lessons: Array<Lesson>,
    students: Array<Student>,
    presents: Array<Array<Boolean>>,
    onClick: (Int) -> (Int) -> (Event) -> Unit
) = child(fStudentListFull) {
    attrs.lessons = lessons
    attrs.students = students
    attrs.presents = presents
    attrs.onClick = onClick
}