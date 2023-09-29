package com.hitchhikerprod.phantasie;

public class Options {
    private int argPointer;

    private boolean binary;
    private String filename;
    private boolean spells;

    private Options() {
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

    public String getFilename() {
        return this.filename;
    }

    private void setFilename(String val) {
        this.filename = val;
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
        } else if (args[argPointer].equals("-s")) {
            setSpells(true);
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
        System.out.println("  -b, --binary          Dump bytes instead of structured data");
        System.out.println("      --help            Display this message");
        System.out.println("  -s, --[no]spells      Enable display of spells (default: off)");
        System.exit(0);
    }
}
