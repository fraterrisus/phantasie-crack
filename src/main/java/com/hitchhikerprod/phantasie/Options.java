package com.hitchhikerprod.phantasie;

public class Options {
    private int argPointer;

    private boolean binary;
    private boolean inventory;
    private String filename;
    private boolean skills;
    private boolean spells;

    private Options() {
        this.binary = false;
        this.inventory = false;
        this.skills = false;
        this.spells = false;
    }

    public static Options from(String[] args) {
        Options o = new Options();
        o.argPointer = 0;

        while (o.argPointer < args.length) {
            final String arg = args[o.argPointer];
            if (arg.startsWith("--")) {
                o.parseLongOption(args);
            } else if (arg.startsWith("-")) {
                o.parseShortOption(args);
            } else {
                o.setFilename(arg);
                o.argPointer++;
            }
        }

        return o;
    }

    public boolean showBinary() {
        return this.binary;
    }

    private void setBinary(boolean val) {
        this.binary = val;
    }

    public boolean showInventory() {
        return this.inventory;
    }

    private void setInventory(boolean val) {
        this.inventory = val;
    }

    public String getFilename() {
        return this.filename;
    }

    private void setFilename(String val) {
        this.filename = val;
    }

    public boolean showSkills() {
        return this.skills;
    }

    private void setSkills(boolean val) {
        this.skills = val;
    }

    public boolean showSpells() {
        return this.spells;
    }

    private void setSpells(boolean val) {
        this.spells = val;
    }

    private void parseLongOption(String[] args) {
        if (args[argPointer].equals("--binary")) {
            setBinary(true);
            argPointer++;
        } else if (args[argPointer].equals("--nobinary")) {
            setBinary(false);
            argPointer++;
        } else if (args[argPointer].equals("--inventory")) {
            setInventory(true);
            argPointer++;
        } else if (args[argPointer].equals("--noinventory")) {
            setInventory(false);
            argPointer++;
        } else if (args[argPointer].equals("--skills")) {
            setSkills(true);
            argPointer++;
        } else if (args[argPointer].equals("--noskills")) {
            setSkills(false);
            argPointer++;
        } else if (args[argPointer].equals("--spells")) {
            setSpells(true);
            argPointer++;
        } else if (args[argPointer].equals("--nospells")) {
            setSpells(false);
            argPointer++;
        } else if (args[argPointer].equals("--help")) {
            helpMessage();
        } else {
            System.out.println("Unrecognized command line argument: " + args[argPointer]);
            System.out.println();
            helpMessage();
        }
    }

    private void parseShortOption(String[] args) {
        if (args[argPointer].equals("-b")) {
            setBinary(true);
            argPointer++;
        } else if (args[argPointer].equals("-i")) {
            setInventory(true);
            argPointer++;
        } else if (args[argPointer].equals("-s")) {
            setSpells(true);
            argPointer++;
        } else if (args[argPointer].equals("-2")) {
            setSkills(true);
            argPointer++;
        } else {
            System.out.println("Unrecognized command line argument: " + args[argPointer]);
            System.out.println();
            helpMessage();
        }
    }

    private void helpMessage() {
        System.out.println("Usage: java -jar phantasie.jar [options] filename.img");
        System.out.println("Options:");
        System.out.println("  -b, --[no]binary      Dump bytes instead of structured data (default: off)");
        System.out.println("  -i, --[no]inventory   Show character inventory (default: off)");
        System.out.println("      --help            Display this message");
        System.out.println("  -s, --[no]spells      Show spells known and learnable (default: off)");
        System.out.println("  -2, --[no]skills      Show second line with skills and runes (default: off)");
        System.exit(0);
    }
}
