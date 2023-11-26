package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.*;
import duelistmod.helpers.ImmutableList;
import duelistmod.variables.Strings;

public class SplashCaptureAction extends AbstractGameAction
{
	private final AbstractPlayer p;
	private boolean upgrade;
	private final ImmutableList<DuelistCard> cards;
	private final boolean randomize;
	private boolean etherealCheck = false;
	private boolean exhaustCheck = false;
	private boolean costChangeCheck = false;
	private boolean summonCheck = false;
	private boolean tributeCheck = false;
	private final boolean sendExtraToDiscard;
	private final boolean canCancel;
	private final int cardCopies;
	
	public SplashCaptureAction(ImmutableList<DuelistCard> cardsToChooseFrom, int amount, int cardCopies)
	{
		setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.randomize = false;		
		this.sendExtraToDiscard = true;
		this.canCancel = false;
		this.cardCopies = cardCopies;
		checkFlags();
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard gridCard : cards)
			{
				if (this.upgrade) { gridCard.upgrade(); }
				if (this.randomize)
				{
					if (!gridCard.isEthereal && etherealCheck) 
					{
		                gridCard.isEthereal = true;
		                gridCard.rawDescription = Strings.etherealForCardText + gridCard.rawDescription;
		    		}
		    		
		    		if (!gridCard.exhaust && exhaustCheck) 
		    		{
		                gridCard.exhaust = true;
		                gridCard.rawDescription = gridCard.rawDescription + DuelistMod.exhaustForCardText;
		    		}
		    		
		    		if (costChangeCheck && gridCard.cost > -1)
		    		{
						int lowCostRoll = 1;
						int highCostRoll = 4;
						int randomNum = AbstractDungeon.cardRandomRng.random(lowCostRoll, highCostRoll);
						int gridCardCost = gridCard.cost;
						gridCard.setCostForTurn(-gridCard.cost + randomNum);
		    			if (randomNum != gridCardCost) { gridCard.isCostModifiedForTurn = true; }
		    		}       
		    		
		    		if (summonCheck && gridCard instanceof DuelistCard)
		    		{
						int lowSummonRoll = 0;
						int highSummonRoll = 0;
						int randomNum = AbstractDungeon.cardRandomRng.random(lowSummonRoll, highSummonRoll);
		    			DuelistCard dC = (DuelistCard)gridCard;
		    			if (dC.isSummonCard())
		    			{
							dC.modifySummonsForTurn(randomNum);
						}
		    		}
		    		
		    		if (tributeCheck && gridCard instanceof DuelistCard)
		    		{
						int lowTributeRoll = 0;
						int highTributeRoll = 0;
						int randomNum = AbstractDungeon.cardRandomRng.random(lowTributeRoll, highTributeRoll);
		    			DuelistCard dC = (DuelistCard)gridCard;
		    			if (dC.isTributeCard())
		    			{
							dC.modifyTributesForTurn(-randomNum);
						}
		    		}
					if (gridCard instanceof DuelistCard) {
						((DuelistCard)gridCard).fixUpgradeDesc();
					}
					gridCard.initializeDescription();
				}
				tmp.addToBottom(gridCard);
			}
	
			tmp.group.sort(GridSort.getComparator());
			if (this.amount >= tmp.group.size())
			{
				this.confirmLogic(tmp.group);
				this.p.hand.refreshHandLayout();
			}
			
			else
			{
				//if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
				String btmScreenTxt = Strings.configChooseString + this.amount + Strings.configAddCardHandString;
				if (this.amount != 1 ) { btmScreenTxt = Strings.configChooseString + this.amount + Strings.configAddCardHandPluralString; }
				DuelistMod.duelistCardSelectScreen.open(false, tmp, this.amount, btmScreenTxt, this::confirmLogic);
				AbstractDungeon.overlayMenu.cancelButton.show("Cancel");
			}
			tickDuration();
			return;
		}
		tickDuration();
	}

	private void confirmLogic(List<AbstractCard> selectedCards) {
		for (AbstractCard c : selectedCards)
		{
			if (!(c instanceof CancelCard))
			{
				if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE)
				{
					this.p.createHandIsFullDialog();
					if (sendExtraToDiscard)
					{
						for (int i = 0; i < this.cardCopies; i++) {
							this.p.discardPile.addToTop(c.makeStatEquivalentCopy());
						}
					}
				}
				else
				{
					for (int i = 0; i < this.cardCopies; i++) {
						AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy()));
					}
				}
				this.p.hand.refreshHandLayout();
				this.p.hand.applyPowers();
			}
		}
	}
	
    private void checkFlags()
    {
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoCostChanges()) { this.costChangeCheck = false; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoTributeChanges()) { this.tributeCheck = false; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoSummonChanges()) { this.summonCheck = false; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getAlwaysUpgrade()) { this.upgrade = true; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNeverUpgrade()) { this.upgrade = false; }
    	if (!DuelistMod.persistentDuelistData.RandomizedSettings.getAllowEthereal()) { this.etherealCheck = false; }
    	if (!DuelistMod.persistentDuelistData.RandomizedSettings.getAllowExhaust()) { this.exhaustCheck = false; }
    }
}
