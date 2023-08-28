package duelistmod.abstracts.enemyDuelist;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SlaversCollar;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.CardDisappearEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.DuelistStance;
import duelistmod.abstracts.TokenCard;
import duelistmod.actions.common.DelayedActionAction;
import duelistmod.actions.enemyDuelist.EnemyAnimateOrbAction;
import duelistmod.actions.enemyDuelist.EnemyChannelAction;
import duelistmod.actions.enemyDuelist.EnemyDiscardAtEndOfTurnAction;
import duelistmod.actions.enemyDuelist.EnemyDrawActualCardsAction;
import duelistmod.actions.enemyDuelist.EnemyDuelistDoNextCardAction;
import duelistmod.actions.enemyDuelist.EnemyDuelistMakePlayAction;
import duelistmod.actions.enemyDuelist.EnemyDuelistTriggerEndOfTurnOrbActions;
import duelistmod.actions.enemyDuelist.EnemyDuelistTurnStartDrawAction;
import duelistmod.actions.enemyDuelist.EnemyEvokeOrbAction;
import duelistmod.actions.enemyDuelist.EnemyUseCardAction;
import duelistmod.cards.EarthGiant;
import duelistmod.cards.pools.dragons.ArmageddonDragonEmp;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.CardForHashSets;
import duelistmod.dto.RandomizedOptions;
import duelistmod.enums.DuelistCardType;
import duelistmod.enums.EnemyDuelistCounter;
import duelistmod.enums.EnemyDuelistFlag;
import duelistmod.helpers.Util;
import duelistmod.interfaces.EnemyEnergyRelic;
import duelistmod.interfaces.TriFunction;
import duelistmod.orbs.enemy.EnemyDark;
import duelistmod.orbs.enemy.EnemyEmptyOrbSlot;
import duelistmod.powers.EmperorPower;
import duelistmod.powers.SummonPower;
import duelistmod.powers.duelistPowers.WonderGaragePower;
import duelistmod.powers.enemyPowers.EnemyDrawPilePower;
import duelistmod.stances.enemy.EnemyNeutralStance;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.megacrit.cardcrawl.cards.AbstractCard.*;

public class AbstractEnemyDuelist extends AbstractMonster {

    public static AbstractEnemyDuelist enemyDuelist;
    public static boolean finishedSetup;
    public ArrayList<AbstractEnemyDuelistRelic> relics;
    public DuelistStance stance;
    public ArrayList<DuelistOrb> orbs;
    public final ArrayList<AbstractCard> cardsPlayedThisCombat = new ArrayList<>();
    public final HashMap<UUID, AbstractEnemyDuelistCard> holderMap = new HashMap<>();
    public final HashMap<EnemyDuelistCounter, Integer> counters = new HashMap<>();
    public final HashMap<EnemyDuelistFlag, Object> flags = new HashMap<>();
    public final HashMap<DuelistCardType, HashSet<CardForHashSets>> uniqueCardMap = new HashMap<>();
    public int maxOrbs;
    public int masterMaxOrbs;
    public int masterHandSize;
    public int gameHandSize;
    public int mantraGained;
    public EnemyEnergyManager energy;
    protected EnergyOrbInterface energyOrb;
    public EnemyEnergyPanel energyPanel;
    public CardGroup drawPile;
    public CardGroup hand;
    public CardGroup discardPile;
    public CardGroup exhaustPile;
    public CardGroup limbo;
    public CardGroup graveyard;
    public CardGroup cardPool;
    public CardGroup resummonPile;
    public AbstractCard cardInUse;
    public int damagedThisCombat;
    public int cardsPlayedThisTurn;
    public int attacksPlayedThisTurn;
    public int drawSize = 3;
    public int summonCombatCount = 0;
    public int summonTurnCount = 0;
    public int tributeCombatCount = 0;
    public int tributeTurnCount = 0;
    public int energyAmt = 3;
    public boolean onSetupTurn;
    private static int attacksDrawnForAttackPhase;
    private static int setupsDrawnForSetupPhase;
    public String energyString;
    protected AbstractAnimation animation;
    public final ArrayList<String> dialog = new ArrayList<>();

    public AbstractEnemyDuelist(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, null);
        this.mantraGained = 0;
        this.onSetupTurn = true;
        this.energyString = "[E]";
        finishedSetup = false;
        this.drawX = Settings.WIDTH * 0.75f - 150.0f * Settings.xScale;
        this.type = AbstractMonster.EnemyType.BOSS;
        this.energyPanel = new EnemyEnergyPanel(this);
        this.hand = new EnemyCardGroup(CardGroup.CardGroupType.HAND, this, false);
        this.drawPile = new EnemyCardGroup(CardGroup.CardGroupType.UNSPECIFIED, this, true);
        this.discardPile = new EnemyCardGroup(CardGroup.CardGroupType.UNSPECIFIED, this, false);
        this.exhaustPile = new EnemyCardGroup(CardGroup.CardGroupType.UNSPECIFIED, this, false);
        this.graveyard = new EnemyCardGroup(CardGroup.CardGroupType.UNSPECIFIED, this, false);
        this.limbo = new EnemyCardGroup(CardGroup.CardGroupType.UNSPECIFIED, this, false);
        this.masterHandSize = 10;
        this.gameHandSize = 3;
        this.drawSize = this.gameHandSize;
        final int n = 0;
        this.maxOrbs = n;
        this.masterMaxOrbs = n;
        this.stance = new EnemyNeutralStance();
        this.orbs = new ArrayList<>();
        this.relics = new ArrayList<>();
        for (EnemyDuelistCounter type : EnemyDuelistCounter.values()) {
            this.counters.put(type, 0);
        }
        for (EnemyDuelistFlag flag : EnemyDuelistFlag.values()) {
            this.flags.put(flag, flag.defaultValue());
        }
    }


    @Override
    public void init() {
        (AbstractEnemyDuelist.enemyDuelist = this).setHp(this.maxHealth);
        this.energy.energyMaster = this.energyAmt;
        this.generateAll();
        this.afterInit();
        super.init();
        this.preBattlePrep();
        AbstractEnemyDuelist.finishedSetup = true;
        Util.log("Running duelist enemy init()");
    }

    public void afterInit() {}

    public void handleEnergyRelics() {
        int amt = 0;
        for (AbstractRelic r : this.relics) {
            if (r instanceof EnemyEnergyRelic) {
                amt += ((EnemyEnergyRelic)r).energy();
            }
        }
        if (amt > 0) {
            this.energy.energyMaster += amt;
        }
    }

    public void getMove(final int num) {
        this.setMove((byte)0, AbstractMonster.Intent.NONE);
    }

    public void generateDeck() {}
    public int modifyCardPriority(int currentPriority, AbstractEnemyDuelistCard c) { return 0; }

    public void generateAll() {
        this.generateDeck();
        for (AbstractCard c : this.drawPile.group) {
            AbstractEnemyDuelistCard ac = new AbstractEnemyDuelistCard(c);
            this.holderMap.put(ac.getCopyUUID(), ac);
        }
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.maxHealth = Math.round(this.maxHealth * 1.2f);
        }
        this.currentHealth = this.maxHealth;
        this.updateHealthBar();
    }

    public void applyMonsterPowersAtBattleStart() {
        if (!this.hasPower(SummonPower.POWER_ID)) {
            this.addToBot(new ApplyPowerAction(this, this, new SummonPower(this), 0));
        }
        if (!this.hasPower(EnemyDrawPilePower.POWER_ID)) {
            this.addToBot(new ApplyPowerAction(this, this, new EnemyDrawPilePower()));
        }
    }

    @Override
    public void usePreBattleAction() {
        this.energy.recharge();
        this.applyMonsterPowersAtBattleStart();
        for (final AbstractRelic r : this.relics) {
            r.atBattleStartPreDraw();
        }
        this.addToBot(new DelayedActionAction(new EnemyDuelistTurnStartDrawAction()));
        for (final AbstractRelic r : this.relics) {
            r.atBattleStart();
        }
        if (this.hasPower("Minion")) {
            this.playMusic();
        }
        else {
            this.playMusic();
        }
    }

    public void playMusic() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        String musicKey;
        if (AbstractDungeon.actNum == 0) {
            musicKey = "BOSS_BOTTOM";
        }
        else if (AbstractDungeon.actNum == 1) {
            musicKey = "BOSS_CITY";
        }
        else if (AbstractDungeon.actNum == 2) {
            musicKey = "BOSS_BEYOND";
        }
        else {
            musicKey = "MINDBLOOM";
        }
        AbstractDungeon.getCurrRoom().playBgmInstantly(musicKey);
    }

    public void takeTurn() {
        AbstractEnemyDuelist.attacksDrawnForAttackPhase = 0;
        AbstractEnemyDuelist.setupsDrawnForSetupPhase = 0;
        this.startTurn();
        this.addToBot(new EnemyDuelistMakePlayAction());
        this.onSetupTurn = !this.onSetupTurn;
    }

    public void makePlay() {
        boolean onlyCardInHand = this.hand.group.size() == 1;
        for (final AbstractCard _c : this.hand.group) {
            final AbstractEnemyDuelistCard c = fromCard(_c);
            if (c.canUse(this) && c.getPriority(this.hand.group) > -100) {
                if (c.cardBase instanceof DuelistCard) {
                    DuelistCard dc = (DuelistCard)c.cardBase;
                    if (!dc.shouldEnemyUse(onlyCardInHand, AbstractDungeon.player, this)) {
                        continue;
                    }
                }
                c.temporaryNoRunic = false;
                c.cardBase.drawScale = 0.8F;
                c.cardBase.targetDrawScale = 0.8F;
                c.bossLighten();
                this.useCard(c.cardBase, this, EnemyEnergyPanel.totalCount);
                this.addToBot(new DelayedActionAction(new EnemyDuelistDoNextCardAction()));
                return;
            }
        }
        this.addToBot(new EnemyDuelistTriggerEndOfTurnOrbActions());
    }

    @Override
    public void update() {
        super.update();
        for (final AbstractRelic r : this.relics) {
            r.update();
        }
        for (final AbstractOrb o : this.orbs) {
            o.update();
            o.updateAnimation();
        }
        this.combatUpdate();
    }

    @Override
    public void applyEndOfTurnTriggers() {        
        this.energy.recharge();
        for (final AbstractPower p : AbstractEnemyDuelist.enemyDuelist.powers) {
            p.onEnergyRecharge();
        }
        for (final AbstractCard c : this.hand.group) {
            c.triggerOnEndOfTurnForPlayingCard();
        }
        this.stance.onEndOfTurn();
        this.addToBot(new EnemyDiscardAtEndOfTurnAction());
        for (final AbstractCard c : this.hand.group) {
            c.resetAttributes();
        }
        this.addToBot(new DelayedActionAction(new EnemyDuelistTurnStartDrawAction()));
    }

    public void startTurn() {
        this.summonTurnCount = 0;
        this.tributeTurnCount = 0;
        this.cardsPlayedThisTurn = 0;
        this.attacksPlayedThisTurn = 0;
        for (Map.Entry<EnemyDuelistFlag, Object> entry : this.flags.entrySet()) {
            if (entry.getKey().isResetAtTurnStart()) {
                this.flags.put(entry.getKey(), entry.getKey().defaultValue());
            }
        }
        for (final AbstractCard c : this.hand.group) {
            fromCard(c).lockIntentValues = true;
        }
        this.applyStartOfTurnRelics();
        this.applyStartOfTurnPreDrawCards();
        this.applyStartOfTurnCards();
        this.applyStartOfTurnOrbs();
    }

    public void draw(int numCards) {
        if (this.drawPile.isEmpty() && this.discardPile.isEmpty()) {
            this.generateDeck();
        }
        for (int i = 0; i < numCards; ++i) {
            if (this.drawPile.isEmpty()) {
                for (AbstractCard c : this.discardPile.group) {
                    this.drawPile.moveToDeck(c, true);
                }
                this.discardPile.group.clear();
            }
            if (!this.drawPile.isEmpty()) {
                final AbstractCard card = this.drawPile.getTopCard();
                AbstractEnemyDuelistCard cardHolder = fromCard(card);
                AbstractCard c = cardHolder.cardBase;
                this.addCardToHand(cardHolder);
                this.drawPile.removeTopCard();
                for (final AbstractPower p : this.powers) {
                    p.onCardDraw(c);
                }
                for (final AbstractRelic r : this.relics) {
                    r.onCardDraw(c);
                }
            }
        }
        if (this.drawPile instanceof EnemyCardGroup) {
            ((EnemyCardGroup)this.drawPile).updateDrawPilePower();
        }
    }

    public void addCardToHand(AbstractEnemyDuelistCard card) {
        AbstractCard c = card.cardBase;
        c.current_x = CardGroup.DRAW_PILE_X;
        c.current_y = CardGroup.DRAW_PILE_Y;
        c.setAngle(0.0f, true);
        c.lighten(false);
        c.drawScale = 0.12f;
        c.targetDrawScale = 0.35f;
        c.triggerWhenDrawn();
        card.temporaryNoRunic = false;
        card.bossDarken();
        c.current_y = Settings.HEIGHT / 2.0f;
        c.current_x = (float)Settings.WIDTH;
        if ((c instanceof TokenCard || c.hasTag(Tags.TOKEN)) && !c.upgraded && AnyDuelist.from(this).hasPower(WonderGaragePower.POWER_ID) && c.canUpgrade()) {
            c.upgrade();
        }
        if (this.hand.size() < this.masterHandSize) {
            this.hand.addToHand(c);
        } else {
            this.discardPile.addToBottom(c);
        }
    }

    private Integer tributeCostCalc(AbstractCard card, List<AbstractEnemyDuelistCard> playing) {
        if (!(card instanceof DuelistCard)) return 0;
        if (card instanceof ArmageddonDragonEmp) {
            return Math.max(0, ((DuelistCard)card).tributes - (int) playing.stream().filter(c -> c.cardBase.hasTag(Tags.DRAGON)).count());
        }
        if (card instanceof EarthGiant) {
            return Math.max(0, ((DuelistCard)card).tributes - (int) playing.stream().filter(c -> c.cardBase.type == CardType.SKILL).count());
        }
        return ((DuelistCard)card).tributes;
    }

    public ArrayList<AbstractEnemyDuelistCard> manualHandEvaluation(ArrayList<AbstractEnemyDuelistCard> sortedHand) {
        if (sortedHand.isEmpty()) return sortedHand;

        for (AbstractEnemyDuelistCard c : sortedHand) {
            c.bossDarken();
        }
        int summons = 0;
        int maxSummons = 0;
        if (this.hasPower(SummonPower.POWER_ID)) {
            SummonPower pow = (SummonPower) this.getPower(SummonPower.POWER_ID);
            summons = pow.amount;
            maxSummons = pow.getMaxSummons();
        }
        ArrayList<AbstractEnemyDuelistCard> output = new ArrayList<>();
        boolean finished = false;
        int budget = this.energyPanel.getCurrentEnergy();
        TriFunction<Integer, Integer, Integer, Boolean> finishedCheck = (s, ms, energy) -> {
            int cantPlay = 0;
            for (AbstractEnemyDuelistCard card : sortedHand) {
                if (!card.canUse(AbstractDungeon.player, s, ms, energy, tributeCostCalc(card.cardBase, output))) {
                    cantPlay++;
                }
            }
            return cantPlay == sortedHand.size();
        };
        int counter= 0;
        while (!finished && counter < 3000) {
            Util.log("Evaluating hand, iteration: " + counter + "\nSorted Hand size=" + sortedHand.size() + ", output size=" + output.size());
            counter++;
            AbstractEnemyDuelistCard card = sortedHand.get(0);
            boolean calcCanUse = card.canUse(AbstractDungeon.player, summons, maxSummons, budget, tributeCostCalc(card.cardBase, output));
            if (!calcCanUse) {
                if (sortedHand.size() == 1) {
                    output.add(sortedHand.remove(0));
                    finished = true;
                    break;
                }
                Integer nextPlayableIndex = null;
                for (int i = 1; i < sortedHand.size(); i++) {
                    AbstractEnemyDuelistCard c = sortedHand.get(i);
                    if (c.canUse(AbstractDungeon.player, summons, maxSummons, budget, tributeCostCalc(c.cardBase, output))) {
                        nextPlayableIndex = i;
                        break;
                    }
                }
                if (nextPlayableIndex == null) {
                    output.addAll(sortedHand);
                    sortedHand.clear();
                    finished = true;
                    break;
                }
                sortedHand.add(0, sortedHand.remove((int)nextPlayableIndex));
                continue;
            }

            summons += card.cardBase instanceof DuelistCard ? ((DuelistCard)card.cardBase).summons : 0;
            boolean mausoleumActive = this.hasPower(EmperorPower.POWER_ID) && !((EmperorPower)this.getPower(EmperorPower.POWER_ID)).flag;
            summons -= card.cardBase instanceof DuelistCard && !mausoleumActive ? ((DuelistCard)card.cardBase).tributes : 0;
            budget -= card.cardBase.costForTurn;
            budget += card.energyGeneratedIfPlayed;
            if (budget < 0) {
                budget = 0;
            }
            maxSummons += card.incrementGeneratedIfPlayed;
            if (summons > maxSummons) {
                summons = maxSummons;
            }
            if (summons < 0) {
                summons = 0;
            }
            card.bossLighten();
            output.add(sortedHand.remove(0));
            finished = finishedCheck.apply(summons, maxSummons, budget);
        }
        if (!finished) {
            Util.log("Exited early from hand evaluation");
        }
        output.addAll(sortedHand);
        output.forEach(AbstractEnemyDuelistCard::refreshIntentHbLocation);
        return output;
    }

    public List<AbstractEnemyDuelistCard> selectCards(int amt, ArrayList<AbstractCard> cards) {
        return selectCards(amt, cards, null);
    }

    public List<AbstractEnemyDuelistCard> selectCards(int amt, ArrayList<AbstractCard> cards, RandomizedOptions randomize) {
        ArrayList<AbstractEnemyDuelistCard> input = new ArrayList<>();
        cards.forEach(c -> {
            if (randomize != null) {
                Util.randomize(c, randomize);
            }
            input.add(AbstractEnemyDuelist.fromCard(c));
        });
        Collections.sort(input);
        ArrayList<AbstractEnemyDuelistCard> output = this.manualHandEvaluation(input);
        if (output.size() <= amt) {
            return output;
        }
        return output.subList(0, amt);
    }

    public void endTurnStartTurn() {
        if (!AbstractDungeon.getCurrRoom().isBattleOver) {
            this.addToBot(new EnemyDrawActualCardsAction(this));
            this.addToBot(new WaitAction(0.2f));
            this.applyStartOfTurnPostDrawRelics();
            this.applyStartOfTurnPostDrawPowers();
            this.cardsPlayedThisTurn = 0;
            this.attacksPlayedThisTurn = 0;
        }
    }

    public static AbstractEnemyDuelistCard fromCardOrNull(AbstractCard card) {
        if (enemyDuelist == null) return null;
        if (!(card instanceof DuelistCard)) return null;
        return enemyDuelist.holderMap.getOrDefault(((DuelistCard)card).copyUUID, null);
    }

    public static AbstractEnemyDuelistCard fromCard(AbstractCard card) {
        if (card instanceof DuelistCard && enemyDuelist.holderMap.containsKey(((DuelistCard)card).copyUUID)) {
           return enemyDuelist.holderMap.get(((DuelistCard)card).copyUUID);
        }
        AbstractEnemyDuelistCard newHolder = new AbstractEnemyDuelistCard(card);
        enemyDuelist.holderMap.put(newHolder.getCopyUUID(), newHolder);
        return newHolder;
    }

    public void preApplyIntentCalculations() {
        final boolean hasShuriken = this.hasRelic("Shuriken");
        int attackCount = 0;
        int artifactCount = 0;
        if (AbstractDungeon.player.hasPower("Artifact")) {
            artifactCount = AbstractDungeon.player.getPower("Artifact").amount;
        }
        for (final AbstractCard c : this.hand.group) {
            AbstractEnemyDuelistCard holder = fromCard(c);
            holder.manualCustomDamageModifier = 0;
            holder.manualCustomDamageModifierMult = 1.0f;
        }
        int foundAttacks = 0;
        for (int i = 0; i < this.hand.size(); ++i) {
            final AbstractCard c2 = this.hand.group.get(i);
            AbstractEnemyDuelistCard c2a = fromCard(c2);
            if (!c2a.lockIntentValues) {
                if (c2a.artifactConsumedIfPlayed > 0) {
                    artifactCount -= c2a.artifactConsumedIfPlayed;
                }
                if (c2a.vulnGeneratedIfPlayed > 0 && artifactCount <= 0) {
                    for (int j = i + 1; j < this.hand.size(); ++j) {
                        AbstractCard c3 = this.hand.group.get(i);
                        (fromCard(c3)).manualCustomVulnModifier = true;
                    }
                }
                if (hasShuriken) {
                    final AbstractEnemyDuelistCard c4 = fromCard(this.hand.group.get(i));
                    if (c4.cardBase.type == CardType.ATTACK) {
                        if (++attackCount == 3) {
                            for (int k = i + 1; k < this.hand.size(); ++k) {
                                final AbstractEnemyDuelistCard c5 = fromCard(this.hand.group.get(k));
                                ++c5.manualCustomDamageModifier;
                            }
                            attackCount = 0;
                        }
                    }
                }
                if (c2a.strengthGeneratedIfPlayed > 0) {
                    for (int j = i + 1; j < this.hand.size(); ++j) {
                        final AbstractEnemyDuelistCard AbstractEnemyDuelistCard3;
                        final AbstractEnemyDuelistCard c3 = AbstractEnemyDuelistCard3 = fromCard(this.hand.group.get(j));
                        AbstractEnemyDuelistCard3.manualCustomDamageModifier += c2a.strengthGeneratedIfPlayed;
                    }
                }
                if (c2a.damageMultGeneratedIfPlayed > 1) {
                    for (int j = i + 1; j < this.hand.size(); ++j) {
                        final AbstractEnemyDuelistCard c3 = fromCard(this.hand.group.get(j));
                        c3.manualCustomDamageModifierMult = (float)c2a.damageMultGeneratedIfPlayed;
                    }
                }
                final AbstractEnemyDuelistCard AbstractEnemyDuelistCard7 = fromCard(c2);
                AbstractEnemyDuelistCard7.manualCustomDamageModifierMult *= c2a.damageMultIfPlayed;
                if (c2a.cardBase.type == CardType.ATTACK) {
                    ++foundAttacks;
                    if (this.hasRelic("Pen Nib")) {
                        final AbstractRelic r = this.getRelic("Pen Nib");
                        if (r.counter + foundAttacks == 10) {
                            c2a.manualCustomDamageModifierMult = 2.0f;
                        }
                    }
                }
            }
        }
        for (final AbstractCard c6 : this.hand.group) {
            AbstractEnemyDuelistCard cc6 = fromCard(c6);
            if (!cc6.bossDarkened) {
                cc6.createIntent();
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.preApplyIntentCalculations();
        this.hand.applyPowers();
    }

    @Override
    public int getIntentDmg() {
        int totalIntentDmg = -1;
        for (final AbstractCard c : this.hand.group) {
            final AbstractEnemyDuelistCard cB = fromCard(c);
            if (cB.intentDmg > 0 && (!cB.bossDarkened || AbstractDungeon.player.hasRelic("Runic Dome"))) {
                if (totalIntentDmg == -1) {
                    totalIntentDmg = 0;
                }
                totalIntentDmg += cB.intentDmg;
            }
        }
        Util.log("Calculated intent damage: " + totalIntentDmg);
        return totalIntentDmg;
    }

    @Override
    public int getIntentBaseDmg() {
        return this.getIntentDmg();
    }

    public boolean hasRelic(final String targetID) {
        for (final AbstractRelic r : this.relics) {
            if (r.relicId.equals(targetID)) {
                return true;
            }
        }
        return false;
    }

    public AbstractRelic getRelic(final String targetID) {
        for (final AbstractRelic r : this.relics) {
            if (r.relicId.equals(targetID)) {
                return r;
            }
        }
        return null;
    }

    public void gainEnergy(final int e) {
        EnemyEnergyPanel.addEnergy(e);
        this.hand.glowCheck();
    }

    public void loseEnergy(final int e) {
        EnemyEnergyPanel.useEnergy(e);
    }

    public void onCardDrawOrDiscard() {
        for (final AbstractPower p : this.powers) {
            p.onDrawOrDiscard();
        }
        for (final AbstractRelic r : this.relics) {
            r.onDrawOrDiscard();
        }
        if (this.hasPower("Corruption")) {
            for (final AbstractCard c : this.hand.group) {
                if (c.type == CardType.SKILL && c.costForTurn != 0) {
                    c.modifyCostForCombat(-9);
                }
            }
        }
        this.hand.applyPowers();
        this.hand.glowCheck();
    }

    public void useCard(final AbstractCard c, AbstractMonster monster, final int energyOnUse) {
        AbstractEnemyDuelistCard holder = fromCard(c);
        AnyDuelist duelist = new AnyDuelist(this);
        if (monster == null) {
            monster = this;
        }
        if (c.type == CardType.ATTACK) {
            ++this.attacksPlayedThisTurn;
            this.useFastAttackAnimation();
            if (c.damage > MathUtils.random(20)) {
                this.onPlayAttackCardSound();
            }
        }
        ++this.cardsPlayedThisTurn;
        this.cardsPlayedThisCombat.add(c);
        List<DuelistCardType> types = DuelistCardType.of(c);
        for (DuelistCardType type : types) {
            this.uniqueCardMap.compute(type, (k, v) -> {
                if (v == null) {
                    HashSet<CardForHashSets> set = new HashSet<>();
                    set.add(new CardForHashSets(c));
                    return set;
                } else {
                    v.add(new CardForHashSets(c));
                    return v;
                }
            });
        }
        duelist.receiveCardUsed(c);
        holder.calculateCardDamage(monster);
        if (c.cost == -1 && EnemyEnergyPanel.totalCount < energyOnUse && !c.ignoreEnergyOnUse) {
            c.energyOnUse = EnemyEnergyPanel.totalCount;
        }
        if (c.cost == -1 && c.isInAutoplay) {
            c.freeToPlayOnce = true;
        }
        if (c instanceof DuelistCard) {
            ((DuelistCard)c).onDuelistPlayCard(c, AbstractDungeon.player, duelist);
            ((DuelistCard)c).duelistUseCard(this, AbstractDungeon.player);
        } else {
            c.use(AbstractDungeon.player, monster);
        }
        this.addToBot(new EnemyUseCardAction(c, monster));
        if (!c.dontTriggerOnUseCard) {
            for (final AbstractPower p : this.powers) {
                p.onPlayCard(c, monster);
            }
            for (final AbstractRelic r : this.relics) {
                r.onPlayCard(c, monster);
            }
            this.stance.onPlayCard(c);

            for (final AbstractCard card : this.hand.group) {
                card.onPlayCard(c, monster);
            }
            for (final AbstractCard card : this.discardPile.group) {
                card.onPlayCard(c, monster);
            }
            for (final AbstractCard card : this.drawPile.group) {
                card.onPlayCard(c, monster);
            }
            this.hand.triggerOnOtherCardPlayed(c);
        }
        DuelistCard.handleOnEnemyPlayCardForAllAbstracts(c, duelist);
        this.hand.removeCard(c);
        this.cardInUse = c;
        c.target_x = (float)(Settings.WIDTH / 2);
        c.target_y = (float)(Settings.HEIGHT / 2);
        if (c.costForTurn > 0 && !c.freeToPlay() && !c.isInAutoplay && (!this.hasPower("Corruption") || c.type != CardType.SKILL)) {
            this.energy.use(c.costForTurn);
        }
        holder.showIntent = false;
    }

    public void combatUpdate() {
        if (this.cardInUse != null) {
            this.cardInUse.update();
        }
        this.energyPanel.update();
        this.limbo.update();
        this.hand.update();
        this.hand.updateHoverLogic();
        for (final AbstractPower p : this.powers) {
            p.updateParticles();
        }
        for (final AbstractOrb o : this.orbs) {
            o.update();
        }
        this.stance.update();
    }

    public void onPlayAttackCardSound() {
    }

    public void damage(final DamageInfo info) {
        int damageAmount = info.output;
        boolean hadBlock = this.currentBlock != 0;
        if (damageAmount < 0) {
            damageAmount = 0;
        }
        if (damageAmount > 1 && this.hasPower("Intangible")) {
            damageAmount = 1;
        }
        final boolean weakenedToZero = damageAmount == 0;
        damageAmount = this.decrementBlock(info, damageAmount);
        if (info.owner == this) {
            for (final AbstractRelic r : this.relics) {
                damageAmount = r.onAttackToChangeDamage(info, damageAmount);
            }
        }
        if (info.owner == AbstractDungeon.player) {
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                damageAmount = r.onAttackToChangeDamage(info, damageAmount);
            }
        }
        if (info.owner != null) {
            for (final AbstractPower p : info.owner.powers) {
                damageAmount = p.onAttackToChangeDamage(info, damageAmount);
            }
        }
        for (final AbstractRelic r : this.relics) {
            damageAmount = r.onAttackedToChangeDamage(info, damageAmount);
        }
        for (final AbstractPower p : this.powers) {
            damageAmount = p.onAttackedToChangeDamage(info, damageAmount);
        }
        if (info.owner == this) {
            for (final AbstractRelic r : this.relics) {
                r.onAttack(info, damageAmount, this);
            }
        }
        if (info.owner == AbstractDungeon.player) {
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                r.onAttack(info, damageAmount, this);
            }
        }
        if (info.owner != null) {
            for (final AbstractPower p : info.owner.powers) {
                p.onAttack(info, damageAmount, this);
            }
            for (final AbstractPower p : this.powers) {
                damageAmount = p.onAttacked(info, damageAmount);
            }
            for (final AbstractRelic r : this.relics) {
                damageAmount = r.onAttacked(info, damageAmount);
            }
        }
        for (final AbstractRelic r : this.relics) {
            damageAmount = r.onLoseHpLast(damageAmount);
        }
        this.lastDamageTaken = Math.min(damageAmount, this.currentHealth);
        final boolean probablyInstantKill = this.currentHealth == 0;
        if (damageAmount > 0 || probablyInstantKill) {
            for (final AbstractPower p2 : this.powers) {
                damageAmount = p2.onLoseHp(damageAmount);
            }
            for (final AbstractPower p2 : this.powers) {
                p2.wasHPLost(info, damageAmount);
            }
            for (final AbstractRelic r2 : this.relics) {
                r2.wasHPLost(damageAmount);
            }
            if (info.owner != null) {
                for (final AbstractPower p2 : info.owner.powers) {
                    p2.onInflictDamage(info, damageAmount, this);
                }
            }
            if (info.owner != this) {
                this.useStaggerAnimation();
            }
            if (damageAmount > 0) {
                for (final AbstractRelic r2 : this.relics) {
                    r2.onLoseHp(damageAmount);
                }
                if (info.owner != this) {
                    this.useStaggerAnimation();
                }
                if (damageAmount >= 99 && !CardCrawlGame.overkill) {
                    CardCrawlGame.overkill = true;
                }
                this.currentHealth -= damageAmount;
                if (!probablyInstantKill) {
                    AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, damageAmount));
                }
                if (this.currentHealth < 0) {
                    this.currentHealth = 0;
                }
                this.healthBarUpdatedEvent();
                this.updateCardsOnDamage();
            }
            if (this.currentHealth <= this.maxHealth / 2.0f && !this.isBloodied) {
                this.isBloodied = true;
                for (final AbstractRelic r2 : this.relics) {
                    if (r2 != null) {
                        r2.onBloodied();
                    }
                }
            }
            if (this.currentHealth < 1) {
                this.die();
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.cleanCardQueue();
                    AbstractDungeon.effectList.add(new DeckPoofEffect(64.0f * Settings.scale, 64.0f * Settings.scale, true));
                    AbstractDungeon.effectList.add(new DeckPoofEffect(Settings.WIDTH - 64.0f * Settings.scale, 64.0f * Settings.scale, false));
                    AbstractDungeon.overlayMenu.hideCombatPanels();
                }
                if (this.currentBlock > 0) {
                    this.loseBlock();
                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0f + AbstractMonster.BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0f + AbstractMonster.BLOCK_ICON_Y));
                }
                for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDead && !m.isDying && m.hasPower("Minion")) {
                        AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                        AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                    }
                }
            }
        }
        else {
            if (weakenedToZero && this.currentBlock == 0) {
                if (hadBlock) {
                    AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractMonster.TEXT[30]));
                }
                else {
                    AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
                }
            }
            else if (Settings.SHOW_DMG_BLOCK) {
                AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractMonster.TEXT[30]));
            }
        }
    }

    public void die() {
        if (this.currentHealth <= 0) {
            this.useFastShakeAnimation(5.0f);
            CardCrawlGame.screenShake.rumble(4.0f);
            if (this.hasPower("Minion")) {
                if (Settings.FAST_MODE) {
                    this.deathTimer += 0.7f;
                }
                else {
                    ++this.deathTimer;
                }
            }
            else {
                this.onBossVictoryLogic();
            }
        }
        AbstractEnemyDuelist.enemyDuelist = null;
        AbstractEnemyDuelist.finishedSetup = false;
        AbstractDungeon.actionManager.addToTop(new RemoveAllTemporaryHPAction(this, this));
        this.hand.clear();
        this.limbo.clear();
        this.orbs.clear();
        this.stance.onExitStance();
        (this.stance = AbstractEnemyStance.getStanceFromName("Neutral")).onEnterStance();
        super.die();
    }

    protected void onFinalBossVictoryLogic() {
    }

    private void updateCardsOnDamage() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (final AbstractCard c : this.hand.group) {
                c.tookDamage();
            }
        }
    }

    public void updateCardsOnDiscard() {
        for (final AbstractCard c : this.hand.group) {
            c.didDiscard();
        }
    }

    public void heal(final int healAmount) {
        int amt = healAmount;
        for (final AbstractRelic r : this.relics) {
            amt = r.onPlayerHeal(amt);
        }
        super.heal(amt);
        if (this.currentHealth > this.maxHealth / 2.0f && this.isBloodied) {
            this.isBloodied = false;
            for (final AbstractRelic r : this.relics) {
                r.onNotBloodied();
            }
        }
    }

    public void preBattlePrep() {
        this.damagedThisCombat = 0;
        this.cardsPlayedThisTurn = 0;
        this.attacksPlayedThisTurn = 0;
        this.maxOrbs = 0;
        this.orbs.clear();
        this.increaseMaxOrbSlots(this.masterMaxOrbs, false);
        this.isBloodied = (this.currentHealth <= this.maxHealth / 2);
        AbstractPlayer.poisonKillCount = 0;
        this.gameHandSize = this.masterHandSize;
        this.cardInUse = null;
        AbstractDungeon.overlayMenu.endTurnButton.enabled = false;
        this.hand.clear();
        if (this.hasRelic("SlaversCollar")) {
            ((SlaversCollar)this.getRelic("SlaversCollar")).beforeEnergyPrep();
        }
        this.energy.prep();
        this.powers.clear();
        this.healthBarUpdatedEvent();
        this.applyPreCombatLogic();
    }

    public ArrayList<String> getRelicNames() {
        final ArrayList<String> arr = new ArrayList<>();
        for (final AbstractRelic relic : this.relics) {
            arr.add(relic.relicId);
        }
        return arr;
    }

    public void applyPreCombatLogic() {
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atPreBattle();
            }
        }
    }

    public void applyStartOfTurnRelics() {
        this.stance.atStartOfTurn();
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atTurnStart();
            }
        }
    }

    public void applyStartOfTurnPostDrawRelics() {
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atTurnStartPostDraw();
            }
        }
    }

    public void applyStartOfTurnPreDrawCards() {
        for (final AbstractCard c : this.hand.group) {
            if (c != null) {
                c.atTurnStartPreDraw();
            }
        }
    }

    public void applyStartOfTurnCards() {
        for (final AbstractCard c : this.hand.group) {
            if (c != null) {
                c.atTurnStart();
                c.triggerWhenDrawn();
            }
        }
    }

    public boolean relicsDoneAnimating() {
        for (final AbstractRelic r : this.relics) {
            if (!r.isDone) {
                return false;
            }
        }
        return true;
    }

    public void switchedStance() {
        for (final AbstractCard c : this.hand.group) {
            c.switchedStance();
        }
    }

    public void onStanceChange(final String id) {
    }

    public void addBlock(final int blockAmount) {
        float tmp = (float)blockAmount;
        for (final AbstractRelic r : this.relics) {
            tmp = (float)r.onPlayerGainedBlock(tmp);
        }
        if (tmp > 0.0f) {
            for (final AbstractPower p : this.powers) {
                p.onGainedBlock(tmp);
            }
        }
        super.addBlock((int)Math.floor(tmp));
    }

    public void triggerEvokeAnimation(final int slot) {
        if (this.maxOrbs <= 0) {
            return;
        }
        this.orbs.get(slot).triggerEvokeAnimation();
    }

    public void evokeOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(0).onEvoke();
            final DuelistOrb orbSlot = new EnemyEmptyOrbSlot();
            for (int i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }
            this.orbs.set(this.orbs.size() - 1, orbSlot);
            for (int i = 0; i < this.orbs.size(); ++i) {
                this.orbs.get(i).setSlot(i, this.maxOrbs);
            }
        }
    }

    public void evokeNewestOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(this.orbs.size() - 1) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(this.orbs.size() - 1).onEvoke();
        }
    }

    public void evokeWithoutLosingOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(0).onEvoke();
        }
    }

    public void removeNextOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            final DuelistOrb orbSlot = new EnemyEmptyOrbSlot(this.orbs.get(0).cX, this.orbs.get(0).cY);
            for (int i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }
            this.orbs.set(this.orbs.size() - 1, orbSlot);
            for (int i = 0; i < this.orbs.size(); ++i) {
                this.orbs.get(i).setSlot(i, this.maxOrbs);
            }
        }
    }

    public boolean hasEmptyOrb() {
        if (this.orbs.isEmpty()) {
            return false;
        }
        for (final AbstractOrb o : this.orbs) {
            if (o instanceof EnemyEmptyOrbSlot) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOrb() {
        return !this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot);
    }

    public int filledOrbCount() {
        int orbCount = 0;
        for (final AbstractOrb o : this.orbs) {
            if (!(o instanceof EnemyEmptyOrbSlot)) {
                ++orbCount;
            }
        }
        return orbCount;
    }

    public void channelOrb(DuelistOrb orbToSet) {
        if (this.maxOrbs <= 0) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0f, AbstractPlayer.MSG[4], true));
            return;
        }
        orbToSet.owner = AnyDuelist.from(this);
        if (this.hasRelic("Dark Core") && !(orbToSet instanceof EnemyDark)) {
            orbToSet = new EnemyDark();
        }
        int index = -1;
        for (int i = 0; i < this.orbs.size(); ++i) {
            if (this.orbs.get(i) instanceof EnemyEmptyOrbSlot) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            orbToSet.cX = this.orbs.get(index).cX;
            orbToSet.cY = this.orbs.get(index).cY;
            this.orbs.set(index, orbToSet);
            this.orbs.get(index).setSlot(index, this.maxOrbs);
            orbToSet.playChannelSFX();
            for (final AbstractPower p : this.powers) {
                p.onChannel(orbToSet);
            }
            for (AbstractRelic relic : this.relics) {
                if (relic instanceof OnChannelRelic) {
                    ((OnChannelRelic)relic).onChannel(orbToSet);
                }
            }
            AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orbToSet);
            AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orbToSet);
            orbToSet.applyFocus();
        }
        else {
            AbstractDungeon.actionManager.addToTop(new EnemyChannelAction(orbToSet));
            AbstractDungeon.actionManager.addToTop(new EnemyEvokeOrbAction(1));
            AbstractDungeon.actionManager.addToTop(new EnemyAnimateOrbAction(1));
        }
    }

    public void increaseMaxOrbSlots(final int amount, final boolean playSfx) {
        if (this.maxOrbs == 10) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0f, AbstractPlayer.MSG[3], true));
            return;
        }
        if (playSfx) {
            CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1f);
        }
        this.maxOrbs += amount;
        for (int i = 0; i < amount; ++i) {
            this.orbs.add(new EnemyEmptyOrbSlot());
        }
        for (int i = 0; i < this.orbs.size(); ++i) {
            this.orbs.get(i).setSlot(i, this.maxOrbs);
        }
    }

    public void decreaseMaxOrbSlots(final int amount) {
        if (this.maxOrbs <= 0) {
            return;
        }
        this.maxOrbs -= amount;
        if (this.maxOrbs < 0) {
            this.maxOrbs = 0;
        }
        if (!this.orbs.isEmpty()) {
            this.orbs.remove(this.orbs.size() - 1);
        }
        for (int i = 0; i < this.orbs.size(); ++i) {
            this.orbs.get(i).setSlot(i, this.maxOrbs);
        }
    }

    public void applyStartOfTurnOrbs() {
        if (!this.orbs.isEmpty()) {
            for (final AbstractOrb o : this.orbs) {
                o.onStartOfTurn();
            }
            if (this.hasRelic("Cables") && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
                this.orbs.get(0).onStartOfTurn();
            }
        }
    }

    public void render(final SpriteBatch sb) {
        super.render(sb);
        if (!this.isDead) {
            if (!this.orbs.isEmpty()) {
                for (final AbstractOrb o : this.orbs) {
                    o.render(sb);
                }
            }
            for (final AbstractRelic r : this.relics) {
                r.render(sb);
            }
            this.renderHand(sb);
            this.stance.render(sb);
            this.energyPanel.render(sb);
        }
    }

    public void renderHand(final SpriteBatch sb) {
        this.hand.renderHand(sb, this.cardInUse);
        if (this.cardInUse != null && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.HAND_SELECT && !PeekButton.isPeeking) {
            this.cardInUse.render(sb);
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
                AbstractDungeon.effectList.add(new CardDisappearEffect(this.cardInUse.makeCopy(), this.cardInUse.current_x, this.cardInUse.current_y));
                this.cardInUse = null;
            }
        }
        this.limbo.render(sb);
    }

    public void dispose() {
        super.dispose();
    }

    public void setupDialog(ArrayList<String> strings) {
        this.dialog.addAll(strings);
    }

    public HashSet<AbstractCard> getUniqueCardsSetPlayedByType(DuelistCardType type) {
        HashSet<CardForHashSets> set = this.uniqueCardMap.getOrDefault(type, new HashSet<>());
        HashSet<AbstractCard> output = new HashSet<>();
        for (CardForHashSets c : set) {
            output.add(c.card());
        }
        return output;
    }

    public List<AbstractCard> getUniqueCardsPlayedByType(DuelistCardType type) {
        HashSet<CardForHashSets> set = this.uniqueCardMap.getOrDefault(type, new HashSet<>());
        return CardForHashSets.setToList(set);
    }

    static {
        AbstractEnemyDuelist.attacksDrawnForAttackPhase = 0;
        AbstractEnemyDuelist.setupsDrawnForSetupPhase = 0;
    }

    private enum DrawTypes
    {
        Attack,
        Setup,
        EitherPhase
    }
}
