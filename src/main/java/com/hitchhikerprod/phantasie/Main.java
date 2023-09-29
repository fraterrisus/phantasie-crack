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
            final int blockSize = 0xb0;
            byte[] data = new byte[blockSize];
            final List<Character> charList = new ArrayList<>();

            for (int charnum = 0; charnum < 6; charnum++) {
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
                if (options.showSpells()) {
                    System.out.println(c.spellsToString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
