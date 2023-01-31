package duelistmod.monsters;

import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.EnemyEnergyManager;
import duelistmod.cards.AlphaMagnet;
import duelistmod.cards.CastleWalls;
import duelistmod.cards.DarkMagician;
import duelistmod.cards.GiantSoldier;
import duelistmod.cards.GoldenApples;
import duelistmod.cards.Hinotama;
import duelistmod.cards.Kuriboh;
import duelistmod.cards.MillenniumShield;
import duelistmod.cards.PowerGiant;
import duelistmod.cards.PowerWall;
import duelistmod.cards.PreventRat;
import duelistmod.cards.RedMedicine;
import duelistmod.cards.Reinforcements;
import duelistmod.cards.SilverApples;
import duelistmod.cards.Sparks;
import duelistmod.cards.StrayLambs;
import duelistmod.cards.incomplete.BerserkerCrush;
import duelistmod.cards.incomplete.DestructPotion;
import duelistmod.cards.pools.dragons.ArmageddonDragonEmp;
import duelistmod.cards.pools.dragons.BabyDragon;
import duelistmod.cards.pools.dragons.BackgroundDragon;
import duelistmod.cards.pools.dragons.BlizzardDragon;
import duelistmod.cards.pools.dragons.BlueEyes;
import duelistmod.cards.pools.dragons.BlueEyesUltimate;
import duelistmod.cards.pools.dragons.BoosterDragon;
import duelistmod.cards.pools.dragons.BusterBlader;
import duelistmod.cards.pools.dragons.CaveDragon;
import duelistmod.cards.pools.dragons.FiendSkull;
import duelistmod.cards.pools.dragons.FiveHeaded;
import duelistmod.cards.pools.dragons.LesserDragon;
import duelistmod.cards.pools.dragons.MirageDragon;
import duelistmod.cards.pools.dragons.RedEyes;
import duelistmod.cards.pools.dragons.SpiralSpearStrike;
import duelistmod.cards.pools.dragons.StardustDragon;
import duelistmod.cards.pools.dragons.TotemDragon;
import duelistmod.cards.pools.dragons.YamataDragon;

import java.util.ArrayList;

public class DownfallDuelistTester extends AbstractEnemyDuelist {

    private static final int HP_MIN = 300;
    private static final int HP_MAX = 380;
    private static final int A7_HP_MIN = 350;
    private static final int A7_HP_MAX = 430;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = -25.0F;
    private static final float HB_W = 200.0F;
    private static final float HB_H = 330.0F;

    public DownfallDuelistTester() {
        super("New Kaiba", "DownfallDuelistTester", 50, HB_X, HB_Y, HB_W, HB_H, "KaibaModel2", HP_MIN, HP_MAX, A7_HP_MIN, A7_HP_MAX);
        this.energyOrb = new EnergyOrbPurple();
        this.energy = new EnemyEnergyManager(this.energyAmt);
        this.loadAnimation("duelistModResources/images/char/duelistCharacter/Spine/kaiba/nyoxide_seto akiba.atlas", "duelistModResources/images/char/duelistCharacter/Spine/kaiba/nyoxide_seto akiba.json", 9.5f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        this.flipHorizontal = true;
        e.setTimeScale(0.6f);
        this.masterMaxOrbs = 3;
        this.maxOrbs = 3;
        this.type = EnemyType.ELITE;
    }

    @Override
    public void generateDeck() {
        ArrayList<AbstractCard> deck = new ArrayList<>();
        deck.add(new GiantSoldier());
        deck.add(new GiantSoldier());
        deck.add(new GiantSoldier());
        deck.add(new GiantSoldier());
        deck.add(new AlphaMagnet());
        deck.add(new DarkMagician());

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
        deck.add(new LesserDragon());
        deck.add(new CaveDragon());
        deck.add(new CaveDragon());
        deck.add(new CaveDragon());
        deck.add(new MillenniumShield());
        deck.add(new YamataDragon());
        deck.add(new YamataDragon());
        deck.add(new YamataDragon());
        deck.add(new BlueEyes());
        deck.add(new BoosterDragon());
        deck.add(new DestructPotion());
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
        deck.add(new ArmageddonDragonEmp());
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
