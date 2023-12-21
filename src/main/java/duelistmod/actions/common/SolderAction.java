package duelistmod.actions.common;

import java.util.*;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.relics.*;
import duelistmod.variables.Tags;

public class SolderAction extends AbstractGameAction
{
	private final AbstractPlayer p;
	private final ArrayList<AbstractCard> cards;
	private boolean canCancel = false;
	private boolean fromSolderToken = false;
	private SolderToken solderToken;
	private final boolean canSolderAgain;
	private boolean fromRelic = false;
	private int iteration = 0;
	private int magicBonus = 0;
	private HashMap<UUID, AbstractCard> originalMap = new HashMap<>();

	public SolderAction(int magic)
	{
		this(AbstractDungeon.player.hand.group, magic, true);
	}

	public SolderAction(int magic, SolderToken relic) {
		this(magic);
		this.fromSolderToken = true;
		this.solderToken = relic;
	}
	
	public SolderAction(ArrayList<AbstractCard> cardsToChooseFrom, int magic, boolean canCancel)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;	
		this.amount = 1;
		if (p.hasRelic(Fluxrod.ID)) { this.amount++; p.getRelic(Fluxrod.ID).flash(); }
		this.cards = cardsToChooseFrom;
		this.canCancel = canCancel;
		this.magicBonus = magic;
		this.canSolderAgain = true;
	}
	
	public SolderAction(ArrayList<AbstractCard> cardsToChooseFrom, int magic, boolean canCancel, boolean fromRelic)
	{
		this(cardsToChooseFrom, magic, canCancel);
		this.fromRelic = fromRelic;
	}
	
	private SolderAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, int magic, boolean canCancel, int iteration, boolean fromRelic)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;	
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.iteration = iteration + 1;
		this.canCancel = canCancel;
		this.magicBonus = magic;
		this.canSolderAgain = false;
		this.fromRelic = fromRelic;
	}

	public void update()
	{
		if (AbstractDungeon.player.hasRelic(BrazeToken.ID) && !fromRelic)
		{
			AbstractDungeon.player.getRelic(BrazeToken.ID).flash();
			DuelistCard.draw(2);
			AbstractDungeon.actionManager.addToBottom(new SolderAction(this.cards, this.magicBonus, this.canCancel, true));
			this.isDone = true;
			return;
		}
		if (this.magicBonus == 0) { this.isDone = true; return; }
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : cards) 
			{ 
				boolean allowCard = false;
				if (DuelistMod.magicNumberCards.containsKey(c.cardID)) { allowCard = true; }
				if (!c.hasTag(Tags.ALLOYED) && (c.magicNumber > 0 || allowCard) && !c.type.equals(CardType.STATUS) && !c.type.equals(CardType.CURSE)) 
				{
					AbstractCard copy = c.makeStatEquivalentCopy();
					tmp.addToTop(copy);
					originalMap.put(copy.uuid, c);
				}
			}
			tmp.group.sort(GridSort.getComparator());
			//if (this.canCancel && tmp.group.size() > 0) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount == 1 && tmp.group.size() > 0) {SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Card to Solder " + this.magicBonus); }
			else if (tmp.group.size() > 0) { SelectScreenHelper.open(tmp, this.amount,  "Choose " + this.amount + " Cards to Solder " + this.magicBonus); }
			tickDuration();
			return;
		}
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)) {
			boolean soldered = false;
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				c.unhover();
				c.stopGlowing();
				if (!(c instanceof CancelCard))
				{
					soldered = true;
					AbstractCard original = originalMap.get(c.uuid);
					if (this.magicBonus != 0) { DuelistCard.handleOnSolderForAllAbstracts(); }
					if (original instanceof DuelistCard)
					{
						modify((DuelistCard) original, this.magicBonus);
					}
					else if (original != null)
					{
						original.baseMagicNumber += this.magicBonus;
						if (original.baseMagicNumber < 0) { original.baseMagicNumber = 0; }
						original.magicNumber = original.baseMagicNumber;
					}
					//AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(original.makeStatEquivalentCopy()));
				}
			}
			if (soldered) {
				this.addToBot(new VFXAction(new WhirlwindEffect(new Color(0.75f, 0.75f, 0.75f, 1.0f), true)));
				if (this.solderToken != null) {
					this.solderToken.onSolderRan();
					this.solderToken = null;
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
			this.p.hand.glowCheck();
			if (this.canSolderAgain && AbstractDungeon.player.hasPower(RoboticKnightPower.POWER_ID) && this.iteration == 0) {
				AbstractDungeon.actionManager.addToBottom(new SolderAction(this.cards, this.amount, this.magicBonus, this.canCancel, this.iteration, this.fromRelic));
				this.isDone = true;
				return;
			}
		}
		tickDuration();
	}
	
	private void modify(DuelistCard original, int magic)
	{
		if (magic == 0) { return; }
		if (AbstractDungeon.player.hasPower(FluxPower.POWER_ID)) { magic += AbstractDungeon.player.getPower(FluxPower.POWER_ID).amount; }
		if (original.hasTag(Tags.MAGIC_NUM_SCALE_BY_10)) { magic = magic * 10; }
		if (original.hasTag(Tags.BAD_MAGIC))
		{
			original.baseMagicNumber -= magic;
			if (original.baseMagicNumber < 0) { original.baseMagicNumber = 0; }
			original.magicNumber = original.baseMagicNumber;
			original.isMagicNumberModified = true;
		}
		else
		{
			original.baseMagicNumber += magic;
			if (original.baseMagicNumber < 0) { original.baseMagicNumber = 0; }
			original.magicNumber = original.baseMagicNumber;
			original.isMagicNumberModified = true;
		}
	}
}
