import data.Student
import data.studentList
import react.*
import react.dom.li
import react.dom.ol

interface RStudentsListProps : RProps {
    var students: Array<Student>
}

class RStudentsList : RComponent<RStudentsListProps, RState>() {
    override fun RBuilder.render() {
        ol {
            props.students.map {
                li {
                    +"${it.firstname} ${it.surname}"
                }
            }
        }
    }
}

fun RBuilder.listOfStudents(students: Array<Student>) =
    child(functionalComponent<RStudentsListProps> {
        ol {
            studentList.map {
                li {
                    +"${it.firstname} ${it.surname}"
                }
            }
        }
    }) {
        attrs.students = students
    }