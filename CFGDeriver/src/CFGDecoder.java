import java.io.*;
import java.util.*;

/*
Class to help converting CFG to PDA and passing the correct step count based on bound value passed.
@author Rubal Goyal
 */
public class CFGDecoder {
    private File encodingFile;
    private PDA pda;

    public CFGDecoder(File encodingFile) {
        this.encodingFile = encodingFile;
        ruleDecoder();
    }

    private void ruleDecoder() {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.encodingFile));
            int numRules = Integer.parseInt(bufferedReader.readLine());

            this.pda = new PDA();

            String currentRule;
            while( (currentRule = bufferedReader.readLine()) != null ) {
                currentRule = currentRule.replace(" ", "");
                this.pda.addRule(currentRule);
            }
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public Result isStringAccepted(String inputString, int boundValue){
        int minSteps = 0;
        switch (boundValue){
            case 1:
                minSteps = 100;
                break;
            case 2:
                minSteps = 100;
                break;
            case 3:
                minSteps = 2*inputString.length() - 1;
                break;
        }

        int totalStepCount = this.pda.deriveString( putStringInReverse(inputString), minSteps);
        boolean isStringDerived = totalStepCount > -1 ? true : false;
        Result result = new Result(totalStepCount, isStringDerived);

        return result;
    }

    private Stack<Character> putStringInReverse(String inputString){
        Stack<Character> stringStack = new Stack<>();
        for(int i = inputString.length()-1; i >=0; i--){
            stringStack.add(inputString.charAt(i));
        }

        return stringStack;
    }

}

