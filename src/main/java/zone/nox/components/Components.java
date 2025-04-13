package zone.nox.components;

import dev.nipafx.ginevra.html.Image;
import zone.nox.data.Post;

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

	public static PostImage postImage(Image image) {
		return new PostImage(image);
	}

}
