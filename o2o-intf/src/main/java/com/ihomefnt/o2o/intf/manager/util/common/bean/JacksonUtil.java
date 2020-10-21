package com.ihomefnt.o2o.intf.manager.util.common.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;  
import java.beans.IntrospectionException;  
import java.beans.Introspector;  
import java.beans.PropertyDescriptor;  
import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;  
import java.util.HashMap;  
import java.util.Map;

public class JacksonUtil {

	private static final ObjectMapper mapper = new ObjectMapper();
	  private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

	  public static final String obj2Str(Object o)
	  {
	    try
	    {
	      return mapper.writeValueAsString(o);
	    } catch (JsonParseException e) {
	      LOGGER.error("对象转json出错", e);
	    } catch (JsonMappingException e) {
	      LOGGER.error("对象转json出错", e);
	    } catch (IOException e) {
	      LOGGER.error("对象转json出错", e);
	    }
	    return null;
	  }

	  public static final void writeObj(OutputStream out, Object value) {
	    try {
	      mapper.writeValue(out, value);
	    } catch (JsonParseException e) {
	      LOGGER.error("writeObj出错", e);
	    } catch (JsonMappingException e) {
	      LOGGER.error("writeObj出错", e);
	    } catch (IOException e) {
	      LOGGER.error("writeObj出错", e);
	    }
	  }

	  public static final <T> T str2Obj(String s, Class<T> valueType) {
	    try {
	      return mapper.readValue(s, valueType);
	    } catch (JsonParseException e) {
	      LOGGER.error("json转对象出错，字符串为:{}", s, e);
	    } catch (JsonMappingException e) {
	      LOGGER.error("json转对象出错，字符串为:{}", s, e);
	    } catch (IOException e) {
	      LOGGER.error("json转对象出错，字符串为:{}", s, e);
	    }
	    return null;
	  }

	  public static final <T> T readObj(InputStream in, Class<T> valueType) {
	    try {
	      return mapper.readValue(in, valueType);
	    } catch (JsonParseException e) {
	      LOGGER.error("json转对象出错", e);
	    } catch (JsonMappingException e) {
	      LOGGER.error("json转对象出错", e);
	    } catch (IOException e) {
	      LOGGER.error("json转对象出错", e);
	    }
	    return null;
	  }

	  public static final <T> T readObj(InputStream in, JavaType valueType)
	  {
	    try {
	      return mapper.readValue(in, valueType);
	    } catch (JsonParseException e) {
	      LOGGER.error("json转对象出错", e);
	    } catch (JsonMappingException e) {
	      LOGGER.error("json转对象出错", e);
	    } catch (IOException e) {
	      LOGGER.error("json转对象出错", e);
	    }
	    return null;
	  }

	  public static final <T> T str2Obj(String s, Class<?> collectionClass, Class<?>[] elementClasses) {
	    JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	    try {
	      return mapper.readValue(s, javaType);
	    } catch (JsonParseException e) {
	      LOGGER.error("json转对象出错，字符串为:{}", s, e);
	    } catch (JsonMappingException e) {
	      LOGGER.error("json转对象出错，字符串为:{}", s, e);
	    } catch (IOException e) {
	      LOGGER.error("json转对象出错，字符串为:{}", s, e);
	    }
	    return null;
	  }

	public static final <T> T str2SimpleObj(String s, Class<?> parametrized,
			Class parameterClasses1, Class parameterClasses2)
	  {
	    JavaType javaType1 = mapper.getTypeFactory().constructParametricType(parameterClasses1, new Class[] { parameterClasses2 });
	    JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, new JavaType[] { javaType1 });
	    try {
	      return mapper.readValue(s, javaType);
	    } catch (JsonParseException e) {
	      LOGGER.error("json转对象出错，字符串为:{}", s, e);
	    } catch (JsonMappingException e) {
	      LOGGER.error("json转对象出错，字符串为:{}", s, e);
	    } catch (IOException e) {
	      LOGGER.error("json转对象出错，字符串为:{}", s, e);
	    }
	    return null;
	  }
	  
	  
    public static final Map<String, Object> toMap(Object bean)  
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {  
        Map<String, Object> returnMap = new HashMap<String, Object>();  
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());  
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
        for (int i = 0; i< propertyDescriptors.length; i++) {  
            PropertyDescriptor descriptor = propertyDescriptors[i];  
            String propertyName = descriptor.getName();  
            if (!propertyName.equals("class")) {  
                Method readMethod = descriptor.getReadMethod();  
                Object result = readMethod.invoke(bean, new Object[0]);  
                if (result != null) {  
                    returnMap.put(propertyName, result);  
                } else {  
                    returnMap.put(propertyName, "");  
                }  
            }  
        }  
        return returnMap;  
    }
}
