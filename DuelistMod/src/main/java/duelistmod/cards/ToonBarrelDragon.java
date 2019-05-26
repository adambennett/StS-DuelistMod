package duelistmod.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.relics.DragonRelicB;

public class ToonBarrelDragon extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DuelistMod.makeID("ToonBarrelDragon");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.TOON_BARREL_DRAGON);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;
	private static final int DAMAGE = 0;
	private static int MIN_DMG = 25;
	private static int MAX_DMG = 40;
	private static int MIN_DMG_U = 30;
	private static int MAX_DMG_U = 45;
	// /STAT DECLARATION/

	public ToonBarrelDragon() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		this.isMultiDamage = true;
		this.multiDamage = new int[]{0, 0, 0, 0, 0};
		this.magicNumber = this.baseMagicNumber = 25;
		this.toon = true;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.TOON);
		this.tags.add(Tags.DRAGON);
		this.tags.add(Tags.MACHINE);
		this.tags.add(Tags.GOOD_TRIB);
		this.tags.add(Tags.FULL);
		this.misc = 0;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 3;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		int[] damageArray = new int[AbstractDungeon.getMonsters().monsters.size()];
		for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++)
		{
			int randomNum = AbstractDungeon.cardRandomRng.random(MIN_DMG, MAX_DMG );
			int randomNumU = AbstractDungeon.cardRandomRng.random(MIN_DMG_U, MAX_DMG_U);
			if (upgraded) { damageArray[i] = randomNum; }
			else { damageArray[i] = randomNumU; }
		}
		
		if (player().hasPower(MountainPower.POWER_ID))
		{
			for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++)
			{
				damageArray[i] = (int)Math.floor(damageArray[i] * 1.5);
			}
		}
		
		this.multiDamage = damageArray;
		tribute(p, this.tributes, false, this);
		for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++)
		{
			AbstractMonster mon = AbstractDungeon.getMonsters().monsters.get(i);
			damageThroughBlock(mon, p, damageArray[i], AbstractGameAction.AttackEffect.FIRE);
		}
		
		
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new ToonBarrelDragon();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.upgradeBaseCost(1);
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
    	
    	// Check for Toon World
    	else if (!p.hasPower(ToonWorldPower.POWER_ID) && !p.hasPower(ToonKingdomPower.POWER_ID)) { this.cantUseMessage = DuelistMod.toonWorldString; return false; }
    	
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
		// Dragon
		if (tributingCard.hasTag(Tags.DRAGON) && !AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID)) 
		{ 
			if (!AbstractDungeon.player.hasPower(MountainPower.POWER_ID)) { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, DuelistMod.dragonStr)); }
			else { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, DuelistMod.dragonStr + 1)); }
		}
		
		if (tributingCard.hasTag(Tags.DRAGON) && AbstractDungeon.player.hasRelic(DragonRelicB.ID))
		{
			if (DuelistMod.dragonRelicBFlipper) { drawRare(1, CardRarity.RARE); }
			DuelistMod.dragonRelicBFlipper = !DuelistMod.dragonRelicBFlipper;
		}
		
		if (tributingCard.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID))
		{
			TwoAmountPower power = (TwoAmountPower)player().getPower(TyrantWingPower.POWER_ID);
			power.amount2++;
			power.updateDescription();
		}
		
		// Toon
		if (tributingCard.hasTag(Tags.TOON)) { damageAllEnemiesThornsFire(5); }
		
		// Machine
		if (tributingCard.hasTag(Tags.MACHINE))
		{
			applyPowerToSelf(new ArtifactPower(player(), DuelistMod.machineArt));
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