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

interface AddStudentProps : RProps {}

val fAddStudent =
    functionalComponent<AddStudentProps> {
        h2 { +"Edit Students List here" }
        h4 { +"(Type number of element to delete it)" }
        div {
            li {
                input(type = InputType.text) {
                    attrs {
                        id = "name"
                        placeholder = "Name"
                    }
                }
                input(type = InputType.text) {
                    attrs {
                        id = "surname"
                        placeholder = "Surname"
                    }
                }
            }
            li {
                input(type = InputType.text) {
                    attrs {
                        id = "deleteS"
                        placeholder = "delete here"
                    }
                }
            }
        }
    }

fun RBuilder.addStudent(
) = child(
    withDisplayName("studentsAdd", fAddStudent)
) {}