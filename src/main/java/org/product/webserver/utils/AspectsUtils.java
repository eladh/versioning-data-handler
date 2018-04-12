package org.product.webserver.utils;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

public class AspectsUtils {


   public static <T> T recreateInstanceWithInterceptor(Object instance, Class<?> type, MethodInterceptor interceptor) {
      return (T) getProxy(instance, type, interceptor);
   }

   private static <T> T getProxy(Object instance, Class<?> type, MethodInterceptor interceptor) {
      if (AopUtils.isAopProxy(instance)) {
         return (T) instance;
      }
      ProxyFactory factory = null;
      try {
         factory = new ProxyFactory(instance != null ? instance : type.newInstance());
      } catch (Exception e) {
         e.printStackTrace();
      }

      if (type.isInterface()) {
         factory.addInterface(type);
      } else {
         factory.setProxyTargetClass(true);
      }
      factory.addAdvice(interceptor);
      return (T) factory.getProxy();
   }



   // Use for data serialization
   public static Object getRealObject(Advised proxy) {
      if (!AopUtils.isAopProxy((proxy))) {
         return proxy;
      }
      try {
         return (proxy).getTargetSource().getTarget();
      } catch (Exception e) {
         return proxy;
      }
   }
}

