package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class DragonMaster extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("DragonMaster");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.DRAGON_MASTER);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public DragonMaster() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tributes = 3;
        this.baseDamage = this.damage = 20;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.DRAGON);
        this.tags.add(DefaultMod.GOOD_TRIB);
        this.tags.add(DefaultMod.FULL);
        this.misc = 0;
		this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute and attack
    	tribute(p, this.tributes, false, this);
    	attack(m, AFX, this.damage);
    	
    	// Generate 2 random dragons and target them at the same target as the attack() above
    	// If this card is upgraded, the two dragons get upgraded as well
    	DuelistCard extraDragA = (DuelistCard) returnTrulyRandomFromOnlyFirstSet(DefaultMod.DRAGON, DefaultMod.TOON);
    	while (extraDragA.hasTag(DefaultMod.EXEMPT) || extraDragA.originalName.equals("Gandora")) { extraDragA = (DuelistCard) returnTrulyRandomFromOnlyFirstSet(DefaultMod.DRAGON, DefaultMod.TOON); }
    	DuelistCard extraDragB = (DuelistCard) returnTrulyRandomFromOnlyFirstSet(DefaultMod.DRAGON, DefaultMod.TOON);
    	while (extraDragB.hasTag(DefaultMod.EXEMPT) || extraDragB.originalName.equals("Gandora")) { extraDragB = (DuelistCard) returnTrulyRandomFromOnlyFirstSet(DefaultMod.DRAGON, DefaultMod.TOON); }
    	String cardNameA = extraDragA.originalName;
    	String cardNameB = extraDragB.originalName;
    	if (DefaultMod.debug) { System.out.println("theDuelist:DragonMaster --- > Generated: " + cardNameA); System.out.println("theDuelist:DragonMaster --- > Generated: " + cardNameB); }
    	if (!extraDragA.tags.contains(DefaultMod.TRIBUTE)) { extraDragA.misc = 52; }
    	extraDragA.freeToPlayOnce = true;
    	extraDragA.applyPowers();
    	extraDragA.purgeOnUse = true;
    	if (this.upgraded) { extraDragA.upgrade(); }
    	AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(extraDragA, m));
    	extraDragA.onResummon(1);
    	extraDragA.checkResummon();
    	if (!extraDragB.tags.contains(DefaultMod.TRIBUTE)) { extraDragB.misc = 52; }       
        extraDragB.freeToPlayOnce = true;       
        extraDragB.applyPowers();     
        extraDragB.purgeOnUse = true;
        if (this.upgraded) { extraDragB.upgrade(); }  
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(extraDragB, m));     
        extraDragB.onResummon(1);
        extraDragB.checkResummon();
    	
    	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DragonMaster();
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
	public void onTribute(DuelistCard tributingCard) {
		if (tributingCard.hasTag(DefaultMod.DRAGON) && !AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID)) 
		{ 
			if (!AbstractDungeon.player.hasPower(MountainPower.POWER_ID)) { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 1)); }
			else { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 2)); }
		}
		
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