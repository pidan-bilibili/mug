import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual1 = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual1 += d.removeFirst();
        }
        assertEquals("persiflage", actual1);

        Deque e = palindrome.wordToDeque("T A");
        String actual2 = "";
        for (int i = 0; i < "T A".length(); i++) {
            actual2 += e.removeFirst();
        }
        assertEquals("T A", actual2);

        Deque f = palindrome.wordToDeque("t 7");
        String actual3 = "";
        for (int i = 0; i < "t 7".length(); i++) {
            actual3 += f.removeFirst();
        }
        assertEquals("t 7", actual3);
    }

    @Test
    public void testisPalindrome() {
        assertTrue("fail", palindrome.isPalindrome("rur"));
        assertTrue("fail", palindrome.isPalindrome("p"));
        assertFalse("fail", palindrome.isPalindrome("boa"));
        assertTrue("fail", palindrome.isPalindrome(""));
    }

    @Test
    public void testisPalindromeOffByOne() {
        CharacterComparator offByone = new OffByOne();
        assertTrue("fail", palindrome.isPalindrome("ab", offByone));
        assertTrue("fail", palindrome.isPalindrome("a", offByone));
        assertTrue("fail", palindrome.isPalindrome(" ", offByone));
        assertFalse("fail", palindrome.isPalindrome("ab", null));
        assertFalse("fail", palindrome.isPalindrome(null, offByone));
        assertTrue("fail", palindrome.isPalindrome("abab", offByone));
        assertTrue("fail", palindrome.isPalindrome("abxab", offByone));
    }
}
