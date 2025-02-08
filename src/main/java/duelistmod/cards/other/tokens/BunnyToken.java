package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.TokenCard;
import duelistmod.actions.unique.PurgeSpecificCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class BunnyToken extends TokenCard {

    public static final String ID = DuelistMod.makeID("BunnyToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Bunilla.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    private boolean isPermanent;
    private boolean isForcingUpgrade;

    public BunnyToken() {
        this(false);
    }

    public BunnyToken(boolean isPermanent) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TOKEN);
        this.tags.add(Tags.BEAST);
        this.summons = this.baseSummons = 1;
        this.baseBlock = this.block = 7;
        this.purgeOnUse = !isPermanent;
        this.isPermanent = isPermanent;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        block();
        postDuelistUseCard(owner, targets);
    }

    @Override public AbstractCard makeCopy() {
        return new BunnyToken();
    }

	@Override public void upgrade() {
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
			this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (!this.isPermanent && DuelistMod.persistentDuelistData.CardConfigurations.getTokensPurgeAtEndOfTurn()) {
            AnyDuelist duelist = AnyDuelist.from(this);
            AbstractDungeon.effectList.add(new ExhaustCardEffect(this));
            AbstractDungeon.actionManager.addToTop(new PurgeSpecificCard(this, duelist.handGroup()));
        }
    }

    @Override
    public boolean canUpgrade() {
        return super.canUpgrade() || this.isForcingUpgrade;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public void setForcingUpgrade(boolean forcingUpgrade) {
        isForcingUpgrade = forcingUpgrade;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        if (card instanceof BunnyToken) {
            BunnyToken token = (BunnyToken) card;
            token.setForcingUpgrade(this.isForcingUpgrade);
            token.setPermanent(this.isPermanent);
            return token;
        }
        return card;
    }
}
