package ua.mykolamurza.chatullo.mentions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
class TreeTest {

    @org.junit.jupiter.api.Test
    void visualize() {
        List<String> data = Arrays.asList("Notch", "jeb_", "user", "used", "useful", "username1", "username2", "username3", "mykolamurza", "justADeni", "vt");
        Tree.construct(data);
        Branch[][] base = Tree.base;
        ArrayList<Character> entrychars = new ArrayList<>();
        for (int i = 0; i <= 127; i++) {
            if (base[i] != null)
                entrychars.add((char)i);
        }
        for (int j = 0; j < entrychars.size(); j++) {
            char entrychar = entrychars.get(j);
            Branch[] branches = base[entrychar];

            StringBuilder padding = new StringBuilder();
            if (j == 0) {
                if (entrychars.size() == 1)
                    padding.append("──");
                else
                    padding.append("┌──");
            } else if (j+1 != entrychars.size()) {
                padding.append("├──");
            } else {
                padding.append("└──");
            }

            padding.append(entrychar);

            for (int i = 0; i < branches.length; i++) {
                Branch branch = branches[i];

                if (branches.length > 1 && i != 0) {
                    padding.append("\n");
                    if (j+1 < entrychars.size())
                        padding.append("│");
                    else
                        padding.append(" ");

                    if (branches.length == 2) {
                        padding.append("  └──");
                    } else if (i + 1 != branches.length) {
                        padding.append("  ├──");
                    } else {
                        padding.append("  └──");
                    }
                } else {
                    padding.append("──");
                }

                padding.append(branch.c);
                if (branch.isEnd)
                    padding.append(".");
                listchar(branch, padding, 6);

            }
            System.out.println(padding.toString());
        }
    }

    void listchar(Branch previous, StringBuilder padding, int depth) {
        if (previous.sub == null)
            return;

        for (int i = 0; i < previous.sub.length; i++) {
            Branch branch = previous.sub[i];
            //if (branch.sub == null)
            //    continue;

            if (previous.sub.length > 1 && i != 0) {
                padding.append("\n");
                padding.append(" ".repeat(depth));
                if (previous.sub.length == 2) {
                    padding.append("└──");
                } else if (i + 1 != previous.sub.length) {
                    padding.append("├──");
                } else {
                    padding.append("└──");
                }
            } else {
                padding.append("──");
            }
            padding.append(branch.c);
            int dots = 0;
            if (branch.isEnd) {
                padding.append('.');
                dots++;
            }

            listchar(branch, padding, depth + 3 + dots);
        }
    }
}