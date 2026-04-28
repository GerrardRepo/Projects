# CLI Turn Based Project
## Project Overview

This Turn-Based Project is a Java command-line game developed using object-oriented design and guided by SOLID principles.

In the game, the player selects a character class, chooses a difficulty setting, and selects suitable items before battling through multiple stages of turn-based combat against either wolves, goblins or both.

The arena includes several gameplay features, such as:

- **Status effects** that can be applied to both the player and enemy units
- **Special abilities (skills)** for each character, with delays or cooldown mechanics implemented using the **Strategy Pattern**
- An **inventory system** that allows the player to gain advantages during battle.
- **Dynamic choice selection** system that only shows available actions to the user.
- and **many more**.

Overall, the project focuses on applying software engineering and object-oriented design concepts to build a functional, modular, and maintainable turn-based combat system.

## UML Class Diagram
![image alt](https://github.com/GerrardRepo/Projects/blob/5692b79a9315d8e48a37bc92f0e06d7c0a5b4d50/CLI%20Turn%20Based%20Game/Diagrams/SC2002%20CLASS%20DIAGRAM.png)

## UML Sequence Diagram
![image alt]()

## Combatants Statistics and Information

### Player Classes

- **Warrior** - HP:260 ATK:40 DEF:20 SPD:30 - special skill is Shield Bash (damage + stun)
- **Wizard** - HP:200 ATK:50 DEF:10 SPD:20 - special skill is Arcane Blast (hits all enemies, +10 ATK per kill)

### Enemies

- **Goblin** - HP:55 ATK:35 DEF:15 SPD:25
- **Wolf** - HP:40 ATK:45 DEF:5 SPD:35

### Difficulty Levels

- **Easy** - 3 Goblins
- **Medium** - 1 Goblin + 1 Wolf -> Backup: 2 Wolves
- **Hard** - 2 Goblins -> Backup: 1 Goblin + 2 Wolves

### Items (pick 2 at the start, single use)

- **Potion** - Heal 100 HP
- **Power Stone** - Trigger special skill for free (no cooldown)
- **Smoke Bomb** - Block all enemy damage for 2 rounds

### Actions

- Basic Attack - single target, damage = ATK - DEF
- Defend - +10 DEF for 2 rounds (doesn't stack)
- Use Item - consume an item from inventory
- Special Skill - class specific, 3 round cooldown
