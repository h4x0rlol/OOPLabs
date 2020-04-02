package component

import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.functionalComponent

interface AnyFullProps<O, S> : RProps {
    var obj: O
    var subobjs: Array<S>
    var presents: Array<Boolean>
    var onClick: (Int) -> (Event) -> Unit
}

fun <O, S> fAnyFull(
    rComponent: RBuilder.(S, Boolean, (Event) -> Unit) -> ReactElement
) =
    functionalComponent<AnyFullProps<O, S>> {
        h3 {
            +it.obj.toString()
        }
        ul {
            val t = Array<Any>(3){0}
            it.subobjs.mapIndexed { index, sub ->
                li {
                    t[index] = rComponent(sub, it.presents[index], it.onClick(index))
                }
            }

        }
    }




fun <O, S> RBuilder.anyFull(
    rComponent: RBuilder.(S, Boolean, (Event) -> Unit) -> ReactElement,
    obj: O,
    subobjs: Array<S>,
    presents: Array<Boolean>,
    onClick: (Int) -> (Event) -> Unit
) = child(
    withDisplayName("Full",  fAnyFull<O, S>(rComponent))
){
    attrs.obj = obj
    attrs.subobjs = subobjs
    attrs.presents = presents
    attrs.onClick = onClick
}