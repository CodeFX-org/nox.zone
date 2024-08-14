package zone.nox;

public enum Target {

	DEV, LOCAL_PRODUCTION, GLOBAL_PRODUCTION;

	static Target from(Config config) {
		return switch (config.target().orElse("dev")) {
			case "dev" -> DEV;
			case "local" -> LOCAL_PRODUCTION;
			case "global" -> GLOBAL_PRODUCTION;
			default -> throw new IllegalArgumentException();
		};
	}

	public boolean embedLocalVideo() {
		return switch (this) {
			case DEV, LOCAL_PRODUCTION -> true;
			case GLOBAL_PRODUCTION -> false;
		};
	}

	public boolean embedYouTubeVideo() {
		return switch (this) {
			case DEV, GLOBAL_PRODUCTION -> true;
			case LOCAL_PRODUCTION -> false;
		};
	}

}
