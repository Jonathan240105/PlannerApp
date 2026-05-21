import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object FechaUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatoAPI = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatoLegible =
        DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy", Locale("es", "ES"))

    @RequiresApi(Build.VERSION_CODES.O)
    fun deApiALegible(fechaApi: String?): String {
        if (fechaApi.isNullOrBlank()) return "Sin fecha"
        return try {
            val fechaLimpia = fechaApi?.replace("/", "-")
            val localDateTime = LocalDateTime.parse(fechaLimpia, formatoAPI)
            localDateTime.format(formatoLegible)
        } catch (e: Exception) {
            "Fecha inválida"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deLegibleAApi(fechaLegible: String?): String {
        if (fechaLegible.isNullOrBlank()) return ""
        return try {
            val localDate = java.time.LocalDate.parse(fechaLegible, formatoLegible)
            val localDateTime = localDate.atStartOfDay()
            localDateTime.format(formatoAPI)
        } catch (e: Exception) {
            ""
        }
    }
}

private fun String?.isNull(action: () -> Boolean): Boolean = this == null || this.isBlank()