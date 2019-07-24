package com.georgefeng.trans.doc_trans;

import java.util.Map;
import java.lang.reflect.Field;

public class EnvSetup {
	@SuppressWarnings({ "unchecked" })
	  public static void updateEnv(String name, String val) throws ReflectiveOperationException {
	    Map<String, String> env = System.getenv();
	    Field field = env.getClass().getDeclaredField("m");
	    field.setAccessible(true);
	    ((Map<String, String>) field.get(env)).put(name, val);
	  }
}
