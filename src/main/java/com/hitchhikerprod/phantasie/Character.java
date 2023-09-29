package com.hitchhikerprod.phantasie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bcordes on 5/1/17.
 */
public class Character {
    private static final List<Integer> unknownBytes = List.of(0x06, 0x07, 0x08, 0x0f, 0x10, 0x11, 0x12);

    private final int rosterId;
    private final Map<Integer, Integer> unknown;

    private final String name;
    private final int strength;
    private final int intelligence;
    private final int dexterity;
    private final int constitution;
    private final int charisma;
    private final int luck;
    private final int age; // - 17
    private final int maxPower;
    private final int curPower;
    private final int powerBoost;
    private final int maxHealth;
    private final int curHealth;
    private final int gold; // 2B
    private final int score;
    private final int level;
    private final int experience; // 3B
    private final Set<Integer> spellsKnown;
    private final Set<Integer> spellsAvailable;
    private final CharacterClass charClass;
    private final Race race;
    private final List<Integer> equipment;
    private final int weaponRating;
    private final int shieldRating;
    private final int armorRating;

    public Character(byte[] data, int charNum) {
        age = 0;

        strength        = getByte(data, charNum, IDX_STR);
        intelligence    = getByte(data, charNum, IDX_INT);
        dexterity       = getByte(data, charNum, IDX_DEX);
        constitution    = getByte(data, charNum, IDX_CON);
        charisma        = getByte(data, charNum, IDX_CHR);
        luck            = getByte(data, charNum, IDX_LUC);
        //age             = getByte(data, charNum, IDX_AGE);
        maxPower        = getByte(data, charNum, IDX_MPOW);
        curPower        = getByte(data, charNum, IDX_CPOW);
        maxHealth       = getByte(data, charNum, IDX_MHP);
        curHealth       = getByte(data, charNum, IDX_CHP);
        gold            = getBytes(data, charNum, IDX_GOLD, 2);
        score           = getByte(data, charNum, IDX_SCORE);
        level           = getByte(data, charNum, IDX_LEVEL);
        rosterId        = getByte(data, charNum, IDX_ROSTER_ID);
        experience      = getBytes(data, charNum, IDX_EXP, 3);
        powerBoost      = getByte(data, charNum, IDX_POWER_UP);
        spellsKnown     = new HashSet<>();
        spellsAvailable = new HashSet<>();
        for (int i = 0; i < 54; i++) {
            final int b = getByte(data, charNum, IDX_SPELLS + i);
            if (b == 1) {
                spellsKnown.add(i);
            } else if (b == 2) {
                spellsAvailable.add(i);
            }
        }

        final StringBuilder nameBuilder = new StringBuilder();
        final int nameIndex = addressOf(charNum, IDX_NAME);
        for (int i = 0; i < 12; i++) {
            final char c = (char) (data[nameIndex + i] & 0x7f);
            if (c > 0) { nameBuilder.append(c); }
        }
        name            = nameBuilder.toString();

        charClass       = CharacterClass.from(getByte(data, charNum, IDX_CLASS));

        equipment       = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            equipment.add(getByte(data, charNum, IDX_EQUIP + i));
        }

        race            = Race.from(getByte(data, charNum, IDX_RACE));
        weaponRating    = getByte(data, charNum, IDX_WEAPON);
        armorRating     = getByte(data, charNum, IDX_ARMOR);
        shieldRating    = getByte(data, charNum, IDX_SHIELD);

        // short[] equipData = getWord(data, charNum, IDX_EQUIP, 0x20);
        //equipment = new Equipment(equipData);

        unknown = new HashMap<>();
        for (int idx : unknownBytes) {
            unknown.put(idx, getByte(data, charNum, idx));
        }
    }

    public static int addressOf(int charNum, int field) {
        return field;
        //return 0x208f + (charNum * 0xa1) + field;
    }

    private static int getByte(byte[] data, int charNum, int field) {
        return (int)(data[addressOf(charNum, field)]) & 0xff;
    }
    
    private static int getBytes(byte[] data, int charNum, int field, int count) {
        final int index = addressOf(charNum, field);
        int value = 0;
        for (int i = 0; i < count; i++) {
            value = value << 8;
            value += (int)(data[index + i]) & 0xff;
        }
        return value;
    }

    enum CharacterClass {
        THIEF(0), MONK(1), RANGER(2), FIGHTER(3),
        PRIEST(4), WIZARD(5);
        private final int diskValue;

        CharacterClass(int diskValue) { this.diskValue = diskValue; }

        private static CharacterClass from(int val) {
            return EnumSet.allOf(CharacterClass.class).stream()
                .filter(a -> a.diskValue == val)
                .findFirst().orElseThrow(IllegalArgumentException::new);
        }

        private int toInt() { return diskValue; }

        private String toAbbr() {
            return name().substring(0,3);
        }
    }

    enum Race {
        HUMAN(0), DWARF(1), ELF(2), HALFLING(3), GNOME(4),
        PIXIE(5), SPRITE(6), OGRE(7), GNOLL(8), TROLL(9),
        KOBOLD(10), ORC(11), GOBLIN(12), LIZARDMAN(13), MINOTAUR(14),
        UNDEAD(15);
        private final int diskValue;

        Race(int diskValue) { this.diskValue = diskValue; }

        private static Race from(int val) {
            return EnumSet.allOf(Race.class).stream()
                .filter(a -> a.diskValue == val)
                .findFirst().orElseThrow(IllegalArgumentException::new);
        }

        private int toInt() { return diskValue; }

        public String toAbbr() {
            return name().substring(0,3);
        }
    }

    public static String headerString() {
        StringBuilder buffer = new StringBuilder();
        Formatter fmt = new Formatter(buffer);
        buffer.append("    ");
        fmt.format("%-12s ", "Name");
        fmt.format("%3s ", "Rac");
        fmt.format("%3s ", "Cls");
        fmt.format("%2s ", "Lv");
        //fmt.format("%3s ", "Age");
        fmt.format("%6s ", "XP");
        fmt.format("%2s ", "ST");
        fmt.format("%2s ", "IQ");
        fmt.format("%2s ", "DX");
        fmt.format("%2s ", "CO");
        fmt.format("%2s ", "CH");
        fmt.format("%2s ", "LK");
        fmt.format("%7s ", "Health");
        fmt.format("%7s   ", "Power");
        fmt.format("%5s ", "Bank");
        fmt.format("%2s ", "Wp");
        fmt.format("%2s ", "Ar");
        fmt.format("%2s ", "Sh");
        unknownBytes.forEach(e -> fmt.format(" %03x", e));
        //buffer.append("Statuses");
        return buffer.toString();
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        final Formatter fmt = new Formatter(buffer);
        fmt.format("#%02d:", rosterId);
        fmt.format("%-12s ", name);
        fmt.format("%3s ", race.toAbbr());
        fmt.format("%3s ", charClass.toAbbr());
        fmt.format("%2d ", level);
        //fmt.format("%3d ", age);
        fmt.format("%06d ", experience);
        fmt.format("%02d ", strength);
        fmt.format("%02d ", intelligence);
        fmt.format("%02d ", dexterity);
        fmt.format("%02d ", constitution);
        fmt.format("%02d ", charisma);
        fmt.format("%02d ", luck);
        fmt.format("%03d/%03d ", curHealth, maxHealth);
        fmt.format("%03d/%03d%+01d ", curPower, maxPower, powerBoost);
        fmt.format("%5d ", gold);
        fmt.format("%02d ", weaponRating);
        fmt.format("%02d ", armorRating);
        fmt.format("%02d ", shieldRating);
        unknown.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEachOrdered(e -> fmt.format(" %03d", e.getValue()));
/*
        buffer.append("\n");
        buffer.append("    Inventory:");
        equipment.forEach(e -> fmt.format(" %03d", e));
*/
        return buffer.toString();
    }

    public String spellsToString() {
        final StringBuilder buffer = new StringBuilder();
        final Formatter fmt = new Formatter(buffer);
        buffer.append("  Spells known:");
        spellsKnown.stream().sorted()
            .forEachOrdered(e -> fmt.format(" (%02d)%s", e+1, SPELL_NAMES.get(e)));
        buffer.append("\n");
        buffer.append("  Spells available:");
        spellsAvailable.stream().sorted()
            .forEachOrdered(e -> fmt.format(" (%02d)%s", e+1, SPELL_NAMES.get(e)));
        return buffer.toString();
    }

    // linapple snapshot: 0x208f

    private static final int IDX_STR      = 0x000;
    private static final int IDX_INT      = 0x001;
    private static final int IDX_DEX      = 0x002;
    private static final int IDX_CON      = 0x003;
    private static final int IDX_CHR      = 0x004;
    private static final int IDX_LUC      = 0x005;
    // private static final int IDX_AGE   = 0x006; // don't think this is right
    // private static final int IDX_      = 0x007; // all zeroes
    // private static final int IDX_      = 0x008; // Could be class? F:07, M:0a, P:0c, W:0b
    private static final int IDX_MPOW     = 0x009;
    private static final int IDX_CPOW     = 0x00a;
    private static final int IDX_MHP      = 0x00b;
    private static final int IDX_CHP      = 0x00c;
    private static final int IDX_GOLD     = 0x00d;
    // private static final int IDX_      = 0x00f;
    // private static final int IDX_      = 0x010;
    // private static final int IDX_      = 0x011;
    // private static final int IDX_      = 0x012;
    private static final int IDX_ATTACK   = 0x013;
    // private static final int IDX_      = 0x014;
    private static final int IDX_PARRY    = 0x015;
    // private static final int IDX_      = 0x016;
    private static final int IDX_FINDITEM = 0x017;
    // private static final int IDX_      = 0x018;
    private static final int IDX_SPOTTRAP = 0x019;
    // private static final int IDX_      = 0x01a;
    private static final int IDX_DISARM   = 0x01b;
    // private static final int IDX_      = 0x01c;
    private static final int IDX_LISTEN   = 0x01d;
    // private static final int IDX_      = 0x01e;
    // private static final int IDX_      = 0x01f;
    // private static final int IDX_      = 0x020;
    private static final int IDX_PICKLOCK = 0x021;
    // private static final int IDX_      = 0x022;
    // private static final int IDX_      = 0x023;
    // private static final int IDX_      = 0x024;
    private static final int IDX_SWIM     = 0x025;
    // private static final int IDX_      = 0x026;
    // private static final int IDX_      = 0x027;
    // private static final int IDX_      = 0x028;
    private static final int IDX_SCORE    = 0x029;
    // private static final int IDX_      = 0x02a; //   1   1 runes?
    // private static final int IDX_      = 0x02b; //   0   0
    // private static final int IDX_      = 0x02c; //   0   0
    // private static final int IDX_      = 0x02d; //   0   0
    // private static final int IDX_      = 0x02e; //   0   0
    // private static final int IDX_      = 0x02f; //   4   4
    // private static final int IDX_      = 0x030; //  23  23
    // private static final int IDX_      = 0x031; //  51  51
    private static final int IDX_EXP      = 0x032; // 3 bytes
    private static final int IDX_LEVEL    = 0x035;
    // private static final int IDX_      = 0x036; //   3   3
    // private static final int IDX_      = 0x037; //   4   3
    // private static final int IDX_      = 0x038; //   8   8
    // private static final int IDX_      = 0x039; // 110  97
    private static final int IDX_ROSTER_ID = 0x03a;
    private static final int IDX_POWER_UP = 0x03b;
    private static final int IDX_SPELLS   = 0x03c; // spell ID 1; 01=learned, 02=available
    private static final int IDX_NAME     = 0x082; // 12?? bytes
    private static final int IDX_CLASS    = 0x096;
    private static final int IDX_EQUIP    = 0x097; // 9 bytes
    private static final int IDX_RACE     = 0x0a0;
    private static final int IDX_WEAPON   = 0x0a1;
    private static final int IDX_SHIELD   = 0x0a2;
    private static final int IDX_ARMOR    = 0x0a3;
    // private static final int IDX_      = 0x0a4;
    // private static final int IDX_      = 0x0a5;
    // private static final int IDX_      = 0x0a6;
    // private static final int IDX_      = 0x0a7;
    // private static final int IDX_      = 0x0a8;
    // private static final int IDX_      = 0x0a9;
    // private static final int IDX_      = 0x0aa;
    
    private static final List<String> SPELL_NAMES = List.of(
        "Healing 1", "Healing 2", "Healing 3", "Healing 4",
        "FireFlash 1", "FireFlash 2", "FireFlash 3", "FireFlash 4",
        "Quickness 1", "Quickness 2", "Quickness 3", "Quickness 4",
        "Strength 1", "Strength 2", "Strength 3", "Strength 4",
        "Protection 1", "Protection 2", "Protection 3", "Protection 4",
        "Confusion 1", "Confusion 2", "Confusion 3", "Confusion 4",
        "Weakness 1", "Weakness 2", "Weakness 3", "Weakness 4",
        "Binding 1", "Binding 2", "Binding 3", "Binding 4",
        "MindBlast 1", "MindBlast 2", "MindBlast 3", "MindBlast 4",
        "FlameBolt 1", "FlameBolt 2", "FlameBolt 3", "FlameBolt 4",
        "Charm", "Sleep", "Teleportation", "Resurrection",
        "Ninja 2", "Fear", "Dissolve", "Summon Elemental",
        "Dispel Undead", "Ninja 1", "Awaken", "Monster Evaluation",
        "Vision", "Transportation"
    );
}

/*
00002080   -- -- -- --  -- -- -- --  -- -- -- --  -- -- -- 14
00002090   0E 0F 10 0A  0B 19 00 07  05 05 1B 1B  00 62 01 8A
000020A0   01 03 6E 46  37 3C 03 34  07 34 10 3C  03 34 01 00
000020B0   11 3C 01 00  36 3E 00 00  05 01 00 00  00 00 04 17
000020C0   33 00 09 73  02 03 04 08  6E 01 00 00  00 00 00 00
000020D0   00 00 00 00  00 00 00 00  00 00 00 00  00 00 00 00
000020E0   00 00 00 00  00 00 00 00  00 00 00 00  00 00 00 00
000020F0   00 00 00 00  00 00 00 00  00 00 00 00  00 00 00 00
00002100   00 00 00 00  00 00 00 00  00 00 00 00  00 00 00 00
00002110   00 54 52 4F  4B 44 4F 4E  00 00 00 00  00 00 00 00
00002120   00 00 00 00  00 03 00 00  00 04 17 33  79 7B 81 0E
*/
