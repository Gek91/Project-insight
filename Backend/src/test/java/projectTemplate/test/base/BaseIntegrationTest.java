package projectTemplate.test.base;

public abstract class BaseIntegrationTest extends BaseTest {

//	public static final int JETTY_SERVER_PORT = 8181;
//	public static final String JETTY_SERVER_LOCAL_ADDRESS = "localhost";
//	public static final String JETTY_SERVER_APP_CONTEXT_PATH = "/api";
//
//	//Jetty server
//	private static Server jettyServer;
////	private AddHeadersClientRequestFilter addHeadersClientRequestFilter = new AddHeadersClientRequestFilter();
//
//
//	/*
//	 * Start up jetty server
//	 */
//	@Before
//	public void setupAndStartJettyServer() throws Exception {
//
//		Map<Class<? extends AbstractModule>, Class<? extends AbstractModule>> testModulesToReplace = new HashMap<Class<? extends AbstractModule>, Class<? extends AbstractModule>>();
//
//		//Module to replace
//		testModulesToReplace.put(CoreGuiceModule.class, TestCoreGuiceModule.class);
//
//		//Test context listener, receives the list of module to replace
//		 applicatonModuleLoaderContextListener = new ApplicationModulesLoaderContextListener(testModulesToReplace);
//
//		//Create servlet container
//		ServletContextHandler servletContainerContext = new ServletContextHandler();
//		servletContainerContext.setInitParameter("resteasy.servlet.mapping.prefix", JETTY_SERVER_APP_CONTEXT_PATH);
//		servletContainerContext.addServlet(HttpServletDispatcher.class, JETTY_SERVER_APP_CONTEXT_PATH + "/*");
//		servletContainerContext.addEventListener(applicatonModuleLoaderContextListener);
//
//		//start the server
//		jettyServer = new Server(JETTY_SERVER_PORT);
//		jettyServer.setHandler(servletContainerContext);
//		jettyServer.start();
//
//	}
//
//	/*
//	 *
//	 * Stop jetty server
//	 */
//	@After
//	public void stopJettyServer() throws Exception {
//		jettyServer.stop();
//	}
//
//	/*
//	 * Crate proxy client for specified interface
//	 */
//	protected <T> T createHttpClientProxy(Class<T> proxyInterface) {
//
//		ResteasyClient client = new ResteasyClientBuilder().build();
//
//		//TODO: need for autentication
////		client.register(addHeadersClientRequestFilter);
//
//		//create new web resource at specified URI
//		ResteasyWebTarget target = client.target("http://" + JETTY_SERVER_LOCAL_ADDRESS + ":" + JETTY_SERVER_PORT + JETTY_SERVER_APP_CONTEXT_PATH);
//		return target.proxy(proxyInterface);
//
//	}
}
