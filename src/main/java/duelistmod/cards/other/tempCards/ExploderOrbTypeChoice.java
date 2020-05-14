package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.EvokeSpecificOrbAction;
import duelistmod.orbs.*;
import duelistmod.patches.AbstractCardEnum;

public class ExploderOrbTypeChoice extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ExploderAllOrbs");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AllOrbsCard.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private AbstractOrb orb;
    // /STAT DECLARATION/
    
    private static String getImg(String orbName)
    {
    	String extra = "OrbCard.png";
    	String alt_extra = "Card.png";
    	String newString = "";
    	boolean useAlt = false;
    	boolean useNewString = false;
    	if (orbName.equals("MonsterOrb")) { useAlt = true; }
    	if (orbName.equals("DragonOrb")) { useAlt = true; }
    	if (orbName.equals("Millennium Orb")) { useNewString = true; newString = "MillenniumOrbCard.png"; }
    	if (orbName.equals("Dark Millennium Orb")) { useNewString = true; newString = "DarkMillenniumOrbCard.png"; }
    	if (orbName.equals("Light Millennium Orb")) { useNewString = true; newString = "LightMillenniumOrbCard.png"; }
    	if (useNewString) { return DuelistMod.makeCardPath(newString); }
    	else if (useAlt) { return DuelistMod.makeCardPath(orbName + alt_extra); }
    	else { return DuelistMod.makeCardPath(orbName + extra);}
    }
    
    private static String getDesc(String orbName)
    {
    	String normal = DESCRIPTION + orbName + " orbs.";
    	String alt = DESCRIPTION + orbName + "s.";
    	String dOrb = DESCRIPTION + orbName + "s. NL (Includes DragonOrb+)";

    	boolean useAlt = false;
    	boolean useDOrb = false;
    	
    	if (orbName.equals("MonsterOrb")) { useAlt = true; }
    	if (orbName.equals("DragonOrb")) { useDOrb = true; }
    	if (orbName.equals("Millennium Orb")) { useAlt = true; }
    	if (orbName.equals("Dark Millennium Orb")) { useAlt = true;  }
    	if (orbName.equals("Light Millennium Orb")) { useAlt = true; }
    	
    	if (useDOrb) { return dOrb; }
    	else if (useAlt) { return alt; }
    	else { return normal; }
    }

    public ExploderOrbTypeChoice(AbstractOrb orb) 
    { 
    	super(ID, orb.name, getImg(orb.name), COST, getDesc(orb.name), TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.orb = orb;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	for (AbstractOrb o : p.orbs)
    	{
    		if (o.getClass().equals(this.orb.getClass()))
    		{
    			this.addToBot(new EvokeSpecificOrbAction(o, 1));
    		}
    		else if (o instanceof DragonPlusOrb && this.orb instanceof DragonOrb)
    		{
    			this.addToBot(new EvokeSpecificOrbAction(o, 1));
    		}
    	}
    }
    
    @Override public AbstractCard makeCopy() { return new ExploderOrbTypeChoice(this.orb); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		
	}
	
	@Override public void onResummon(int summons) 
	{ 
		
	}
	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() {}
	
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}