package duelistmod.helpers.customConsole;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.potions.PotionSlot;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.RandomOrbHelper;
import duelistmod.helpers.Util;
import duelistmod.helpers.customConsole.commands.Block;
import duelistmod.helpers.customConsole.commands.Channel;
import duelistmod.helpers.customConsole.commands.DrawRare;
import duelistmod.helpers.customConsole.commands.DrawTag;
import duelistmod.helpers.customConsole.commands.Drop;
import duelistmod.helpers.customConsole.commands.DuelistScore;
import duelistmod.helpers.customConsole.commands.EnemyDuelistPiles;
import duelistmod.helpers.customConsole.commands.Evoke;
import duelistmod.helpers.customConsole.commands.Heal;
import duelistmod.helpers.customConsole.commands.Increment;
import duelistmod.helpers.customConsole.commands.Invert;
import duelistmod.helpers.customConsole.commands.OrbSlots;
import duelistmod.helpers.customConsole.commands.Resummon;
import duelistmod.helpers.customConsole.commands.Setup;
import duelistmod.helpers.customConsole.commands.Summon;
import duelistmod.helpers.customConsole.commands.TempHP;
import duelistmod.helpers.customConsole.commands.Tribute;
import duelistmod.helpers.customConsole.commands.PotionSlotCom;
import duelistmod.orbs.AirOrb;
import duelistmod.orbs.Alien;
import duelistmod.orbs.Anticrystal;
import duelistmod.orbs.Black;
import duelistmod.orbs.Blaze;
import duelistmod.orbs.Buffer;
import duelistmod.orbs.Consumer;
import duelistmod.orbs.DarkMillenniumOrb;
import duelistmod.orbs.DragonOrb;
import duelistmod.orbs.DragonPlusOrb;
import duelistmod.orbs.DuelistCrystal;
import duelistmod.orbs.DuelistGlass;
import duelistmod.orbs.DuelistHellfire;
import duelistmod.orbs.DuelistLight;
import duelistmod.orbs.Earth;
import duelistmod.orbs.FireOrb;
import duelistmod.orbs.Gadget;
import duelistmod.orbs.Gate;
import duelistmod.orbs.Glitch;
import duelistmod.orbs.Lava;
import duelistmod.orbs.LightMillenniumOrb;
import duelistmod.orbs.Metal;
import duelistmod.orbs.MillenniumOrb;
import duelistmod.orbs.Mist;
import duelistmod.orbs.MonsterOrb;
import duelistmod.orbs.Moon;
import duelistmod.orbs.Mud;
import duelistmod.orbs.ReducerOrb;
import duelistmod.orbs.Sand;
import duelistmod.orbs.Shadow;
import duelistmod.orbs.Smoke;
import duelistmod.orbs.Splash;
import duelistmod.orbs.Storm;
import duelistmod.orbs.Summoner;
import duelistmod.orbs.Sun;
import duelistmod.orbs.Surge;
import duelistmod.orbs.TokenOrb;
import duelistmod.orbs.VoidOrb;
import duelistmod.orbs.WaterOrb;
import duelistmod.orbs.WhiteOrb;

import java.util.ArrayList;
import java.util.List;

public class CustomConsoleCommandHelper {

    public static void setupCommands() {
        ConsoleCommand.addCommand("channel", Channel.class);
        ConsoleCommand.addCommand("heal", Heal.class);
        ConsoleCommand.addCommand("summon", Summon.class);
        ConsoleCommand.addCommand("tribute", Tribute.class);
        ConsoleCommand.addCommand("increment", Increment.class);
        ConsoleCommand.addCommand("resummon", Resummon.class);
        ConsoleCommand.addCommand("specialsummon", Resummon.class);
        ConsoleCommand.addCommand("orbslots", OrbSlots.class);
        ConsoleCommand.addCommand("setup", Setup.class);
        ConsoleCommand.addCommand("potionslot", PotionSlotCom.class);
        ConsoleCommand.addCommand("duelistscore", DuelistScore.class);
        ConsoleCommand.addCommand("evoke", Evoke.class);
        ConsoleCommand.addCommand("invert", Invert.class);
        ConsoleCommand.addCommand("temphp", TempHP.class);
        ConsoleCommand.addCommand("block", Block.class);
        ConsoleCommand.addCommand("drawrare", DrawRare.class);
        ConsoleCommand.addCommand("drawtag", DrawTag.class);
        ConsoleCommand.addCommand("enemyduelist", EnemyDuelistPiles.class);
        ConsoleCommand.addCommand("drop", Drop.class);
    }

    public static void gainPotionSlots(int amount) {
        int initSlots = AbstractDungeon.player.potionSlots;
        AbstractDungeon.player.potionSlots += amount;
        for (int j = 0; j < initSlots; j++) { AbstractDungeon.player.potions.add(new PotionSlot(initSlots + j)); }
    }

    public static void healAll(int amount) {
        if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom() != null) {
            AbstractDungeon.player.heal(amount, true);
            MonsterGroup monsters = AbstractDungeon.getMonsters();
            if (monsters != null) {
                for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                    if (!(mon.isDead || mon.isDying || mon.escaped || mon.isDeadOrEscaped())) {
                        mon.heal(amount, true);
                    }
                }
            }
        }
    }

    public static ArrayList<String> getOrbNames() {
        ArrayList<String> orbs = new ArrayList<>();
        orbs.add("Air");
        orbs.add("Alien");
        orbs.add("Anticrystal");
        orbs.add("Black");
        orbs.add("Blaze");
        orbs.add("Buffer");
        orbs.add("Consumer");
        orbs.add("DarkMillenniumOrb");
        orbs.add("DragonOrb");
        orbs.add("DragonOrb+");
        orbs.add("Crystal");
        orbs.add("Glass");
        orbs.add("Hellfire");
        orbs.add("Light");
        orbs.add("Earth");
        orbs.add("Fire");
        orbs.add("Gadget");
        orbs.add("Gate");
        orbs.add("Glitch");
        orbs.add("Lava");
        orbs.add("LightMillenniumOrb");
        orbs.add("Metal");
        orbs.add("MillenniumOrb");
        orbs.add("Mist");
        orbs.add("MonsterOrb");
        orbs.add("Moon");
        orbs.add("Mud");
        orbs.add("Reducer");
        orbs.add("Sand");
        orbs.add("Shadow");
        orbs.add("Smoke");
        orbs.add("Splash");
        orbs.add("Storm");
        orbs.add("Summoner");
        orbs.add("Sun");
        orbs.add("Surge");
        orbs.add("TokenOrb");
        orbs.add("Void");
        orbs.add("Water");
        orbs.add("White");
        orbs.add("Dark");
        orbs.add("Lightning");
        orbs.add("Frost");
        orbs.add("Plasma");
        return orbs;
    }

    public static void channel(String orb, int amount) {
        AbstractOrb output = null;

        switch (orb.toLowerCase()) {
            case "air": output = new AirOrb(); break;
            case "alien": output = new Alien(); break;
            case "anticrystal": output = new Anticrystal(); break;
            case "black": output = new Black(); break;
            case "blaze": output = new Blaze(); break;
            case "buffer": output = new Buffer(); break;
            case "consumer": output = new Consumer(); break;
            case "darkmillenniumorb": output = new DarkMillenniumOrb(); break;
            case "dragonorb": output = new DragonOrb(); break;
            case "dragonorb+": output = new DragonPlusOrb(); break;
            case "crystal": output = new DuelistCrystal(); break;
            case "glass": output = new DuelistGlass(); break;
            case "hellfire": output = new DuelistHellfire(); break;
            case "light": output = new DuelistLight(); break;
            case "earth": output = new Earth(); break;
            case "fire": output = new FireOrb(); break;
            case "gadget": output = new Gadget(); break;
            case "gate": output = new Gate(); break;
            case "glitch": output = new Glitch(); break;
            case "lava": output = new Lava(); break;
            case "lightmillenniumorb": output = new LightMillenniumOrb(); break;
            case "metal": output = new Metal(); break;
            case "millenniumorb": output = new MillenniumOrb(); break;
            case "mist": output = new Mist(); break;
            case "monsterorb": output = new MonsterOrb(); break;
            case "moon": output = new Moon(); break;
            case "mud": output = new Mud(); break;
            case "reducer": output = new ReducerOrb(); break;
            case "sand": output = new Sand(); break;
            case "shadow": output = new Shadow(); break;
            case "smoke": output = new Smoke(); break;
            case "splash": output = new Splash(); break;
            case "storm": output = new Storm(); break;
            case "summoner": output = new Summoner(); break;
            case "sun": output = new Sun(); break;
            case "surge": output = new Surge(); break;
            case "tokenorb": output = new TokenOrb(); break;
            case "void": output = new VoidOrb(); break;
            case "water": output = new WaterOrb(); break;
            case "white": output = new WhiteOrb(); break;
            case "dark": output = new Dark(); break;
            case "lightning": output = new Lightning(); break;
            case "frost": output = new Frost(); break;
            case "plasma": output = new Plasma(); break;
            case "random!":
                List<AbstractOrb> o = RandomOrbHelper.returnOrbList();
                if (o.size() > 0) {
                    int roll = AbstractDungeon.cardRandomRng != null ? AbstractDungeon.cardRandomRng.random(0, o.size() - 1) : 0;
                    output = o.get(roll);
                }
                break;
            default:
                Util.log("Orb not implemented for basemod console: " + orb, true);
                break;
        }

        try {
            if (output != null) {
                DuelistCard.channel(output.getClass().getDeclaredConstructor().newInstance(), amount);
            }
        } catch (Exception ex) {
            StringBuilder st = new StringBuilder();
            for (StackTraceElement s : ex.getStackTrace()) {
               st.append(s.toString());
            }
            Util.log("Exception while attempting to construct instance of orb: " + orb, false);
            Util.log(ex.getMessage(), false);
            Util.log(st.toString(), false);
        }
    }

}
