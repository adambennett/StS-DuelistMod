package defaultmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class Invigoration extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("Invigoration");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.INVIGORATION);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    //private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    private static ArrayList<DuelistCard> natureMonsters = new ArrayList<DuelistCard>();
    // /STAT DECLARATION/

    public Invigoration() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.SPELL);
        this.tags.add(DefaultMod.ALL);
        this.misc = 0;
		this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (!this.upgraded)
    	{
	    	// Generate 2 random dragons and target them at the same target as the attack() above
	    	// If this card is upgraded, the two dragons get upgraded as well
	    	DuelistCard extraDragA = (DuelistCard) returnTrulyRandomFromEitherSet(DefaultMod.PLANT, DefaultMod.INSECT);    	
	    	String cardNameA = extraDragA.originalName;    	
	    	if (DefaultMod.debug) { System.out.println("theDuelist:Invigoration --- > Generated: " + cardNameA); }
	    	//if (!extraDragA.tags.contains(DefaultMod.TRIBUTE)) { extraDragA.misc = 52; }    	
	        extraDragA.freeToPlayOnce = true;       
	        extraDragA.applyPowers();      
	        extraDragA.purgeOnUse = true;
	        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(extraDragA, m));    	
	        //extraDragA.onResummon(1);
	        //extraDragA.checkResummon();
    	}
    	else
    	{
    		natureMonsters = new ArrayList<DuelistCard>();
    		for (int i = 0; i < 5; i++)
    		{
    			DuelistCard nature = (DuelistCard) returnTrulyRandomFromEitherSet(DefaultMod.PLANT, DefaultMod.INSECT);
    			while (natureMonsters.contains(nature))
    			{
    				nature = (DuelistCard) returnTrulyRandomFromEitherSet(DefaultMod.PLANT, DefaultMod.INSECT);
    			}
    			natureMonsters.add(nature);
    		}
    		
    		openRandomCardChoice(3, natureMonsters);
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Invigoration();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBaseCost(2);
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