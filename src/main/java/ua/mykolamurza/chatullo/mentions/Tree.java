package ua.mykolamurza.chatullo.mentions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Tree {

    // Utility class, private constructor
    // so that it can't get instantiated
    private Tree() {}

    // All 128 characters from ascii table: https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html
    // When no player IGN starts with said char (corresponding to index), it's null
    public static Branch[] base = new Branch[128];

    public static void construct(List<String> strings) {
        Branch[] newbase = new Branch[128];
        for (String string: strings) {
            if (string.isEmpty())
                continue;

            char c = string.charAt(0);
            Branch current = newbase[c];
            if (current == null){
                current = new Branch((char)129, new Branch[0]);
                newbase[c] = current;
            }
            add(string.substring(1), current);
        }
        base = newbase;
    }

    private static void add(String left, Branch previous) {
        // We have reached the end of our string
        if (left.isEmpty())
            return;

        char c = left.charAt(0);
        // Branch was newly created, no sub-branches
        if (previous.c == 129) {
            previous.c = c;

            if (left.length() == 1)
                return;

            Branch next = new Branch((char) 129, new Branch[0]);
            previous.sub = new Branch[]{next};

            add(left.substring(1), next);
            return;
        }

        // Branch already has sub-branches
        for (Branch branch : previous.sub) {
            // Found a sub-branch with our char, follow it
            if (branch.c == c) {
                if (left.length() > 1)
                    add(left.substring(1), branch);

                break;
            }
        }
        // Did not find any sub-branch with our char, create it
        Branch[] augmentedbranches = new Branch[previous.sub.length + 1];
        System.arraycopy(previous.sub, 0, augmentedbranches, 0, previous.sub.length);
        Branch next = new Branch(c, new Branch[0]);
        // And add it to the sub-branches
        augmentedbranches[augmentedbranches.length - 1] = next;
        previous.sub = augmentedbranches;

        if (left.length() > 1)
            add(left.substring(1), next);
    }

}
