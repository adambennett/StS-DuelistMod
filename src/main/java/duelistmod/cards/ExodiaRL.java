package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class ExodiaRL extends DuelistCard 
{

    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("ExodiaRL");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.EXODIA_RIGHT_LEG);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ExodiaRL() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.EXODIA);
        this.tags.add(Tags.EXODIA_PIECE);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.LIMITED);
        this.tags.add(Tags.EXODIA_DECK);
        this.exodiaDeckCopies = 1;
        this.summons = this.baseSummons = 1;
        this.damage =  this.baseDamage = 1;
        this.baseBlock = this.block = 5;
        this.exodiaName = "Right Leg";
        this.originalName = this.name;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    	summon(p, this.summons, this);
    	block(this.block);
    	
    	// If player has already played at least 1 other piece of exodia
    	if (p.hasPower(ExodiaPower.POWER_ID))
    	{
    		// If power has not already triggered once or this is not the first piece played in second set
    		if (p.getPower(ExodiaPower.POWER_ID).amount > 0)
    		{
    			ExodiaPower power = (ExodiaPower) p.getPower(ExodiaPower.POWER_ID);
    			power.addNewPiece(this);
    		}
    		
    		// If power has already triggered and player has the power but it's 0
    		// Just reroll the power
    		else
    		{
    			applyPowerToSelf(new ExodiaPower(p, p, this));
    		}
    	}
    	
    	// If player doesn't yet have any pieces assembled
    	else { applyPowerToSelf(new ExodiaPower(p, p, this)); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ExodiaRL();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            //this.exhaust = false;
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }




}
