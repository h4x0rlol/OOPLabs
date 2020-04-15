package component

import container.*
import data.*
import react.*
import react.dom.*
import react.router.dom.*

interface AppProps : RProps {
    var lessons: Map<Int, Lesson>
    var students: Map<Int, Student>
}

interface RouteNumberResult : RProps {
    var number: String
}

fun fApp() =
    functionalComponent<AppProps> { props ->
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Lessons" } }
                    li { navLink("/students") { +"Students" } }
                }
            }
        }
        switch {
            route("/lessons",
                exact = true,
                render = { lessonListContainer{} }
            )
            route("/students",
                exact = true,
                render = { studentListContainer {} }
            )
            route(
                "/lessons/:number",
                exact = true,
                render = renderObject(
                    { props.lessons[it] },
                    { index, lesson ->
                        lessonFullContainer {
                            attrs.obj = index to lesson
                        }
                    }
                )
            )
            route(
                "/students/:number",
                exact = true,
                render = renderObject(
                    { props.students[it] },
                    { index, student ->
                        studentFullContainer {
                            attrs.obj = index to student
                        }
                    }
                )
            )
            route(
                "/lessons/:number/edit",
                render = renderObject(
                    { props.lessons[it] },
                    { index, lesson ->
                        lessonEditContainer {
                            attrs.lesson = index to lesson
                        }
                    }
                )
            )
            route(
                "/students/:number/edit",
                render = renderObject(
                    { props.students[it] },
                    { index, student ->
                        studentEditContainer {
                            attrs.student = index to student
                        }
                    }
                )
            )
        }
    }

fun <O> RBuilder.renderObject(
    selector: (Int) -> O?,
    rElement: (Int, O) -> ReactElement
) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val obj = selector(num)
        if (obj != null){
            rElement(num, obj)}
        else
            p { +"Object not found" }
    }
