package projectTemplate.module.app.commons;

import java.util.Objects;

public enum AppRuntimeEnvironment implements RuntimeEnvironment {

	DEVELOPMENT("dev"),
	JUNIT_TEST("junit_test"),
	TEST("test"),
	CERT("cert"),
	PRODUCTION("prod");

	private String id;

	private AppRuntimeEnvironment(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public static AppRuntimeEnvironment getValueById(String id) {

		AppRuntimeEnvironment env = null;

		for (AppRuntimeEnvironment e : values()) {
			if (Objects.equals(e.getId(), id)) {
				env = e;
				break;
			}
		}

		return env;

	}

}
