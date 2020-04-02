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
            rComponent(it.subobjs, it.name, it.path)
        }
    }

fun <S> RBuilder.anyEdit(
    rEdit: RBuilder.() -> ReactElement,
    rComponent: RBuilder.(Array<S>, String, String) -> ReactElement,
    subobjs: Array<S>,
    name: String,
    path: String
) = child(
    withDisplayName("Edit", fAnyEdit<S>(rEdit, rComponent))
) {
    // attrs.obj = obj
    attrs.subobjs = subobjs
    attrs.name = name
    attrs.path = path

}
