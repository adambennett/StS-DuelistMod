package duelistmod.dto;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistCard;
import duelistmod.actions.common.DrawFromRarityAction;
import duelistmod.actions.common.DrawFromTagAction;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.actions.common.TsunamiAction;
import duelistmod.actions.enemyDuelist.EnemyChannelAction;
import duelistmod.actions.enemyDuelist.EnemyDiscardAction;
import duelistmod.actions.enemyDuelist.EnemyDrawActualCardsAction;
import duelistmod.actions.enemyDuelist.EnemyDrawFromRarityAction;
import duelistmod.actions.enemyDuelist.EnemyDrawFromTagAction;
import duelistmod.actions.enemyDuelist.EnemyIncreaseMaxOrbAction;
import duelistmod.actions.unique.PlayRandomFromDiscardAction;
import duelistmod.cards.EarthGiant;
import duelistmod.cards.GiantOrc;
import duelistmod.cards.GiantTrapHole;
import duelistmod.cards.incomplete.RevivalRose;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.cards.pools.dragons.ArmageddonDragonEmp;
import duelistmod.cards.pools.dragons.GiantRex;
import duelistmod.cards.pools.machine.ChaosAncientGearGiant;
import duelistmod.characters.TheDuelist;
import duelistmod.enums.EnemyDuelistCounter;
import duelistmod.enums.EnemyDuelistFlag;
import duelistmod.helpers.DebuffHelper;
import duelistmod.helpers.Util;
import duelistmod.orbs.Alien;
import duelistmod.orbs.FireOrb;
import duelistmod.powers.SummonPower;
import duelistmod.powers.duelistPowers.LeavesPower;
import duelistmod.powers.duelistPowers.ReinforcementsPower;
import duelistmod.powers.duelistPowers.VinesPower;
import duelistmod.powers.incomplete.MagiciansRobePower;
import duelistmod.powers.incomplete.MagickaPower;
import duelistmod.relics.CoralToken;
import duelistmod.relics.GhostToken;
import duelistmod.relics.Leafpile;
import duelistmod.relics.NaturiaRelic;
import duelistmod.relics.VampiricPendant;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.megacrit.cardcrawl.cards.AbstractCard.*;

public class AnyDuelist {

    private final AbstractPlayer player;
    private final AbstractEnemyDuelist enemy;

    private static final Consumer<AbstractCard> giantOrcCalc = (orc) -> {
        if (orc instanceof GiantOrc) {
            if (orc.cost > 0) {
                orc.modifyCostForCombat(-orc.magicNumber);
                orc.isCostModified = true;
            }
        }
    };

    private static final Consumer<AbstractCard> earthGiantCalc = (giant) -> {
        if (giant instanceof EarthGiant) {
            DuelistCard dc = (DuelistCard)giant;
            if (dc.tributes > 0) {
                AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(dc, -dc.magicNumber, true));
            }
        }
    };

    private static final BiConsumer<AbstractCard, AbstractCard> monsterCalcs = (checkCard, cardPlayed) -> {
        boolean checks = (checkCard instanceof GiantRex && cardPlayed.hasTag(Tags.DINOSAUR)) ||
                         (checkCard instanceof ChaosAncientGearGiant) && cardPlayed.hasTag(Tags.MACHINE) ||
                         (checkCard instanceof ArmageddonDragonEmp) && cardPlayed.hasTag(Tags.DRAGON);
        if (checks) {
            DuelistCard dc = (DuelistCard)checkCard;
            if (dc.tributes > 0) {
                AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(dc, -dc.magicNumber, true));
            }
        }
    };

    private static final BiConsumer<AnyDuelist, AbstractCard> spellcasterMagickaCalc = (duelist, card) -> {
        if (duelist.hasPower(SummonPower.POWER_ID))
        {
            SummonPower instance = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
            boolean isOnlySpellcasters = instance.isEveryMonsterCheck(Tags.SPELLCASTER, false);
            int extra = 0;
            if (isOnlySpellcasters) {
                if (duelist.hasPower(MagickaPower.POWER_ID)) {
                    extra = duelist.getPower(MagickaPower.POWER_ID).amount;
                    if (!card.hasTag(Tags.NO_MANA_RESET)) {
                        if (duelist.hasPower(MagiciansRobePower.POWER_ID)) {
                            TwoAmountPower pow = (TwoAmountPower)duelist.getPower(MagiciansRobePower.POWER_ID);
                            if (pow.amount2 > 0) {
                                pow.flash();
                                pow.amount2--; pow.updateDescription();
                            } else {
                                duelist.getPower(MagickaPower.POWER_ID).amount = 0;
                                duelist.getPower(MagickaPower.POWER_ID).updateDescription();
                            }
                        } else {
                            duelist.getPower(MagickaPower.POWER_ID).amount = 0;
                            duelist.getPower(MagickaPower.POWER_ID).updateDescription();
                        }
                    }
                }
                if (DuelistMod.spellcasterBlockOnAttack + extra > 0 && duelist.hasPower(MagickaPower.POWER_ID) && extra > 0) {
                    MagickaPower pow = (MagickaPower)duelist.getPower(MagickaPower.POWER_ID);
                    duelist.block(DuelistMod.spellcasterBlockOnAttack + extra);
                    pow.flash();
                } else if (DuelistMod.spellcasterBlockOnAttack + extra > 0) {
                    duelist.block(DuelistMod.spellcasterBlockOnAttack + extra);
                }
            }
        }
    };

    public AnyDuelist(AbstractEnemyDuelist enemy) {
        this(null, enemy);
    }

    public AnyDuelist(AbstractPlayer player) {
        this(player, null);
    }

    public AnyDuelist(AbstractPlayer player, AbstractEnemyDuelist enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public void receiveCardUsed(AbstractCard card) {
        this.handleZombieSubTypesOnPlay(card);
        this.vampirePlayedThisTurn(card);
        if (this.hasPower(SummonPower.POWER_ID)) {
            SummonPower pow = (SummonPower)this.getPower(SummonPower.POWER_ID);
            for (DuelistCard summoned : pow.getCardsSummoned()) {
                summoned.onCardPlayedWhileSummoned(card);
            }
        }
        for (AbstractCard c : this.resummonPile()) {
            if (c instanceof DuelistCard) {
                ((DuelistCard)c).onCardPlayedWhileInGraveyard(card);
            }
        }
        for (AbstractCard c : this.hand()) {
            if (c.hasTag(Tags.CARDINAL) && !c.uuid.equals(card.uuid) && !card.hasTag(Tags.CARDINAL)) {
                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(c, this.handGroup()));
                if (this.player != null && this.hasRelic(CoralToken.ID)) {
                    AbstractDungeon.actionManager.addToBottom(new TsunamiAction(3));
                }
            }
        }
        for (AbstractOrb o : this.orbs()) {
            if (o instanceof Alien) {
                ((Alien)o).triggerPassiveEffect();
            }
            if (o instanceof FireOrb) {
                ((FireOrb)o).playCard(card);
            }
        }

        if (card.hasTag(Tags.SPIDER)) {
            if (this.player != null) {
                DuelistMod.spidersPlayedThisCombat++;
                if (DuelistMod.spidersPlayedThisCombat > DuelistMod.spidersToPlayForTempHp) {
                    DuelistCard.gainTempHP(DuelistMod.spiderTempHP);
                    if (DuelistMod.spiderEffectResets) {
                        DuelistMod.spidersPlayedThisCombat = 0;
                    }
                }
            } else if (this.enemy != null) {
                this.enemy.counters.compute(EnemyDuelistCounter.SPIDER, (k,v)->v==null?1:v+1);
                if (this.enemy.counters.getOrDefault(EnemyDuelistCounter.SPIDER, 0) > DuelistMod.spidersToPlayForTempHp) {
                    DuelistCard.gainTempHP(this.enemy, this.enemy, DuelistMod.spiderTempHP);
                    if (DuelistMod.spiderEffectResets) {
                        this.enemy.counters.put(EnemyDuelistCounter.SPIDER, 0);
                    }
                }
            }
        }

        if (card.hasTag(Tags.BUG)) {
            if (this.player != null) {
                DuelistMod.bugsPlayedThisCombat++;
                if (DuelistMod.bugsPlayedThisCombat > DuelistMod.bugsToPlayForTempHp) {
                    DuelistCard.gainTempHP(DuelistMod.bugTempHP);
                    if (DuelistMod.bugEffectResets) {
                        DuelistMod.bugsPlayedThisCombat = 0;
                    }
                }
            } else if (this.enemy != null) {
                this.enemy.counters.compute(EnemyDuelistCounter.BUG, (k,v)->v==null?1:v+1);
                if (this.enemy.counters.getOrDefault(EnemyDuelistCounter.BUG, 0) > DuelistMod.bugsToPlayForTempHp) {
                    DuelistCard.gainTempHP(this.enemy, this.enemy, DuelistMod.bugTempHP);
                    if (DuelistMod.bugEffectResets) {
                        this.enemy.counters.put(EnemyDuelistCounter.BUG, 0);
                    }
                }
            }
        }

        if (card.hasTag(Tags.NATURIA)) {
            if (this.hasRelic(Leafpile.ID)) {
                this.applyPowerToSelf(Util.leavesPower(1, this));
            }

            int amt = 1;
            if (this.hasRelic(NaturiaRelic.ID)) {
                amt++;
            }

            AbstractPower vines = Util.vinesPower(amt, this);
            if (vines instanceof VinesPower) {
                if (!this.hasPower(VinesPower.POWER_ID)) {
                    this.applyPowerToSelf(vines);
                    for (AbstractPower pow : this.powers()) {
                        if (pow instanceof DuelistPower) {
                            ((DuelistPower)pow).onGainVines();
                        }
                    }
                    for (AbstractRelic r : this.relics()) {
                        if (r instanceof DuelistRelic) {
                            ((DuelistRelic)r).onGainVines();
                        }
                    }
                }
            } else if (vines instanceof LeavesPower) {
                this.applyPowerToSelf(vines);
            }
        }

        if (this.player != null) {
            DuelistMod.secondLastCardPlayed = DuelistMod.lastCardPlayed;
            DuelistMod.lastCardPlayed = card;
        } else if (this.enemy != null) {
            this.enemy.flags.put(EnemyDuelistFlag.SECOND_LAST_CARD_PLAYED, this.enemy.flags.getOrDefault(EnemyDuelistFlag.LAST_CARD_PLAYED, null));
            this.enemy.flags.put(EnemyDuelistFlag.LAST_CARD_PLAYED, card);
        }

        AnyHaunted.from(this.powers()).triggerHaunt(card);

        if (this.hasOrb()) {
            for (AbstractOrb o : this.orbs()) {
                if (o instanceof FireOrb) {
                    ((FireOrb)o).triggerPassiveEffect();
                }
            }
        }

        if (this.player != null) {
            DuelistMod.playedOneCardThisCombat = true;
        } else if (this.enemy != null) {
            this.enemy.flags.put(EnemyDuelistFlag.PLAYED_CARD_THIS_COMBAT, true);
        }

        if (card.type.equals(CardType.ATTACK)) {
            spellcasterMagickaCalc.accept(this, card);
            for (AbstractCard c : this.discardPile()) {
                giantOrcCalc.accept(c);
            }
            for (AbstractCard c : this.drawPile()) {
                giantOrcCalc.accept(c);
            }
        }
        if (card.type.equals(CardType.SKILL)) {

            for (AbstractCard c : this.discardPile()) {
                earthGiantCalc.accept(c);
            }
            for (AbstractCard c : this.drawPile()) {
                earthGiantCalc.accept(c);
            }
        }
        if (card.type.equals(CardType.POWER)) {
            for (AbstractCard c : this.discardPile()) {
                if (c instanceof GiantTrapHole) {
                    c.modifyCostForCombat(-c.magicNumber);
                    c.isCostModified = true;
                }
            }
            for (AbstractCard c : this.drawPile()) {
                if (c instanceof GiantTrapHole) {
                    c.modifyCostForCombat(-c.magicNumber);
                    c.isCostModified = true;
                }
            }
        }
        if (card instanceof DuelistCard) {
            DuelistCard dc = (DuelistCard)card;
            if (card.hasTag(Tags.SPELL)) {
                if (this.player != null) {
                    DuelistMod.spellCombatCount++;
                    DuelistMod.playedSpellThisTurn = true;
                    if (!DuelistMod.uniqueSpellsThisRunMap.containsKey(card.cardID)) {
                        DuelistMod.uniqueSpellsThisRunMap.put(card.cardID, card);
                        DuelistMod.uniqueSpellsThisRun.add((DuelistCard) card);
                        DuelistMod.uniqueSpellsThisCombat.add((DuelistCard) card);
                        DuelistMod.loadedSpellsThisRunList += card.cardID + "~";
                    }
                } else if (this.enemy != null) {
                    this.enemy.counters.compute(EnemyDuelistCounter.SPELLS_PLAYED, (k,v)->v==null?1:v+1);
                    this.enemy.flags.put(EnemyDuelistFlag.PLAYED_SPELL_THIS_TURN, true);
                }
            }
            if (card.hasTag(Tags.MONSTER)) {

                if (!Util.isExempt(card)) {
                    if (this.player != null && (DuelistMod.battleFusionMonster == null || DuelistMod.battleFusionMonster instanceof CancelCard)) {
                        DuelistMod.battleFusionMonster = card.makeStatEquivalentCopy();
                    } else if (this.enemy != null && (this.enemy.flags.getOrDefault(EnemyDuelistFlag.BATTLE_FUSION_MONSTER, null) == null || this.enemy.flags.getOrDefault(EnemyDuelistFlag.BATTLE_FUSION_MONSTER, null) instanceof CancelCard)) {
                        this.enemy.flags.put(EnemyDuelistFlag.LAST_CARD_PLAYED, card.makeStatEquivalentCopy());
                    }
                }

                if (card.hasTag(Tags.PLANT)) {
                    if (this.player != null) {
                        DuelistMod.secondLastPlantPlayed = DuelistMod.lastPlantPlayed;
                        DuelistMod.lastPlantPlayed = card;
                    } else if (this.enemy != null) {
                        this.enemy.flags.put(EnemyDuelistFlag.SECOND_LAST_PLANT_PLAYED, this.enemy.flags.getOrDefault(EnemyDuelistFlag.LAST_PLANT_PLAYED, null));
                        this.enemy.flags.put(EnemyDuelistFlag.LAST_PLANT_PLAYED, card);
                    }
                    if (dc.tributes > 1) {
                        for (AbstractCard c : this.exhaustPile()) {
                            if (c instanceof RevivalRose) {
                                ((DuelistCard)c).block();
                            }
                        }
                    }
                }

                if (card.hasTag(Tags.GHOSTRICK)) {
                    if (this.player != null) {
                        DuelistMod.secondLastGhostrickPlayed = DuelistMod.lastGhostrickPlayed;
                        DuelistMod.lastGhostrickPlayed = card;
                    } else if (this.enemy != null) {
                        this.enemy.flags.put(EnemyDuelistFlag.SECOND_LAST_GHOSTRICK_PLAYED, this.enemy.flags.getOrDefault(EnemyDuelistFlag.LAST_GHOSTRICK_PLAYED, null));
                        this.enemy.flags.put(EnemyDuelistFlag.LAST_GHOSTRICK_PLAYED, card);
                    }
                }

                if (this.hasPower(ReinforcementsPower.POWER_ID) && dc.tributes < 1 && card.hasTag(Tags.MONSTER)) {
                    DuelistCard.summon(this.creature(), 1, dc);
                }

                this.discardPile().forEach(c -> monsterCalcs.accept(c, card));
                this.drawPile().forEach(c -> monsterCalcs.accept(c, card));
            }
            if (card.hasTag(Tags.TRAP) && this.player != null) {
                if (!DuelistMod.uniqueTrapsThisRunMap.containsKey(card.cardID))
                {
                    DuelistMod.uniqueTrapsThisRunMap.put(card.cardID, card);
                    DuelistMod.uniqueTrapsThisRun.add((DuelistCard) card);
                    DuelistMod.loadedSpellsThisRunList += card.cardID + "~";
                }
            }
        }

    }

    public static AnyDuelist from(AbstractCreature creature) {
        AbstractPlayer player = creature instanceof AbstractPlayer ? (AbstractPlayer) creature : null;
        AbstractEnemyDuelist enemy = creature instanceof AbstractEnemyDuelist ? (AbstractEnemyDuelist) creature : null;
        return new AnyDuelist(player, enemy);
    }

    public static AnyDuelist from(AbstractCard cardSource) {
        AbstractEnemyDuelistCard cardCheck = AbstractEnemyDuelist.fromCardOrNull(cardSource);
        if (cardCheck == null) {
            return new AnyDuelist(AbstractDungeon.player);
        }
        return new AnyDuelist(AbstractEnemyDuelist.enemyDuelist);
    }

    public static AnyDuelist from(AbstractRelic relic) {
        if (AbstractEnemyDuelist.enemyDuelist == null) {
            return new AnyDuelist(AbstractDungeon.player);
        }
        for (AbstractRelic r : AbstractEnemyDuelist.enemyDuelist.relics) {
            if (r == relic) {
                return new AnyDuelist(AbstractEnemyDuelist.enemyDuelist);
            }
        }
        return new AnyDuelist(AbstractDungeon.player);
    }

    public static AnyDuelist from(AbstractPower power) {
        return from(power.owner);
    }

    public void block(int amt) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.creature(), this.creature(), amt));
    }

    public void talk(String text, float duration, float bubbleDuration) {
        TalkAction action = null;
        if (this.player != null) {
            action = new TalkAction(true, text, duration, bubbleDuration);
        } else if (this.enemy != null) {
            action = new TalkAction(this.enemy, text, duration, bubbleDuration);
        }

        if (action != null) {
            AbstractDungeon.actionManager.addToBottom(action);
        }
    }

    public boolean player() {
        return this.player != null;
    }

    public AbstractPlayer getPlayer() {
        return this.player;
    }

    public AbstractEnemyDuelist getEnemy() {
        return this.enemy;
    }

    public AbstractCreature creature() {
        return this.player != null ? this.player : this.enemy;
    }

    public boolean hasPower(String powerId) {
        return this.player != null ? this.player.hasPower(powerId) : this.enemy != null && this.enemy.hasPower(powerId);
    }

    public boolean hasRelic(String relicId) {
        return this.player != null ? this.player.hasRelic(relicId) : this.enemy != null && this.enemy.hasRelic(relicId);
    }

    public AbstractRelic getRelic(String relicId) {
        return this.player != null ? this.player.getRelic(relicId) : this.enemy != null ? this.enemy.getRelic(relicId) : null;
    }

    public AbstractPower getPower(String powerId) {
        return this.player != null ? this.player.getPower(powerId) : this.enemy != null ? this.enemy.getPower(powerId) : null;
    }

    public List<AbstractRelic> relics() {
        if (this.enemy != null) {
            return new ArrayList<>(this.enemy.relics);
        }
        return this.player != null ? this.player.relics : new ArrayList<>();
    }

    public List<AbstractOrb> orbs() {
        if (this.enemy != null) {
            return new ArrayList<>(this.enemy.orbs);
        }
        return this.player != null ? this.player.orbs : new ArrayList<>();
    }

    public ArrayList<AbstractCard> getCardsPlayedCombat() {
        return this.player != null ? AbstractDungeon.actionManager.cardsPlayedThisCombat : this.enemy != null ? this.enemy.cardsPlayedThisCombat : new ArrayList<>();
    }

    public List<AbstractPower> powers() {
        return this.player != null ? this.player.powers : this.enemy != null ? this.enemy.powers : new ArrayList<>();
    }


    public List<AbstractCard> hand() {
        return this.player != null ? this.player.hand.group : this.enemy != null ? this.enemy.hand.group : new ArrayList<>();
    }

    public CardGroup handGroup() {
        return this.player != null ? this.player.hand : this.enemy.hand;
    }

    public List<AbstractCard> discardPile() {
        return this.player != null ? this.player.discardPile.group : this.enemy != null ? this.enemy.discardPile.group : new ArrayList<>();
    }


    public List<AbstractCard> drawPile() {
        return this.player != null ? this.player.drawPile.group : this.enemy != null ? this.enemy.drawPile.group : new ArrayList<>();
    }

    public CardGroup drawPileGroup() {
        return this.player != null ? this.player.drawPile : this.enemy.drawPile;
    }

    public List<AbstractCard> masterDeck() {
        return this.player != null ? this.player.masterDeck.group : new ArrayList<>();
    }


    public List<AbstractCard> exhaustPile() {
        return this.player != null ? this.player.exhaustPile.group : this.enemy != null ? this.enemy.exhaustPile.group : new ArrayList<>();
    }

    public List<AbstractCard> resummonPile() {
        return this.player != null ? TheDuelist.resummonPile.group : this.enemy != null ? this.enemy.graveyard.group : new ArrayList<>();
    }

    public CardGroup resummonPileGroup() {
        return this.player != null ? TheDuelist.resummonPile : this.enemy.graveyard;
    }

    public List<AbstractCard> limbo() {
        return this.player != null ? this.player.limbo.group : this.enemy != null ? this.enemy.limbo.group : new ArrayList<>();
    }

    public CardGroup limboGroup() {
        return this.player != null ? this.player.limbo : this.enemy.limbo;
    }

    public List<AbstractPotion> potions() {
        return this.player != null ? this.player.potions : new ArrayList<>();
    }

    public AbstractStance stance() {
        return this.player != null ? this.player.stance : this.enemy != null ? this.enemy.stance : null;
    }

    public boolean hasOrb() {
        return this.player != null ? this.player.hasOrb() : this.enemy != null && this.enemy.hasOrb();
    }

    public int currentBlock() {
        return this.player != null ? this.player.currentBlock : this.enemy != null ? this.enemy.currentBlock : 0;
    }

    public boolean hasBlock() {
        return this.player != null ? this.player.currentBlock > 0 : this.enemy != null && this.enemy.currentBlock > 0;
    }

    public void setLastTagSummoned(AbstractCard summoned) {
        ArrayList<CardTags> types = DuelistCard.getAllMonsterTypes(summoned);
        if (types.size() > 0) {
            CardTags type = types.get(AbstractDungeon.cardRandomRng.random(types.size() - 1));
            if (this.player != null) {
                DuelistMod.lastTagSummoned = type;
            } else if (this.enemy != null) {
                this.enemy.flags.put(EnemyDuelistFlag.LAST_TYPE_SUMMONED, type);
            }
        }
    }

    public CardTags getLastTagSummoned() {
        if (this.player != null) {
            return DuelistMod.lastTagSummoned;
        } else if (this.enemy != null) {
            return (CardTags)this.enemy.flags.getOrDefault(EnemyDuelistFlag.LAST_TYPE_SUMMONED, Tags.ALL);
        }
        return Tags.ALL;
    }

    public void draw(int amt) {
        this.draw(amt, false);
    }

    public void draw(int amt, boolean bottom) {
        AbstractGameAction draw = this.player() ? new DrawCardAction(this.getPlayer(), amt) : new EnemyDrawActualCardsAction(this.getEnemy(), amt);
        if (bottom) {
            AbstractDungeon.actionManager.addToBottom(draw);
        } else {
            AbstractDungeon.actionManager.addToTop(draw);
        }
    }

    public void drawTag(int cards, CardTags tag) {
        drawTag(cards, tag, false);
    }

    public void drawTag(int cards, CardTags tag, boolean bottom) {
        AbstractGameAction draw = this.player() ? new DrawFromTagAction(this.creature(), cards, tag) : new EnemyDrawFromTagAction(this, cards, tag);
        if (bottom) {
            AbstractDungeon.actionManager.addToBottom(draw);
        } else {
            AbstractDungeon.actionManager.addToTop(draw);
        }
    }

    public void drawRare(int cards, CardRarity tag) {
        drawRare(cards, tag, false);
    }

    public void drawRare(int cards, CardRarity tag, boolean bottom) {
        AbstractGameAction draw = this.player() ? new DrawFromRarityAction(this.creature(), cards, tag) : new EnemyDrawFromRarityAction(this, cards, tag);
        if (bottom) {
            AbstractDungeon.actionManager.addToBottom(draw);
        } else {
            AbstractDungeon.actionManager.addToTop(draw);
        }
    }

    public void discard(int amt) {
        this.discard(amt, true, true);
    }

    public void discard(int amt, boolean isRandom) {
        this.discard(amt, isRandom, true);
    }

    public void discard(int amt, boolean isRandom, boolean bottom) {
        AbstractGameAction discard = this.player() ? new DiscardAction(this.creature(), this.creature(), amt, isRandom, false) : new EnemyDiscardAction(this.creature(), this.creature(), amt, isRandom, false);
        if (bottom) {
            AbstractDungeon.actionManager.addToBottom(discard);
        } else {
            AbstractDungeon.actionManager.addToTop(discard);
        }
    }

    public void addCardToHand(AbstractCard card) {
        ArrayList<AbstractCard> input = new ArrayList<>();
        input.add(card);
        this.addCardsToHand(input);
    }

    public void addCardsToHand(ArrayList<AbstractCard> cards) {
        if (this.enemy != null) {
            ArrayList<AbstractEnemyDuelistCard> c = new ArrayList<>();
            for (AbstractCard card : cards) {
                c.add(AbstractEnemyDuelist.fromCard(card));
            }
            AbstractDungeon.actionManager.addToTop(new EnemyDrawActualCardsAction(this.enemy, c));
        } else if (this.player()) {
            DuelistCard.addCardsToHand(cards);
        }
    }

    public void gainEnergy(int amt) {
        if (this.player()) {
            DuelistCard.gainEnergy(amt);
        } else if (this.enemy != null) {
            this.enemy.gainEnergy(amt);
        }
    }

    public void channel(AbstractOrb orb) {
        this.channel(orb, 1);
    }

    public void channel(AbstractOrb orb, int amt) {
        if (!this.player() && this.enemy == null) return;

        for (int i = 0; i < amt; i++) {
            if (this.player()) {
                AbstractDungeon.actionManager.addToTop(new ChannelAction(orb.makeCopy()));
            } else {
                AbstractDungeon.actionManager.addToTop(new EnemyChannelAction(orb.makeCopy()));
            }
        }
    }

    public void increaseMaxHP(int amt, boolean showEffect) {
        if (this.player != null) {
            this.player.increaseMaxHp(amt, showEffect);
        } else if (this.enemy != null) {
            this.enemy.increaseMaxHp(amt, showEffect);
        }
    }

    public void increaseOrbSlots(int amt) {
        if (this.player != null) {
            AbstractDungeon.actionManager.addToTop(new IncreaseMaxOrbAction(amt));
        } else if (this.enemy != null) {
            AbstractDungeon.actionManager.addToTop(new EnemyIncreaseMaxOrbAction(amt, this.enemy));
        }
    }

    public void applyPower(AbstractCreature target, AbstractCreature source, AbstractPower power) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, power, power.amount));
        this.handGroup().glowCheck();
    }

    public void applyPowerToSelf(AbstractPower power, AbstractCreature source) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.creature(), source, power, power.amount));
        this.handGroup().glowCheck();
    }

    public void applyPowerToSelf(AbstractPower power) {
        this.applyPowerToSelf(power, this.creature());
    }

    public void removePower(AbstractCreature target, AbstractCreature source, AbstractPower power) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, source, power, power.amount));
        if (AbstractDungeon.player != null) {
            AbstractDungeon.player.hand.glowCheck();
        }
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            AbstractEnemyDuelist.enemyDuelist.hand.glowCheck();
        }
    }

    public void removePowerFromSelf(AbstractPower power) {
        this.removePower(this.creature(), this.creature(), power);
    }

    public void damage(AbstractCreature target, AbstractCreature source, int dmg, DamageInfo.DamageType type, AttackEffect fx) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(source, dmg, type), fx));
    }

    public void damageSelf(int damage) {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.creature(), this.creature(), damage, AbstractGameAction.AttackEffect.POISON));
    }

    public void damageSelfNotHP(int damage) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.creature(), new DamageInfo(this.creature(), damage, DamageInfo.DamageType.NORMAL)));
    }

    public void normalMultiDmg(DuelistCard card) {
        if (card.hasTag(Tags.DRAGON)) {
            card.baseAFX = AttackEffect.FIRE;
        }
        if (this.player()) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.creature(), card.multiDamage, card.damageTypeForTurn, card.baseAFX));
        } else if (this.enemy != null) {
            card.attack(AbstractDungeon.player);
        }
    }

    public void thornsMultiDmg(int damage, DuelistCard ref) {
        if (this.player()) {
            int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.creature(), damageArray, DamageInfo.DamageType.THORNS, AttackEffect.SLASH_HORIZONTAL));
        } else if (this.enemy != null) {
            ref.attack(AbstractDungeon.player);
        }
    }

    public void incrementSummonCount(int summons) {
        if (this.player != null) {
            DuelistMod.summonCombatCount += summons;
            DuelistMod.summonRunCount += summons;
            DuelistMod.summonTurnCount += summons;
        } else {
            this.enemy.summonCombatCount += summons;
            this.enemy.summonTurnCount += summons;
        }
    }

    public void incrementTributeCount(int tributes) {
        if (this.player != null) {
            DuelistMod.tribCombatCount += tributes;
            DuelistMod.tribRunCount += tributes;
        } else {
            this.enemy.tributeCombatCount += tributes;
            this.enemy.tributeTurnCount += tributes;
        }
    }

    public void handleZombieSubTypesOnPlay(AbstractCard played) {
        this.incrementGhostrickPlayed(played);
        this.incrementMayakashiPlayed(played);
        this.incrementShiranuiPlayed(played);
        this.incrementVampiresPlayed(played);
        this.incrementVendreadPlayed(played);
    }

    private void incrementVampiresPlayed(AbstractCard playedCard) {
        if (this.player == null && this.enemy == null) return;

        if (playedCard.hasTag(Tags.VAMPIRE)) {
            if (this.player != null) {
                DuelistMod.vampiresPlayed++;
            } else {
                this.enemy.counters.compute(EnemyDuelistCounter.VAMPIRE, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        int vamp = DuelistMod.vampiresNeedPlayed + 1;
        if (this.hasRelic(VampiricPendant.ID)) {
            vamp -= 5;
        }
        if (vamp <= 0) {
            vamp = 1;
        }

        int siphonAmt = 5;
        if (DuelistMod.vampiresPlayEffect) {
            if (this.player != null && DuelistMod.vampiresPlayed >= vamp) {
                DuelistMod.vampiresPlayed = 0;
                DuelistCard.siphonAllEnemies(siphonAmt);
            } else if (this.enemy != null && this.enemy.counters.getOrDefault(EnemyDuelistCounter.VAMPIRE, 0) >= vamp) {
                this.enemy.counters.put(EnemyDuelistCounter.VAMPIRE, 0);
                DuelistCard.siphon(AbstractDungeon.player, this.enemy, siphonAmt);
            }
        }
    }

    private void incrementGhostrickPlayed(AbstractCard playedCard) {
        if (this.player == null && this.enemy == null) return;

        if (playedCard.hasTag(Tags.GHOSTRICK)) {
            if (this.player != null) {
                DuelistMod.ghostrickPlayed++;
            } else {
                this.enemy.counters.compute(EnemyDuelistCounter.GHOSTRICK, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        int copies = this.hasRelic(GhostToken.ID) ? 2 : 1;
        if (DuelistMod.ghostrickPlayEffect) {
            if (this.player != null && DuelistMod.ghostrickPlayed >= (DuelistMod.ghostrickNeedPlayed + 1)) {
                DuelistMod.ghostrickPlayed = 0;
                if (this.player.discardPile.size() > 0) {
                    AbstractDungeon.actionManager.addToBottom(new PlayRandomFromDiscardAction(1, copies, playedCard.uuid));
                }
            } else if (this.enemy != null && this.enemy.counters.getOrDefault(EnemyDuelistCounter.GHOSTRICK, 0) >= (DuelistMod.ghostrickNeedPlayed + 1)) {
                this.enemy.counters.put(EnemyDuelistCounter.GHOSTRICK, 0);
                if (this.enemy.discardPile.size() > 0) {
                    // TODO: enemy resummons
                }
            }
        }
    }

    private void incrementMayakashiPlayed(AbstractCard playedCard) {
        if (this.player == null && this.enemy == null) return;

        if (playedCard.hasTag(Tags.MAYAKASHI)) {
            if (this.player != null) {
                DuelistMod.mayakashiPlayed++;
            } else {
                this.enemy.counters.compute(EnemyDuelistCounter.MAYAKASHI, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        if (DuelistMod.mayakashiPlayEffect) {
            if (this.player != null && DuelistMod.mayakashiPlayed >= (DuelistMod.mayakashiNeedPlayed + 1)) {
                DuelistMod.mayakashiPlayed = 0;
                AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
                if (targetMonster != null) {
                    AbstractPower debuff = DebuffHelper.getRandomDebuff(this.player, targetMonster, 2);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(targetMonster, this.player, debuff));
                }
            } else if (this.enemy != null && this.enemy.counters.getOrDefault(EnemyDuelistCounter.MAYAKASHI, 0) >= (DuelistMod.mayakashiPlayed + 1)) {
                this.enemy.counters.put(EnemyDuelistCounter.MAYAKASHI, 0);
                AbstractPower debuff = DebuffHelper.getRandomPlayerDebuff(AbstractDungeon.player, 2);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this.enemy, debuff));
            }
        }
    }

    private void incrementVendreadPlayed(AbstractCard playedCard) {
        if (this.player == null && this.enemy == null) return;

        if (playedCard.hasTag(Tags.VENDREAD)) {
            if (this.player != null) {
                DuelistMod.vendreadPlayed++;
            } else {
                this.enemy.counters.compute(EnemyDuelistCounter.VENDREAD, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        if (DuelistMod.vendreadPlayEffect) {
            if (this.player != null && DuelistMod.vendreadPlayed >= (DuelistMod.vendreadNeedPlayed + 1)) {
                DuelistMod.vendreadPlayed = 0;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.player, this.player, new StrengthPower(this.player, 1)));
            } else if (this.enemy != null && this.enemy.counters.getOrDefault(EnemyDuelistCounter.VENDREAD, 0) >= (DuelistMod.vendreadPlayed + 1)) {
                this.enemy.counters.put(EnemyDuelistCounter.VENDREAD, 0);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.enemy, this.enemy, new StrengthPower(this.enemy, 1)));
            }
        }
    }

    private void incrementShiranuiPlayed(AbstractCard playedCard) {
        if (this.player == null && this.enemy == null) return;

        if (playedCard.hasTag(Tags.SHIRANUI)) {
            if (this.player != null) {
                DuelistMod.shiranuiPlayed++;
            } else {
                this.enemy.counters.compute(EnemyDuelistCounter.SHIRANUI, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        if (DuelistMod.shiranuiPlayEffect) {
            if (this.player != null && DuelistMod.shiranuiPlayed >= (DuelistMod.shiranuiNeedPlayed + 1)) {
                DuelistMod.shiranuiPlayed = 0;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.player, this.player, new DexterityPower(this.player, 1)));
            } else if (this.enemy != null && this.enemy.counters.getOrDefault(EnemyDuelistCounter.SHIRANUI, 0) >= (DuelistMod.shiranuiPlayed + 1)) {
                this.enemy.counters.put(EnemyDuelistCounter.SHIRANUI, 0);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.enemy, this.enemy, new DexterityPower(this.enemy, 1)));
            }
        }
    }

    public void vampirePlayedThisTurn(AbstractCard card) {
        if (this.player == null && this.enemy == null) return;

        if (card.hasTag(Tags.VAMPIRE)) {
            if (this.player != null) {
                DuelistMod.playedVampireThisTurn = true;
            } else {
                this.enemy.flags.put(EnemyDuelistFlag.PLAYED_VAMPIRE_THIS_TURN, true);
            }
        }
    }

    public void incrementMegatypeTributesThisRun() {
        if (this.player != null) {
            DuelistMod.megatypeTributesThisRun++;
        } else if (this.enemy != null) {
            this.enemy.counters.compute(EnemyDuelistCounter.MEGATYPE_TRIBUTES, (k,v)->v==null?1:v+1);
        }
    }

    public int getSummonCombatCount() {
        return this.player != null ? DuelistMod.summonCombatCount : this.enemy != null ? this.enemy.summonCombatCount : 0;
    }

    public int getSummonTurnCount() {
        return this.player != null ? DuelistMod.summonTurnCount : this.enemy != null ? this.enemy.summonTurnCount : 0;
    }

    public int getTributeCombatCount() {
        return this.player != null ? DuelistMod.tribCombatCount : this.enemy != null ? this.enemy.tributeCombatCount : 0;
    }

    @Override
    public String toString() {
        return "AnyDuelist[" + this.hashCode() + "] { " + (this.player != null ? "Player" : this.enemy != null ? "Enemy - " + this.enemy.name : "Neither") + " } ";
    }
}
