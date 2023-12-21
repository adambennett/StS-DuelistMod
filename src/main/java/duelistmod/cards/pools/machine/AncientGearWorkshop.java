package duelistmod.cards.pools.machine;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.*;
import duelistmod.cards.other.tempCards.RarityTempCardA;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class AncientGearWorkshop extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("AncientGearWorkshop");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AncientGearWorkshop.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public AncientGearWorkshop() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 3;
        this.baseSecondMagic = this.secondMagic = 1;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.IS_OVERFLOW);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.ANCIENT_FOR_MACHINE);
        this.misc = 0;
        this.originalName = this.name;
    }
    
    @Override
    public void triggerOverflowEffect()
    {
    	super.triggerOverflowEffect();
    	applyPowerToSelf(new ArtifactPower(AbstractDungeon.player, this.secondMagic));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (this.magicNumber > 0) 
    	{
    		ArrayList<AbstractCard> choices = new ArrayList<>();
    		choices.add(new RarityTempCardA(this.magicNumber, CardRarity.COMMON));
    		choices.add(new RarityTempCardA(this.magicNumber, CardRarity.UNCOMMON));
    		choices.add(new RarityTempCardA(this.magicNumber, CardRarity.RARE));
    		this.addToBot(new CardSelectScreenResummonAction(choices, 1));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AncientGearWorkshop();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }
    


	

	










   
}
