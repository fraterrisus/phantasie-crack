package com.hitchhikerprod.phantasie;

import java.util.List;

public class Equipment {
    public static final List<String> NAMES = List.of(
        // Shields, 0x01 - 0x14
        "Glove", "Wooden Shield", "Wooden Shield +1",
        "Small Shield", "Small Shield +1", "Small Shield +2", "Small Shield +3",
        "Medium Shield", "Medium Shield +1", "Medium Shield +2", "Medium Shield +3",
        "Large Shield", "Large Shield +1", "Large Shield +2", "Large Shield +3",
        "Giant Shield", "Giant Shield +1", "Giant Shield +2", "Giant Shield +3",
        "God Shield",
        // Armor, 0x15 - 0x28
        "Clothing", "Robes", "Leather", "Hard Leather",
        "Ring Mail", "Scale Mail", "Chain Mail", "Splint Mail",
        "Banded Mail", "Plate Mail", "Clothing +1", "Robes +1",
        "Leather +1", "Leather +2", "Ring Mail +1", "Ring Mail +2",
        "Chain Mail +1", "Chain Mail +2", "God Robes", "God Armor",
        // Weapons (non-magic), 0x29 - 0x46
        "Stick", "Knife", "Small Club", "Small Staff",
        "Small Mace", "Dagger", "Small Flail", "Club",
        "Mace", "Small Hammer", "Small Axe", "Staff",
        "Short Sword", "Flail", "Hammer", "Pitchfork",
        "Spear", "Axe", "Sword", "Heavy Mace",
        "Maul", "Trident", "Large Spear", "Large Axe",
        "Morningstar", "Pike", "Longsword", "Spetum",
        "Bardiche", "Halberd",
        // Weapons (magic), 0x47 - 0x64
        "Small Mace +1", "Dagger +1", "Small Mace +2", "Dagger +2",
        "Dagger +3", "Staff +1", "Dagger +4", "Flail +1",
        "Spear +1", "Axe +1", "Sword +1", "Sword +2",
        "Sword +3", "Large Axe +1", "Sword +4", "Sword +5",
        "Sword +6", "Halberd +1", "Sword +7", "Halberd +2",
        "Halberd +3", "Sword +10", "Halberd +4", "Halberd +5",
        "Halberd +6", "Halberd +7", "God Knife", "God Mace",
        "God Axe", "God Sword",
        // Healing potions, 0x65 - 0x6e
        "Pot.Healing 1", "Pot.Healing 2", "Pot.Healing 3", "Pot.Healing 4",
        "Pot.Healing 5", "Pot.Healing 6", "Pot.Healing 7", "Pot.Healing 8",
        "Pot.Healing 9", "Pot.Healing 10",
        // Mana potions, 0x6f - 0x78
        "Pot.Magic 1", "Pot.Magic 2", "Pot.Magic 3", "Pot.Magic 4",
        "Pot.Magic 5", "Pot.Magic 6", "Pot.Magic 7", "Pot.Magic 8",
        "Pot.Magic 9", "Pot.Magic 10",
        // Scrolls, 0x79 - 0x8c
        "Scroll #1", "Scroll #2", "Scroll #3", "Scroll #4",
        "Scroll #5", "Scroll #6", "Scroll #7", "Scroll #8",
        "Scroll #9", "Scroll #10", "Scroll #11", "Scroll #12",
        "Scroll #13", "Scroll #14", "Scroll #15", "Scroll #16",
        "Scroll #17", "Scroll #18", "Scroll #19", "Scroll #20",
        // Rings, 0x8d - 0x96
        "Ring #1", "Ring #2", "Ring #3", "Ring #4",
        "Ring #5", "Ring #6", "Ring #7", "Ring #8",
        "Ring #9", "Wand",
        // Treasures, 0x97 - 0xb4
        "Quartz", "Crystal", "Key", "Bronze",
        "Copper", "Sapphire", "Jade", "Silver",
        "Emerald", "Ring", "Ruby", "Gold",
        "Necklace", "Brass", "Orb", "Gold Cup",
        "Bracelet", "Statuette", "Crown", "Earrings",
        "Gold Ball", "Gold Key", "Platinum", "Statue",
        "Gold Ring", "Diamond", "Gold Box", "Golden Ox",
        "Gold Fox", "God Ring"
    );
}
