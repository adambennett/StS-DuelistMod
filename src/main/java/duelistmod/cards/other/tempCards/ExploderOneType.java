package duelistmod.cards.other.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.orbs.DragonPlusOrb;
import duelistmod.patches.AbstractCardEnum;

public class ExploderOneType extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ExploderOneType");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("QuestionOrbCard.png");
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
    // /STAT DECLARATION/

    public ExploderOneType() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> choices = new ArrayList<>();
    	ArrayList<String> uniqueOrbs = new ArrayList<String>();
    	ArrayList<AbstractOrb> toPass = new ArrayList<AbstractOrb>();
    	for (AbstractOrb o : p.orbs) { if (!uniqueOrbs.contains(o.name) && (!(o instanceof DragonPlusOrb))  && (!(o instanceof EmptyOrbSlot))) { uniqueOrbs.add(o.name); toPass.add(o); }}
    	for (AbstractOrb o : toPass) { choices.add(new ExploderOrbTypeChoice(o)); }
    	if (choices.size() > 0) { this.addToBot(new CardSelectScreenResummonAction(choices, 1)); }
    }
    @Override public AbstractCard makeCopy() { return new ExploderOneType(); }

    
    
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