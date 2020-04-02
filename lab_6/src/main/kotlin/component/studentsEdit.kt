package component

import data.Student
import data.studentList
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*

interface StudentsEditProps : RProps {}

interface StudentsEditState : RState {
    var students: Array<Student>
}

class StudentsEdit : RComponent<StudentsEditProps, StudentsEditState>() {
    override fun componentWillMount() {
        state.students = studentList
    }

    override fun RBuilder.render() {
        div {
            h2 { +"Edit here" }
            ul {
                state.students.map { student: Student ->
                    li {
                        input() {
                            attrs {
                                value = student.firstname
                                onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState {
                                        student.firstname = target.value
                                    }
                                }
                            }
                        }
                        input() {
                            attrs {
                                value = student.surname
                                onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState {
                                        student.surname = target.value
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.studentsEdit(
) = child(StudentsEdit::class) {}
