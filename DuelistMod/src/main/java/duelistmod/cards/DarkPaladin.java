package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class DarkPaladin extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("DarkPaladin");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkPaladin.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_DIAGONAL;
    private static final int COST = 2;
    private static final int DAMAGE = 24;
    // /STAT DECLARATION/

    public DarkPaladin() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPELLCASTER);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 2;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	attack(m, AFX, this.damage);
    	ArrayList<DuelistCard> orbs = new ArrayList<DuelistCard>();
    	ArrayList<String> orbNames = new ArrayList<String>();
    	ArrayList<AbstractCard> orbsToChooseFrom = DuelistCardLibrary.orbCardsForGeneration();
		for (int i = 0; i < 5; i++)
		{
			AbstractCard random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1));
			while (orbNames.contains(random.name)) { random = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1)); }
			orbs.add((DuelistCard) random.makeCopy());
			orbNames.add(random.name);
		}
    	AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(orbs, this.magicNumber, false, false, false, true));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkPaladin();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!this.upgraded) 
        {
            this.upgradeName();
            this.upgradeTributes(-1);
            if (DuelistMod.hasUpgradeBuffRelic) { this.upgradeMagicNumber(1); }
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
				if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } }
			}
		}
    	
  		
    	
    	// Check for # of summons >= tributes
    	else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = this.tribString;
    	return false;
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		spellcasterSynTrib(tributingCard);
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