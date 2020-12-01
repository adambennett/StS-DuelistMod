package duelistmod.metrics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin;
import com.megacrit.cardcrawl.rooms.EmptyRoom;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import duelistmod.metrics.builders.*;

public class CreatureExportData implements Comparable<CreatureExportData> {

    @JsonIgnore
    public ModExportData mod;

    public AbstractCreature creature;
    public String name;
    public String type;
    public int minHP, maxHP;
    public boolean isPlayer;
    public String cardColor;

    public CreatureExportData(Exporter export, AbstractCreature creature) {
        this.creature = creature;
        this.name = creature.name;
        this.mod = export.findMod(creature.getClass());
        this.mod.creatures.add(this);
        this.minHP = this.maxHP = creature.maxHealth;
        if (creature instanceof AbstractPlayer) {
            AbstractPlayer player = (AbstractPlayer)creature;
            this.isPlayer = true;
            this.type = "Player";
            this.cardColor = ExportUploader.colorName(player.getCardColor());
            this.minHP = this.maxHP = player.startingMaxHP;
        } else if (creature instanceof AbstractMonster) {
            AbstractMonster monster = (AbstractMonster)creature;
            this.type = ExportUploader.toTitleCase(monster.type.toString());
            // TODO: find and call constructor at different ascension levels to get max hp and other variables
        }
    }

    public static ArrayList<CreatureExportData> exportAllCreatures(Exporter export) {
        ArrayList<CreatureExportData> creatures = new ArrayList<>();
        for (AbstractCreature m : getAllCreatures()) {
            creatures.add(new CreatureExportData(export, m));
        }
        Collections.sort(creatures);
        return creatures;
    }

    public static ArrayList<AbstractCreature> getAllCreatures() {
        ArrayList<AbstractCreature> creatures = new ArrayList<>();
        creatures.addAll(getAllPlayers());
        creatures.addAll(getAllMonsters());
        return creatures;
    }

    // Get all player characters
    public static ArrayList<AbstractPlayer> getAllPlayers() {
        // We need to initialize DailyMods before creating AbstractPlayers
        ModHelper.setModsFalse();
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        try {
            Method createCharacter = CardCrawlGame.class.getDeclaredMethod("createCharacter", AbstractPlayer.PlayerClass.class);
            createCharacter.setAccessible(true);
            for (AbstractPlayer.PlayerClass playerClass : AbstractPlayer.PlayerClass.values()) {
                try {
                    AbstractPlayer p = CardCrawlGame.characterManager.recreateCharacter(playerClass);//(AbstractPlayer)createCharacter.invoke(null, playerClass);
                    p.name = p.title;
                    players.add(p);
                } catch (Exception e) {
                    ExportUploader.logger.error("Exception occured when creating character", e);
                }
            }
        } catch (Exception e) {
            ExportUploader.logger.error("Exception occured when getting createCharacter method", e);
        }
        return players;
    }

    public static ArrayList<AbstractMonster> getAllMonsters() {
        // We need to initialize the random seeds before creating AbstractMonsters (for AbstractDungeon.monsterHpRng among others)
        Settings.seed = 12345L;
        AbstractDungeon.generateSeeds();

        // For rendering monsters we need:
        AbstractDungeon.player = CardCrawlGame.characterManager.getAllCharacters().get(0);
        AbstractDungeon.player.isDead = true; // don't render monster health bars
        AbstractDungeon.currMapNode = new MapRoomNode(0, -1);
        AbstractDungeon.currMapNode.room = new EmptyRoom();
        AbstractDungeon.currMapNode.room.monsters = new MonsterGroup(new AbstractMonster[0]); // needed to render monsters
        AbstractDungeon.id = ""; // for a Replay the Spire creature

        // Now get all monsters
        // There is unfortunately no list of all monsters in the game. The best we can do is to use MonsterHelper.getEncounter
        ArrayList<AbstractMonster> creatures = new ArrayList<>();
        HashSet<String> seenMonsters = new HashSet<>();
        for (String encounter : BaseMod.encounterList) {
            ExportUploader.logger.info("Getting monsters for encounter " + encounter);
            MonsterGroup monsters = MonsterHelper.getEncounter(encounter);
            for (AbstractMonster monster : monsters.monsters) {
                String id = monster.getClass().getName();
                if (seenMonsters.contains(id)) continue;
                creatures.add(monster);
                seenMonsters.add(id);
            }
        }

        // Custom monsters (BaseMod)
        @SuppressWarnings("unchecked")
        HashMap<String,BaseMod.GetMonsterGroup> customMonsters =
                (HashMap<String,BaseMod.GetMonsterGroup>) ReflectionHacks.getPrivateStatic(BaseMod.class, "customMonsters");
        for (BaseMod.GetMonsterGroup group : customMonsters.values()) {
            MonsterGroup monsters = group.get();
            for (AbstractMonster monster : monsters.monsters) {
                String id = monster.getClass().getName();
                if (seenMonsters.contains(id)) continue;
                creatures.add(monster);
                seenMonsters.add(id);
            }
        }

        // Awake lagavulin looks different
        Lagavulin lagavulin = new Lagavulin(false);
        lagavulin.id = lagavulin.id + "Awake";
        lagavulin.name = lagavulin.name + " (Awake)";
        creatures.add(lagavulin);

        // Downed byrd looks different
        Byrd byrd = new Byrd(500,500);
        byrd.changeState(Byrd.GROUND_STATE);
        byrd.id = byrd.id + "Grounded";
        byrd.name = byrd.name + " (Grounded)";
        creatures.add(byrd);

        Collections.sort(creatures, (AbstractMonster a, AbstractMonster b) -> { return a.name.compareTo(b.name); });
        return creatures;
    }

    @Override
    public int compareTo(CreatureExportData that) {
        if (creature.isPlayer && !that.creature.isPlayer) return -1;
        if (!creature.isPlayer && that.creature.isPlayer) return 1;
        return name.compareTo(that.name);
    }

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        builder.append("name", name);
        builder.append("type", type);
        builder.append("minHP", minHP);
        builder.append("maxHP", maxHP);
        builder.append("isPlayer", isPlayer);
        builder.append("creature_id", creature.id);
        return builder.build();
    }
}
