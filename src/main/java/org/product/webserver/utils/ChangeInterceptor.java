package org.product.webserver.utils;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChangeInterceptor implements MethodInterceptor {

   private final String prefixOgnl;


   public ChangeInterceptor(String thePrefixOgnl) {
      prefixOgnl = thePrefixOgnl;
   }

   @Override
   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      Method method = methodInvocation.getMethod();
      String methodName = StringUtils.uncapitalize(method.getName().substring(3));

      if (method.getName().startsWith("get")) {
         return operateGetter(methodInvocation, methodName);
      }

      if (method.getName().startsWith("set")) {
         // get info about the change the timestamp of the latest / current version to decide about the Merge/Conflict Strategy
         return operateSetter(methodInvocation, methodName);
      }

      return methodInvocation.proceed();
   }

   private Object operateSetter(MethodInvocation methodInvocation, String methodName) throws Throwable {
      String fieldName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);
      String ognl = prefixOgnl + '.' + fieldName;

      Object prevObj = getMethodValue(methodInvocation, fieldName);
      Object methodResult = methodInvocation.proceed();
      Object newObj = getMethodValue(methodInvocation, fieldName);

      return methodResult;
   }

   private Object operateGetter(MethodInvocation methodInvocation, String methodName) throws Throwable {
      Object invocationResult = methodInvocation.proceed();

      if (invocationResult == null) {
         return null;
      }

      Class<?> returnType = methodInvocation.getMethod().getReturnType();

      if (need2Intercept(invocationResult)) {
         return invocationResult;
      }

      String ognl = prefixOgnl + '.' + methodName;

      if (methodInvocation.getThis() instanceof Map) {
         String key = methodInvocation.getArguments()[0] instanceof String ? StringUtils.wrap(methodInvocation.getArguments()[0].toString(), "\"") : methodInvocation.getArguments()[0].toString();
         ognl = prefixOgnl + '[' + key + ']';
      }

      return AspectsUtils.recreateInstanceWithInterceptor(invocationResult, returnType,
              new ChangeInterceptor(ognl));
   }

   private boolean need2Intercept(Object invocationResult) {
      return invocationResult.getClass().equals(Object.class) || List.class.isAssignableFrom(invocationResult.getClass()) ||
              Modifier.isFinal(invocationResult.getClass().getModifiers()) || Date.class.isAssignableFrom(invocationResult.getClass());
   }

   private static Object getMethodValue(MethodInvocation methodInvocation, String fieldName) {
      try {
         Field field = ReflectionUtils.findField(methodInvocation.getThis().getClass(), fieldName);
         if (field != null) {
            field.setAccessible(true);
            return field.get(methodInvocation.getThis());
         }
         return null;

      } catch (Exception ignored) {
         return null;
      }
   }
}
