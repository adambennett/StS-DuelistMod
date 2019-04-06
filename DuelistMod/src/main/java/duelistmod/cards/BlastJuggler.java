package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import duelistmod.*;
import duelistmod.patches.*;
import duelistmod.powers.SummonPower;

public class BlastJuggler extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("BlastJuggler");
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
	//private ArrayList<AbstractCard> tooltips;
	// /STAT DECLARATION/

	public BlastJuggler() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.METAL_RAIDERS);
		this.tags.add(Tags.GOOD_TRIB);
		this.tags.add(Tags.MACHINE);
		this.originalName = this.name;
		this.summons = this.baseSummons = 1;
		this.isSummon = true;
		this.baseDamage = this.damage = 5;
		//tooltips = new ArrayList<>();
		//tooltips.add(new ExplosiveToken());
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, this.summons, this);
		attack(m, this.baseAFX, this.damage);
		int tokens = 0;
    	SummonPower summonsInstance = (SummonPower) p.getPower(SummonPower.POWER_ID);
    	ArrayList<String> summonsList = summonsInstance.summonList;
    	ArrayList<String> newSummonList = new ArrayList<String>();
    	for (String s : summonsList)
    	{
    		if ((s.equals("Explosive Token") || (s.equals("Exploding Token"))))
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
    		AbstractMonster randomM = getRandomMonster();
    		int roll = AbstractDungeon.cardRandomRng.random(1, 3);
    		attack(randomM, this.baseAFX, roll);
    	}
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() 
	{
		return new BlastJuggler();
	}

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (!this.upgraded) 
		{
			this.upgradeName();
			this.upgradeBaseCost(0);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		if (tributingCard.hasTag(Tags.MACHINE))
		{
			applyPowerToSelf(new ArtifactPower(player(), DuelistMod.machineArt));
		}
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
		summon(p, 1, new ExplosiveToken("Exploding Token"));
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
		summon(p, 1, new ExplosiveToken("Exploding Token"));
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