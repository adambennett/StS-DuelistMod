package duelistmod.cards.pools.toon.treat;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.TreatCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class ChocolateTreat extends TreatCard {

    public static final String ID = DuelistMod.makeID("ChocolateTreat");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ChocolateTreat.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;

    public ChocolateTreat() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, UPGRADE_DESCRIPTION);
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    }

    @Override public AbstractCard makeCopy() {
        return new ChocolateTreat();
    }

    @Override
    public void treat() {
        if (roulette()) {
            AnyDuelist duelist = AnyDuelist.from(this);
            duelist.drawTag(1, Tags.MONSTER);
        }
    }
}
