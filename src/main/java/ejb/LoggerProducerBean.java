package ejb;


import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import java.util.logging.Logger;

@jakarta.ejb.Singleton(name = "LoggerProducerEJB")
@jakarta.ejb.Startup
public class LoggerProducerBean {

    //private Logger logger = Logger.getLogger(LoggerProducerBean.class.getName());

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}