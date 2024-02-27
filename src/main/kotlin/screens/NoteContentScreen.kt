package screens

import data.Entity

object NoteContentScreen {
    fun printContent(note: Entity.Note) {
        println("=============================")
        println("Текст заметки: ${note.text}")
        println("=============================")
    }
}