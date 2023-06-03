package patternmatchingforswitch;

public class PatternMatchingForSwitchWithNullHandler {

    static double getDoubleTraditionalSwitch(Object object) {
        return switch (object) {
            case String s -> {
                if(s == null) {
                    yield 0d;
                }
                if (s.length() > 0) {
                    yield Double.parseDouble(s);
                } else {
                    yield 0d;
                }
            }
            default -> 0d;
        };
    }

    static double getDoubleUsingNullHandler(Object object) {
        return switch (object) {
            case String s when s.length() > 0 -> Double.parseDouble(s);
            case null -> 0d;
            default -> 0d;
        };
    }

    public static void main(String[] args) {
        System.out.printf("Comportamento com valores nulos: %s%n", getDoubleUsingNullHandler(null));
        System.out.println("Isso vai disparar um erro :(");
        getDoubleTraditionalSwitch(null);
    }
}
