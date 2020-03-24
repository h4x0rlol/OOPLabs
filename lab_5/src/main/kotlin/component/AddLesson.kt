package component

import data.Lesson
import data.Student
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.onChange
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import kotlin.browser.document

interface AddLessonProps : RProps {
    var lessons: Array<Lesson>
    var addFunc: (String) -> (Event) -> Unit
}

interface AddLessonState : RState {
    var newLesson: String
}

class AddLesson : RComponent<AddLessonProps, AddLessonState>() {
    override fun componentWillMount() {
        state.newLesson = ""
    }

    override fun RBuilder.render() {
        h2 {
            +"Add lesson"
        }

        div {
            input(type = InputType.text) {
                attrs {
                    value = state.newLesson
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            newLesson = target.value
                        }
                        //console.log(value)
                    }
                }
            }
            button {
                +"add"
                attrs.onClickFunction = props.addFunc(state.newLesson)
            }

        }
    }
}

fun RBuilder.addlesson(
    lessons: Array<Lesson>,
    addFunc: (String) -> (Event) -> Unit
) = child(AddLesson::class) {
    attrs.lessons = lessons
    attrs.addFunc = addFunc
}
