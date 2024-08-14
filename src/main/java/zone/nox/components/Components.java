package zone.nox.components;

import zone.nox.data.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Components {

	public static Layout layout = new Layout(null, null, List.of());

	public static Header header = new Header();

	public static Footer footer = new Footer();

	public static PostBlock postBlock(Post post) {
		return new PostBlock(post);
	}

	public static PageHeader pageHeader = new PageHeader("", "", Optional.empty());

	public static PostContent postContent(Post post) {
		return new PostContent(post);
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
