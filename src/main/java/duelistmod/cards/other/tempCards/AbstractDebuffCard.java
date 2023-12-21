package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.patches.AbstractCardEnum;

public class AbstractDebuffCard extends BuffCard {
    public static final String ID = DuelistMod.makeID("AbstractDebuffCard");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("SphereChaos.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 0;

    public AbstractDebuffCard(AbstractPower power) {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, power);
    	this.baseMagicNumber = this.magicNumber = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	if (this.getPowerToApply() != null) {
            applyPower(this.getPowerToApply(), m);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AbstractDebuffCard(getPowerToApply());
    }

}
