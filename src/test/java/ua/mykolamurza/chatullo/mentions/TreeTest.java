package ua.mykolamurza.chatullo.mentions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
class TreeTest {
    /*
    @org.junit.jupiter.api.Test
    void construct() {
        List<String> data = Arrays.asList("Notch", "jeb_", "username1", "username2", "mykolamurza", "justADeni");
        //for (int i = 0; i < 10000; i++)
        //    Tree.construct(data);

        long starttime = System.nanoTime();
        Tree.construct(data);
        long endtime = System.nanoTime();
        Branch[] base = Tree.base;
        for (int i = 0; i <= 127; i++) {
            Branch start = base[i];
            if (start == null) {
                continue;
            }
            System.out.println("Depth: 0, char: " + (char) i);
            list(start, 1);
        }
        System.out.println("Tree construction time: " + (endtime - starttime) + " nanos");
    }

    // Recursive tests, yummy ;)
    void list(Branch branch, int depth) {
        if (branch.sub == null) {
            System.out.println("Depth: " + depth + ", char: " + branch.c + ", Branch End");
            return;
        }
        System.out.println("Depth: " + depth + ", char: " + branch.c);
        for (Branch next: branch.sub) {
            list(next, depth + 1);
        }
    }
    */

    @org.junit.jupiter.api.Test
    void visualize() {
        List<String> data = Arrays.asList("Notch", "jeb_", "username1", "username2", "mykolamurza", "justADeni");
        Tree.construct(data);
        Branch[][] base = Tree.base;
        ArrayList<Character> entrychars = new ArrayList<>();
        for (int i = 0; i <= 127; i++) {
            if (base[i] != null)
                entrychars.add((char)i);
        }
        for (char entrychar: entrychars) {
            Branch[] branches = base[entrychar];

            for (Branch branch: branches) {
                System.out.print(entrychar);
                //Branch branch = base[entrychar];
                listchar(branch, 0);
                System.out.println();
            }
        }
    }

    void listchar(Branch previous, int depth) {
        System.out.print("-" + previous.c);
        //if (previous.isEnd)
        //    System.out.println("-END");
        if (previous.sub != null) {
            for (int i = 0; i < previous.sub.length; i++) {
                if (i > 0) {
                    //System.out.println("-".repeat(depth));
                    System.out.println("\nBRANCH DIVERGE");
                }
                listchar(previous.sub[i], depth + 1);
            }
        }
    }
}