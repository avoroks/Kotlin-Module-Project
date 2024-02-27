import java.lang.Exception
import java.util.Scanner

object ConsoleReader {
    fun <T> readConsoleInput(
            parseLogic: (String) -> T,
            validationRules: (String) -> Boolean,
            exitByZero: Boolean = false,
            greetingText: String? = null,
            errorText: String? = null): T? {
        var parsedValue: T? = null

        while (true) {
            greetingText?.let { println(it) }
            val line = Scanner(System.`in`).nextLine()
            if (exitByZero && line == "0") break

            try {
                if (!validationRules(line)) throw IllegalArgumentException()
                parsedValue = parseLogic(line)
                break
            } catch (_: Exception) {
                println(errorText ?: "Неверный ввод.")
                continue
            }
        }
        return parsedValue
    }
}