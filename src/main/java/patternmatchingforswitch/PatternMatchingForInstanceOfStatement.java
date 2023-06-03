package patternmatchingforswitch;

public class PatternMatchingForInstanceOfStatement {

    public static void main(String[] args) {
        Object object = args[0];
        if (object instanceof String s) {
            System.out.printf("Object is a string %s", s);
        }
    }
}
