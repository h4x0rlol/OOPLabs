import data.Student
import org.w3c.dom.events.Event
import react.*
import react.dom.div

interface RClassProps : RProps {
    var name: String
    var students: Array<Student>
}

interface RClassState : RState {
    var present: Array<Boolean>

}

class RClass : RComponent<RClassProps, RClassState>() {

    override fun componentWillMount() {
        state.apply {
            present = Array(props.students.size) { false }
        }
    }

    fun onClick() = { index: Int ->
        { _: Event ->
            setState {
                present[index] = !present[index]
            }
        }
    }

    override fun RBuilder.render() {
        div {
            +props.name
            studentList(props.students, state.present, onClick())
        }


    }

}

fun RBuilder.rclass(name: String, students: Array<Student>) =
    child(RClass::class) {
        attrs.name = name
        attrs.students = students
    }