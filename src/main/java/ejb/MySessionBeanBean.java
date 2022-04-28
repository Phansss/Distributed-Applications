package ejb;

@jakarta.ejb.Stateless(name = "MySessionBeanEJB")
public class MySessionBeanBean {
    public MySessionBeanBean() {
    }

    public int doSomethingReallyDifficult(int a, int b) {
        return  a + b;
    }
}
