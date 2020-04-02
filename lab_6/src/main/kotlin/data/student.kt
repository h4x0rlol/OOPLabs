package data

data class Student (
    var firstname: String,
    var surname: String
) {
    override fun toString(): String =
        "$firstname $surname"
}

val studentList =
    arrayOf(
        Student("Sheldon", "Cooper"),
        Student("Leonard", "Hofstadter"),
        Student("Howard", "Wolowitz")
    )