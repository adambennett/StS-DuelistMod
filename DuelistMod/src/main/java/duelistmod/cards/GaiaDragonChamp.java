package duelistmod.cards;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.relics.DragonRelicB;

public class GaiaDragonChamp extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GaiaDragonChamp");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GAIA_DRAGON_CHAMP);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 2;
    private static final int DAMAGE = 16;
    private static final int UPGRADE_PLUS_DMG = 4;
    // /STAT DECLARATION/

    public GaiaDragonChamp() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.GOOD_TRIB);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<DuelistCard> tributeList = tribute(p, this.tributes, false, this);
    	attack(m, AFX, this.damage);
    	if (!upgraded && tributeList.size() > 0)
    	{
    		DuelistCard summon = returnRandomFromArray(tributeList);
    		DuelistCard cardCopy = DuelistCard.newCopyOfMonster(summon.originalName);
			if (cardCopy != null && !cardCopy.hasTag(Tags.EXEMPT))
			{
				DuelistCard.fullResummon(cardCopy, this.upgraded, m, false);
			}
    	}
    	
    	else if (upgraded && tributeList.size() > 0)
    	{
    		DuelistCard summon = tributeList.get(0);
    		DuelistCard cardCopy = DuelistCard.newCopyOfMonster(summon.originalName);
			if (cardCopy != null && !cardCopy.hasTag(Tags.EXEMPT))
			{
				DuelistCard.fullResummon(cardCopy, summon.upgraded, m, false);
			}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new GaiaDragonChamp();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
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