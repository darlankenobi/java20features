package patternmatchingforswitch;

public class PatternMatchingForSwitch {

    static double getDoubleUsingIf(Object object) {
        double result;
        if (object instanceof Integer) {
            result = ((Integer) object).doubleValue();
        } else if (object instanceof Float) {
            result = ((Float) object).doubleValue();
        } else if (object instanceof String) {
            result = Double.parseDouble(((String) object));
        } else {
            result = 0d;
        }
        return result;
    }

    static double getDoubleUsingPatternMatchingForSwitch(Object object) {
        return switch (object) {
            case Integer i -> i.doubleValue();
            case Float f -> f.doubleValue();
            case String s -> Double.parseDouble(s);
            default -> 0d;
        };
    }

    public static void main(String[] args) {
        final Object object = args[0];
        System.out.printf("Using if: %s%n", getDoubleUsingIf(object));
        System.out.printf("Using PatternMatchingForSwitch: %s%n", getDoubleUsingPatternMatchingForSwitch(object));
    }

}
