package defaultmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.FocusPower;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class IcyCrevasse extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("IcyCrevasse");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.ICY_CREVASSE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    //private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    private static ArrayList<DuelistCard> natureMonsters = new ArrayList<DuelistCard>();
    // /STAT DECLARATION/

    public IcyCrevasse() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.TRAP);
        this.tags.add(DefaultMod.ALL);
        this.tags.add(DefaultMod.ORB_DECK);
        this.startingOrbDeckCopies = 1;
        this.misc = 0;
		this.originalName = this.name;
		this.exhaust = true;
		this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	boolean foundFrost = false;
    	int noOfFrost = 0;
    	for (AbstractOrb o : player().orbs)
    	{
    		if (o instanceof Frost)
    		{
    			foundFrost = true;   
    			noOfFrost++;
    		}
    	}
    	if (!this.upgraded)
    	{
	    	if (foundFrost)
	    	{
	    		applyPowerToSelf(new FocusPower(p, 1));
	    	}
    	}
    	else
    	{
    		if (foundFrost)
	    	{
	    		applyPowerToSelf(new FocusPower(p, noOfFrost));
	    	}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new IcyCrevasse();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
		
	}


	@Override
	public void onResummon(int summons) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		 
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) 
	{
		if (DefaultMod.debug) { System.out.println("theDuelist:Invigoration:optionSelected() ---> can I see the card we selected? the card is: " + natureMonsters.get(arg2).originalName); }
	}
}