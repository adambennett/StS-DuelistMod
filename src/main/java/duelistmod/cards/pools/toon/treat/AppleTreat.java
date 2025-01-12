package duelistmod.cards.pools.toon.treat;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.TreatCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;
import java.util.stream.Collectors;

public class AppleTreat extends TreatCard {

    public static final String ID = DuelistMod.makeID("AppleTreat");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AppleTreat.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;

    public AppleTreat() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, UPGRADE_DESCRIPTION);
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    }

    @Override public AbstractCard makeCopy() {
        return new AppleTreat();
    }

    @Override
    public void treat(boolean fromApple) {
        AnyDuelist duelist = AnyDuelist.from(this);
        List<TreatCard> applesInHand = duelist.hand().stream()
                .filter(c -> c instanceof AppleTreat)
                .map(c -> (TreatCard)c)
                .collect(Collectors.toList());
        List<TreatCard> treatsInHand = duelist.hand().stream()
                .filter(c -> c instanceof TreatCard && !(c instanceof AppleTreat))
                .map(c -> (TreatCard)c)
                .collect(Collectors.toList());
        int appleTriggers = applesInHand.size();
        for (int i = 0; i < appleTriggers; i++) {
            for (TreatCard treat : treatsInHand) {
                treat.treat(true);
            }
        }
    }
}
