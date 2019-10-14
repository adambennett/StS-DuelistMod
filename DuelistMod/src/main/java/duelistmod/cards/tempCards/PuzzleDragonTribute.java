package duelistmod.cards.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class PuzzleDragonTribute extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("PuzzleDragonTribute");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PuzzleDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    // /STAT DECLARATION/

    public PuzzleDragonTribute() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<DuelistCard> candidates = new ArrayList<DuelistCard>();
    	ArrayList<DuelistCard> prefCandidates = new ArrayList<DuelistCard>();
    	for (AbstractCard c : p.drawPile.group)
    	{
    		if (c.hasTag(Tags.DRAGON) && c instanceof DuelistCard)
    		{
    			if (((DuelistCard)c).tributes > 0)
    			{
    				candidates.add((DuelistCard) c);
    			}
    			
    			if (((DuelistCard)c).tributes > 1)
    			{
    				prefCandidates.add((DuelistCard) c);
    			}
    		}
    	}
    	
    	if (prefCandidates.size() > 0)
    	{
    		DuelistCard rand = prefCandidates.get(AbstractDungeon.cardRandomRng.random(prefCandidates.size() - 1));
    		rand.modifyTributes(-this.magicNumber);
    	}
    	
    	else if (candidates.size() > 0)
    	{
    		DuelistCard rand = candidates.get(AbstractDungeon.cardRandomRng.random(candidates.size() - 1));
    		rand.modifyTributes(-this.magicNumber);
    	}
    }
    @Override public AbstractCard makeCopy() { return new PuzzleDragonTribute(); }

    
    
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