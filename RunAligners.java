import java.util.*;
import java.lang.Math;
import java.io.*;

public class RunAligners{
    public RunAligners(){

    }

    public void runGlobalAlignment() throws FileNotFoundException, IOException{
        GlobalAligner test = new GlobalAligner("PLEASANTLY","MEANLY", "BLOSUM62.txt", 5);
        test.align();
        System.out.println(test.getAlignmentScore());
        test.printAlignment();
    }

    public void runLocalAlignment() throws FileNotFoundException, IOException{
        LocalAligner test = new LocalAligner("MEANLYPRTEINSTRING", "PLEASANTLYEINSTEIN", "PAM250.txt", 5);
        test.align();
        System.out.println("Alignment Score: " + test.getAlignmentScore());
        test.printAlignment();
    }

    public void runOverlapAlignment(){

    }

    public void runMultipleAlignment(){
        
    }

    public void runAffineGapAlignment(){
        
    }

    public void runFittingAlignment(){

    }
}