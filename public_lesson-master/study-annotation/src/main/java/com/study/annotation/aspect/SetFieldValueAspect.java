package com.study.annotation.aspect;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.study.annotation.util.BeanUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 基于注解@Aspect的AOP实现
 * 
 * @author allen
 */
@Component
@Aspect
public class SetFieldValueAspect {

	@Autowired
	private BeanUtil beanUtil;

	/**
	 * 环绕通知：目标方法执行前后分别执行一些代码，发生异常的时候执行另外一些代码
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.study.annotation.annotation.NeedSetFieldValue)")
	public Object doSetFieldValue(ProceedingJoinPoint pjp) throws Throwable {
		String methodName = pjp.getSignature().getName();
		System.out.println("【环绕通知中的--->前置通知】：the method 【" + methodName + "】 begins with " + Arrays.asList(pjp.getArgs()));
		
		//执行目标方法
		Object ret = pjp.proceed();
		
		System.out.println("【环绕通知中的--->后置通知】：-----------------end.----------------------");
		
		// 设置属性值
		if (ret instanceof Collection) {
			this.beanUtil.setFieldValueForCollection((Collection) ret);
		} else if (ret instanceof Map){
			// 不是集合，也需要设置属性值，，beanUtil中还提供一个设置单个对象属性值的方法
		} else if (ret instanceof String) {

		}

		return ret;
	}
}
