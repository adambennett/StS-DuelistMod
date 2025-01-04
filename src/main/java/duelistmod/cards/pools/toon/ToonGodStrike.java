package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToonGodStrike extends DynamicDamageCard {
    public static final String ID = DuelistMod.makeID("ToonGodStrike");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ToonGodStrike.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    private static final int baseTrib = 12;

    public ToonGodStrike() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = this.originalDamage = 0;
        this.tributes = this.baseTributes = baseTrib;
        this.baseMagicNumber = this.magicNumber = 1;
        this.baseSecondMagic = this.secondMagic = 30;
        this.misc = 0;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.TOON);
        this.tags.add(Tags.REQUIRES_TOON_WORLD);
        this.tags.add(Tags.EXEMPT);
        this.enemyIntent = AbstractMonster.Intent.ATTACK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	duelistUseCard(p, m);
    }

    @Override
    public int damageFunction() {
        AnyDuelist duelist = AnyDuelist.from(this);
        int total = 0;
        if (duelist.hasPower(SummonPower.POWER_ID)) {
            SummonPower pow = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
            int uniqueTypes = pow.getNumberOfUniqueMonsterTypesSummoned(true, false);
            total = this.magicNumber * uniqueTypes;
        }
        return Math.max(0, total);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        if (targets.size() > 0) {
            attack(targets.get(0), this.baseAFX, this.damage);
        }
        if (this.tributes == 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, baseTrib - this.tributes, true));
            this.rawDescription = this.originalDescription;
            this.initializeDescription();
        }
        else if (this.tributes != baseTrib)
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyTributeAction(this, baseTrib - this.tributes, true));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        boolean triggered = false;
        AbstractCard lastCard = DuelistMod.lastCardPlayed != null ? DuelistMod.lastCardPlayed : c;
        if (DuelistMod.secondLastCardPlayed != null && lastCard != null && !DuelistMod.secondLastCardPlayed.uuid.equals(lastCard.uuid)) {
            List<CardTags> secondLastMonsterTypes = new ArrayList<>();
            List<CardTags> lastMonsterTypes = new ArrayList<>();
            for (CardTags tag : DuelistMod.monsterTypes) {
                for (CardTags t : DuelistMod.secondLastCardPlayed.tags) {
                    if (t.equals(tag)) secondLastMonsterTypes.add(t);
                }
                for (CardTags t : lastCard.tags) {
                    if (t.equals(tag)) lastMonsterTypes.add(t);
                }
            }
            List<CardTags> mismatchesOne = secondLastMonsterTypes.stream().filter(t -> !lastMonsterTypes.contains(t)).collect(Collectors.toList());
            List<CardTags> mismatchesTwo = lastMonsterTypes.stream().filter(t -> !secondLastMonsterTypes.contains(t)).collect(Collectors.toList());
            if (!mismatchesOne.isEmpty() && !mismatchesTwo.isEmpty()) {
                triggered = true;
            }
        }
    	if (triggered && this.tributes > 0) {
    		AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(this, -this.magicNumber, true));
    	}
    }

    @Override
    public void upgrade() {
    	if (!upgraded) {
    		if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
    		else { this.upgradeName(NAME + "+"); }
    		this.upgradeMagicNumber(1);
    		this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
    		this.initializeDescription();
    	}
    }

	@Override
    public AbstractCard makeCopy() {
        return new ToonGodStrike();
    }
}
