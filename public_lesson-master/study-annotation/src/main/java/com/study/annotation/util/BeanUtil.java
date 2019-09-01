package com.study.annotation.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.study.annotation.annotation.SetValue;

@Component
public class BeanUtil implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		if (this.applicationContext == null) {
			this.applicationContext = applicationContext;
			System.out.println("--------SpringUtil.applicationContext  ok ---------");
		}
	}

	public void setFieldValueForCollection(Collection col) {
		if (CollectionUtils.isEmpty(col)) {
			return;
		}

		// 1获得clazz对象  userMapper.class
		Class<?> clazz = col.iterator().next().getClass();

		// 2、获取里面的字段
		Field[] fields = clazz.getDeclaredFields();
//		Field[] fields = clazz.getFields();

		// 3、遍历处理带有指定注解的字段
		for (Field field : fields) {
			// 3.1 获取字段的SetValue注解
			SetValue sv = field.getAnnotation(SetValue.class);
			if (sv == null) {
				continue;
			}

			// 3.2 获取注解的信息，准备好干活工具
			// 要调用的bean
			Object bean = this.applicationContext.getBean(sv.beanClass());
			try {
				// 要调用的方法
				Method method = sv.beanClass().getMethod(sv.method(),
						clazz.getDeclaredField(sv.paramField()).getType());

				// 获取给入参的值的获取方法
				Method paramValueGetMethod = clazz.getMethod(
						"get" + StringUtils.capitalize(sv.paramField()));

				// 设置值的set方法
				Method setValueMethod = clazz.getMethod(
						"set" + StringUtils.capitalize(field.getName()),
						field.getType());

				// 获取目标属性值的方法
				Method getTargetValueMethod = null;
				boolean needInnerField = !StringUtils.isEmpty(sv.targetField());

				// 4 遍历对象
				for (Object obj : col) {
					// 4.1 获取到参数值 - customerId
					Object paramValue = paramValueGetMethod.invoke(obj);
					if (paramValue == null) {
						continue;
					}

					Object value = null; // User.class

					// 4.2 调用bean的方法获得目标对象 value=User对象
					value = method.invoke(bean, paramValue);

					if (needInnerField) {
						if (value != null) {
							if (getTargetValueMethod == null) {
								// user.getUserName
								getTargetValueMethod = value.getClass().getMethod("get" + StringUtils.capitalize(sv.targetField()));
							}
							value = getTargetValueMethod.invoke(value);
						}
					}
					// 4.3 设置值 order.setCustomerName();
					setValueMethod.invoke(obj, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
