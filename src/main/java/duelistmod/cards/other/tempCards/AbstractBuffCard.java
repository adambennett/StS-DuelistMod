package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class AbstractBuffCard extends BuffCard {
    public static final String ID = DuelistMod.makeID("AbstractBuffCard");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.RED_MEDICINE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 0;


    public AbstractBuffCard(AbstractPower power) {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, power);
    	this.tags.add(Tags.TOKEN);
    	this.baseMagicNumber = this.magicNumber = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)  {
    	if (this.getPowerToApply() != null) {
            applyPower(this.getPowerToApply(), p);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AbstractBuffCard(this.getPowerToApply());
    }

}
