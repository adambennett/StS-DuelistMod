package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.patches.*;
import duelistmod.powers.*;

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
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public ComicHand() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL); 
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.TOON);
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
    	SummonPower summonsInstance = (SummonPower) p.getPower(SummonPower.POWER_ID);
    	ArrayList<String> summonsList = summonsInstance.summonList;
    	ArrayList<String> newSummonList = new ArrayList<String>();
    	for (String s : summonsList)
    	{
    		if (DuelistMod.summonMap.get(s).hasTag(Tags.TOON))
    		{
    			tokens++;
    		}
    		else
    		{
    			newSummonList.add(s);
    		}
    	}
    	
    	tributeChecker(player(), tokens, this, false);
    	summonsInstance.summonList = newSummonList;
    	summonsInstance.amount -= tokens;
    	for (int i = 0; i < tokens; i++)
    	{
    		DuelistCard tempCard = (DuelistCard) returnTrulyRandomFromSet(Tags.MONSTER);
    		summon(player(), 1, tempCard);
    		AbstractMonster randomM = getRandomMonster();
    		attack(randomM, AFX, this.damage);
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
    
    // If player doesn't have enough summons, can't play card
  	@Override
  	public boolean canUse(AbstractPlayer p, AbstractMonster m)
  	{
  		// Check super canUse()
  		boolean canUse = super.canUse(p, m); 
  		if (!canUse) { return false; }
  		
  		// Pumpking & Princess
  		else if (this.misc == 52) { return true; }
  		
  		// Mausoleum check
    	else if (p.hasPower(EmperorPower.POWER_ID))
		{
			EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
			if (!empInstance.flag)
			{
				return true;
			}
			else
			{
				if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= 1) { return true; } }
			}
		}

  		// Check for # of summons >= tributes
  		else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= 1) { return true; } } }

  		// Player doesn't have something required at this point
  		this.cantUseMessage = "You need at least 1 Summon";
  		return false;
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