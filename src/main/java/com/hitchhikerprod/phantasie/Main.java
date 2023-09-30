package com.hitchhikerprod.phantasie;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        mainTranslate(args);
    }

    public static void mainTranslate(String[] args) {
        final Options options = Options.from(args);

        ApplewinSnapshot disk;
        try {
            disk = ApplewinSnapshot.from(options.getFilename());

            RandomAccessFile file = disk.getFile();
            // Roster: 0x2111, 38 chars * 0x82 bytes
            // Party: 0x405a, 6 chars

            /* For party info, we need to find other stuff, like the collected treasure that you haven't inventoried
            yet. */

            long pointer = 0x00406f; // 0x2111 - 0x82 = 0x208f
            final int blockSize = 0xaa;
            final List<Character> charList = new ArrayList<>();

            for (int charnum = 0; charnum < 6; charnum++) {
                final byte[] data = new byte[blockSize];
                file.seek(pointer);
                file.read(data, 0, blockSize);

                charList.add(new Character(data, charnum));

                if (options.showBinary()) {
                    for (byte b : data) {
                        System.out.printf("%02x ", b);
                    }
                    System.out.println();
                }

                pointer += 0xaa;
            }
            if (options.showBinary()) { System.out.println(); }

            System.out.println(Character.headerString());
            for (Character c : charList) {
                System.out.println(c);
                if (options.showSkills()) {
                    System.out.println(c.skillsToString());
                }
                if (options.showInventory()) {
                    System.out.println(c.equipmentToString());
                }
                if (options.showSpells()) {
                    System.out.println(c.spellsToString());
                }
                if (options.showInventory() || options.showSpells()) {
                    System.out.println();
                }
            }

            final byte[] partyData = new byte[0x100];
            pointer = 0x004510;
            file.seek(pointer);
            file.read(partyData, 0, 0x100);

            System.out.println();
            System.out.println("Party data:");
/*
            for (int i = 3; i < 10; i++) {
                final int d = getBytes(partyData, i, 1);
                System.out.printf("  0x%02x: %4d 0x%02x\n", i, d, d);
            }
*/
            final int days = getBytes(partyData, 8, 1);
            final int minutes = getBytes(partyData, 9, 1);
            System.out.printf("  Travel time  : %d days %d min\n", days, minutes);
            System.out.printf("  XP earned    : %d\n", 10 * getBytes(partyData, 11, 2));
            System.out.printf("  Gold in hand : %d\n", getBytes(partyData, 13, 2));

            final List<Integer> items = new ArrayList<>();
            int ptr = 0x53;
            while (true) {
                final int d = getBytes(partyData, ptr, 1);
                if (d == 0) { break; }
                items.add(d);
                ptr--;
            }
            if (!items.isEmpty()) {
                System.out.print("  Items found  :");
                for (Integer id : items) {
                    System.out.printf(" (%d)%s", id, Equipment.NAMES.get(id-1));
                }
            }
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static int getBytes(byte[] data, int ptr, int count) {
        int value = 0;
        for (int i = 0; i < count; i++) {
            value = value << 8;
            value += (int)(data[ptr + i]) & 0xff;
        }
        return value;
    }

}
