import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class TimeRange(
    private val code: String
) : CodedEnum<String> {
    DAILY("daily"),
    WEEKLY("weekly"),
    TOTAL("total");

    companion object {
        fun fromCode(code: String?): TimeRange =
            entries.find { it.code.lowercase() == code?.lowercase() } ?: throw IllegalArgumentException("Unknown code: $code")
    }

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class ScheduleTypeConverter : AbstractCodedEnumConverter<TimeRange, String>(TimeRange::class.java)
}
