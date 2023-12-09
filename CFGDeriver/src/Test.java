import java.util.Stack;

class PDATest {
    private static final char EPSILON = '!';

    public static void main(String[] args) {
        // Define the PDATest for the given CFG
        PDATest PDATest = new PDATest();
        // Membership test for the given string
        String inputString = "bbbbbbaaaaaaaaaaaaaaaaaaaaaabbbababbaaaaaaaaaaaaaaaaaaaaaabbbbbb";
        boolean isMember = PDATest.isMember(inputString);

        System.out.println("String \"" + inputString + "\" is " + (isMember ? "accepted" : "rejected") + " by the PDATest.");
    }

    // PDATest states
    private enum State {
        Q0, Q1, Q2
    }

    // PDATest stack symbol
    private enum StackSymbol {
        Z
    }

    // PDATest transitions
    private State transition(State currentState, char inputSymbol, char stackTop) {
        switch (currentState) {
            case Q0:
                if (inputSymbol == 'a' && stackTop == EPSILON) return State.Q1;
                if (inputSymbol == 'b' && stackTop == EPSILON) return State.Q2;
                break;
            case Q1:
                if (inputSymbol == 'a' && stackTop == 'a') return State.Q1;
                if (inputSymbol == 'b' && stackTop == 'a') return State.Q1;
                if (inputSymbol == 'b' && stackTop == EPSILON) return State.Q0;
                break;
            case Q2:
                if (inputSymbol == 'b' && stackTop == 'b') return State.Q2;
                if (inputSymbol == 'a' && stackTop == 'b') return State.Q2;
                if (inputSymbol == 'a' && stackTop == EPSILON) return State.Q0;
                break;
        }
        return null; // Invalid transition
    }

    // PDATest stack operations
    private StackSymbol pop(Stack<Character> stack) {
        return StackSymbol.Z;
    }

    private void push(Stack<Character> stack, StackSymbol symbol) {
        stack.push(EPSILON);
    }

    // Check if a given string is accepted by the PDATest
    private boolean isMember(String inputString) {
        Stack<Character> stack = new Stack<>();
        stack.push(EPSILON);
        State currentState = State.Q0;

        for (char inputSymbol : inputString.toCharArray()) {
            char stackTop = stack.pop();
            currentState = transition(currentState, inputSymbol, stackTop);
            if (currentState == null) {
                return false; // Invalid transition, reject the string
            }
            push(stack, StackSymbol.Z);
        }

        // Check if the PDATest is in an accepting state after processing the input string
        return currentState == State.Q0;
    }
}
