package component

import data.Lesson
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import kotlin.browser.document

interface AddLessonProps : RProps {}

val fAddLesson =
    functionalComponent<AddLessonProps> {
        h2 { +"Edit Lessons List here" }
        h4 { +"(Type number of element to delete it)"}
        div {
            li {
                input(type = InputType.text) {
                    attrs {
                        id = "lesson"
                        placeholder = "add here"
                    }
                }
            }
            li{
                input(type = InputType.text) {
                    attrs {
                        id = "deleteL"
                        placeholder = "delete here"
                    }
                }
            }
        }
    }



fun RBuilder.addLesson(
) = child(
    withDisplayName("studentsAdd", fAddLesson)
) {}