package defaultmod.cards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import defaultmod.DefaultMod;
import defaultmod.interfaces.RandomEffectsHelper;
import defaultmod.patches.*;

public class BlastJuggler extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = defaultmod.DefaultMod.makeID("BlastJuggler");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DefaultMod.makePath(DefaultMod.BLAST_JUGGLER);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
	private static final int COST = 1;
	private static final int SUMMONS = 1;
	private static int MIN_TURNS_ROLL = 3;
	private static int MAX_TURNS_ROLL = 7;
	private static int DEBUFFS = 3;
	private ArrayList<AbstractCard> tooltips;
	// /STAT DECLARATION/

	public BlastJuggler() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(DefaultMod.MONSTER);
		this.tags.add(DefaultMod.METAL_RAIDERS);
		this.tags.add(DefaultMod.GOOD_TRIB);
		this.tags.add(DefaultMod.REPLAYSPIRE);
		this.originalName = this.name;
		this.summons = SUMMONS;
		this.isSummon = true;
		tooltips = new ArrayList<>();
		tooltips.add(new ExplosiveToken());
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, SUMMONS, this);
		summon(p, 1, new ExplosiveToken("Exploding Token"));
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

		// For each debuff to apply, apply a random debuff with a new random turn number
		for (int i = 0; i < DEBUFFS; i++)
		{
			AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
			applyPower(RandomEffectsHelper.getRandomDebuff(AbstractDungeon.player, targetMonster, randomTurnNum), targetMonster);
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


	@Override
	public String getID() {
		return ID;
	}
}