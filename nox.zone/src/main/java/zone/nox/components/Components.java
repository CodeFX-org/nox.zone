package zone.nox.components;

import zone.nox.data.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Components {

	public static Layout layout = new Layout(null, null, List.of());

	public static Header header(String text, LocalDateTime dateTime) {
		return new Header(Optional.empty(), text, dateTime, 1);
	}

	public static Header header(String text, LocalDateTime dateTime, int level) {
		return new Header(Optional.empty(), text, dateTime, level);
	}

	public static Header header(String title, String text, LocalDateTime dateTime) {
		return new Header(Optional.of(title), text, dateTime, 1);
	}

	public static Header header(String title, String text, LocalDateTime dateTime, int level) {
		return new Header(Optional.of(title), text, dateTime, level);
	}

	public static Header header(Post post) {
		return new Header(Optional.of(post.title()), post.summary(), post.date(), 1);
	}

	public static Header header(Post post, int level) {
		return new Header(Optional.of(post.title()), post.summary(), post.date(), level);
	}

	public static PostBlock post(Post post) {
		return new PostBlock(post);
	}

	private static final LocalDateTime FIRST_DAY_2024 = LocalDate.of(2024, 4, 24).atTime(0, 0);
	private static final DateTimeFormatter ABSOLUTE_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd / HH:mm 'NTZ'");
	private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm 'NTZ'");

	public static String format(LocalDateTime dateTime) {
		return dateTime.isBefore(FIRST_DAY_2024)
				? ABSOLUTE_DATE.format(dateTime)
				: "Day " + (dateTime.getDayOfMonth() - FIRST_DAY_2024.getDayOfMonth() + 1) + " / " + TIME.format(dateTime);
	}

}
