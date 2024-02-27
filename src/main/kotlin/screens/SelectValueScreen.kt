package screens

import ConsoleReader.readConsoleInput
import data.Command
import data.Command.EXIT
import data.Command.NEW
import data.Command.OPEN_EXISTING
import data.Entity
import data.Entity.Archive
import data.Entity.Note
import kotlin.system.exitProcess

class SelectValueScreen<T : Entity>(val entityList: MutableList<T> = mutableListOf()) {
    inline fun <reified T : Entity> printContent() {
        val listText = when (T::class) {
            Note::class -> "Список заметок:"
            Archive::class -> "Список архивов:"
            else -> "Список элементов:"
        }

        println("=============================")

        println(listText)
        println("0. ${NEW.description}")
        entityList.forEachIndexed { index, element -> println("${index + 1}. ${element.name}") }
        println("${entityList.size + 1}. ${EXIT.description}")

        println("=============================")
    }

    fun receiveCommandFromConsole(): Pair<Command, Entity?> = readConsoleInput(
            parseLogic = { line ->
                var selectedValue: Entity? = null
                val command = when (line.toInt()) {
                    0 -> NEW
                    entityList.size + 1 -> EXIT
                    else -> {
                        selectedValue = entityList.find { it.id == line.toInt() }
                        OPEN_EXISTING
                    }
                }
                command to selectedValue
            },
            validationRules = { it.all { char -> char.isDigit() } && it.toInt() in 0..entityList.size + 1},
            errorText = "Выберите из пунктов меню. В консоль нужно ввести цифру от 0 до ${entityList.size + 1}"
    ) ?: exitProcess(1)
}