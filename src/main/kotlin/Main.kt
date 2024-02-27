import screens.AddNewValueScreen.receiveNewEntity
import data.Command.EXIT
import data.Command.NEW
import data.Command.OPEN_EXISTING
import data.Entity
import data.Entity.Archive
import data.Entity.Note
import screens.NoteContentScreen
import screens.SelectValueScreen
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val archivesListScreen: SelectValueScreen<Archive> = SelectValueScreen()

    processCommand(
            selectValueScreen = archivesListScreen,
            addAction = { id, name, _ -> archivesListScreen.entityList.add(Archive(id = id, name = name)) },
            openExistingAction = { selectedArchive ->
                val notesListScreen: SelectValueScreen<Note> = SelectValueScreen((selectedArchive as Archive).notes.toMutableList())
                processCommand(
                        selectValueScreen = notesListScreen,
                        addAction = { id, name, text ->
                            run {
                                notesListScreen.entityList.add(Note(id = id, name = name, text = text!!))
                                selectedArchive.notes.add(Note(id = id, name = name, text = text))
                            }
                        },
                        openExistingAction = { selectedNote -> NoteContentScreen.printContent(selectedNote as Note) }
                )
            }
    )
}

inline fun <reified T : Entity> processCommand(
        selectValueScreen: SelectValueScreen<T>,
        addAction: (Int, String, String?) -> Unit,
        openExistingAction: (Entity) -> Unit) {
    while (true) {
        selectValueScreen.printContent<T>()
        val (command, selectedEntity) = selectValueScreen.receiveCommandFromConsole()

        when (command) {
            NEW -> {
                val (name, text) = receiveNewEntity<T>()
                name?.let {
                    val id = selectValueScreen.entityList.size + 1
                    addAction(id, name, text)
                }
            }

            OPEN_EXISTING -> openExistingAction(selectedEntity!!)

            EXIT -> {
                when (T::class) {
                    Note::class -> break
                    else -> exitProcess(0)
                }
            }
        }
    }
}