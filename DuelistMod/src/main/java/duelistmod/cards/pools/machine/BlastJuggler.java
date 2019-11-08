package duelistmod.cards.pools.machine;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.ExplosiveToken;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class BlastJuggler extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("BlastJuggler");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.BLAST_JUGGLER);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public BlastJuggler() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.METAL_RAIDERS);
		this.tags.add(Tags.GOOD_TRIB);
		this.tags.add(Tags.MACHINE);
		this.tags.add(Tags.DETONATE_DMG_SELF_DISABLED);
		this.tags.add(Tags.DETONATE_DMG_ENEMIES_ALLOWED);
		this.originalName = this.name;
		this.summons = this.baseSummons = 1;
		this.isSummon = true;
		this.baseDamage = this.damage = 5;
		this.baseMagicNumber = this.magicNumber = 3;
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		detonationTribute(this.magicNumber);
		summon();
		attack(m);		
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() 
	{
		return new BlastJuggler();
	}
	
    // Checking for Monster Zones if the challenge is enabled
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }

    	if (Util.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeLevel20)
    	{
    		if ((DuelistMod.getChallengeDiffIndex() < 3) && this.misc == 52) { return true; }
    		if (p.hasPower(SummonPower.POWER_ID))
    		{
    			int sums = DuelistCard.getSummons(p); int max = DuelistCard.getMaxSummons(p);
    			if (sums + this.summons <= max) 
    			{ 
    				return true; 
    			}
    			else 
    			{ 
    				if (sums < max) 
    				{ 
    					if (max - sums > 1) { this.cantUseMessage = "You only have " + (max - sums) + " monster zones"; }
    					else { this.cantUseMessage = "You only have " + (max - sums) + " monster zone"; }
    					
    				}
    				else { this.cantUseMessage = "No monster zones remaining"; }
    				return false; 
    			}
    		}
    		else
    		{
    			return true;
    		}
    	}
    	
    	else
    	{
    		return true;
    	}
    }

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (!this.upgraded) 
		{
			this.upgradeName();
			this.upgradeDamage(3);
			this.upgradeMagicNumber(2);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		machineSynTrib(tributingCard);
	}


	@Override
	public void onResummon(int summons)
	{

	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
		summon(p, 1, new ExplosiveToken());
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
		summon(p, 1, new ExplosiveToken());
	}
	
	/*
	@Override
	public void renderCardTip(SpriteBatch sb) 
	{
		super.renderCardTip(sb);
		boolean renderTip = (boolean) ReflectionHacks.getPrivate(this, AbstractCard.class, "renderTip");

		int count = 0;
		if (!Settings.hideCards && renderTip) {
			if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard) {
				return;
			}
			for (AbstractCard c : tooltips) {
				float dx = (AbstractCard.IMG_WIDTH * 0.9f - 5f) * drawScale;
				float dy = (AbstractCard.IMG_HEIGHT * 0.4f - 5f) * drawScale;
				if (current_x > Settings.WIDTH * 0.75f) {
					c.current_x = current_x + dx;
				} else {
					c.current_x = current_x - dx;
				}
				if (count == 0) {
					c.current_y = current_y + dy;
				} else {
					c.current_y = current_y - dy;
				}
				c.drawScale = drawScale * 0.8f;
				c.render(sb);
				count++;
			}
		}
	}
	*/

	@Override
	public String getID() {
		return ID;
	}


	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}