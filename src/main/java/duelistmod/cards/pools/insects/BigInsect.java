package duelistmod.cards.pools.insects;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class BigInsect extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BigInsect");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BigInsect.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public BigInsect() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 10;
        this.baseMagicNumber = this.magicNumber = 8;
        this.summons = this.baseSummons = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.INSECT);
        this.originalName = this.name;
        this.isSummon = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (p.hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower pow = (SummonPower)p.getPower(SummonPower.POWER_ID);
    		boolean fullInsectStack = pow.typeSummonsMatchMax(Tags.INSECT);
    		if (fullInsectStack) { this.attack(m, AttackEffect.SLASH_HEAVY, this.baseDamage + this.magicNumber); }
    		else { attack(m); }
    	}
    	else { attack(m); }
    }
    
    @Override
    public void triggerOnGlowCheck() 
    {
    	super.triggerOnGlowCheck();
    	if (player().hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
    		boolean fullInsectStack = pow.typeSummonsMatchMax(Tags.INSECT);
    		if (fullInsectStack)
    		{
    			this.glowColor = Color.GOLD;
    		}
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BigInsect();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
	
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
