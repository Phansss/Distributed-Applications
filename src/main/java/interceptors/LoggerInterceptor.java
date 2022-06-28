package Interceptors;

        import Interceptors.Loggable;
        import jakarta.inject.Inject;
        import jakarta.interceptor.AroundInvoke;
        import jakarta.interceptor.Interceptor;
        import jakarta.interceptor.InvocationContext;

        import java.util.logging.Logger;

@Interceptor
@Loggable
public class LoggerInterceptor {

    @Inject
    private Logger logger;

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
        System.out.println("Entered function: " + ic.getTarget().getClass().getName());
        logger.entering(ic.getTarget().getClass().getName(), ic.getMethod().getName());
        try {
            return ic.proceed();
        } finally {
            System.out.println("Exited function: " + ic.getTarget().getClass().getName()
                                + " with parameters: " + ic.getParameters());
            logger.exiting(ic.getTarget().getClass().getName(), ic.getMethod().getName());

        }
    }

}

