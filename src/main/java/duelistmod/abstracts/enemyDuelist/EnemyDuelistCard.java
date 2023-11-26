package duelistmod.abstracts.enemyDuelist;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RunicDome;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.DebuffParticleEffect;
import com.megacrit.cardcrawl.vfx.ShieldParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.BuffParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.StunStarEffect;
import com.megacrit.cardcrawl.vfx.combat.UnknownParticleEffect;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.cards.Hinotama;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.enums.EnemyDuelistCanUseReason;
import duelistmod.helpers.PowHelper;
import duelistmod.helpers.Util;
import duelistmod.powers.EmperorPower;
import duelistmod.powers.SummonPower;
import duelistmod.powers.ToonKingdomPower;
import duelistmod.powers.ToonWorldPower;
import duelistmod.stances.enemy.EnemyDivinity;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import static com.megacrit.cardcrawl.cards.AbstractCard.*;

public class EnemyDuelistCard implements Comparable<EnemyDuelistCard> {

    public AbstractCard cardBase;
    public AbstractEnemyDuelist owner;
    public boolean hov2;
    public int limit;
    public boolean forceDraw;
    public boolean bossDarkened;
    public boolean tempLighten;
    public boolean intentActive;
    public boolean showIntent;
    public int incrementGeneratedIfPlayed;
    public int energyGeneratedIfPlayed;
    public int strengthGeneratedIfPlayed;
    public int damageMultGeneratedIfPlayed;
    public int damageMultIfPlayed;
    public int focusGeneratedIfPlayed;
    public int vulnGeneratedIfPlayed;
    public int artifactConsumedIfPlayed;
    public boolean vulnCalculated;
    public static final String[] TEXT;
    public int newPrio;
    public int manualCustomDamageModifier;
    public float manualCustomDamageModifierMult;
    public boolean manualCustomVulnModifier;
    public static boolean fakeStormPower;
    private static final float INTENT_HB_W;
    public Hitbox intentHb;
    public AbstractMonster.Intent intent;
    public AbstractMonster.Intent tipIntent;
    public float intentAlpha;
    public float intentAlphaTarget;
    public float intentOffsetX;
    public float intentOffsetY;
    private final BobEffect bobEffect;
    private Texture intentImg;
    private Texture intentBg;
    private final PowerTip intentTip;
    public int intentDmg;
    protected int intentMultiAmt;
    private final Color intentColor;
    private float intentParticleTimer;
    private float intentAngle;
    public boolean lockIntentValues;
    public ArrayList<AbstractGameEffect> intentFlash;
    private final ArrayList<AbstractGameEffect> intentVfx;
    public boolean alwaysDisplayText;
    public static HashMap<String, Texture> imgMap;
    public int currentPriority;
    public EnemyDuelistCanUseReason cantUseReason;
    private UUID copyUUID;
    private boolean checkedMagicNumber;
    public boolean runic;
    public boolean temporaryNoRunic;

    public EnemyDuelistCard(AbstractCard baseCard) {
        this.cardBase = baseCard;
        this.hov2 = false;
        this.forceDraw = false;
        this.bossDarkened = false;
        this.tempLighten = false;
        this.intentActive = false;
        this.showIntent = false;
        this.energyGeneratedIfPlayed = 0;
        this.strengthGeneratedIfPlayed = 0;
        this.damageMultGeneratedIfPlayed = 1;
        this.damageMultIfPlayed = 1;
        this.focusGeneratedIfPlayed = 0;
        this.vulnGeneratedIfPlayed = 0;
        this.artifactConsumedIfPlayed = 0;
        this.vulnCalculated = false;
        this.newPrio = 0;
        this.manualCustomDamageModifier = 0;
        this.manualCustomDamageModifierMult = 1.0f;
        this.manualCustomVulnModifier = false;
        this.intentHb = new Hitbox(EnemyDuelistCard.INTENT_HB_W, EnemyDuelistCard.INTENT_HB_W);
        this.intentOffsetY = -90.0f * Settings.scale;
        this.bobEffect = new BobEffect();
        this.intentTip = new PowerTip();
        this.intentColor = Color.WHITE.cpy();
        this.intentFlash = new ArrayList<>();
        this.intentVfx = new ArrayList<>();
        this.alwaysDisplayText = false;
        this.owner = AbstractEnemyDuelist.enemyDuelist;
        this.limit = 99;
        this.checkedMagicNumber = false;
        this.intent = baseCard instanceof DuelistCard ? ((DuelistCard)baseCard).enemyIntent : null;
        this.currentPriority = this.autoPriority();
        this.runic = AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(RunicDome.ID);
        if (this.cardBase.type == CardType.CURSE || this.cardBase.type == CardType.STATUS) {
            this.forceDraw = true;
        }
    }

    public UUID getCopyUUID() {
        if (this.copyUUID == null) {
            this.setCopyUUID();
        }
        return copyUUID;
    }

    public void setCopyUUID() {
        this.setCopyUUID(UUID.randomUUID());
    }

    private void setCopyUUID(UUID copyUUID) {
        this.copyUUID = copyUUID;
        if (this.cardBase instanceof DuelistCard) {
            ((DuelistCard)this.cardBase).copyUUID = copyUUID;
        }
    }

    public static int statusValue() {
        int value = -5;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractEnemyDuelist.enemyDuelist != null) {
            if (AbstractEnemyDuelist.enemyDuelist.hasRelic("Medical Kit")) {
                value += 4;
            }
        }
        return value;
    }

    public int getPriority(final ArrayList<AbstractCard> hand) {
        if (this.cardBase.type == CardType.STATUS) {
            return statusValue();
        }
        return this.autoPriority();
    }

    public int getValue() {
        return 0;
    }

    public int getUpgradeValue() {
        return 0;
    }

    public int autoPriority() {
        final AbstractEnemyDuelist ownerBoss = this.owner;
        final boolean setupPhase = ownerBoss.onSetupTurn;
        float blockModifier = 1.0f;
        if (setupPhase) {
            blockModifier = 1.5f;
        }
        int value = 0;
        if (this.cardBase.type == CardType.STATUS) {
            value -= 10;
        }
        else if (this.cardBase.type == CardType.CURSE && this.cardBase.costForTurn < -1) {
            value -= 100;
        }
        if (this.cardBase.type == CardType.ATTACK) {
            int dmg = evaluateSpecificCardDmg();
            if (ownerBoss.currentHealth > ownerBoss.maxHealth / 2) {
                Util.log("Evaluating damage value for " + this.cardBase.name + ": " + dmg);
                value += Math.max(dmg, 0);
            }
            else {
                value += (int)Math.max(dmg * 2.0f, 0.0f);
            }
            if (this.owner.stance instanceof EnemyDivinity) {
                value *= 2;
            }
        }
        if (this.cardBase.block > 0) {
            float blockMod = ownerBoss.currentHealth > ownerBoss.maxHealth / 2 ? 0.7f : 1.3f;
            Util.log("Evaluating block value for " + this.cardBase.name + ": " + this.cardBase.block);
            value += (int)Math.max(this.cardBase.block * blockMod * blockModifier, 0.0f);
        }
        if (this.cardBase.type == CardType.POWER) {
            if (value == 0) value = 4;
            if (ownerBoss.currentHealth > ownerBoss.maxHealth / 2) {
                value *= 5;
            }
            else {
                value /= 2;
            }
        }
        if (this.cardBase.isEthereal) {
            value *= 2;
        }

        if (this.cardBase.hasTag(Tags.CARDINAL)) {
            value += 25;
        }

        if (this.cardBase instanceof DuelistCard) {
            DuelistCard dc = (DuelistCard)this.cardBase;
            if (dc.cardDraw && dc.costForTurn == 0 && Util.getChallengeLevel() > 0) {
                value += 900;
            }
            int netSummons = dc.summons - dc.tributes;
            value += netSummons;
            if (dc.damage <= 0 && dc.block <= 0 && dc.type != CardType.POWER) {
                int magicBonus = (3 * (dc.magicNumber > 0 ? dc.magicNumber : dc.secondMagic > 0 ? dc.secondMagic : Math.max(dc.thirdMagic, 0)));
                this.checkedMagicNumber = true;
                Util.log("Evaluating magic number value for " + this.cardBase.name + ": " + magicBonus);
                value += magicBonus;
            }

            value += dc.enemyHandScoreBonus(value);
        }

        value += ownerBoss.modifyCardPriority(value, this);
        Util.log("VALUE OF " + this.cardBase.name + ": " + value);
        return value;
    }

    public int backupPriority() {
        return this.cardBase.rarity == CardRarity.UNCOMMON ? 10 : this.cardBase.rarity == CardRarity.RARE ? 20 : 0;
    }

    public int thirdPriorityCheck(EnemyDuelistCard o) {
        DuelistCard dc = this.cardBase instanceof DuelistCard ? (DuelistCard)this.cardBase : null;
        DuelistCard oc = o.cardBase instanceof DuelistCard ? (DuelistCard)o.cardBase : null;
        if (dc == null || oc == null) return 0;
        if ((this.checkedMagicNumber || o.checkedMagicNumber) && dc.tributes != oc.tributes) return dc.tributes;
        return (3 * (dc.magicNumber > 0 ? dc.magicNumber : dc.secondMagic > 0 ? dc.secondMagic : Math.max(dc.thirdMagic, 0)));
    }

    public int evaluateSpecificCardDmg() {
        int dmg = this.cardBase.damage;
        if (this.cardBase instanceof Hinotama) {
            dmg *= 3;
        }
        return dmg;
    }

    public void applyPowers() {
        if (this.cardBase instanceof DuelistCard) {
            ((DuelistCard)this.cardBase).applyDuelistPowers();
        }
        this.applyPowersToBlock();
        final AbstractPlayer player = AbstractDungeon.player;
        this.cardBase.isDamageModified = false;
        float tmp = (float)this.cardBase.baseDamage;
        if (this.owner == null) {
            this.owner = AbstractEnemyDuelist.enemyDuelist;
        }
        if (this.owner.relics != null) {
            for (final AbstractRelic r : this.owner.relics) {
                tmp = r.atDamageModify(tmp, cardBase);
                if (this.cardBase.baseDamage != (int)tmp) {
                    this.cardBase.isDamageModified = true;
                }
            }
        }
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.atDamageGive(tmp, this.cardBase.damageTypeForTurn, cardBase);
        }
        tmp = this.owner.stance.atDamageGive(tmp, this.cardBase.damageTypeForTurn, cardBase);
        if (this.cardBase.baseDamage != (int)tmp) {
            this.cardBase.isDamageModified = true;
        }
        for (final AbstractPower p : player.powers) {
            tmp = p.atDamageReceive(tmp, this.cardBase.damageTypeForTurn, cardBase);
        }
        tmp = this.owner.stance.atDamageReceive(tmp, this.cardBase.damageTypeForTurn);
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.atDamageFinalGive(tmp, this.cardBase.damageTypeForTurn, cardBase);
        }
        for (final AbstractPower p : player.powers) {
            tmp = p.atDamageFinalReceive(tmp, this.cardBase.damageTypeForTurn, cardBase);
        }
        if (tmp < 0.0f) {
            tmp = 0.0f;
        }
        if (this.cardBase.baseDamage != MathUtils.floor(tmp)) {
            this.cardBase.isDamageModified = true;
        }
        this.cardBase.damage = MathUtils.floor(tmp);
        this.cardBase.initializeDescription();
        if (this.intent != null && !this.bossDarkened) {
            this.createIntent();
        }
        if (AbstractEnemyDuelist.enemyDuelist != null && AbstractEnemyDuelist.enemyDuelist.hasPower("stslib:Stunned")) {
            this.bossDarken();
            this.destroyIntent();
        }
    }

    protected void applyPowersToBlock() {
        this.cardBase.isBlockModified = false;
        float tmp = (float)this.cardBase.baseBlock;
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.modifyBlock(tmp, cardBase);
        }
        if (this.cardBase.baseBlock != MathUtils.floor(tmp)) {
            this.cardBase.isBlockModified = true;
        }
        if (tmp < 0.0f) {
            tmp = 0.0f;
        }
        this.cardBase.block = MathUtils.floor(tmp);
        if (this.cardBase instanceof DuelistCard) {
            ((DuelistCard)this.cardBase).duelistApplyPowersToBlock();
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.applyPowersToBlock();
        final AbstractPlayer player = AbstractDungeon.player;
        this.cardBase.isDamageModified = false;
        if (mo == null) {
            mo = this.owner;
        }
        else if (this.owner == null && mo instanceof AbstractEnemyDuelist) {
            this.owner = (AbstractEnemyDuelist)mo;
        }
        if (mo != null) {
            this.cardBase.damage = MathUtils.floor(this.calculateDamage(mo, player, (float)this.cardBase.baseDamage));
            this.intentDmg = MathUtils.floor(this.manualCustomDamageModifierMult * this.calculateDamage(mo, player, (float)(this.cardBase.baseDamage + this.customIntentModifiedDamage() + this.manualCustomDamageModifier)));
            this.intentDmg = MathUtils.floor(this.manualCustomDamageModifierMult * this.calculateDamage(mo, player, (float)(this.cardBase.baseDamage + this.customIntentModifiedDamage() + this.manualCustomDamageModifier)));
        }
        this.cardBase.initializeDescription();
    }

    private float calculateDamage(final AbstractMonster mo, final AbstractPlayer player, float tmp) {
        for (final AbstractRelic r : this.owner.relics) {
            tmp = r.atDamageModify(tmp, cardBase);
            if (this.cardBase.baseDamage != (int)tmp) {
                this.cardBase.isDamageModified = true;
            }
        }
        for (final AbstractPower p : this.owner.powers) {
            tmp = p.atDamageGive(tmp, this.cardBase.damageTypeForTurn, cardBase);
        }
        final AbstractEnemyStance stanceAtCardPlay = this.getEnemyStanceAtMomentOfCardPlay();
        tmp = stanceAtCardPlay.atDamageGive(tmp, this.cardBase.damageTypeForTurn, cardBase);
        if (this.cardBase instanceof DuelistCard) {
            tmp = ((DuelistCard)this.cardBase).calculateModifiedCardDamageDuelist(this.owner, player, tmp);
        }
        if (this.cardBase.baseDamage != (int)tmp) {
            this.cardBase.isDamageModified = true;
        }
        if (mo == this.owner) {
            for (final AbstractPower p2 : player.powers) {
                tmp = p2.atDamageReceive(tmp, this.cardBase.damageTypeForTurn, cardBase);
            }
            tmp = player.stance.atDamageReceive(tmp, this.cardBase.damageTypeForTurn);
        }
        else {
            for (final AbstractPower p2 : mo.powers) {
                tmp = p2.atDamageReceive(tmp, this.cardBase.damageTypeForTurn, cardBase);
            }
        }
        for (final AbstractPower p2 : this.owner.powers) {
            tmp = p2.atDamageFinalGive(tmp, this.cardBase.damageTypeForTurn, cardBase);
        }
        if (mo == this.owner) {
            for (final AbstractPower p2 : player.powers) {
                tmp = p2.atDamageFinalReceive(tmp, this.cardBase.damageTypeForTurn, cardBase);
            }
        }
        else {
            for (final AbstractPower p2 : mo.powers) {
                tmp = p2.atDamageFinalReceive(tmp, this.cardBase.damageTypeForTurn, cardBase);
            }
        }
        if (tmp < 0.0f) {
            tmp = 0.0f;
        }
        if (this.cardBase.baseDamage != MathUtils.floor(tmp)) {
            this.cardBase.isDamageModified = true;
        }
        return tmp;
    }

    private AbstractEnemyStance getEnemyStanceAtMomentOfCardPlay() {
        AbstractEnemyStance stanceAtCardPlay = (AbstractEnemyStance)this.owner.stance;

        for (final AbstractCard card : this.owner.hand.group) {
            if (this.cardBase == card) {
                break;
            }
            /*if (!(card instanceof AbstractStanceChangeCard)) {
                continue;
            }
            stanceAtCardPlay = ((AbstractStanceChangeCard)card).changeStanceForIntentCalc(stanceAtCardPlay);*/
        }
        return stanceAtCardPlay;
    }

    public void triggerOnEndOfPlayerTurn() {
        this.cardBase.triggerOnEndOfPlayerTurn();
        if (this.cardBase.isEthereal) {
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(cardBase, AbstractEnemyDuelist.enemyDuelist.hand));
        }
    }

    public boolean hasEnoughEnergy(Integer calculatedEnergy) {
        for (final AbstractPower p : AbstractEnemyDuelist.enemyDuelist.powers) {
            if (!p.canPlayCard(cardBase)) {
                this.cardBase.cantUseMessage = AbstractCard.TEXT[13];
                return false;
            }
        }
        if (AbstractEnemyDuelist.enemyDuelist.hasPower("Entangled") && this.cardBase.type == CardType.ATTACK) {
            this.cardBase.cantUseMessage = AbstractCard.TEXT[10];
            return false;
        }
        for (final AbstractRelic r : AbstractEnemyDuelist.enemyDuelist.relics) {
            if (!r.canPlay(cardBase)) {
                return false;
            }
        }
        for (final AbstractCard c : AbstractEnemyDuelist.enemyDuelist.hand.group) {
            if (!c.canPlay(cardBase)) {
                return false;
            }
        }
        int currentEnergy = calculatedEnergy != null ? calculatedEnergy : EnemyEnergyPanel.totalCount;
        if (currentEnergy >= this.cardBase.costForTurn || this.cardBase.freeToPlay() || this.cardBase.isInAutoplay) {
            return true;
        }
        this.cardBase.cantUseMessage = AbstractCard.TEXT[11];
        return false;
    }

    private boolean abstractCardSuperCanUse(AbstractCreature m, Integer calculatedEnergy) {
        if (this.cardBase.type == CardType.STATUS && this.cardBase.costForTurn < -1 && !AbstractEnemyDuelist.enemyDuelist.hasRelic("Medical Kit")) {
            return false;
        } else if (this.cardBase.type == CardType.CURSE && this.cardBase.costForTurn < -1 && !AbstractEnemyDuelist.enemyDuelist.hasRelic("Blue Candle")) {
            return false;
        } else {
            return this.cardPlayable(m) && this.hasEnoughEnergy(calculatedEnergy);
        }
    }

    public boolean xCostTributeCheck(final AbstractCreature p, Integer calculatedSummons) {
        if (this.cardBase.hasTag(Tags.X_COST)) {
            boolean mausoActive = (p.hasPower(EmperorPower.POWER_ID) && (!((EmperorPower)p.getPower(EmperorPower.POWER_ID)).flag));
            boolean atLeastOneTribute = calculatedSummons != null ? calculatedSummons > 0 : (p.hasPower(SummonPower.POWER_ID) && (p.getPower(SummonPower.POWER_ID).amount) > 0);
            return mausoActive || atLeastOneTribute;
        }
        return true;
    }

    private boolean checkModifyCanUseForAbstracts(AbstractEnemyDuelist p) {
        this.cantUseReason = EnemyDuelistCanUseReason.ABSTRACT_CHECKS_FAILED;
        if (cardBase instanceof DuelistCard) {
            DuelistCard dc = (DuelistCard)cardBase;
            if (!p.stance.modifyCanUse(p, dc)) {
                return false;
            }
            for (AbstractRelic r : p.relics) {
                if (r instanceof DuelistRelic) {
                    if (!((DuelistRelic)r).modifyCanUse(p, dc)) {
                        return false;
                    }
                }
            }
            for (DuelistOrb o : p.orbs) {
                if (!o.modifyCanUse(p, dc)) {
                    return false;
                }
            }
            for (AbstractPower pow : p.powers) {
                if (pow instanceof DuelistPower) {
                    DuelistPower duelPower = ((DuelistPower)pow);
                    if (!duelPower.modifyCanUse(p, dc)) {
                        return false;
                    }
                }
            }
            for (AbstractCard c : p.hand.group) {
                if (c instanceof DuelistCard) {
                    if (!((DuelistCard)c).modifyCanUseWhileInHand(p)) {
                        return false;
                    }
                }
            }
            for (AbstractCard c : p.discardPile.group) {
                if (!((DuelistCard)c).modifyCanUseWhileInDiscard(p)) {
                    return false;
                }
            }
            for (AbstractCard c : p.drawPile.group) {
                if (c instanceof DuelistCard) {
                    if (!((DuelistCard)c).modifyCanUseWhileInDraw(p)) {
                        return false;
                    }
                }
            }
            for (AbstractCard c : p.exhaustPile.group) {
                if (c instanceof DuelistCard) {
                    if (!((DuelistCard)c).modifyCanUseWhileInExhaust(p)) {
                        return false;
                    }
                }
            }
            for (AbstractCard c : TheDuelist.resummonPile.group) {
                if (c instanceof DuelistCard) {
                    if (!((DuelistCard)c).modifyCanUseWhileInGraveyard(p)) {
                        return false;
                    }
                }
            }
            if (p.hasPower(SummonPower.POWER_ID)) {
                SummonPower pow = (SummonPower)p.getPower(SummonPower.POWER_ID);
                for (DuelistCard c : pow.getCardsSummoned()) {
                        if (!c.modifyCanUseWhileSummoned(p)) {
                            return false;
                        }
                }
            }
        }
        this.cantUseReason = null;
        return true;
    }

    public boolean duelistCanUse(boolean summonChallenge, Integer calculatedSummons, Integer calculatedMaxSummons, Integer calculatedTributeCost) {
        if (!(this.cardBase instanceof DuelistCard)) return true;

        DuelistCard dc = (DuelistCard)this.cardBase;
        AnyDuelist duelist = AnyDuelist.from(this.owner);
        calculatedTributeCost = calculatedTributeCost == null ? dc.tributes : calculatedTributeCost;
        if (dc.isTributeCard(true)) {
            calculatedTributeCost += dc.checkModifyTributeCostForAbstracts(duelist, calculatedTributeCost);
            calculatedTributeCost = Util.modifyTributesForApexFeralTerritorial(duelist, dc, calculatedTributeCost);
        }
        boolean abstracts = checkModifyCanUseForAbstracts(this.owner);
        if (!abstracts) {
            // cantUseMessage set in the above function already.
            return false;
        }

        // Make sure Toon monsters have Toon World active.
        boolean passToonCheck = !this.cardBase.hasTag(Tags.TOON_WORLD) || ((this.owner.hasPower(ToonWorldPower.POWER_ID) || (this.owner.hasPower(ToonKingdomPower.POWER_ID))));
        if (!passToonCheck) {
            this.cantUseReason = EnemyDuelistCanUseReason.NO_TOON_WORLD;
            return false;
        }

        // Cards without Summon or Tribute are ok.
        if (calculatedTributeCost <= 0 && dc.summons <= 0) {
            return true;
        }

        // Check cards with Summons and/or Tributes.
        SummonPower summonPower = PowHelper.getPowerFromEnemyDuelist(SummonPower.POWER_ID);
        boolean hasMauso = this.owner.hasPower(EmperorPower.POWER_ID);
        int currentSummons = calculatedSummons != null ? calculatedSummons : this.owner.hasPower(SummonPower.POWER_ID) ? this.owner.getPower(SummonPower.POWER_ID).amount : 0;
        int netSummons = currentSummons + dc.summons - calculatedTributeCost;
        int netSummonsNoTrib = currentSummons + dc.summons;
        if (!Util.isSpawningBombCasingOnDetonate()) {
            if (dc.detonationCheckForSummonZones > 0) {
                netSummons -= dc.detonationCheckForSummonZones;
                netSummonsNoTrib -= dc.detonationCheckForSummonZones;
            } else if (dc.xDetonate && summonPower != null) {
                netSummons -= summonPower.numExplosiveTokens();
                netSummonsNoTrib -= summonPower.numExplosiveTokens();
            }
        }

        int maxSummons = calculatedMaxSummons != null ? calculatedMaxSummons : summonPower != null ? summonPower.getMaxSummons() : 0;
        maxSummons += dc.addToMaxSummonsDuringSummonZoneChecks();
        maxSummons += Util.checkBeastTag(currentSummons, maxSummons, dc);
        boolean summonZonesCheck = netSummons > -1 && netSummons <= maxSummons;

        // If checking for space in summon zones
        if (summonChallenge) {

            // Not tributing, either because no tribute cost or Emperor's Mausoleum is active
            if (calculatedTributeCost < 1 || (hasMauso && (!((EmperorPower)this.owner.getPower(EmperorPower.POWER_ID)).flag))) {
                boolean summonZoneCheck = netSummonsNoTrib <= maxSummons;
                if (!summonZoneCheck) {
                    this.cantUseReason = EnemyDuelistCanUseReason.NOT_ENOUGH_SUMMON_ZONES;
                }
                return summonZoneCheck;
            }

            // Player has summons, and enough to pay tribute cost
            else if (currentSummons >= calculatedTributeCost) {
                if (!summonZonesCheck) {
                    this.cantUseReason = EnemyDuelistCanUseReason.NOT_ENOUGH_SUMMON_ZONES;
                }
                return summonZonesCheck;
            } else {
                this.cantUseReason = EnemyDuelistCanUseReason.NOT_ENOUGH_SUMMONS;
                return false;
            }
        }

        // Only checking if tribute is possible, ignoring summon zone spaces
        else {
            boolean tribCheck = calculatedTributeCost < 1 || currentSummons >= calculatedTributeCost;
            boolean mausoCheck = hasMauso ? (!((EmperorPower) this.owner.getPower(EmperorPower.POWER_ID)).flag) || tribCheck : tribCheck;
            if (!mausoCheck) {
                this.cantUseReason = EnemyDuelistCanUseReason.NOT_ENOUGH_SUMMONS;
            }
            return mausoCheck;
        }
    }

    public boolean canUse(final AbstractCreature target, Integer calculatedSummons, Integer calculatedMaxSummons, Integer calculatedEnergy, Integer calculatedTributeCost) {
        boolean superCheck = abstractCardSuperCanUse(target, calculatedEnergy);
        if (!(this.cardBase instanceof DuelistCard)) {
            this.cantUseReason = EnemyDuelistCanUseReason.BASE_CARD_CHECKS_FAILED;
            return superCheck;
        }
        DuelistCard dc = (DuelistCard)this.cardBase;
        boolean outFlag = false;
        boolean cardChecks = dc.cardSpecificCanUse(this.owner);
        boolean xCostTributeChecks = this.xCostTributeCheck(this.owner, calculatedSummons);
        boolean summonChallenge = Util.isSummoningZonesRestricted();
        boolean goldChallenge = (Util.getChallengeDiffIndex() < 3);
        boolean resummon = summonChallenge ? goldChallenge && dc.misc == 52 : dc.misc == 52;
        this.cantUseReason = null;

        // super.canUse() or card is ignoring that AND card is being resummoned or it passes its own checks
        if ((superCheck || dc.ignoreSuperCanUse) && (resummon || (cardChecks && xCostTributeChecks))) {

            // True if: resummoning, card is ignoring normal global checks, or tribute/toon/summon/etc checks all pass
            outFlag = resummon || dc.ignoreDuelistCanUse || this.duelistCanUse(summonChallenge, calculatedSummons, calculatedMaxSummons, calculatedTributeCost);
        }
        if (!outFlag && this.cantUseReason == null) {
            this.cantUseReason = EnemyDuelistCanUseReason.UNKNOWN;
        }
        return outFlag;
    }

    public boolean canUse(final AbstractCreature target) {
        return canUse(target, null, null, null, null);
    }

    public boolean cardPlayable(final AbstractCreature m) {
        if (m == null) {
            return false;
        }
        return ((this.cardBase.target != CardTarget.ENEMY && this.cardBase.target != CardTarget.SELF_AND_ENEMY) || AbstractDungeon.player == null || !AbstractDungeon.player.isDying) && !AbstractDungeon.getMonsters().areMonstersBasicallyDead();
    }

    public void hover() {
        if (cardBase instanceof DuelistCard) {
            ((DuelistCard)cardBase).hoverFromEnemyCard();
        }
        if (!this.hov2 && this.owner.hand.group.stream().anyMatch(c -> c.uuid == this.cardBase.uuid)) {
            this.hov2 = true;
            EnemyCardGroup.hov2holder = this;
            AbstractEnemyDuelist.enemyDuelist.hand.refreshHandLayout();
            this.cardBase.targetDrawScale = 0.8f;
            this.cardBase.drawScale = 0.8f;
        }
    }

    public void unhover() {
        if (cardBase instanceof DuelistCard) {
            ((DuelistCard)cardBase).unhoverFromEnemyCard();
        }
        if (this.hov2) {
            this.hov2 = false;
            if (EnemyCardGroup.hov2holder == this) {
                EnemyCardGroup.hov2holder = null;
            }
            AbstractEnemyDuelist.enemyDuelist.hand.refreshHandLayout();
            this.cardBase.targetDrawScale = 0.35f;
            Util.log("Resetting cardBase draw scale to 0.35f - A");
        }
    }

    public void bossDarken() {
        if (!this.bossDarkened) {
            this.bossDarkened = true;
        }
    }

    public void bossLighten() {
        if (this.bossDarkened) {
            this.bossDarkened = false;
        }
    }

    public void renderHelperB(final SpriteBatch sb, final Color color, final TextureAtlas.AtlasRegion img, final float drawX, final float drawY) {
        sb.setColor(color);
        sb.draw(img, drawX + img.offsetX - img.originalWidth / 2.0f, drawY + img.offsetY - img.originalHeight / 2.0f, img.originalWidth / 2.0f - img.offsetX, img.originalHeight / 2.0f - img.offsetY, (float)img.packedWidth, (float)img.packedHeight, this.cardBase.drawScale * Settings.scale, this.cardBase.drawScale * Settings.scale, this.cardBase.angle);
    }

    public void renderHelperB(final SpriteBatch sb, final Color color, final TextureAtlas.AtlasRegion img, final float drawX, final float drawY, final float scale) {
        sb.setColor(color);
        sb.draw(img, drawX + img.offsetX - img.originalWidth / 2.0f, drawY + img.offsetY - img.originalHeight / 2.0f, img.originalWidth / 2.0f - img.offsetX, img.originalHeight / 2.0f - img.offsetY, (float)img.packedWidth, (float)img.packedHeight, this.cardBase.drawScale * Settings.scale * scale, this.cardBase.drawScale * Settings.scale * scale, this.cardBase.angle);
    }

    public void renderHelperB(final SpriteBatch sb, final Color color, final Texture img, final float drawX, final float drawY) {
        sb.setColor(color);
        sb.draw(img, drawX + 256.0f, drawY + 256.0f, 256.0f, 256.0f, 512.0f, 512.0f, this.cardBase.drawScale * Settings.scale, this.cardBase.drawScale * Settings.scale, this.cardBase.angle, 0, 0, 512, 512, false, false);
    }

    public void renderHelperB(final SpriteBatch sb, final Color color, final Texture img, final float drawX, final float drawY, final float scale) {
        sb.setColor(color);
        sb.draw(img, drawX, drawY, 256.0f, 256.0f, 512.0f, 512.0f, this.cardBase.drawScale * Settings.scale * scale, this.cardBase.drawScale * Settings.scale * scale, this.cardBase.angle, 0, 0, 512, 512, false, false);
    }

    public void update() {
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            cardBase.update();
            if (this.intent != null) {
                this.updateIntent();
            }
        }
        if (this.bossDarkened) {
            ReflectionHacks.setPrivate(this, AbstractCard.class, "tintColor", new Color(109.65f, 94.35f, 165.75f, 0.0f));
        }
    }

    public void refreshIntentHbLocation() {
        this.intentHb.move(this.cardBase.target_x + this.intentOffsetX, this.cardBase.target_y + this.intentOffsetY);
    }

    private void updateIntent() {
        this.bobEffect.update();
        if (this.intentAlpha != this.intentAlphaTarget && this.intentAlphaTarget == 1.0f) {
            this.intentAlpha += Gdx.graphics.getDeltaTime();
            if (this.intentAlpha > this.intentAlphaTarget) {
                this.intentAlpha = this.intentAlphaTarget;
            }
        }
        else if (this.intentAlphaTarget == 0.0f) {
            this.intentAlpha -= Gdx.graphics.getDeltaTime() / 1.5f;
            if (this.intentAlpha < 0.0f) {
                this.intentAlpha = 0.0f;
            }
        }
        if (!this.owner.isDying && !this.owner.isEscaping) {
            this.updateIntentVFX();
        }
        Iterator<AbstractGameEffect> i = this.intentVfx.iterator();
        while (i.hasNext()) {
            final AbstractGameEffect e = i.next();
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }
        i = this.intentFlash.iterator();
        while (i.hasNext()) {
            final AbstractGameEffect e = i.next();
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }
    }

    private void updateIntentVFX() {
        if (this.intentAlpha > 0.0f) {
            if (this.intent != AbstractMonster.Intent.ATTACK_DEBUFF && this.intent != AbstractMonster.Intent.DEBUFF && this.intent != AbstractMonster.Intent.STRONG_DEBUFF && this.intent != AbstractMonster.Intent.DEFEND_DEBUFF) {
                if (this.intent != AbstractMonster.Intent.ATTACK_BUFF && this.intent != AbstractMonster.Intent.BUFF && this.intent != AbstractMonster.Intent.DEFEND_BUFF) {
                    if (this.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
                        this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                        if (this.intentParticleTimer < 0.0f) {
                            this.intentParticleTimer = 0.5f;
                            this.intentVfx.add(new ShieldParticleEffect(this.intentHb.cX, this.intentHb.cY));
                        }
                    }
                    else if (this.intent == AbstractMonster.Intent.UNKNOWN) {
                        this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                        if (this.intentParticleTimer < 0.0f) {
                            this.intentParticleTimer = 0.5f;
                            this.intentVfx.add(new UnknownParticleEffect(this.intentHb.cX, this.intentHb.cY));
                        }
                    }
                    else if (this.intent == AbstractMonster.Intent.STUN) {
                        this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                        if (this.intentParticleTimer < 0.0f) {
                            this.intentParticleTimer = 0.67f;
                            this.intentVfx.add(new StunStarEffect(this.intentHb.cX, this.intentHb.cY));
                        }
                    }
                }
                else {
                    this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.intentParticleTimer < 0.0f) {
                        this.intentParticleTimer = 0.1f;
                        this.intentVfx.add(new BuffParticleEffect(this.intentHb.cX, this.intentHb.cY));
                    }
                }
            }
            else {
                this.intentParticleTimer -= Gdx.graphics.getDeltaTime();
                if (this.intentParticleTimer < 0.0f) {
                    this.intentParticleTimer = 1.0f;
                    this.intentVfx.add(new DebuffParticleEffect(this.intentHb.cX, this.intentHb.cY));
                }
            }
        }
    }

    private boolean getIsMultiDamageFromCard() {
        return ReflectionHacks.getPrivate(cardBase, AbstractCard.class, "isMultiDamage");
    }

    private void updateIntentTip() {
        switch (this.intent) {
            case ATTACK: {
                this.intentTip.header = EnemyDuelistCard.TEXT[0];
                if (getIsMultiDamageFromCard()) {
                    this.intentTip.body = EnemyDuelistCard.TEXT[1] + this.intentDmg + EnemyDuelistCard.TEXT[2] + this.intentMultiAmt + EnemyDuelistCard.TEXT[3];
                }
                else {
                    this.intentTip.body = EnemyDuelistCard.TEXT[4] + this.intentDmg + EnemyDuelistCard.TEXT[5];
                }
                this.intentTip.img = this.getAttackIntentTip();
                break;
            }
            case ATTACK_BUFF: {
                this.intentTip.header = EnemyDuelistCard.TEXT[6];
                if (getIsMultiDamageFromCard()) {
                    this.intentTip.body = EnemyDuelistCard.TEXT[7] + this.intentDmg + EnemyDuelistCard.TEXT[2] + this.intentMultiAmt + EnemyDuelistCard.TEXT[8];
                }
                else {
                    this.intentTip.body = EnemyDuelistCard.TEXT[9] + this.intentDmg + EnemyDuelistCard.TEXT[5];
                }
                this.intentTip.img = ImageMaster.INTENT_ATTACK_BUFF;
                break;
            }
            case ATTACK_DEBUFF: {
                this.intentTip.header = EnemyDuelistCard.TEXT[10];
                this.intentTip.body = EnemyDuelistCard.TEXT[11] + this.intentDmg + EnemyDuelistCard.TEXT[5];
                this.intentTip.img = ImageMaster.INTENT_ATTACK_DEBUFF;
                break;
            }
            case ATTACK_DEFEND: {
                this.intentTip.header = EnemyDuelistCard.TEXT[0];
                if (getIsMultiDamageFromCard()) {
                    this.intentTip.body = EnemyDuelistCard.TEXT[12] + this.intentDmg + EnemyDuelistCard.TEXT[2] + this.intentMultiAmt + EnemyDuelistCard.TEXT[3];
                }
                else {
                    this.intentTip.body = EnemyDuelistCard.TEXT[12] + this.intentDmg + EnemyDuelistCard.TEXT[5];
                }
                this.intentTip.img = ImageMaster.INTENT_ATTACK_DEFEND;
                break;
            }
            case BUFF: {
                this.intentTip.header = EnemyDuelistCard.TEXT[10];
                this.intentTip.body = EnemyDuelistCard.TEXT[19];
                this.intentTip.img = ImageMaster.INTENT_BUFF;
                break;
            }
            case DEBUFF: {
                this.intentTip.header = EnemyDuelistCard.TEXT[10];
                this.intentTip.body = EnemyDuelistCard.TEXT[20];
                this.intentTip.img = ImageMaster.INTENT_DEBUFF;
                break;
            }
            case STRONG_DEBUFF: {
                this.intentTip.header = EnemyDuelistCard.TEXT[10];
                this.intentTip.body = EnemyDuelistCard.TEXT[21];
                this.intentTip.img = ImageMaster.INTENT_DEBUFF2;
                break;
            }
            case DEFEND: {
                this.intentTip.header = EnemyDuelistCard.TEXT[13];
                this.intentTip.body = EnemyDuelistCard.TEXT[22];
                this.intentTip.img = ImageMaster.INTENT_DEFEND;
                break;
            }
            case DEFEND_DEBUFF: {
                this.intentTip.header = EnemyDuelistCard.TEXT[13];
                this.intentTip.body = EnemyDuelistCard.TEXT[23];
                this.intentTip.img = ImageMaster.INTENT_DEFEND;
                break;
            }
            case DEFEND_BUFF: {
                this.intentTip.header = EnemyDuelistCard.TEXT[13];
                this.intentTip.body = EnemyDuelistCard.TEXT[24];
                this.intentTip.img = ImageMaster.INTENT_DEFEND_BUFF;
                break;
            }
            case ESCAPE: {
                this.intentTip.header = EnemyDuelistCard.TEXT[14];
                this.intentTip.body = EnemyDuelistCard.TEXT[25];
                this.intentTip.img = ImageMaster.INTENT_ESCAPE;
                break;
            }
            case MAGIC: {
                this.intentTip.header = EnemyDuelistCard.TEXT[15];
                this.intentTip.body = EnemyDuelistCard.TEXT[26];
                this.intentTip.img = ImageMaster.INTENT_MAGIC;
                break;
            }
            case SLEEP: {
                this.intentTip.header = EnemyDuelistCard.TEXT[16];
                this.intentTip.body = EnemyDuelistCard.TEXT[27];
                this.intentTip.img = ImageMaster.INTENT_SLEEP;
                break;
            }
            case STUN: {
                this.intentTip.header = EnemyDuelistCard.TEXT[17];
                this.intentTip.body = EnemyDuelistCard.TEXT[28];
                this.intentTip.img = ImageMaster.INTENT_STUN;
                break;
            }
            case UNKNOWN: {
                this.intentTip.header = EnemyDuelistCard.TEXT[18];
                this.intentTip.body = EnemyDuelistCard.TEXT[29];
                this.intentTip.img = ImageMaster.INTENT_UNKNOWN;
                break;
            }
            case NONE: {
                this.intentTip.header = "";
                this.intentTip.body = "";
                this.intentTip.img = ImageMaster.INTENT_UNKNOWN;
                break;
            }
            default: {
                this.intentTip.header = "NOT SET";
                this.intentTip.body = "NOT SET";
                this.intentTip.img = ImageMaster.INTENT_UNKNOWN;
                break;
            }
        }
    }

    private Texture getAttackIntentTip() {
        int tmp;
        if (getIsMultiDamageFromCard() || this.intentMultiAmt > 0) {
            tmp = this.intentDmg * this.intentMultiAmt;
        }
        else {
            tmp = this.intentDmg;
        }
        if (tmp < 5) {
            return ImageMaster.INTENT_ATK_TIP_1;
        }
        if (tmp < 10) {
            return ImageMaster.INTENT_ATK_TIP_2;
        }
        if (tmp < 15) {
            return ImageMaster.INTENT_ATK_TIP_3;
        }
        if (tmp < 20) {
            return ImageMaster.INTENT_ATK_TIP_4;
        }
        if (tmp < 25) {
            return ImageMaster.INTENT_ATK_TIP_5;
        }
        return (tmp < 30) ? ImageMaster.INTENT_ATK_TIP_6 : ImageMaster.INTENT_ATK_TIP_7;
    }

    public void createIntent() {
        if (this.intent == null) {
            return;
        }
        this.refreshIntentHbLocation();
        if (!this.intentActive) {
            this.intentParticleTimer = 0.5f;
        }
        if (!this.lockIntentValues) {
            this.calculateCardDamage(null);
        }
        if (!this.lockIntentValues && this.cardBase.damage > -1) {
            if (getIsMultiDamageFromCard()) {
                this.intentMultiAmt = this.cardBase.magicNumber;
            }
            else {
                this.intentMultiAmt = -1;
            }
        }
        if (!this.intentActive) {
            this.intentImg = this.getIntentImg();
        }
        if (!this.intentActive) {
            this.intentBg = this.getIntentBg();
        }
        this.tipIntent = this.intent;
        if (!this.intentActive) {
            this.intentAlpha = 0.0f;
        }
        if (!this.intentActive) {
            this.intentAlphaTarget = 1.0f;
        }
        this.updateIntentTip();
        Util.log("Intent created: " + this.intent);
        this.showIntent = true;
        this.intentActive = true;
    }

    public int customIntentModifiedDamage() {
        return 0;
    }

    public void destroyIntent() {
        if (this.intent != null) {
            this.intentImg = null;
            this.intentBg = null;
            this.intentAlpha = 0.0f;
            this.intentAlphaTarget = 0.0f;
            this.showIntent = false;
            this.intentParticleTimer = 0.0f;
            this.tipIntent = null;
        }
    }

    public void render(final SpriteBatch sb) {
        if (this.runic && !this.temporaryNoRunic) return;

        if (cardBase instanceof DuelistCard) {
            ((DuelistCard)cardBase).renderFromEnemyCard(sb);
        }
        if (this.intent != null && !this.hov2) { //this.showIntent && !this.hov2) {
            if (!this.owner.isDying && !this.owner.isEscaping && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.isDead && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != AbstractMonster.Intent.NONE && !Settings.hideCombatElements) {
                this.renderIntentVfxBehind(sb);
                this.renderIntent(sb);
                this.renderIntentVfxAfter(sb);
                this.renderDamageRange(sb);
            }
            cardBase.hb.render(sb);
            this.intentHb.render(sb);
        }
    }

    private Texture getIntentImg() {
        switch (this.intent) {
            case ATTACK: {
                return this.getAttackIntent();
            }
            case ATTACK_BUFF: {
                return this.getAttackIntent();
            }
            case ATTACK_DEBUFF: {
                return this.getAttackIntent();
            }
            case ATTACK_DEFEND: {
                return this.getAttackIntent();
            }
            case BUFF: {
                return ImageMaster.INTENT_BUFF_L;
            }
            case DEBUFF: {
                return ImageMaster.INTENT_DEBUFF_L;
            }
            case STRONG_DEBUFF: {
                return ImageMaster.INTENT_DEBUFF2_L;
            }
            case DEFEND: {
                return ImageMaster.INTENT_DEFEND_L;
            }
            case DEFEND_DEBUFF: {
                return ImageMaster.INTENT_DEFEND_L;
            }
            case DEFEND_BUFF: {
                return ImageMaster.INTENT_DEFEND_BUFF_L;
            }
            case ESCAPE: {
                return ImageMaster.INTENT_ESCAPE_L;
            }
            case MAGIC: {
                return ImageMaster.INTENT_MAGIC_L;
            }
            case SLEEP: {
                return ImageMaster.INTENT_SLEEP_L;
            }
            case STUN: {
                return null;
            }
            case UNKNOWN: {
                return ImageMaster.INTENT_UNKNOWN_L;
            }
            default: {
                return ImageMaster.INTENT_UNKNOWN_L;
            }
        }
    }

    @SuppressWarnings({"SameReturnValue", "DuplicateBranchesInSwitch"})
    private Texture getIntentBg() {
        //noinspection SwitchStatementWithTooFewBranches
        switch (this.intent) {
            case ATTACK_DEFEND: {
                return null;
            }
            default: {
                return null;
            }
        }
    }

    protected Texture getAttackIntent() {
        int tmp;
        if (getIsMultiDamageFromCard() || this.intentMultiAmt > 0) {
            tmp = this.intentDmg * this.intentMultiAmt;
        }
        else {
            tmp = this.intentDmg;
        }
        if (tmp < 5) {
            return ImageMaster.INTENT_ATK_1;
        }
        if (tmp < 10) {
            return ImageMaster.INTENT_ATK_2;
        }
        if (tmp < 15) {
            return ImageMaster.INTENT_ATK_3;
        }
        if (tmp < 20) {
            return ImageMaster.INTENT_ATK_4;
        }
        if (tmp < 25) {
            return ImageMaster.INTENT_ATK_5;
        }
        return (tmp < 30) ? ImageMaster.INTENT_ATK_6 : ImageMaster.INTENT_ATK_7;
    }

    private void renderDamageRange(final SpriteBatch sb) {
        if (this.intent.name().contains("ATTACK") || this.alwaysDisplayText) {
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, this.overrideIntentText(), this.intentHb.cX - 30.0f * Settings.scale, this.intentHb.cY + this.bobEffect.y - 12.0f * Settings.scale, this.intentColor);
        }
    }

    public String overrideIntentText() {
        if (this.cardBase.type == CardType.POWER && (this.owner.hasPower("Storm") || EnemyDuelistCard.fakeStormPower)) {
           // return "(" + (3 + AbstractEnemyOrb.masterPretendFocus + EnZap.getFocusAmountSafe()) + ")";
        }
        if (getIsMultiDamageFromCard()) {
            return this.intentDmg + "x" + this.intentMultiAmt;
        }
        return Integer.toString(this.intentDmg);
    }

    private void renderIntentVfxBehind(final SpriteBatch sb) {
        for (final AbstractGameEffect e : this.intentVfx) {
            if (e.renderBehind) {
                e.render(sb);
            }
        }
    }

    private void renderIntentVfxAfter(final SpriteBatch sb) {
        for (final AbstractGameEffect e : this.intentVfx) {
            if (!e.renderBehind) {
                e.render(sb);
            }
        }
    }

    private void renderIntent(final SpriteBatch sb) {
        this.intentColor.a = this.intentAlpha;
        sb.setColor(this.intentColor);
        if (this.intentBg != null) {
            Util.log("Intent bg was not null, drawing intentBg");
            sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.intentAlpha / 2.0f));
            sb.draw(this.intentBg, this.intentHb.cX - 64.0f, this.intentHb.cY - 64.0f + this.bobEffect.y, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 128, 128, false, false);
        }
        if (this.intentImg != null && this.intent != AbstractMonster.Intent.UNKNOWN && this.intent != AbstractMonster.Intent.STUN) {
            if (this.intent != AbstractMonster.Intent.DEBUFF && this.intent != AbstractMonster.Intent.STRONG_DEBUFF) {
                this.intentAngle = 0.0f;
            }
            else {
                this.intentAngle += Gdx.graphics.getDeltaTime() * 150.0f;
            }
            sb.setColor(this.intentColor);
            sb.draw(this.intentImg, this.intentHb.cX - 64.0f, this.intentHb.cY - 64.0f + this.bobEffect.y, 64.0f, 64.0f, 128.0f, 128.0f, Settings.scale, Settings.scale, this.intentAngle, 0, 0, 128, 128, false, false);
            //Util.log("Drew intent img - intentHb.cX=" + this.intentHb.cX + ", intentHb.cY=" + this.intentHb.cY + ", bobEffect.y=" + this.bobEffect.y);
        } else {
            //Util.log("Could not render intent img - intentImg=" + this.intentImg);
        }
        for (final AbstractGameEffect e : this.intentFlash) {
            e.render(sb, this.intentHb.cX - 64.0f, this.intentHb.cY - 64.0f);
        }
    }

    public void onSpecificTrigger() {
    }

    @Override
    public int compareTo(EnemyDuelistCard a) {
        int check = Integer.compare(a.autoPriority(), this.autoPriority());
        if (check == 0) {
            check = Integer.compare(a.backupPriority(), this.backupPriority());
            if (check == 0) {
                check = Integer.compare(a.thirdPriorityCheck(this), this.thirdPriorityCheck(a));
                if (check == 0) {
                    check = a.cardBase.name.compareTo(this.cardBase.name);
                }
            }
        }
        return check;
    }

    @Override
    public String toString() {
        return "{ " + this.cardBase.cardID + " }: " + this.autoPriority() + ", " + this.backupPriority();
    }

    static {
        INTENT_HB_W = 64.0f * Settings.scale;
        EnemyDuelistCard.imgMap = new HashMap<>();
        TEXT = CardCrawlGame.languagePack.getUIString("AbstractMonster").TEXT;
    }
}
