package duelistmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.ExodiaPower;
import duelistmod.variables.*;

public class ExodiaHead extends DuelistCard 
{

    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ExodiaHead");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.EXODIA_HEAD);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public ExodiaHead() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.EXODIA);
        this.tags.add(Tags.EXODIA_PIECE);
        this.tags.add(Tags.EXODIA_HEAD);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.LIMITED);
        this.tags.add(Tags.EXODIA_DECK);
		this.tags.add(Tags.ARCANE);
        this.exodiaDeckCopies = 1; 
        this.magicNumber = this.baseMagicNumber = 100;
        this.exodiaName = "Head";
        this.originalName = this.name;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
       if (p.hasPower(ExodiaPower.POWER_ID))
       {
    	   ExodiaPower powerInstance = (ExodiaPower)p.getPower(ExodiaPower.POWER_ID);
    	   if (powerInstance.checkForAllPiecesButHead())
    	   {
    		   powerInstance.addNewPiece(this);
    		   powerInstance.headDamage(this.magicNumber);
    	   }
       }
    }
    
    @Override
    public void triggerOnGlowCheck()
    {
    	super.triggerOnGlowCheck();
    	boolean dealExtra = false;
    	if (AbstractDungeon.player.hasPower(ExodiaPower.POWER_ID))
        {
     	   ExodiaPower powerInstance = (ExodiaPower)AbstractDungeon.player.getPower(ExodiaPower.POWER_ID);
     	   if (powerInstance.checkForAllPiecesButHead())
     	   {
     		   dealExtra = true;
     	   }
        }
        if (dealExtra) {
        	 this.glowColor = Color.GOLD;
        }
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ExodiaHead();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (canUpgrade()) {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(75);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber <= 4925) { return true; }
    	return false;
    }












}
