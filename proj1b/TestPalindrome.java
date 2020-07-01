import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testWordPalindrome() {
        //测试长度为0的字符串
        assertTrue(palindrome.isPalindrome(""));
        //测试长度为1的字符串
        assertTrue(palindrome.isPalindrome("a"));
        //测试回文
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("racecar"));
        //测试非回文
        assertFalse(palindrome.isPalindrome("horse"));
    }

    @Test
    public void testWordPalindrome2() {
        OffByOne cc = new OffByOne();
        //测试长度为0的字符串
        assertTrue(palindrome.isPalindrome("", cc));
        //测试长度为1的字符串
        assertTrue(palindrome.isPalindrome("a", cc));
        //测试回文
        assertFalse(palindrome.isPalindrome("moon", cc));
        assertTrue(palindrome.isPalindrome("detrude",cc));
        assertFalse(palindrome.isPalindrome("racecar", cc));
        //测试非回文
        assertFalse(palindrome.isPalindrome("horse", cc));
    }

    @Test
    public void testWordPalindrome3() {
        OffByN cc = new OffByN(5);
        //测试长度为0的字符串
        assertTrue(palindrome.isPalindrome("", cc));
        //测试长度为1的字符串
        assertTrue(palindrome.isPalindrome("a", cc));
        //测试回文
        assertFalse(palindrome.isPalindrome("moon", cc));
        assertTrue(palindrome.isPalindrome("binding",cc));
        assertFalse(palindrome.isPalindrome("racecar", cc));
        //测试非回文
        assertFalse(palindrome.isPalindrome("horse", cc));
    }
}
