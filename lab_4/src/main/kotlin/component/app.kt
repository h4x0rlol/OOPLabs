package component

import data.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.h1

interface AppProps : RProps {
    var students: Array<Student>
}

interface AppState : RState {
    var presents: Array<Array<Boolean>>
    var lessons: Array<Lesson>
}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.lessons = lessonsList
        state.presents = Array(state.lessons.size) {
            Array(props.students.size) { false }
        }
        //console.log("componentwillmount")
    }

    override fun RBuilder.render() {
        h1 { +"App" }
        addlesson(lessonsList, addFunc())
        lessonListFull(
            state.lessons,
            props.students,
            state.presents,
            onClickLessonFull
        )
        studentListFull(
            state.lessons,
            props.students,
            transform(state.presents),
            onClickStudentFull
        )
    }

    fun addFunc() = { newLesson: String ->
        { _: Event ->
            setState {
                lessons += Lesson(newLesson)
                presents += arrayOf(
                    Array(props.students.size) { false })

            }
        }
    }

    fun transform(source: Array<Array<Boolean>>) =
        Array(source[0].size) { row ->
            Array(source.size) { col ->
                source[col][row]
            }
        }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                console.log(state.lessons)
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]

            }
        }

    val onClickLessonFull =
        { indexLesson: Int ->
            { indexStudent: Int ->
                onClick(indexLesson, indexStudent)
            }
        }

    val onClickStudentFull =
        { indexStudent: Int ->
            { indexLesson: Int ->
                onClick(indexLesson, indexStudent)
            }
        }

}

fun RBuilder.app(
    //lessons: Array<Lesson>,
    students: Array<Student>
) = child(App::class) {
    // attrs.lessons = lessons
    attrs.students = students
}