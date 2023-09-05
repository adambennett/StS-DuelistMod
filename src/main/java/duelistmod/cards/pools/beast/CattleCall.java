package duelistmod.cards.pools.beast;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class CattleCall extends DuelistCard {
    public static final String ID = DuelistMod.makeID("CattleCall");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CattleCall.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public CattleCall() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.SPELL);
        this.tags.add(Tags.BAD_MAGIC);
    	this.misc = 0;
    	this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 5;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        AnyDuelist duelist = AnyDuelist.from(this);
        int beasts = (int) duelist.hand().stream().filter(c -> c.hasTag(Tags.BEAST)).count();
        if (beasts >= this.magicNumber && duelist.hasPower(DexterityPower.POWER_ID)) {
            int dex = duelist.getPower(DexterityPower.POWER_ID).amount;
            duelist.applyPowerToSelf(new DexterityPower(duelist.creature(), dex));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.hand().stream().filter(c -> c.hasTag(Tags.BEAST)).count() >= this.magicNumber && duelist.hasPower(DexterityPower.POWER_ID)) {
            this.glowColor = Color.GOLD;
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new CattleCall();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
