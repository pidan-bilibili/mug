public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        int X = x;
        int Y = y;
        if (X - Y == 1 || Y - X == 1) {
            return true;
        }
        return false;
    }
}
