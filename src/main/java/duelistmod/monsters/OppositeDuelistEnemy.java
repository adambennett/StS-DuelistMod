package duelistmod.monsters;

import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import duelistmod.DuelistMod;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.EnemyEnergyManager;
import duelistmod.cards.CastleWalls;
import duelistmod.cards.GoldenApples;
import duelistmod.cards.Hinotama;
import duelistmod.cards.Kuriboh;
import duelistmod.cards.MillenniumShield;
import duelistmod.cards.PowerWall;
import duelistmod.cards.PreventRat;
import duelistmod.cards.RedMedicine;
import duelistmod.cards.Reinforcements;
import duelistmod.cards.SilverApples;
import duelistmod.cards.Sparks;
import duelistmod.cards.incomplete.BerserkerCrush;
import duelistmod.cards.pools.dragons.ArmageddonDragonEmp;
import duelistmod.cards.pools.dragons.BackgroundDragon;
import duelistmod.cards.pools.dragons.BlueEyes;
import duelistmod.cards.pools.dragons.BoosterDragon;
import duelistmod.cards.pools.dragons.CaveDragon;
import duelistmod.cards.pools.dragons.LesserDragon;
import duelistmod.cards.pools.dragons.MirageDragon;
import duelistmod.cards.pools.dragons.SpiralSpearStrike;
import duelistmod.cards.pools.dragons.TotemDragon;
import duelistmod.cards.pools.dragons.YamataDragon;

import java.util.ArrayList;

public class OppositeDuelistEnemy extends AbstractEnemyDuelist {

    private static final float HB_X = 0.0F;
    private static final float HB_Y = -10.0F;
    private static final float HB_W = 200.0F;
    private static final float HB_H = 290.0F;

    public static final String ID = DuelistMod.makeID("KaibaA2");
    public static final String ID_YUGI = DuelistMod.makeID("YugiEnemy");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    private static final MonsterStrings monsterstrings_YUGI = CardCrawlGame.languagePack.getMonsterStrings(ID_YUGI);
    public static final String NAME = monsterstrings.NAME;
    public static final String NAME_YUGI = monsterstrings_YUGI.NAME;

    public OppositeDuelistEnemy() {
        super(getName(), "theDuelist:OppositeDuelistEnemy", 150, HB_X, HB_Y, HB_W, HB_H);
        this.energyOrb = new EnergyOrbPurple();
        this.energy = new EnemyEnergyManager(this.energyAmt);
        this.loadAnimation("duelistModResources/images/char/duelistCharacter/Spine/kaiba/nyoxide_seto akiba.atlas", "duelistModResources/images/char/duelistCharacter/Spine/kaiba/nyoxide_seto akiba.json", 9.5f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        this.flipHorizontal = true;
        e.setTimeScale(DuelistMod.enemyAnimationSpeed);
        this.masterMaxOrbs = 3;
        this.maxOrbs = 3;
        this.type = EnemyType.ELITE;
    }

    private static String getName()
    {
        DuelistMod.getEnemyDuelistModel();
        return DuelistMod.monsterIsKaiba ? NAME : NAME_YUGI;
    }

    private static String getID()
    {
        DuelistMod.getEnemyDuelistModel();
        return DuelistMod.monsterIsKaiba ? ID : ID_YUGI;
    }

    @Override
    public void generateDeck() {
        ArrayList<AbstractCard> deck = new ArrayList<>();
        deck.add(new ArmageddonDragonEmp());
        deck.add(new LesserDragon());
        deck.add(new CaveDragon());
        deck.add(new Sparks());
        deck.add(new Sparks());
        deck.add(new Hinotama());
        deck.add(new CastleWalls());
        deck.add(new CastleWalls());
        deck.add(new PowerWall());
        deck.add(new PowerWall());
        deck.add(new LesserDragon());
        deck.add(new LesserDragon());
        deck.add(new LesserDragon());
        deck.add(new CaveDragon());
        deck.add(new CaveDragon());
        deck.add(new MillenniumShield());
        deck.add(new YamataDragon());
        deck.add(new YamataDragon());
        deck.add(new YamataDragon());
        deck.add(new BlueEyes());
        deck.add(new BoosterDragon());
        deck.add(new Kuriboh());
        deck.add(new GoldenApples());
        deck.add(new GoldenApples());
        deck.add(new SilverApples());
        deck.add(new SilverApples());
        deck.add(new PreventRat());
        deck.add(new PreventRat());
        deck.add(new BackgroundDragon());
        deck.add(new BackgroundDragon());
        deck.add(new MirageDragon());
        deck.add(new MirageDragon());
        deck.add(new SpiralSpearStrike());
        deck.add(new RedMedicine());
        deck.add(new TotemDragon());
        deck.add(new Reinforcements());
        deck.add(new Reinforcements());
        deck.add(new BerserkerCrush());

        // Real deck
        /*deck.add(new ArmageddonDragonEmp());
        deck.add(new BabyDragon());
        deck.add(new BabyDragon());
        deck.add(new BackgroundDragon());
        deck.add(new BackgroundDragon());
        deck.add(new BerserkerCrush());
        deck.add(new BlizzardDragon());
        deck.add(new BlueEyes());
        deck.add(new BlueEyes());
        deck.add(new BlueEyesUltimate());
        deck.add(new BoosterDragon());
        deck.add(new BusterBlader());
        deck.add(new CastleWalls());
        deck.add(new CastleWalls());
        deck.add(new CaveDragon());
        deck.add(new CaveDragon());
        deck.add(new DestructPotion());
        deck.add(new FiendSkull());
        deck.add(new FiendSkull());
        deck.add(new FiveHeaded());
        deck.add(new GoldenApples());
        deck.add(new GoldenApples());
        deck.add(new Hinotama());
        deck.add(new Hinotama());
        deck.add(new Kuriboh());
        deck.add(new LesserDragon());
        deck.add(new LesserDragon());
        deck.add(new MillenniumShield());
        deck.add(new MirageDragon());
        deck.add(new MirageDragon());
        deck.add(new PowerGiant());
        deck.add(new PowerWall());
        deck.add(new PowerWall());
        deck.add(new PreventRat());
        deck.add(new PreventRat());
        deck.add(new RedEyes());
        deck.add(new RedMedicine());
        deck.add(new Reinforcements());
        deck.add(new Reinforcements());
        deck.add(new SilverApples());
        deck.add(new SilverApples());
        deck.add(new Sparks());
        deck.add(new Sparks());
        deck.add(new SpiralSpearStrike());
        deck.add(new SpiralSpearStrike());
        deck.add(new StardustDragon());
        deck.add(new StrayLambs());
        deck.add(new TotemDragon());
        deck.add(new YamataDragon());
        deck.add(new YamataDragon());*/

        for (AbstractCard c : deck) {
            this.drawPile.addToRandomSpot(c);
        }
    }
}
