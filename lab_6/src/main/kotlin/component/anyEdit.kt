package component

import hoc.withDisplayName
import kotlinx.html.classes
import kotlinx.html.id
import kotlinx.html.style
import org.w3c.dom.events.Event
import react.*
import react.dom.*

interface anyEditProps<S> : RProps {
    var subobjs: Array<S>
    var name: String
    var path: String
    var addFunc: (String) -> Unit
}


fun <S> fAnyEdit(
    rEdit: RBuilder.(Array<S>,(String) -> Unit) -> ReactElement,
    rComponent: RBuilder.(Array<S>, String, String) -> ReactElement
) =
    functionalComponent<anyEditProps<S>> {
        div {
            h2("header") {
                +"Edit page"
            }
            rEdit(it.subobjs,it.addFunc)
            rComponent(it.subobjs, it.name, it.path)
        }
    }

fun <S> RBuilder.anyEdit(
    rEdit: RBuilder.(Array<S>,(String) -> Unit) -> ReactElement,
    rComponent: RBuilder.(Array<S>, String, String) -> ReactElement,
    subobjs: Array<S>,
    name: String,
    path: String,
    addFunc: (String) -> Unit
) = child(
    withDisplayName("Edit", fAnyEdit<S>(rEdit, rComponent))
) {
    // attrs.obj = obj
    attrs.subobjs = subobjs
    attrs.name = name
    attrs.path = path
    attrs.addFunc = addFunc
}
