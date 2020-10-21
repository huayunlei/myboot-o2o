package com.ihomefnt.o2o.intf.manager.util.common.secure;

import java.lang.annotation.*;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Documented
public @interface ReqField {
	
	boolean joinSign() default true;

}
