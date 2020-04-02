package component

import data.Lesson
import data.lessonsList
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*

interface LessonsEditProps : RProps {}

interface LessonsEditState : RState {
    var lessons: Array<Lesson>
}

class LessonsEdit : RComponent<LessonsEditProps, LessonsEditState>() {
    override fun componentWillMount() {
        state.lessons = lessonsList
    }

    override fun RBuilder.render() {
        div {
            h2 { +"Edit here" }
            ul {
                state.lessons.mapIndexed { index: Int, lesson: Lesson ->
                    li {
                        input() {
                            attrs {
                                value = lesson.toString()
                                onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setState {
                                        lessons[index] = Lesson(target.value)
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

fun RBuilder.lessonsedit(
) = child(LessonsEdit::class) {}