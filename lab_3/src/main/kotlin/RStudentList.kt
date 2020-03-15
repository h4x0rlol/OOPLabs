import data.Student
import org.w3c.dom.events.Event
import react.*
import react.dom.li
import react.dom.ol

interface RStudentListProps : RProps {
    var students: Array<Student>
    var present: Array<Boolean>
    var onClick: (Int) -> (Event) -> Unit
}

val RFStudentList =
    functionalComponent<RStudentListProps> { props ->
        ol {
            props.students.mapIndexed { index, student ->
                li {
                    rstudent(student, props.present[index], props.onClick(index))
                }
            }
        }
    }


fun RBuilder.studentList(students: Array<Student>, present: Array<Boolean>, onClick: (Int) -> (Event) -> Unit) =
    child(RFStudentList) {
        attrs.students = students
        attrs.present = present
        attrs.onClick = onClick
    }

