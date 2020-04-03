package component

import data.Student
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent
import kotlin.browser.document

interface AddStudentProps : RProps {
    var students: Array<Student>
    var addFunc: (String) -> Unit
}

val fAddStudent =
    functionalComponent<AddStudentProps> {
        h2 { +"Type name and surname" }
        div {

            input(type = InputType.text) {
                attrs {id = "name"
                    placeholder = "Name"
                }
            }
            input(type = InputType.text) {
                attrs {
                    id = "surname"
                    placeholder = "Surname"
                }
            }
            button {
                +"add"
                attrs.onClickFunction = { _: Event ->
                    val newName = document.getElementById("name") as HTMLInputElement
                    val newSurname = document.getElementById("surname") as HTMLInputElement
                    it.addFunc(newName.value + " " + newSurname.value)
                }
            }

        }
    }

fun RBuilder.addStudent(
    students: Array<Student>,
    addFunc: (String) -> Unit
) = child(
    withDisplayName("studentsAdd", fAddStudent)
) {
    attrs.students = students
    attrs.addFunc = addFunc
}