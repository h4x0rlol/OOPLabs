package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import redux.*
import kotlin.browser.document

interface AppProps : RProps {
    var store: Store<State, RAction, WrapperAction>
}

//interface AppState : RState {
//    var presents: Array<Array<Boolean>>
//    var lessons: Array<Lesson>
//    var students: Array<Student>
//}

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
                    li { navLink("/studentsEdit") { +"studentsEdit" } }
                    li { navLink("/lessonsEdit") { +"lessonsEdit" } }
                }
            }
        }

        switch {
            route(
                "/lessonsEdit",
                exact = true,
                render = editLesson(props)
            )
            route(
                "/studentsEdit",
                exact = true,
                render = editStudent(props)
            )
            route("/lessons",
                exact = true,
                render = {
                    anyList(props.store.getState().lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(props.store.getState().students, "Students", "/students")
                }
            )
            route(
                "/lessons/:number",
                render = renderLesson(props)
            )
            route(
                "/students/:number",
                render = renderStudent(props)
            )
        }
    }

fun AppProps.addStudent(): (Event) -> Unit = {
    val newName = document.getElementById("name") as HTMLInputElement
    val newSurname = document.getElementById("surname") as HTMLInputElement
    store.dispatch(AddStudent(newName.value, newSurname.value))
}

fun AppProps.deleteStudent(): (Event) -> Unit = {
    val delEL = document.getElementById("deleteS") as HTMLInputElement
    store.dispatch(DeleteStudent(delEL.value.toInt() - 1))
}

fun AppProps.deleteLesson(): (Event) -> Unit = {
    val delEL = document.getElementById("deleteL") as HTMLInputElement
    store.dispatch(DeleteLesson(delEL.value.toInt() - 1))
}

fun AppProps.addLesson(): (Event) -> Unit = {
    val newLesson = document.getElementById("lesson") as HTMLInputElement
    store.dispatch(AddLesson(newLesson.value))
}

fun AppProps.onClickStudent(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(index, num))
        }
    }

fun AppProps.onClickLesson(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(num, index))
        }
    }

fun RBuilder.renderLesson(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val lesson = props.store.getState().lessons.getOrNull(num)
        if (lesson != null)
            anyFull(
                RBuilder::student,
                lesson,
                props.store.getState().students,
                props.store.getState().presents[num],
                props.onClickLesson(num)
            )
        else
            p { +"No such lesson" }
    }

fun RBuilder.renderStudent(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val student = props.store.getState().students.getOrNull(num)
        if (student != null)
            anyFull(
                RBuilder::lesson,
                student,
                props.store.getState().lessons,
                props.store.getState().presents.map {
                    it[num]
                }.toTypedArray(),
                props.onClickStudent(num)
            )
        else
            p { +"No such student" }
    }

fun RBuilder.editStudent(props: AppProps): () -> ReactElement =
    {
        anyEdit(
            RBuilder::addStudent,
            RBuilder::anyList,
            props.store.getState().students,
            "Edit students",
            "/students",
            props.addStudent(),
            props.deleteStudent()
        )
    }

fun RBuilder.editLesson(props: AppProps): () -> ReactElement =
    {
        anyEdit(
            RBuilder::addLesson,
            RBuilder::anyList,
            props.store.getState().lessons,
            "Edit lessons",
            "/lessons",
            props.addLesson(),
            props.deleteLesson()
        )
    }

fun RBuilder.app(
    store: Store<State, RAction, WrapperAction>
) =
    child(
        withDisplayName("App", fApp())
    ) {
        attrs.store = store
    }





