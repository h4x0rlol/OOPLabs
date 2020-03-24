import component.app
import data.*
import react.dom.render
import react.router.dom.browserRouter
import react.router.dom.hashRouter
import kotlin.browser.document


fun main() {
    render(document.getElementById("root")!!) {
        browserRouter {
            app(studentList)
        }
    }
}
