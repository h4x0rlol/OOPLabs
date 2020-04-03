package component

import data.Lesson
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.button
import react.dom.div
import react.dom.h2
import react.dom.input
import kotlin.browser.document

interface AddLessonProps : RProps {
    var lessons: Array<Lesson>
    var addFunc: (String) -> Unit
}

val fAddLesson =
    functionalComponent<AddLessonProps> {
        h2 { +"Type new lesson" }
        div {
            input(type = InputType.text) {
                attrs {
                    id = "lesson"
                    placeholder = "Lesson"
                }
            }
            button {
                +"add"
                attrs.onClickFunction = { _: Event ->
                    val newValue = document.getElementById("lesson") as HTMLInputElement
                    it.addFunc(newValue.value)
                    console.log(newValue.value)

                }
            }
        }
    }

fun RBuilder.addLesson(
    lessons: Array<Lesson>,
    addFunc: (String) -> Unit
) = child(
    withDisplayName("studentsAdd", fAddLesson)
) {
    attrs.lessons = lessons
    attrs.addFunc = addFunc
}