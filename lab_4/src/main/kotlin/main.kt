import component.addlesson
import component.app
import data.*
import react.dom.render
import kotlin.browser.document


fun main() {
    render(document.getElementById("root")!!) {
     app(studentList)

    }
}