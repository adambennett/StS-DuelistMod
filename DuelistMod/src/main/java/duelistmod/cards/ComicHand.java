package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class ComicHand extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ComicHand");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.COMIC_HAND);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ComicHand() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL); 
        this.tags.add(Tags.ALL);
        //this.tags.add(Tags.TOON);
        this.misc = 0;
        this.originalName = this.name;
        this.damage = this.baseDamage = 12;
        this.upgradeDmg = 6;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int tokens = 0;
    	if (p.hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower summonsInstance = (SummonPower) p.getPower(SummonPower.POWER_ID);
	    	ArrayList<DuelistCard> aSummonsList = summonsInstance.actualCardSummonList;
	    	ArrayList<String> newSummonList = new ArrayList<String>();
	    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
	    	for (DuelistCard s : aSummonsList)
	    	{
	    		if (s.hasTag(Tags.TOON))
	    		{
	    			tokens++;
	    		}
	    		else
	    		{
	    			newSummonList.add(s.originalName);
	    			aNewSummonList.add(s);
	    		}
	    	}
	    	
	    	tributeChecker(player(), tokens, this, true);
	    	summonsInstance.summonList = newSummonList;
	    	summonsInstance.actualCardSummonList = aNewSummonList;
	    	summonsInstance.amount -= tokens;
	    	summonsInstance.updateStringColors();
	    	summonsInstance.updateDescription();
	    	for (int i = 0; i < tokens; i++)
	    	{
	    		DuelistCard tempCard = (DuelistCard) returnTrulyRandomFromSet(Tags.MONSTER);
	    		summon(player(), 1, tempCard);
	    		AbstractMonster randomM = getRandomMonster();
	    		attack(randomM, AFX, this.damage);
	    	}
    	}
    	

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ComicHand();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(this.upgradeDmg);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
   
}