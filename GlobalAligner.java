import java.util.*;
import java.io.*;
import java.lang.Math;

public class GlobalAligner extends Aligner implements AlignerInterface{
    private int alignmentScore;
    private int indelPenalty;
    private int mismatchPenalty;
    private int matchScore;
    private int[][] outputMatrix;
    private int[][] backtrack;
    private ArrayList<String> firstList;
    private ArrayList<String> secondList;

    public GlobalAligner(String a, String b, String filename, int indelPenalty) throws FileNotFoundException, IOException{
        super(a, b, filename);
        this.indelPenalty = indelPenalty;
        this.outputMatrix = new int[super.firstString.length()+1][super.secondString.length()+1];
        this.backtrack = new int[super.firstString.length()+1][super.secondString.length()+1];
        this.firstList = new ArrayList<String>();
        this.secondList = new ArrayList<String>();
        super.initializeHashmap();
        super.readScoringMatrix();
    }

    public GlobalAligner(String a, String b, int indelPenalty, int mismatchPenalty, int matchScore){
        super(a, b);
        this.indelPenalty = indelPenalty;
        this.mismatchPenalty = mismatchPenalty;
        this.matchScore = matchScore;
    }

    public int getAlignmentScore(){
        return this.alignmentScore;
    }
    

    public void align(){
        int x = firstString.length()+1;
        int y = secondString.length()+1;
        
        int counter = 0;
        for(int i = 0; i < outputMatrix.length; i++){
            outputMatrix[i][0] = counter * indelPenalty;
            backtrack[i][0] = 3;
            counter--;
        }
        counter = 0;
        for(int j = 0; j < outputMatrix[0].length; j++){
            outputMatrix[0][j] = counter * indelPenalty;
            backtrack[0][j] = 2;
            counter--;
        }

        for( int i = 1; i < outputMatrix.length; i++){
            for( int j = 1; j < outputMatrix[i].length; j++){
                int diagonal = super.scoringMatrix[aminoAcidMap.get(Character.toString(firstString.charAt(i-1)))][aminoAcidMap.get(Character.toString(secondString.charAt(j-1)))];
                outputMatrix[i][j] = Math.max(outputMatrix[i-1][j-1] + diagonal, Math.max(outputMatrix[i][j-1] - indelPenalty, outputMatrix[i-1][j] - indelPenalty));

                if( outputMatrix[i][j] == outputMatrix[i-1][j-1] + diagonal){
                    backtrack[i][j] = 1;
                }
                else if( outputMatrix[i][j] == outputMatrix[i][j-1] - indelPenalty){
                    backtrack[i][j] = 2;
                }
                else if( outputMatrix[i][j] == outputMatrix[i-1][j] - indelPenalty){
                    backtrack[i][j] = 3;
                }

            }
        }
        this.alignmentScore = outputMatrix[firstString.length()][secondString.length()];
        this.memoization(firstString.length(), secondString.length());
    }

    public void alignSequences(){
        
    }

    public void memoization(int i, int j){
        if( i == 0 && j == 0){
            return;
        }

        if(backtrack[i][j] == 1){
            memoization(i-1,j-1);
            firstList.add(Character.toString(firstString.charAt(i-1)));
            secondList.add(Character.toString(secondString.charAt(j-1)));
        }
        else if(backtrack[i][j] == 2){
            memoization(i,j-1);
            firstList.add("-");
            secondList.add(Character.toString(secondString.charAt(j-1)));
        }
        else if(backtrack[i][j] == 3){
            memoization(i-1,j);
            firstList.add(Character.toString(firstString.charAt(i-1)));
            secondList.add("-");
        }
    }

    public void printAlignment(){
        for(int i = 0; i < firstList.size(); i++){
            System.out.print(firstList.get(i));
        }
        System.out.println();
        for( int i = 0; i < secondList.size(); i++ ){
            System.out.print(secondList.get(i));
        }
        System.out.println();
    }

    public void printBacktrack(){
        for(int i = 0; i < this.backtrack.length; i++){
            for( int j = 0; j < this.backtrack[i].length; j++){
                System.out.print( backtrack[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printMatrix(){
        for(int i = 0; i < outputMatrix.length; i++){
            for( int j = 0; j < outputMatrix[i].length; j++){
                System.out.print(outputMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void debug(){
        System.out.println(super.scoringMatrix[aminoAcidMap.get("P")][aminoAcidMap.get("E")]);
    }

}