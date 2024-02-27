package screens

import ConsoleReader.readConsoleInput
import data.Entity
import data.Entity.Note

object AddNewValueScreen {
    inline fun <reified T : Entity> receiveNewEntity(): Pair<String?, String?> {
        val name = readConsoleInput(
                parseLogic = { it },
                validationRules = { it.isNotEmpty() },
                exitByZero =true,
                greetingText = "Введите имя. Если вам нужно вернуться назад без создания новой сущности, нажмите 0.",
                errorText = "Имя не должно быть пустым."
        )

        val text = name?.let {
            if (T::class == Note::class) {
                readConsoleInput(
                        parseLogic = {
                            it
                        },
                        validationRules = { it.isNotEmpty() },
                        exitByZero =true,
                        greetingText = "Введите текст заметки:",
                        errorText = "Текст не должен быть пустым."
                )
            } else null
        }
        return name to text
    }
}