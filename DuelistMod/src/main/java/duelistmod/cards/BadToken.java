package duelistmod.cards;

import java.util.*;
import java.util.Map.Entry;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;

public class BadToken extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BadToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GREEDPOT_AVATAR);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public BadToken() { super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); }
    public BadToken(String tokenName) { super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); }
    
    
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	Map<DuelistCard,Integer> cards = new HashMap<DuelistCard,Integer>();
    	Map<Integer,Integer> noTribs = new HashMap<Integer,Integer>();
    	ArrayList<ArrayList<DuelistCard>> tribLists = new ArrayList<ArrayList<DuelistCard>>();
    	for (DuelistCard c : DuelistMod.myCards)
    	{
    		if (c.baseTributes > 0 && c.hasTag(Tags.MONSTER))
    		{
    			cards.put(c, c.baseTributes);
    		}
    	}
    	
    	for (int i = 0; i <= 20; i++)
    	{
    		noTribs.put(i, 0);
    	}
    	
    	for (int i = 0; i <= 20; i++)
    	{
    		tribLists.add(new ArrayList<DuelistCard>());
	    	for (Entry<DuelistCard, Integer> a : cards.entrySet())
	    	{
	    		if (a.getValue() == i) { noTribs.put(i, noTribs.get(i) + 1); tribLists.get(i).add(a.getKey()); }
	    	}
    	}
    	
    	int counter = 0;
    	for (Entry<Integer,Integer> a : noTribs.entrySet())
    	{
    		if (tribLists.get(counter).size() > 0)
    		{
    			String tribsString = "";
    			for (DuelistCard c : tribLists.get(counter))
    			{
    				tribsString += c.originalName + ", ";
    			}
    			System.out.println("Tributes: " + a.getKey() + " (#" + a.getValue() + ") :: List: " + tribsString);
    		}
    		else { System.out.println("Tributes: " + a.getKey() + " (#" + a.getValue() + ")"); }
    		counter++;
    	}
    	
    }
   

    @Override public AbstractCard makeCopy() { return new BadToken(); }
	@Override public void onTribute(DuelistCard tributingCard) {}
	@Override public void onResummon(int summons) { }
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