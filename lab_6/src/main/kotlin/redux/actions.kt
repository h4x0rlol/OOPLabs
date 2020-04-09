package redux

class ChangePresent(val lesson: Int, val student: Int) : RAction
class DeleteStudent(val student: Int) : RAction
class AddStudent(val name:String, val surname: String): RAction
class DeleteLesson(val lesson: Int): RAction
class AddLesson(val lesson: String): RAction
