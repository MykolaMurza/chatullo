package ua.mykolamurza.chatullo.mentions;

import java.util.Arrays;
import java.util.List;

public class Tree {

    private Tree() {}

    // All 128 characters from ascii table: https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html
    // When no player IGN starts with said char (corresponding to index), it's null
    public static Branch[][] base = new Branch[128][];

    public static void construct(List<String> strings) {
        Branch[][] newbase = new Branch[128][];
        for (String string: strings) {
            if (string.isEmpty())
                continue;

            char c = string.charAt(0);
            if (newbase[c] == null) {
                String left = string.substring(1);
                Branch current = new Branch((char)0,null);
                newbase[c] = new Branch[]{current};
                while (!left.isEmpty()) {
                    current.c = left.charAt(0);
                    if (left.length() < 2)
                        break;

                    Branch next = new Branch((char)0, null);
                    current.sub = new Branch[]{next};
                    current = next;
                    left = left.substring(1);
                }
                current.isEnd = true;
            } else {
                if (string.length() == 1)
                    continue;

                Branch[] trunk = newbase[c];
                String left = string.substring(1);
                char t = left.charAt(0);
                Branch previous = null;
                // Find first branch stemming from first char
                for (Branch branch: trunk) {
                    if (branch.c == t) {
                        previous = branch;
                        left = left.substring(1);
                        t = left.charAt(0);
                        break;
                    }
                }

                // Branch found, follow it
                if (previous != null) {
                    while (!left.isEmpty()) {
                        if (previous.sub == null)
                            break;

                        boolean found = false;
                        for (Branch branch: previous.sub) {
                            if (branch.c == t) {
                                found = true;
                                previous = branch;
                                left = left.substring(1);
                                t = left.charAt(0);
                                break;
                            }
                        }
                        if (!found)
                            break;
                    }
                }

                if (left.length() == 0)
                    continue;

                // Branched off, start creating new ones

                // Branched off right at the trunk
                if (previous == null) {
                    previous = new Branch(t,null);
                    Branch[] branches = newbase[c];
                    Branch[] augmented = new Branch[branches.length + 1];
                    System.arraycopy(branches, 0, augmented, 0, branches.length);
                    augmented[branches.length] = previous;
                    newbase[c] = augmented;
                    left = left.substring(1);
                    System.out.println("made new branch with: " + t + " left: " + left);
                    t = left.charAt(0);
                } else {
                    System.out.println("previous with: " + previous.c + " wasnt null with: " + t + " left: " + left);
                }

                if (left.length() == 0)
                    continue;

                Branch next = new Branch(t, null);
                if (previous.sub == null){
                    previous.sub = new Branch[]{next};
                } else {
                    Branch[] augmented = new Branch[previous.sub.length + 1];
                    System.arraycopy(previous.sub, 0, augmented, 0, previous.sub.length);
                    augmented[previous.sub.length] = next;
                    previous.sub = augmented;
                }
                previous = next;
                left = left.substring(1);

                while (left.length() > 0) {
                    t = left.charAt(0);
                    next = new Branch(t, null);
                    previous.sub = new Branch[]{next};
                    previous = next;
                    left = left.substring(1);
                }
                next.isEnd = true;
            }
        }
        base = newbase;
    }

}
