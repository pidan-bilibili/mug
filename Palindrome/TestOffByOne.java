import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testequalChars() {
        assertTrue("fail", offByOne.equalChars('a', 'b'));
        assertFalse("fail", offByOne.equalChars('a', 'z'));
        assertTrue("fail", offByOne.equalChars('b', 'a'));
        assertFalse("fail", offByOne.equalChars('z', 'a'));
        assertTrue("fail", offByOne.equalChars('n', 'm'));
        assertTrue("fail", offByOne.equalChars('%', '&'));
        assertFalse("fail", offByOne.equalChars('Z', 'z'));
        assertFalse("fail", offByOne.equalChars('B', 'a'));
        assertFalse("fail", offByOne.equalChars('8', 'a'));
        assertFalse("fail", offByOne.equalChars('@', 'b'));
        assertFalse("fail", offByOne.equalChars('@', '*'));
        assertFalse("fail", offByOne.equalChars('9', '1'));
        assertFalse("fail", offByOne.equalChars('&', 'p'));
        assertFalse("fail", offByOne.equalChars(',', '1'));
        assertFalse("fail", offByOne.equalChars('[', 'p'));
        assertFalse("fail", offByOne.equalChars('A', 'a'));
        assertFalse("fail", offByOne.equalChars('！', ';'));
        assertFalse("fail", offByOne.equalChars('9', 'X'));
        assertFalse("fail", offByOne.equalChars('0', '-'));
    }
}
