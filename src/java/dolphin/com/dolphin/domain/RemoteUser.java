package com.dolphin.domain;


public class RemoteUser {
	private static final ThreadLocal<User> currentUser = new ThreadLocal<User>();

	public static User get() {
		return currentUser.get();
	}

	public static void set(User user) {
		currentUser.set(user);
	}
	
	public static void remove() {
		currentUser.remove();
	}
}