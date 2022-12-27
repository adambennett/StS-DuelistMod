package duelistmod.actions.common;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;

@SuppressWarnings("unused")
public class ShowCardAndAddToExhaustPileEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect
{

	private static final float EFFECT_DUR = 1.5F;
	private AbstractCard card;
	private static final float PADDING = 30.0F * Settings.scale;
	private boolean randomSpot = false;
	private boolean cardOffset = false;

	public ShowCardAndAddToExhaustPileEffect(AbstractCard srcCard, float x, float y, boolean randomSpot, boolean cardOffset, boolean toBottom)
	{
		this.card = cardCopy(srcCard);
		this.cardOffset = cardOffset;
		this.duration = 1.5F;
		this.randomSpot = randomSpot;

		if (cardOffset) {
			identifySpawnLocation(x, y);
		} else {
			this.card.target_x = x;
			this.card.target_y = y;
		}

		AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
		this.card.drawScale = 0.01F;
		this.card.targetDrawScale = 1.0F;
		com.megacrit.cardcrawl.core.CardCrawlGame.sound.play("CARD_EXHAUST");
		if (toBottom) {
			AbstractDungeon.player.exhaustPile.addToBottom(srcCard);
		}
		else if (randomSpot) {
			AbstractDungeon.player.exhaustPile.addToRandomSpot(srcCard);
		} else {
			AbstractDungeon.player.exhaustPile.addToTop(srcCard);
		}
	}

	public ShowCardAndAddToExhaustPileEffect(AbstractCard srcCard, float x, float y, boolean randomSpot, boolean cardOffset)
	{
		this(srcCard, x, y, randomSpot, cardOffset, false);
	}

	public ShowCardAndAddToExhaustPileEffect(AbstractCard srcCard, float x, float y, boolean randomSpot) {
		this(srcCard, x, y, randomSpot, false);
	}

	public ShowCardAndAddToExhaustPileEffect(AbstractCard srcCard, boolean randomSpot, boolean toBottom) {
		this.card = cardCopy(srcCard);
		this.duration = 1.5F;
		this.randomSpot = randomSpot;
		this.card.target_x = MathUtils.random(Settings.WIDTH * 0.1F, Settings.WIDTH * 0.9F);
		this.card.target_y = MathUtils.random(Settings.HEIGHT * 0.8F, Settings.HEIGHT * 0.2F);
		AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
		this.card.drawScale = 0.01F;
		this.card.targetDrawScale = 1.0F;
		if (toBottom) {
			AbstractDungeon.player.exhaustPile.addToBottom(srcCard);
		}
		else if (randomSpot) {
			AbstractDungeon.player.exhaustPile.addToRandomSpot(srcCard);
		} else {
			AbstractDungeon.player.exhaustPile.addToTop(srcCard);
		}
	}

	private void identifySpawnLocation(float x, float y)
	{
		int effectCount = 0;
		if (this.cardOffset) {
			effectCount = 1;
		}
		for (com.megacrit.cardcrawl.vfx.AbstractGameEffect e : AbstractDungeon.effectList) {
			if ((e instanceof ShowCardAndAddToExhaustPileEffect)) {
				effectCount++;
			}
		}

		this.card.current_x = x;
		this.card.current_y = y;
		this.card.target_y = (Settings.HEIGHT * 0.5F);

		switch (effectCount) {
		case 0: 
			this.card.target_x = (Settings.WIDTH * 0.5F);
			break;
		case 1: 
			this.card.target_x = (Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH);
			break;
		case 2: 
			this.card.target_x = (Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH);
			break;
		case 3: 
			this.card.target_x = (Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F);
			break;
		case 4: 
			this.card.target_x = (Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F);
			break;
		default: 
			this.card.target_x = MathUtils.random(Settings.WIDTH * 0.1F, Settings.WIDTH * 0.9F);
			this.card.target_y = MathUtils.random(Settings.HEIGHT * 0.2F, Settings.HEIGHT * 0.8F);
		}

	}

	public void update()
	{
		this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
		this.card.update();

		if (this.duration < 0.0F) {
			this.isDone = true;
			this.card.shrink();
			AbstractDungeon.getCurrRoom().souls.remove(this.card);
		}
	}

	public void render(SpriteBatch sb)
	{
		if (!this.isDone) {
			this.card.render(sb);
		}
	}
	
	private AbstractCard cardCopy(AbstractCard c)
	{
		AbstractCard retCard = c.makeStatEquivalentCopy();
		if (c.exhaust && !retCard.exhaust)
		{
			retCard.exhaust = true;
			retCard.rawDescription = retCard.rawDescription + DuelistMod.exhaustForCardText;
			if (c instanceof DuelistCard) {
				((DuelistCard)c).fixUpgradeDesc();
			}
			retCard.initializeDescription();
		}
		return retCard;
	}

	public void dispose() {}
}
