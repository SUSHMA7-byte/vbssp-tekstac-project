package com.vilt.kaveri.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggableAction {
    String action();   // e.g. BLOCK_USER
    String entity();   // e.g. User, Listing
}
