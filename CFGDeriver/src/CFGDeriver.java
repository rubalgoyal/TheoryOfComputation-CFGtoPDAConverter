import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.SortedMap;

public class CFGDeriver {
    public static void main(String[] args) throws IOException {
        try{
            if(args.length == 0){
                System.out.println("\nMAYBE YOU ARE TRYING TO USE HARD CODED VALUES\nOR \nPASS ARGUMENTS AS:\nboundValue cfgEncodingFile inputString\n");
                System.exit(0);
            }
            int boundValue = Integer.parseInt(args[0]);
            File encodingFile = new File(args[1]);
            File inputStringFile = new File(args[2]);
//            int boundValue = 1;
//            File encodingFile = new File("/Users/rubalgoyal/Desktop/561_TOC/CFGDeriver/TC4/evals/L3Gb.txt");
//            File inputStringFile = new File("/Users/rubalgoyal/Desktop/561_TOC/CFGDeriver/TC4/evals/eval3_1.txt");

            CFGDecoder cfgDecoder =new CFGDecoder(encodingFile);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputStringFile));
            String line;
            if((line = bufferedReader.readLine() ) != null){
                Result result = cfgDecoder.isStringAccepted(line, boundValue);
                boolean isAccepted = result.isStringDerived();
                String printMessage = isAccepted ? "yes": "no";
                System.out.println(printMessage);
            }
            else
                System.out.println("Input String file is empty");
        }
        catch (OutOfMemoryError e){
            System.out.println("\nOhhhh!! OutOfMemoryError error.\nInput string couldn't be derived, loops forever.\n");
        }


    }
}
