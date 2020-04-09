package redux

import data.Lesson
import data.State
import data.Student

fun changeReducer(state: State, action: RAction) =
    when (action) {
        is ChangePresent -> State(
            state.presents.mapIndexed { indexLesson, lesson ->
                if (indexLesson == action.lesson)
                    lesson.mapIndexed { indexStudent, student ->
                        if (indexStudent == action.student)
                            !student
                        else student
                    }.toTypedArray()
                else
                    lesson
            }.toTypedArray(),
            state.lessons,
            state.students
        )

        is DeleteStudent -> State(
            state.presents.mapIndexed { index, _ ->
                state.presents[index].toMutableList().apply {
                    removeAt(action.student)
                }.toTypedArray()
            }.toTypedArray(),
            state.lessons,
            state.students.toMutableList().apply {
                removeAt(action.student)
            }.toTypedArray()
        )

        is AddStudent -> State(
            state.presents.mapIndexed { index, _ ->
                state.presents[index].plus(arrayOf(false))
            }.toTypedArray(),
            state.lessons,
            state.students.plus(Student(action.name, action.surname))
        )

        is DeleteLesson -> State(
            state.presents.toMutableList().apply {
                removeAt(action.lesson)
            }.toTypedArray(),
            state.lessons.toMutableList().apply {
                removeAt(action.lesson)
            }.toTypedArray(),
            state.students
        )

        is AddLesson -> State(
            state.presents.plus(
                arrayOf(
                    Array(state.students.size) { false })
            ),
            state.lessons.plus(Lesson(action.lesson)),
            state.students
        )
        else -> state
    }

