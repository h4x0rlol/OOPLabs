package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import kotlin.reflect.KClass

interface AppProps : RProps {
    var lessons: Array<Lesson>
    var students: Array<Student>
}

interface AppState : RState {
    var presents: Array<Array<Boolean>>
}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.presents = Array(props.lessons.size) {
            Array(props.students.size) { false }
        }

    }

    override fun RBuilder.render() {
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Lessons" } }
                    li { navLink("/students") { +"Students" } }
                    li { navLink("/studentsEdit"){+"studentsEdit"} }
                    li { navLink("/lessonsEdit") {+"lessonsEdit"} }
                }
            }
        }
        switch {
            route("/lessonsEdit",
                exact = true,
                render = {
                    anyEdit(RBuilder::lessonsedit,RBuilder::anyList,props.lessons,"Edited List","/lessons")
                }
            )
            route("/studentsEdit",
                exact = true,
                render = {
                    anyEdit(RBuilder::studentsEdit,RBuilder::anyList,props.students,"Edited List","/students")
                }
            )
            route("/lessons",
                exact = true,
                render = {
                    anyList(props.lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(props.students, "Students", "/students")
                }
            )
            route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val lesson = props.lessons.getOrNull(num)
                    if (lesson != null)
                        anyFull(
                            RBuilder::student,
                            lesson,
                            props.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"No such lesson" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = props.students.getOrNull(num)
                    if (student != null)
                        anyFull(
                            RBuilder::lesson,
                            student,
                            props.lessons,
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

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }
}

fun RBuilder.app(
    lessons: Array<Lesson>,
    students: Array<Student>
) =
    child(
        withDisplayName("AppHoc", App::class)
    ) {
        attrs.lessons = lessons
        attrs.students = students
    }





