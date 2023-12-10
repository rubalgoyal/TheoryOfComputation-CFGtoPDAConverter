import java.util.*;

public class PDA {
    private Map<String, List<String>> rules;
    private Character EPSILON = '!';
    private Character SPACE = '_';
    private final Character START_STATE = 'S';
    private Character STACK_END = '$';

    public PDA(){
        this.rules = new HashMap<>();
    }

    public void addRule(String rule){
        String[] parts = rule.split("->");
        String leftSide = parts[0].trim();
        String rightSide = parts[1].trim();

        if(!this.rules.containsKey(leftSide))
            this.rules.put(leftSide, new ArrayList<>());

        this.rules.get(leftSide).add(rightSide);
    }
    public Result deriveString(Stack<Character> inputString, int minSteps){
        ArrayList< HashMap<Stack<Character>,Stack<Character>> > pdaCopies = new ArrayList<>();
        pdaCopies.add(
                new HashMap<>(){
                    {
                        Stack<Character> currentStack = new Stack<>();
                        currentStack.clear();
                        put(currentStack, inputString);
                    }
                }
        );

        Stack<Character> stringRead;
        boolean isAccepted = false;
        boolean isFirstCopy = false;
        int totalStepCount = 0;

        while( !pdaCopies.isEmpty() ){
            // Start with next copy and start from the string where we created the next copy
            Stack<Character> currentStack = new Stack<>();
            HashMap<Stack<Character>, Stack<Character> > nextCopy = pdaCopies.get(0);
            currentStack = nextCopy.keySet().iterator().next();
            stringRead = nextCopy.values().iterator().next();
            pdaCopies.remove(0);

            //For the first copy of PDA, we need to initialize. For rest copies, we will clone.
            if(!isFirstCopy && currentStack.isEmpty()){
                currentStack.push(this.STACK_END);
                currentStack.push(this.START_STATE);
                isFirstCopy = true;
            }


            int stepNumber = 0;
            while(stepNumber <= minSteps){
                Character stackTop = currentStack.pop();
                if (stackTop.equals(this.STACK_END)){
                    if ( stringRead.isEmpty() )
                        isAccepted = true;
                    else{
                        isAccepted = false;
                        break;
                    }
                }
                // if the top of the stack is a terminal variable, we have to read from the input string
                else if( Character.isLowerCase(stackTop) || stackTop.equals(this.SPACE) || stackTop.equals(this.EPSILON)  ){
                    if( !stringRead.isEmpty() && stringRead.peek().equals(stackTop) ){
                        stringRead.pop();
                    }
                    else if ( stackTop.equals(this.EPSILON) )
                        continue;
                    else {
                        // If there is mismatch between the stack variables, string will not be accepted by this copy
                        isAccepted = false;
                        break;
                    }
                }
                // if the top of the stack is a non-terminal variable, apply a rule
                else if(Character.isUpperCase(stackTop)){
                    List<String> applyRules = this.rules.get(stackTop.toString());
                    for(int i = 0; i <= applyRules.size() -1; i++){
                        if( i == applyRules.size() -1 ){
                            addRuleInReverseOrder(applyRules.get(i), currentStack);
                        }
                        else{
                            Stack<Character> newPdaStackCopy = (Stack<Character>)currentStack.clone();
                            addRuleInReverseOrder(applyRules.get(i), newPdaStackCopy);
                            Stack<Character> finalReadString = (Stack<Character>)stringRead.clone();
                            pdaCopies.add(new HashMap<>() {
                                {put(newPdaStackCopy, finalReadString);}
                            } );
                        }
                    }
                    stepNumber++;
                }
                if(isAccepted)
                    break;
            }

            totalStepCount += stepNumber;
            if(isAccepted)
                break;
        }

        Result result = new Result(totalStepCount, isAccepted);

        return result;
    }

    private void addRuleInReverseOrder(String rule, Stack<Character> pdaStack){
        for(int i = rule.length()-1; i >=0; i--){
            pdaStack.add(rule.charAt(i));
        }
    }

    public int calculateStepB(String inputString){
        int maxChildren = 0;
        for (String key: this.rules.keySet()) {
            if(this.rules.get(key).size() > maxChildren)
                maxChildren = this.rules.get(key).size();
        }

        long maxParseTreeHeight = Math.round( Math.log(inputString.length()) / Math.log(2) );
        double minSteps = (Math.pow((double) maxChildren, maxParseTreeHeight) - 1)/(maxChildren - 1);

        return (int) Math.round(minSteps);
    }
}
