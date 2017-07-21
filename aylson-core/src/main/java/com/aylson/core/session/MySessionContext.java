package com.aylson.core.session;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class MySessionContext {
	private static HashMap mymap = new HashMap();
    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }
    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }
    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
        return null;
        return (HttpSession) mymap.get(session_id);
    }

}
