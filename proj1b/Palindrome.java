public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque dequeString = new ArrayDeque();
        for (int i = 0; i < word.length(); i++) {
            dequeString.addLast(word.charAt(i));
        }
        return dequeString;
    }

    //该方法为非递归方法
    /*
    public boolean isPalindrome(String word) {
        if ((word.length() == 0)||(word.length() == 1)) {
            return true;
        }
        Deque<Character> dequeString = wordToDeque(word);
        while (!dequeString.isEmpty()) {
            Character first = dequeString.removeFirst();
            Character last = dequeString.removeLast();
            if ((first == null) || (last == null)) {
                break;
            }
            if(first != last) {
                return false;
            }
        }
        return true;
    }
    */

    //递归方法
    public boolean isPalindrome(String word) {
        Deque<Character> dequeString = wordToDeque(word);
        return isPalindromeHelper(dequeString);
    }

    private boolean isPalindromeHelper(Deque<Character> deque) {
        if ((deque.size() == 0) || (deque.size() == 1)) {
            return true;
        }
        Character first = deque.removeFirst();
        Character last = deque.removeLast();
        if (first == last) {
            return isPalindromeHelper(deque);
        }
        return false;
    }


    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dequeString = wordToDeque(word);
        return isPalindromeHelper(dequeString, cc);
    }

    private boolean isPalindromeHelper(Deque<Character> deque, CharacterComparator cc) {
        if ((deque.size() == 0) || (deque.size() == 1)) {
            return true;
        }
        Character first = deque.removeFirst();
        Character last = deque.removeLast();
        if (cc.equalChars(first, last)) {
            return isPalindromeHelper(deque, cc);
        }
        return false;
    }
}
