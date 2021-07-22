package projectinsight.module.app.commons;

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
