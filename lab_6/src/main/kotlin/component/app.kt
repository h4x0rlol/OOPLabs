package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import kotlin.reflect.KClass

interface AppProps : RProps {
    //var lessons: Array<Lesson>
    //var students: Array<Student>
}

interface AppState : RState {
    var presents: Array<Array<Boolean>>
    var lessons: Array<Lesson>
    var students: Array<Student>
}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.lessons = lessonsList
        state.students = studentList
        state.presents = Array(state.lessons.size) {
            Array(state.students.size) { false }
        }

    }

    override fun RBuilder.render() {
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Lessons" } }
                    li { navLink("/students") { +"Students" } }
                    li { navLink("/studentsEdit") { +"studentsEdit" } }
                    li { navLink("/lessonsEdit") { +"lessonsEdit" } }
                }
            }
        }
        switch {
            route("/lessonsEdit",
                exact = true,
                render = {
                    anyEdit(
                        RBuilder::addLesson,
                        RBuilder::anyList,
                        state.lessons,
                        "Edited List",
                        "/lessons",
                        addLesson()
                    )
                }
            )
            route("/studentsEdit",
                exact = true,
                render = {
                    anyEdit(
                        RBuilder::addStudent,
                        RBuilder::anyList,
                        state.students,
                        "Edited list",
                        "/students",
                        addStudent()
                    )
                }
            )
            route("/lessons",
                exact = true,
                render = {
                    anyList(state.lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(state.students, "Students", "/students")
                }
            )
            route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val lesson = state.lessons.getOrNull(num)
                    if (lesson != null)
                        anyFull(
                            RBuilder::student,
                            lesson,
                            state.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"No such lesson" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = state.students.getOrNull(num)
                    if (student != null)
                        anyFull(
                            RBuilder::lesson,
                            student,
                            state.lessons,
                            state.presents.map {
                                it[num]
                            }.toTypedArray()
                        ) { onClick(it, num) }
                    else
                        p { +"No such student" }
                }
            )
        }
    }

    fun addStudent() = { newStudent: String ->
        val student = newStudent.split(" ")
        setState {
            students += Student(student[0], student[1])
        }
    }

    fun addLesson() = { newLesson: String ->
        setState {
            lessons += Lesson(newLesson)
            presents += arrayOf(
                Array(state.students.size) { false })
        }
    }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }
}

fun RBuilder.app(
    //lessons: Array<Lesson>,
) =
    child(
        withDisplayName("AppHoc", App::class)
    ) {
        //attrs.lessons = lessons

    }





