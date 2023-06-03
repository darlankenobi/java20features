package patternmatchingforswitch;

public class PatternMatchingForSwitchWithGuardWhen {

    static double getDoubleTraditionalSwitch(Object object) {
        return switch (object) {
            case String s -> {
                if (s.length() > 0) {
                    yield Double.parseDouble(s);
                } else {
                    yield 0d;
                }
            }
            default -> 0d;
        };
    }

    static double getDoubleUsingGuardedPattern(Object object) {
        return switch (object) {
            case String s when s.length() > 0 -> Double.parseDouble(s);
            default -> 0d;
        };
    }

    public static void main(String[] args) {
        System.out.printf("Using traditional switch with if guard: %s%n", getDoubleTraditionalSwitch(""));
        System.out.printf("Using traditional switch with if guard: %s%n", getDoubleTraditionalSwitch("100"));
        System.out.printf("Using new guard: %s%n", getDoubleUsingGuardedPattern(""));
        System.out.printf("Using new guard: %s%n", getDoubleUsingGuardedPattern("20000"));
    }

}
