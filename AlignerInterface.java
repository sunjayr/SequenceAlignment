public interface AlignerInterface {
    public int getAlignmentScore();
    public void alignSequences();
    public void alignPeptides();
    public void memoization(int i, int j);
    public void printAlignment();
}