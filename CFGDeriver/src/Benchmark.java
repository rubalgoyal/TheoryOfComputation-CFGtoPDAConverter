import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Benchmark {

    public static void main(String[] args) throws IOException {

        String path = args[0];
//        String path = "/Users/rubalgoyal/Desktop/561_TOC/CFGDeriver/TC4/evals/";
        HashMap<String, List<String>> encodingInput = fileStrings();
        ArrayList<Integer> boundValues = new ArrayList<>();
        boundValues.add(1);
        boundValues.add(2);
        boundValues.add(3);

        CFGDecoder cfgDecoder;
        File encodingFile;
        BufferedReader bufferedReader;
        String line;
        boolean isAccepted;
        String printMessage = "";
        System.out.printf("\n%-25s %-25s %-15s %-15s %-15s\n"
                                ,"Encoding"
                                ,"InputString"
                                ,"BoundValue"
                                ,"StepNumbers"
                                ,"Output"
                );
        System.out.println("-----------------------------------------------------------------------------------------");
        for (String encoding: encodingInput.keySet()) {
            for (String inputString: encodingInput.get(encoding)) {
                for (Integer bound: boundValues) {
                    if ( encoding.contains("cnf") && bound < 3)
                        continue;
                    if ( encoding.contains("b") && bound == 3)
                        continue;

                    encodingFile = new File(String.join("", path, encoding));
                    cfgDecoder = new CFGDecoder(encodingFile);
                    bufferedReader = new BufferedReader(new FileReader(String.join("", path, inputString)));

                    if((line = bufferedReader.readLine() ) != null){
                        Result result = new Result();
                        try{
                            result = cfgDecoder.isStringAccepted(line, bound);
                            isAccepted = result.isStringDerived();
                            printMessage = isAccepted ? "yes": "no";
                        }
                        catch (Exception e){
                            isAccepted = false;
                            printMessage = "No string derivation";
                        }finally {
                            System.out.printf("%-25s %-25s %-15s %-15s %-15s\n",
                                                    encoding,
                                                    inputString,
                                                    bound,
                                                    result.getTotalStepCount(),
                                                    printMessage
                                );
                        }
                    }

                }
            }
            System.out.println("-----------------------------------------------------------------------------------------\n");
        }
    }

    public static HashMap<String, List<String>> fileStrings(){
        HashMap<String, List<String>> encodingInput = new HashMap<>();
//        encodingInput.put("L1Gb.txt", new ArrayList<>());
//        encodingInput.get("L1Gb.txt").add("eval1_1.txt");
//        encodingInput.get("L1Gb.txt").add("eval1_2.txt");
//        encodingInput.get("L1Gb.txt").add("eval1_3.txt");
//
//        encodingInput.put("L1Gcnf.txt", new ArrayList<>());
//        encodingInput.get("L1Gcnf.txt").add("eval1_1.txt");
//        encodingInput.get("L1Gcnf.txt").add("eval1_2.txt");
//        encodingInput.get("L1Gcnf.txt").add("eval1_3.txt");

        encodingInput.put("L2Gb.txt", new ArrayList<>());
        encodingInput.get("L2Gb.txt").add("eval2_1.txt");
        encodingInput.get("L2Gb.txt").add("eval2_2.txt");
        encodingInput.get("L2Gb.txt").add("eval2_3.txt");

        encodingInput.put("L2cnf.txt", new ArrayList<>());
        encodingInput.get("L2cnf.txt").add("eval2_1.txt");
        encodingInput.get("L2cnf.txt").add("eval2_2.txt");
        encodingInput.get("L2cnf.txt").add("eval2_3.txt");

        encodingInput.put("L3Gb.txt", new ArrayList<>());
        encodingInput.get("L3Gb.txt").add("eval3_1.txt");
        encodingInput.get("L3Gb.txt").add("eval3_2.txt");
        encodingInput.get("L3Gb.txt").add("eval3_3.txt");

        encodingInput.put("L3cnf.txt", new ArrayList<>());
        encodingInput.get("L3cnf.txt").add("eval3_1.txt");
        encodingInput.get("L3cnf.txt").add("eval3_2.txt");
        encodingInput.get("L3cnf.txt").add("eval3_3.txt");

        return encodingInput;
    }
}
