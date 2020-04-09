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
import redux.WrapperAction
import kotlin.browser.document

interface anyEditProps<S> : RProps {
    var subobjs: Array<S>
    var name: String
    var path: String
    var addFunc: (Event) -> Unit
    var delFunc: (Event) -> Unit
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
            button {
                +"Add element"
                attrs.onClickFunction = it.addFunc
            }
            button {
                +"Delete element"
                attrs.onClickFunction = it.delFunc
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
    addFunc: (Event) -> Unit,
    delFunc: (Event) -> Unit
) = child(
    withDisplayName("Edit", fAnyEdit<S>(rEdit, rComponent))
) {
    attrs.subobjs = subobjs
    attrs.name = name
    attrs.path = path
    attrs.addFunc = addFunc
    attrs.delFunc = delFunc
}
