package component

import data.Student
import react.*
import react.dom.*
import react.router.dom.*

interface StudentListProps : RProps {
    var students: Array<Student>
}

val fStudentList =
    functionalComponent<StudentListProps> {
        h2 { +"Students" }
        ul {
            it.students.mapIndexed { index, student ->
                li {
                    navLink("/students/$index") {
                        +"${student.firstname} ${student.surname}"
                    }
                }
            }
        }
    }

fun RBuilder.studentList(students: Array<Student>) =
    child(fStudentList) { attrs.students = students }