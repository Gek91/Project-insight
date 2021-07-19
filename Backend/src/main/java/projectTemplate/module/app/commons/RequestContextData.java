package projectTemplate.module.app.commons;

import projectTemplate.module.app.commons.ConnectionProxy;

public class RequestContextData {

	private ThreadLocal<ConnectionProxy> currentThreadConnectionProxy = new ThreadLocal<>();
//	private AccessTokenInfo accessTokenInfo;
//	private User userData;

	public ConnectionProxy getCurrentThreadConnectionProxy() {
		return this.currentThreadConnectionProxy.get();
	}

	public void setCurrentThreadConnectionProxy(ConnectionProxy connectionProxy) {
		this.currentThreadConnectionProxy.set(connectionProxy);
	}

}
