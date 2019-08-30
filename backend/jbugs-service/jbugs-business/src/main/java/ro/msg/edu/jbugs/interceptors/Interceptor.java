package ro.msg.edu.jbugs.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.sql.Timestamp;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class Interceptor {
    private Timestamp timestamp;

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        Object object = null;
        try{
            //logger.entering(context.getTarget().toString(), context.getMethod().getName());
            System.out.println("START: " + context.getTarget().toString() + " " + context.getMethod().getName());
            timestamp = new Timestamp(System.currentTimeMillis());
            //logger.fine("Entering time: " + timestamp.toString());
            System.out.println("Entering time: " + timestamp.toString());
            object = context.proceed();
            timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("Exiting time: " + timestamp.toString());

        }finally{
            //logger.exiting(context.getTarget().toString(), context.getMethod().getName());
            System.out.println("END: " + context.getTarget().toString() + " " + context.getMethod().getName());
        }
        return object;
    }
}