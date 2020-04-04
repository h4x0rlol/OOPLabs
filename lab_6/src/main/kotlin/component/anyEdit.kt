package component

import hoc.withDisplayName
import kotlinx.html.classes
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import kotlin.browser.document

interface anyEditProps<S> : RProps {
    var subobjs: Array<S>
    var name: String
    var path: String
    var addFunc: (String) -> Unit
    var delFunc: (Int) -> Unit
}

fun <S> fAnyEdit(
    rEdit: RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<S>, String, String) -> ReactElement
) =
    functionalComponent<anyEditProps<S>> {
        div {
            h2("header") {
                +"Edit page"
            }
            rEdit()
            if (it.path == "/lessons") {
                button {
                    +"Add Lesson"
                    attrs.onClickFunction = { _: Event ->
                        val newLesson = document.getElementById("lesson") as HTMLInputElement
                        it.addFunc(newLesson.value)
                        console.log(newLesson.value)
                    }
                }
                button {
                    +"Delete lesson"
                    attrs.onClickFunction = { _: Event ->
                        val delEL = document.getElementById("deleteL") as HTMLInputElement
                        it.delFunc(delEL.value.toInt())
                    }
                }
            }
            if (it.path == "/students") {
                button {
                    +"Add Student"
                    attrs.onClickFunction = { _: Event ->
                        val newName = document.getElementById("name") as HTMLInputElement
                        val newSurname = document.getElementById("surname") as HTMLInputElement
                        it.addFunc(newName.value + " " + newSurname.value)
                    }
                }
                button {
                    +"Delete student"
                    attrs.onClickFunction = { _: Event ->
                        val delEL = document.getElementById("deleteS") as HTMLInputElement
                        it.delFunc(delEL.value.toInt())
                    }
                }
            }

            rComponent(it.subobjs, it.name, it.path)
        }
    }

fun <S> RBuilder.anyEdit(
    rEdit: RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<S>, String, String) -> ReactElement,
    subobjs: Array<S>,
    name: String,
    path: String,
    addFunc: (String) -> Unit,
    delFunc: (Int) -> Unit
) = child(
    withDisplayName("Edit", fAnyEdit<S>(rEdit, rComponent))
) {
    attrs.subobjs = subobjs
    attrs.name = name
    attrs.path = path
    attrs.addFunc = addFunc
    attrs.delFunc = delFunc
}
