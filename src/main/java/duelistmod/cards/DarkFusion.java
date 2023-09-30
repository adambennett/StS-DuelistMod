package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.ModifySummonAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class DarkFusion extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("DarkFusion");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkFusion.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public DarkFusion() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
		this.tags.add(Tags.ARCANE);
        this.tags.add(Tags.FUSION);
        this.baseMagicNumber = this.magicNumber = 1;
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	for (AbstractCard c : p.hand.group)
    	{
    		if (c instanceof DuelistCard)
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.isSummonCard())
    			{
    				AbstractDungeon.actionManager.addToTop(new ModifySummonAction(dC, this.magicNumber, false));
    			}
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkFusion();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (canUpgrade()) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
            else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 5) { return true; }
    	else { return false; }
    }












}
