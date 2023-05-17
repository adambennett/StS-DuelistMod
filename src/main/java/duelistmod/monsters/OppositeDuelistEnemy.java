package duelistmod.monsters;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.abstracts.enemyDuelist.EnemyEnergyManager;
import duelistmod.cards.ApprenticeIllusionMagician;
import duelistmod.cards.BlizzardPrincess;
import duelistmod.cards.BlizzardWarrior;
import duelistmod.cards.BlueDragonSummoner;
import duelistmod.cards.CastleWalls;
import duelistmod.cards.CelticGuardian;
import duelistmod.cards.ChangeHeart;
import duelistmod.cards.DarkHole;
import duelistmod.cards.DarkMagician;
import duelistmod.cards.DarkMagicianGirl;
import duelistmod.cards.EarthGiant;
import duelistmod.cards.ExodiaHead;
import duelistmod.cards.ExodiaLA;
import duelistmod.cards.ExodiaLL;
import duelistmod.cards.ExodiaRA;
import duelistmod.cards.ExodiaRL;
import duelistmod.cards.GoldenApples;
import duelistmod.cards.Hinotama;
import duelistmod.cards.JudgeMan;
import duelistmod.cards.LabyrinthWall;
import duelistmod.cards.MillenniumShield;
import duelistmod.cards.MysticalElf;
import duelistmod.cards.NeoMagic;
import duelistmod.cards.PowerWall;
import duelistmod.cards.PreventRat;
import duelistmod.cards.RedMedicine;
import duelistmod.cards.Reinforcements;
import duelistmod.cards.SilverApples;
import duelistmod.cards.Sparks;
import duelistmod.cards.SummonedSkull;
import duelistmod.cards.SwordsRevealing;
import duelistmod.cards.WingedKuriboh;
import duelistmod.cards.incomplete.AmuletAmbition;
import duelistmod.cards.incomplete.BerserkerCrush;
import duelistmod.cards.incomplete.DiffusionWaveMotion;
import duelistmod.cards.incomplete.ForbiddenLance;
import duelistmod.cards.pools.aqua.Wingedtortoise;
import duelistmod.cards.pools.dragons.ArmageddonDragonEmp;
import duelistmod.cards.pools.dragons.BabyDragon;
import duelistmod.cards.pools.dragons.BackgroundDragon;
import duelistmod.cards.pools.dragons.BlizzardDragon;
import duelistmod.cards.pools.dragons.BlueEyes;
import duelistmod.cards.pools.dragons.BlueEyesUltimate;
import duelistmod.cards.pools.dragons.BoosterDragon;
import duelistmod.cards.pools.dragons.BusterBlader;
import duelistmod.cards.pools.dragons.CaveDragon;
import duelistmod.cards.pools.dragons.DragonMastery;
import duelistmod.cards.pools.dragons.DreadnoughtDreadnoid;
import duelistmod.cards.pools.dragons.EvilMind;
import duelistmod.cards.pools.dragons.FiendSkull;
import duelistmod.cards.pools.dragons.FiveHeaded;
import duelistmod.cards.pools.dragons.LesserDragon;
import duelistmod.cards.pools.dragons.MirageDragon;
import duelistmod.cards.pools.dragons.RedEyes;
import duelistmod.cards.pools.dragons.SilverWing;
import duelistmod.cards.pools.dragons.SpiralSpearStrike;
import duelistmod.cards.pools.dragons.StardustDragon;
import duelistmod.cards.pools.dragons.TotemDragon;
import duelistmod.cards.pools.dragons.YamataDragon;
import duelistmod.cards.pools.insects.KarakuriSpider;
import duelistmod.cards.pools.machine.ScrapFactory;
import duelistmod.events.BattleCity;
import duelistmod.helpers.Util;
import duelistmod.interfaces.EnemyEnergyOrbPurple;
import duelistmod.powers.ExodiaPower;
import duelistmod.relics.enemy.EnemyAnchor;
import duelistmod.relics.enemy.EnemyBagOfPreparation;
import duelistmod.relics.enemy.EnemyCaptainsWheel;
import duelistmod.relics.enemy.EnemyClockworkSouvenir;
import duelistmod.relics.enemy.EnemyCoffeeDripper;
import duelistmod.relics.enemy.EnemyDataDisk;
import duelistmod.relics.enemy.EnemyDragonRelic;
import duelistmod.relics.enemy.EnemyDuelistOrichalcum;
import duelistmod.relics.enemy.EnemyEctoplasm;
import duelistmod.relics.enemy.EnemyFusionHammer;
import duelistmod.relics.enemy.EnemyGoldPlatedCables;
import duelistmod.relics.enemy.EnemyHappyFlower;
import duelistmod.relics.enemy.EnemyHornCleat;
import duelistmod.relics.enemy.EnemyIceCream;
import duelistmod.relics.enemy.EnemyIncenseBurner;
import duelistmod.relics.enemy.EnemyMillenniumArmor;
import duelistmod.relics.enemy.EnemyMillenniumRing;
import duelistmod.relics.enemy.EnemyMillenniumScale;
import duelistmod.relics.enemy.EnemyOrichalcum;
import duelistmod.relics.enemy.EnemyPhilosopherStone;
import duelistmod.relics.enemy.EnemySozu;
import duelistmod.relics.enemy.EnemySpellcasterOrb;
import duelistmod.relics.enemy.EnemyThreadAndNeedle;
import duelistmod.relics.enemy.EnemyVajra;
import duelistmod.relics.enemy.EnemyWhiteBowlRelic;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OppositeDuelistEnemy extends AbstractEnemyDuelist {

    private static final float HB_X = 0.0F;
    private static final float HB_Y = -10.0F;
    private static final float HB_W = 200.0F;
    private static final float HB_H = 290.0F;
    public static final String ID = DuelistMod.makeID("OppositeDuelistEnemy");
    public static final String kaibaStrings = DuelistMod.makeID("KaibaA2");
    public static final String yugiStrings = DuelistMod.makeID("YugiEnemy");
    public static final String kaibaAtlas = "duelistModResources/images/char/duelistCharacter/Spine/kaiba/nyoxide_seto akiba.atlas";
    public static final String kaibaSkeleton = "duelistModResources/images/char/duelistCharacter/Spine/kaiba/nyoxide_seto akiba.json";
    public static final String kaibaAnimation = "idle";
    public static final String yugiAtlas = "duelistModResources/images/char/duelistCharacter/Spine/yugi/nyoxide.atlas";
    public static final String yugiSkeleton = "duelistModResources/images/char/duelistCharacter/Spine/yugi/nyoxide.json";
    public static final String yugiAnimation = "animation";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(kaibaStrings);
    private static final MonsterStrings monsterStringsYugi = CardCrawlGame.languagePack.getMonsterStrings(yugiStrings);
    public static final String NAME = monsterStrings.NAME;
    public static final String NAME_YUGI = monsterStringsYugi.NAME;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final String[] DIALOG_YUGI = monsterStringsYugi.DIALOG;
    private final boolean isKaiba;
    private final boolean isBattleCity;

    public OppositeDuelistEnemy() {
        this(false, null);
    }

    public OppositeDuelistEnemy(boolean isBattleCity, BattleCity event) {
        super(getName(), ID, getHP(isBattleCity), HB_X, HB_Y, HB_W, HB_H);
        this.isBattleCity = isBattleCity;
        this.energyOrb = new EnemyEnergyOrbPurple(event);
        this.energy = new EnemyEnergyManager(this.energyAmt);
        String atlas = DuelistMod.monsterIsKaiba ? kaibaAtlas : yugiAtlas;
        String skeleton = DuelistMod.monsterIsKaiba ? kaibaSkeleton : yugiSkeleton;
        String animation = DuelistMod.monsterIsKaiba ? kaibaAnimation : yugiAnimation;
        float scale = DuelistMod.monsterIsKaiba ? 9.5f : 8.5f;
        this.loadAnimation(atlas, skeleton, scale);
        this.state.setAnimation(0, animation, true).setTimeScale(DuelistMod.enemyAnimationSpeed);
        this.isKaiba = DuelistMod.monsterIsKaiba;
        this.flipHorizontal = true;
        this.masterMaxOrbs = 3;
        this.maxOrbs = 3;

        this.type = EnemyType.ELITE;
        if (Util.getChallengeLevel() > 2) {
            this.drawSize++;
        }
        this.setupDialog(Arrays.stream(DuelistMod.monsterIsKaiba ? DIALOG : DIALOG_YUGI).collect(Collectors.toCollection(ArrayList::new)));
    }

    private static int getHP(boolean isBattleCity) {
        DuelistMod.getEnemyDuelistModel();
        int hp = DuelistMod.monsterIsKaiba
                ? 150   // Kaiba
                : 120;  // Yugi
        hp += (AbstractDungeon.actNum * 5);
        if (Util.getChallengeLevel() > -1) {
            hp += Util.getChallengeLevel() * 5;
        }
        if (isBattleCity) {
            hp += 200;
        }
        return hp;
    }

    private static String getName() {
        DuelistMod.getEnemyDuelistModel();
        return DuelistMod.monsterIsKaiba ? NAME : NAME_YUGI;
    }

    public boolean isKaiba() {
        return this.isKaiba;
    }

    @Override
    public void afterInit() {
        this.generateRelics();
    }

    private void generateRelics() {
        int amt = this.isBattleCity ? 3 : 0;
        if (Util.getChallengeLevel() > 2) {
            amt++;
        }
        if (Util.getChallengeLevel() > 4) {
            amt++;
        }

        List<AbstractEnemyDuelistRelic> randomRelics = this.randomRelics(amt);
        for (AbstractEnemyDuelistRelic relic : randomRelics) {
            relic.instantObtain(AbstractEnemyDuelist.enemyDuelist);
        }
        this.handleEnergyRelics();
    }

    private List<AbstractEnemyDuelistRelic> randomRelics(int amt) {
        ArrayList<AbstractEnemyDuelistRelic> relics = new ArrayList<>();
        ArrayList<AbstractEnemyDuelistRelic> energyRelics = new ArrayList<>();
        ArrayList<AbstractEnemyDuelistRelic> output = new ArrayList<>();

        // General
        relics.add(new EnemyMillenniumRing());
        relics.add(new EnemyMillenniumScale());
        relics.add(new EnemyWhiteBowlRelic());
        relics.add(new EnemyDuelistOrichalcum());
        relics.add(new EnemyMillenniumArmor());
        relics.add(new EnemyIceCream());
        relics.add(new EnemyAnchor());
        relics.add(new EnemyBagOfPreparation());
        relics.add(new EnemyHappyFlower());
        relics.add(new EnemyOrichalcum());
        relics.add(new EnemyThreadAndNeedle());
        relics.add(new EnemyClockworkSouvenir());
        relics.add(new EnemyHornCleat());
        relics.add(new EnemyCaptainsWheel());


        // Duelist-specific
        if (this.isKaiba) {
            relics.add(new EnemyDragonRelic());
            relics.add(new EnemyVajra());
            relics.add(new EnemyIncenseBurner());
        } else {
            relics.add(new EnemySpellcasterOrb());
            relics.add(new EnemyGoldPlatedCables());
            relics.add(new EnemyDataDisk());
        }

        // Energy relics
        if (Util.getChallengeLevel() > 4) {
            energyRelics.add(new EnemyPhilosopherStone());
            energyRelics.add(new EnemySozu());
            energyRelics.add(new EnemyCoffeeDripper());
            energyRelics.add(new EnemyEctoplasm());
            energyRelics.add(new EnemyFusionHammer());
        }

        // Output
        int energyRelicsAdded = 0;
        while (output.size() < amt) {
            boolean energy = energyRelics.size() > 0 && energyRelicsAdded < 2;
            if (energy) {
                energy = AbstractDungeon.cardRandomRng.random(0, 100) < 21;
            }
            if (energy) {
                output.add(energyRelics.remove(AbstractDungeon.cardRandomRng.random(0, energyRelics.size() - 1)));
                energyRelicsAdded++;
            } else if (relics.size() > 1) {
                output.add(relics.remove(AbstractDungeon.cardRandomRng.random(0, relics.size() - 1)));
            } else if (relics.size() == 1) {
                output.add(relics.remove(0));
            } else {
                break;
            }
        }
        return output;
    }

    @Override
    public void generateDeck() {
        ArrayList<AbstractCard> deck = new ArrayList<>();
        if (DuelistMod.monsterIsKaiba) {
            this.kaibaDeck(deck);
        } else {
            this.yugiDeck(deck);
        }

        for (AbstractCard c : deck) {
            this.upgrade(c);
            this.drawPile.addToRandomSpot(c);
        }
    }

    @Override
    public int modifyCardPriority(int currentPriority, AbstractEnemyDuelistCard c) {
        int value = 0;
        if (!DuelistMod.monsterIsKaiba && c.cardBase.hasTag(Tags.EXODIA_PIECE) && c.cardBase instanceof DuelistCard) {
            DuelistCard card = (DuelistCard)c.cardBase;
            if (!this.hasPower(ExodiaPower.POWER_ID)) {
                return card.hasTag(Tags.EXODIA_HEAD) ? -1000 : 1000;
            }
            ExodiaPower pow = (ExodiaPower)this.getPower(ExodiaPower.POWER_ID);
            if (card.hasTag(Tags.EXODIA_HEAD)) {
                return pow.checkForAllPiecesButHead() ? 1000 : -1000;
            }
            return pow.checkForPiece(card.exodiaName) ? -1000 : 1000;
        }
        if (c.cardBase instanceof SwordsRevealing) {
            value += 25;
        }
        return value;
    }

    private void upgrade(AbstractCard deckCard) {
        int roll = AbstractDungeon.cardRandomRng.random(0, 100);
        int check = 5;
        int mod = (AbstractDungeon.actNum + (Util.getChallengeLevel() > -1 ? Util.getChallengeLevel() : 0)) / 2;
        check += mod;
        check += this.isBattleCity ? 10 : 0;

        if (roll <= check) {
            deckCard.upgrade();
            if (deckCard.hasTag(Tags.ARCANE) && deckCard.canUpgrade()) {
                upgrade(deckCard);
            }
        }
    }

    private void battleCityCards(ArrayList<AbstractCard> deck) {
        if (this.isBattleCity) {
            deck.add(new PowerWall());
            deck.add(new Hinotama());
            deck.add(new Hinotama());
        } else {
            deck.add(new CastleWalls());
            deck.add(new Sparks());
            deck.add(new Sparks());
        }
    }

    private void kaibaDeck(ArrayList<AbstractCard> deck) {
        deck.add(new ArmageddonDragonEmp());
        deck.add(new BabyDragon());
        deck.add(new BabyDragon());
        deck.add(new BabyDragon());
        deck.add(new BackgroundDragon());
        deck.add(new BerserkerCrush());
        deck.add(new BlizzardDragon());
        deck.add(new BlueEyes());
        deck.add(new BlueEyes());
        deck.add(new BlueEyesUltimate());
        deck.add(new BoosterDragon());
        deck.add(new BusterBlader());
        deck.add(new CastleWalls());
        deck.add(new CaveDragon());
        deck.add(new CaveDragon());
        deck.add(new DarkHole());
        deck.add(new DragonMastery());
        deck.add(new DreadnoughtDreadnoid());
        deck.add(new EvilMind());
        deck.add(new FiendSkull());
        deck.add(new FiveHeaded());
        deck.add(new Hinotama());
        deck.add(new LesserDragon());
        deck.add(new LesserDragon());
        deck.add(new MillenniumShield());
        deck.add(new MirageDragon());
        deck.add(new PowerWall());
        deck.add(new PreventRat());
        deck.add(new RedEyes());
        deck.add(new RedMedicine());
        deck.add(new Reinforcements());
        deck.add(new SilverApples());
        deck.add(new SilverWing());
        deck.add(new Sparks());
        deck.add(new SpiralSpearStrike());
        deck.add(new StardustDragon());
        deck.add(new TotemDragon());
        deck.add(new WingedKuriboh());
        deck.add(new YamataDragon());
        this.battleCityCards(deck);
    }

    private void yugiDeck(ArrayList<AbstractCard> deck) {
        deck.add(new AmuletAmbition());
        deck.add(new ApprenticeIllusionMagician());
        deck.add(new BlizzardPrincess());
        deck.add(new BlizzardWarrior());
        deck.add(new BlueDragonSummoner());
        deck.add(new CastleWalls());
        deck.add(new CelticGuardian());
        deck.add(new ChangeHeart());
        deck.add(new DarkMagician());
        deck.add(new DarkMagicianGirl());
        deck.add(new DiffusionWaveMotion());
        deck.add(new EarthGiant());
        deck.add(new ExodiaHead());
        deck.add(new ExodiaLA());
        deck.add(new ExodiaLL());
        deck.add(new ExodiaRA());
        deck.add(new ExodiaRL());
        deck.add(new ForbiddenLance());
        deck.add(new GoldenApples());
        deck.add(new Hinotama());
        deck.add(new JudgeMan());
        deck.add(new JudgeMan());
        deck.add(new KarakuriSpider());
        deck.add(new LabyrinthWall());
        deck.add(new MillenniumShield());
        deck.add(new MysticalElf());
        deck.add(new MysticalElf());
        deck.add(new NeoMagic());
        deck.add(new NeoMagic());
        deck.add(new PowerWall());
        deck.add(new ScrapFactory());
        deck.add(new Sparks());
        deck.add(new SummonedSkull());
        deck.add(new SwordsRevealing());
        deck.add(new Wingedtortoise());
        this.battleCityCards(deck);

        // add when all cards are supported, cannot have Earth orbs or any other card generation otherwise
        //deck.add(new PowerKaishin());
        //deck.add(new ThousandEyesIdol());
    }
}
