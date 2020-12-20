public class OffByN implements CharacterComparator {

    private int num;
    public OffByN(int n) {
        this.num = n;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int X = x;
        int Y = y;
        if (X - Y == num || Y - X == num) {
            return true;
        }
        return false;
    }

}
