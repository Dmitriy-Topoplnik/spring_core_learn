package com.epam.spring.core.aspects;

import com.epam.spring.core.entity.Event;
import com.epam.spring.core.utils.EventLogger;
import com.epam.spring.core.utils.FileEventLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Dmitrii_Topolnik on 8/8/2017.
 */
@Aspect
public class ConsoleLoggerLimitAspect {
    private final int maxCount;

    private final EventLogger otherLogger;

    private int currentCount = 0;

    public ConsoleLoggerLimitAspect(int maxCount, EventLogger otherLogger) {
        this.maxCount = maxCount;
        this.otherLogger = otherLogger;
    }

    public void aroundLogEvent(ProceedingJoinPoint jp, Event evt) throws Throwable {
        if (currentCount < maxCount) {
            System.out.println("ConsoleEventLogger max count is not reached. Continue...");
            currentCount++;
            jp.proceed(new Object[] {evt});
        } else {
            System.out.println("ConsoleEventLogger max count is reached. Logging to " + otherLogger.getClass().getSimpleName());
            otherLogger.logEvent(evt);
        }
    }

}
