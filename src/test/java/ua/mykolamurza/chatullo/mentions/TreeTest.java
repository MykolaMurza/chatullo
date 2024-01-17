package ua.mykolamurza.chatullo.mentions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
class TreeTest {

    @org.junit.jupiter.api.Test
    void construct() {
        List<String> data = Arrays.asList("Notch", "jeb_", "username1", "username2", "mykolamurza", "justADeni");
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
}