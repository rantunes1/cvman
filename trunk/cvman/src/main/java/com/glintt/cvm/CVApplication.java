package com.glintt.cvm;

public class CVApplication {
	// private static final long serialVersionUID = 5614209556433681287L;
	//
	// private static final Version LUCENE_VERSION = Version.LUCENE_31;
	// private static final String[] FIELD_NAMES = { "personalInfo",
	// "professionalInfo" };
	//
	// private transient CVSecurityContext secContext;
	// private transient String baseURL;
	// private transient CVUserInfo userInfo;
	//
	// @SuppressWarnings("unchecked")
	// @Required
	// public void setSecurityContext(SecurityContext<? extends CVUser, ?
	// extends UserInfo<CVUser>> authContext) {
	// if (authContext == null) {
	//
	// }
	//
	// CVApplication app = CVApplication.getCurrent();
	// if (app == null) {
	// app = this;
	// }
	//
	// if (!CVSecurityContext.class.isAssignableFrom(authContext.getClass())) {
	// try {
	// app.secContext = new CVSecurityContext((SecurityContext<CVUser,
	// CVUserInfo>) authContext);
	// } catch (Exception ignored) {
	// throw new IllegalArgumentException(
	// "Invalid security context. Expected subclass of 'SecurityContext<CVUser, CVUserInfo>' but got "
	// + ((authContext == null) ? "null" : authContext.getClass().getName()));
	// }
	// } else {
	// app.secContext = (CVSecurityContext) authContext;
	// }
	// }
	//
	// private CVSecurityContext getSecurityContext() {
	// CVApplication app = CVApplication.getCurrent();
	// if (app == null) {
	// app = this;
	// }
	// return app.secContext;
	// }
	//
	// private void setBaseUrl(HttpServletRequest request) {
	// CVApplication app = CVApplication.getCurrent();
	// if (app == null) {
	// app = this;
	// }
	// if (request != null && app.baseURL == null) {
	// StringBuffer requestURLSB = null;
	// try {
	// requestURLSB = request.getRequestURL();
	// } catch (Exception ignored) {
	// }
	// // @todo remove commented lines (?)
	// // String requestURL = requestURLSB.toString();
	// // app.baseURL = requestURL.substring(0,
	// // requestURL.indexOf(request.getPathInfo()));
	// app.baseURL = requestURLSB.toString();
	// System.out.println("Setting base url to : " + app.baseURL);
	// }
	// }
	//
	// private String getBaseUrl() {
	// CVApplication app = CVApplication.getCurrent();
	// if (app == null) {
	// app = this;
	// }
	// return app.baseURL;
	// }
	//
	// private void setUserInfo(CVUserInfo userInfo) {
	// CVApplication app = CVApplication.getCurrent();
	// if (app == null) {
	// app = this;
	// }
	// app.userInfo = userInfo;
	// }
	//
	// public CVUserInfo getUserInfo() {
	// CVApplication app = CVApplication.getCurrent();
	// if (app == null) {
	// app = this;
	// }
	// CVUserInfo userInfo = app.userInfo;
	// if (userInfo == null) {
	// userInfo = new CVUserInfo();
	// }
	// setUserInfo(userInfo);
	// return userInfo;
	// }
	//
	// public void logout() {
	// setUserInfo(null);
	// close();
	// }
	//
	// /**
	// * @deprecated use {@link #getUserInfo()}
	// */
	// @Override
	// @Deprecated
	// public Object getUser() {
	// return getUserInfo().getUser();
	// }
	//
	// /**
	// * @deprecated use use {@link #setUserInfo()}
	// */
	// @Override
	// @Deprecated
	// public void setUser(Object user) {
	// if (user != null && CVUser.class.isAssignableFrom(user.getClass())) {
	// getUserInfo().setUser((CVUser) user);
	// }
	// }
	//
	// // @todo TEST CODE (to be removed)
	// // Person testPerson = new TestPerson();
	// // FacadeFactory.getFacade().store(testPerson);
	// //
	// // try {
	// // Person testPersonBD = FacadeFactory.getFacade().find(Person.class,
	// // testPerson.getId());
	// // System.out.println("person retrieved : " + testPersonBD.getId());
	// // new HRXMLConverter().convertAndSave(testPersonBD, new
	// // FileOutputStream("candidateBDOut.xml"));
	// // System.out.println("person converted");
	// // } catch (Exception e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	//
	// public void authenticateForm(String username, String password) throws
	// ApplicationException {
	// CVSecurityContext secContext = getSecurityContext();
	// CVUserInfo userInfo = getUserInfo();
	// if (secContext != null) {
	// setUserInfo(secContext.authenticateForm(userInfo, username, password));
	// redirect(userInfo);
	// } else {
	// // initialization error
	// logout();
	// NavigableApplication.getCurrentNavigableAppLevelWindow().getNavigator().navigateTo(HomePage.class,
	// null);
	// }
	// }
	//
	// public void authenticateNewUser(CVUser newUser) throws
	// ApplicationException {
	// CVUserInfo userInfo = getUserInfo();
	// setUserInfo(this.secContext.authenticateNewUser(userInfo, newUser));
	// }
	//
	// public void authenticateOAuth(String oauthProviderId) throws
	// ApplicationException {
	// CVSecurityContext authContext = getSecurityContext();
	// CVUserInfo userInfo = getUserInfo();
	// // @todo instead of always redirecting to home page, this should allow
	// // to redirect
	// // directly to the resource user is asking
	// String callbackURI = getBaseUrl() + new
	// PageResource(HomePage.class).getURL();
	// userInfo = authContext.authenticateOAuthProvider(userInfo,
	// oauthProviderId, callbackURI);
	// String authURL = userInfo.getAuthenticationURL();
	// CVApplication.getCurrentNavigableAppLevelWindow().open(new
	// PageResource(authURL));
	// }
	//
	// public void completeOAuthAuthentication(String verifier, Class<? extends
	// Component> callbackPage) throws ApplicationException {
	// CVSecurityContext authContext = getSecurityContext();
	// CVUserInfo userInfo = getUserInfo();
	// userInfo = authContext.signin(userInfo, verifier);
	// redirect(userInfo);
	// }
	//
	// public void redirect(CVUserInfo userInfo) {
	// if (!userInfo.isUserLogged() && !userInfo.isUserConnected()) {
	// Notification notification = new
	// Notification(Lang.getMessage("Login.ErrorMessage.invalid_username_password"),
	// Notification.TYPE_WARNING_MESSAGE);
	// notification.setPosition(Notification.POSITION_CENTERED_TOP);
	// notification.setDelayMsec(1000);
	// ((CVLevelWindow)
	// NavigableApplication.getCurrentNavigableAppLevelWindow()).showNotification(notification);
	// } else {
	// ((CVLevelWindow)
	// NavigableApplication.getCurrentNavigableAppLevelWindow()).refresh();
	// NavigableApplication.getCurrentNavigableAppLevelWindow().getNavigator().navigateTo(HomePage.class,
	// null);
	// }
	// }
}
