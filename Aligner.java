import java.io.*;
import java.util.*;
import java.lang.Math;

public class Aligner{
    protected String firstString;
    protected String secondString;
    protected int[][] scoringMatrix;
    protected String scoringMatrixFile;
    protected HashMap<String, Integer> aminoAcidMap;

    public Aligner(String a, String b, String filename){
        this.firstString = a;
        this.secondString = b;
        this.scoringMatrixFile = filename;
        this.scoringMatrix = new int[20][20];
        this.aminoAcidMap = new HashMap<String, Integer>();

    }

    public Aligner(String a, String b){
        this.firstString = a;
        this.secondString = b;
    }

    public String getFirstString(){
        return this.firstString;
    }

    public String getSecondString(){
        return this.secondString;
    }

    public void initializeHashmap(){
        String[] aminoAcids = {"A", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "Y"};
        for(int i = 0; i < aminoAcids.length; i++){
            this.aminoAcidMap.put(aminoAcids[i], i);
        }
    }
    
    public void readScoringMatrix() throws FileNotFoundException, IOException{
        ArrayList<String[]> inputLines = new ArrayList<String[]>();
        FileReader reader = new FileReader(this.scoringMatrixFile);
        BufferedReader bReader = new BufferedReader(reader);
        String line;
        while((line = bReader.readLine()) != null){
            inputLines.add(line.split(" "));
        }
        
        int i = 0;
        for(String[] d : inputLines){
            for(int j = 0; j < 20; j++){
                this.scoringMatrix[i][j] = Integer.parseInt(d[j]);
            }
            i++;
        }
        bReader.close();
        //printMatrix(scoringMatrix);
    }

    public void printMatrix(int[][] matrix){
        for(int k = 0; k < matrix.length; k++){
            for(int b = 0; b < matrix[k].length;b++){
                System.out.print(matrix[k][b] + " ");
            }
            System.out.println();
        }
    }
}