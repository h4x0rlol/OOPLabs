package component

import react.*
import react.dom.*
import org.w3c.dom.events.Event
import data.*

interface LessonListFullProps : RProps {
    var lessons: Array<Lesson>
    var students: Array<Student>
    var presents: Array<Array<Boolean>>
    var onClick: (Int) -> (Int) -> (Event) -> Unit
}

val fLessonListFull =
    functionalComponent<LessonListFullProps> {
        h2 { +"Lessons" }
        it.lessons.mapIndexed{index, lesson ->
            lessonFull(
                lesson,
                it.students,
                it.presents[index],
                it.onClick(index))
        }
    }


fun RBuilder.lessonListFull(
    lessons: Array<Lesson>,
    students: Array<Student>,
    presents: Array<Array<Boolean>>,
    onClick: (Int) -> (Int) -> (Event) -> Unit
) = child(fLessonListFull) {
    attrs.lessons = lessons
    attrs.students = students
    attrs.presents = presents
    attrs.onClick = onClick
}
