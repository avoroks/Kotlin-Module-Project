package data

sealed class Entity(open val id: Int, open val name: String) {
    data class Archive(override val id: Int, override val name: String, val notes: MutableList<Note> = mutableListOf()) : Entity(id, name)
    data class Note(override val id: Int, override val name: String, val text: String) : Entity(id, name)
}

enum class Command(val description: String? = null, ) {
    EXIT("Выход"), NEW("Добавить новый элемент"), OPEN_EXISTING
}