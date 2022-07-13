package com.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// 스프링 컨테이너에 빈으로 등록하기 위한 어노테이션
// 빈이란? 
// getter setter만 있고 이 외에 없고 기본 생성자가 있는 스프링이 관리하는 객체
@Component
@Aspect
public class LoggerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

//	컨트롤러와 서비스, mapper 에서 파라미터 값 상관없이 처리할 코드 설정
//	exeution으로 pointcut ( 여러 조인트 묶은 것 ) 지정
	@Around("execution(* com.board..controller.*Controller.*(..)) or execution(* com.board..service.*Impl.*(..)) or execution(* com.board..mapper.*Mapper.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

		String type = "";
//		getSignature에 담겨온 함수에서 getDeclaringTypeName을 통해 해당 함수의 타입을 가져온다.
		String name = joinPoint.getSignature().getDeclaringTypeName();
//		logger.debug("name ; "+joinPoint.getSignature());

		if (name.contains("Controller") == true) {
			type = "Controller ===> ";

		} else if (name.contains("Service") == true) {
			type = "ServiceImpl ===> ";

		} else if (name.contains("Mapper") == true) {
			type = "Mapper ===> ";
		}

		logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		Object result = joinPoint.proceed();
		return result;
	}

}