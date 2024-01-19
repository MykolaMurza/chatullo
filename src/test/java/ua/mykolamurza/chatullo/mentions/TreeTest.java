package ua.mykolamurza.chatullo.mentions;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TreeTest {

    //List<String> data = Arrays.asList("Notch", "jeb_", "user", "used", "useful", "usernai", "username1", "username2", "username3", "mykolamurza", "justADeni", "xz");
    List<String> data = Arrays.asList("ShadowDragon64", "MysticCrafter", "DiamondRover", "LavaLeaper", "BlazeChaser",
            "ThunderPenguin", "FrostyFox123", "IronWanderer", "JungleWhisperer", "PixelPioneer", "AquaVortex", "EmberEagle", "GalacticNomad",
            "NetherNinjaX", "QuantumQuasar", "SkywardSamurai", "ObsidianOracle", "CreeperCommander", "StarryStitcher", "SilverStriker",
            "EnderExplorer", "MysticMango", "StormyStardust", "WanderingWyvern", "PixelProwler", "CosmicCraze", "LunarLynx", "BlazeBard",
            "AquaVortex", "EmberEagle", "GalacticNomad", "NetherNinjaX", "RubyRider", "QuantumQuasar", "SkywardSamurai", "ObsidianOracle",
            "CreeperCommander", "StarryStitcher", "SilverStriker", "TerraTitanium", "EnderExplorer", "MysticMango", "StormyStardust",
            "WanderingWyvern", "PixelProwler", "CosmicCraze", "LunarLynx", "DiamondDynamo", "ArcticArchitect", "EnchantedExplorer",
            "PhantomPioneer", "SunlitSorcerer", "DragonDreamer", "GildedGolem", "EmeraldEnigma", "TwilightTinkerer", "QuantumQuill",
            "ObsidianOasis", "LavaLuminary", "EnderEmpress", "CreeperCatalyst", "StarlightSeeker", "IronIgniter", "CelestialCrafter",
            "NetherNomad", "AquaAdventurer", "RubyRogue", "MysticMule", "StormySeeker", "EmberElemental", "PixelPaladin", "LunarLabyrinth");

    @Order(3)
    @Test
    void visualize() {
        Tree.construct(data);
        Branch[][] base = Tree.base;
        ArrayList<Character> entrychars = new ArrayList<>();
        for (int i = 0; i <= 122; i++) {
            if (base[i] != null)
                entrychars.add((char)i);
        }
        for (int j = 0; j < entrychars.size(); j++) {
            char entrychar = entrychars.get(j);
            Branch[] branches = base[entrychar];

            StringBuilder padding = new StringBuilder();

            boolean startpadding = true;
            if (j == 0) {
                if (entrychars.size() == 1) {
                    padding.append("──");
                    startpadding = false;
                } else {
                    padding.append("┌──");
                }
            } else if (j+1 != entrychars.size()) {
                padding.append("├──");
            } else {
                padding.append("└──");
                startpadding = false;
            }

            padding.append(entrychar);

            List<Integer> additional = new ArrayList<>();

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
                listchar(branch, padding, 6, startpadding, additional);

            }

            if (!additional.isEmpty()) {
                for (int index: additional) {
                    int previous = 0;
                    for (String split: padding.toString().split("\n")) {
                        if (split.length() <= index)
                            continue;

                        if (split.charAt(index) == ' ') {
                            padding.setCharAt(index + previous, '│');
                        }
                        previous += split.length() + 1;
                    }
                }
            }
            System.out.println(padding);
        }
    }

    void listchar(Branch previous, StringBuilder padding, int depth, boolean startpadding, List<Integer> additional) {
        if (previous.sub == null)
            return;

        for (int i = 0; i < previous.sub.length; i++) {
            Branch branch = previous.sub[i];

            if (previous.sub.length > 1 && i != 0) {
                padding.append("\n");

                if (startpadding)
                    padding.append("│").append(" ".repeat(depth-1));
                else
                    padding.append(" ".repeat(depth));

                if (i + 1 != previous.sub.length) {
                    padding.append("├──");
                    if (!additional.contains(depth))
                        additional.add(depth);
                } else {
                    padding.append("└──");
                    if (previous.sub.length > 2)
                        if (!additional.contains(depth))
                            additional.add(depth);
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

            listchar(branch, padding, depth + 3 + dots, startpadding, additional);
        }
    }

    @Order(1)
    @Test
    void constructColdpath() {
        long start = System.nanoTime();
        Tree.construct(data);
        long end = System.nanoTime();

        long diff = end - start;
        System.out.println("Cold path tree construction took " + diff/1000000 + "ms and " + diff%1000000 + "ns");
    }

    @Order(2)
    @Test
    void constructHotpath() {
        for (int i = 0; i < 10_000; i++)
            Tree.construct(data);

        long start = System.nanoTime();
        Tree.construct(data);
        long end = System.nanoTime();

        long diff = end - start;
        System.out.println("Hot path tree construction took " + diff/1000000 + "ms and " + diff%1000000 + "ns");
    }

    // Generated with ChatGPT
    private final String[] messages = new String[]{
            "Hey, Notch, just stumbled upon a mineshaft – want to join the adventure? It's at coordinates -150, 30, 200.",
            "Any jeb_ fans here? I'm building a rainbow-themed village; your creative input would be awesome!",
            "Looking for a someone to help me test my redstone contraption. Meet me at my base at sunset, and bring some cobblestone!",
            "Hey, used, I found a spawner near your base. Let's team up and turn it into an XP farm – sound useful?",
            "username1, I'm hosting a PvP tournament at my arena. Grab your best gear and get ready for some action!",
            "how about a group expedition to the Nether this weekend? justADeni, mykolamurza"
    };

    @Order(4)
    @Test
    void matchtest() {
        Tree.construct(data);

        // Regex matching, inspired by https://www.youtube.com/watch?v=Ldio8oSBPz4
        for (int i = 0; i < messages.length; i++) {
            String message = messages[i];

            final String[] split = message.split(" ");

            for (final String currentword : split) {
                //if (currentword.matches("^\\w{3,16}$") && data.contains(currentword)) {
                if (currentword.length() > 2 && currentword.length() < 17 && data.contains(currentword)) {
                    System.out.println("List matched " + currentword + " in message #" + i);
                }
            }
        }

        //Tree matching
        for (int i = 0; i < messages.length; i++) {
            String message = messages[i];

            final String[] split = message.split(" ");

            for (final String currentword : split) {
                if (currentword.length() > 2 && currentword.length() < 17 && Tree.containsExact(currentword)) {
                    System.out.println("Tree matched " + currentword + " in message #" + i);
                }
            }
        }
    }

    @Order(5)
    @Test
    void matchcold() {
        Tree.construct(data);

        HashSet<String> set = new HashSet<>(data);

        long splitlistspeed = 0L;
        long splitsetspeed = 0L;
        long splittreespeed = 0L;
        long firsttreespeed = 0L;
        long manytreespeed = 0L;

        List<String> list = new ArrayList<>(10);

        for (int i = 0; i < messages.length; i++) {
            String message = messages[i];

            long startsplit = System.nanoTime();
            final String[] split = message.split(" ");
            long endsplit = System.nanoTime();
            long splitdiff = endsplit - startsplit;
            splitlistspeed += splitdiff;
            splitsetspeed += splitdiff;
            splittreespeed += splitdiff;

            long start1 = System.nanoTime();
            for (final String currentword : split) {
                if (currentword.length() > 2 && currentword.length() < 17 && Tree.containsExact(currentword)) {
                    //list.add("Tree matched");
                }
            }
            long end1 = System.nanoTime();
            splittreespeed += end1 - start1;

            long start4 = System.nanoTime();
            for (final String currentword : split) {
                if (currentword.length() > 2 && currentword.length() < 17 && set.contains(currentword)) {
                    //list.add("List matched");
                }
            }
            long end4 = System.nanoTime();
            splitsetspeed += end4 - start4;

            long start2 = System.nanoTime();
            for (final String currentword : split) {
                if (currentword.length() > 2 && currentword.length() < 17 && data.contains(currentword)) {
                    //list.add("List matched");
                }
            }
            long end2 = System.nanoTime();
            splitlistspeed += end2 - start2;

            long start3 = System.nanoTime();

            long found = Tree.findFirst(message);
            int last = (int)(found >> 32) + (int)found;
            String left = message;
            while (last != 0){
                left = left.substring(last);
                if (left.length() < 3)
                    break;

                found = Tree.findFirst(left);
                if (found == 0)
                    break;

                last = (int)(found >> 32) + (int)found;
            }
            long end3 = System.nanoTime();
            firsttreespeed += end3 - start3;

            long start5 = System.nanoTime();
            Tree.findMultiple(message);
            long end5 = System.nanoTime();
            manytreespeed += end5 - start5;
        }

        System.out.println("list split took " + splitlistspeed/1000000 + "ms and " + splitlistspeed%1000000 + "ns");
        System.out.println("set  split took " + splitsetspeed/1000000 + "ms and " + splitsetspeed%1000000 + "ns");
        System.out.println("tree split took " + splittreespeed/1000000 + "ms and " + splittreespeed%1000000 + "ns");

        // These two also have the disadvantage of finding much more stuff
        System.out.println("tree first took " + firsttreespeed/1000000 + "ms and " + firsttreespeed%1000000 + "ns");
        System.out.println("tree many  took " + manytreespeed/1000000 + "ms and " + manytreespeed%1000000 + "ns");
    }

    private static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}