public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> a = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            a.addLast(word.charAt(i));
        }
        return a;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        Deque<Character> s = wordToDeque(word);
        int num = s.size() - 1;
        for (int i = 0; i < s.size(); i++) {
            if (s.get(i) == s.get(num - i)) {
                return true;
            }
            return false;
        }
        return true;
    }


    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        Deque<Character> b = wordToDeque(word);
        int num = b.size() - 1;
        if (cc == null) {
            return isPalindrome(word);
        } else if (b.size() == 1 || b.size() == 0) {
            return true;
        } else {
            for (int i = 0; i < b.size(); i++) {
                if (num % 2 == 1) {
                    if (cc.equalChars(b.get(i), b.get(num - i))) {
                        return true;
                    }
                    return false;
                } else {
                    for (int l = 0; l < num / 2; l++) {
                        if (cc.equalChars(b.get(l), b.get(num - l))) {
                            return true;
                        }
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
