package zone.nox.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record Neotropolis(LocalDate start, LocalDate end) {

	private Neotropolis(LocalDate start) {
		var daysToSaturday = 6 /* SATURDAY */ - start.getDayOfWeek().getValue();
		var saturday = start.plusDays(daysToSaturday);
		this(start, saturday);
	}

	private static final List<Neotropolis> EVENTS = List.of(
			new Neotropolis(LocalDate.of(2024, 4, 24)),
			new Neotropolis(LocalDate.of(2025, 4, 23)));
	private static final DateTimeFormatter ABSOLUTE_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd / HH:mm 'NTZ'");
	private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm 'NTZ'");

	public static String format(LocalDateTime dateTime) {
		var neotropolis = EVENTS.stream()
				.filter(neo -> dateTime.toLocalDate().isBefore(neo.end().plusMonths(1)))
				.findFirst()
				.orElse(EVENTS.getLast());
		return formatRelativeTo(dateTime, neotropolis);
	}

	private static String formatRelativeTo(LocalDateTime dateTime, Neotropolis neo) {
		var isNotDuring = dateTime.toLocalDate().isBefore(neo.start())
				|| dateTime.toLocalDate().isAfter(neo.end());
		return isNotDuring
				? ABSOLUTE_DATE.format(dateTime)
				: "Day " + (dateTime.getDayOfMonth() - neo.start().getDayOfMonth() + 1) + " / " + TIME.format(dateTime);
	}

}
