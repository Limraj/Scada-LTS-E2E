package org.scadalts.e2e.app.domain.notification.email;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SendEmailReaction {
}
