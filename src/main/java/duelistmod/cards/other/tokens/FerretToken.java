package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.TokenCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class FerretToken extends TokenCard {
    public static final String ID = DuelistMod.makeID("FerretToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("FerretToken.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;

    public FerretToken() {
    	this(NAME);
    }

    public FerretToken(String tokenName) {
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN); 
    	this.baseDamage = this.damage = 1;
    	this.summons = this.baseSummons = 1;
    	this.purgeOnUse = true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
    	summon();
    	attack(m);
    }

    @Override public AbstractCard makeCopy() {
        return new FerretToken();
    }

	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            }
	    	else { this.upgradeName(NAME + "+"); }
			this.upgradeDamage(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
}
