package duelistmod.powers.incomplete;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.actions.unique.HauntedReductionAction;
import duelistmod.helpers.HauntedHelper;
import duelistmod.variables.Tags;

public class HauntedPower extends DuelistPower {

	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("HauntedPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("HauntedPower.png");
	public final CardTags hauntedCardType;
	public final CardType hauntedCardBaseType;
	private String lastAction = "None.";
	
	public HauntedPower(final AbstractCreature owner, final AbstractCreature source, int amount, CardTags hauntedCardType) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		this.hauntedCardBaseType = null;
		this.hauntedCardType = hauntedCardType;
		updateDescription();
	}
	
	public HauntedPower(final AbstractCreature owner, final AbstractCreature source, int amount, CardType hauntedCardType) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		this.hauntedCardBaseType = hauntedCardType;
		this.hauntedCardType = null;
		updateDescription();
	}
	
	public HauntedPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
        int randRoll = AbstractDungeon.cardRandomRng.random(1, 6);
		if (randRoll == 1) {
			this.hauntedCardType = Tags.MONSTER;
			this.hauntedCardBaseType = null;
		} else if (randRoll == 2) {
			this.hauntedCardType = Tags.SPELL;
			this.hauntedCardBaseType = null;
		} else if (randRoll == 3) {
			this.hauntedCardType = Tags.TRAP;
			this.hauntedCardBaseType = null;
		} else if (randRoll == 4) {
			this.hauntedCardType = null;
			this.hauntedCardBaseType = CardType.ATTACK;
		} else if (randRoll == 5) {
			this.hauntedCardType = null;
			this.hauntedCardBaseType = CardType.SKILL;
		} else {
			this.hauntedCardType = null;
			this.hauntedCardBaseType = CardType.POWER;
		}
		updateDescription();
	}
	
	// Call this with the Haunted card that triggers the negative effect(s)
	// Prevents that card from having the summons/tributes modified after you've already played it
	public void triggerHaunt(AbstractCard triggerCard) {
		if (this.amount > 0) {
			AbstractPlayer p = AbstractDungeon.player;
			AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new GhostIgniteEffect(p.hb.cX, p.hb.cY), 1.0F));
			lastAction = HauntedHelper.triggerRandomAction(this.amount, triggerCard, false);
			updateDescription();
		} else {
			DuelistCard.removePower(this, this.owner);
		}
	}
	
	@Override
	public void updateDescription() {
		if (this.amount < 1) {
			DuelistCard.removePower(this, this.owner);
			return;
		}

		// Card Type selected is either: Attack, Skill, or Power
		if (this.hauntedCardBaseType != null) {
			boolean useAn = this.hauntedCardBaseType == CardType.ATTACK;
            String cardTypeString = this.hauntedCardBaseType.toString().toLowerCase();
            cardTypeString = cardTypeString.substring(0, 1).toUpperCase() + cardTypeString.substring(1);
            if (useAn) {
                if (this.amount > 1) {
					this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[2] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[5] + lastAction;
				} else {
					this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[2] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4] + lastAction;
				}
			} else {
                if (this.amount > 1) {
					this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[1] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[5] + lastAction;
				} else {
					this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[1] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4] + lastAction;
				}
			}
		}

		// Card Type selected is either Monster, Spell, Trap, or Toon
		else if (this.hauntedCardType != null) {
			String cardTypeString = this.hauntedCardType.toString().toLowerCase();
			cardTypeString = cardTypeString.substring(0, 1).toUpperCase() + cardTypeString.substring(1);
			if (this.amount > 1) {
				this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[1] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[5] + lastAction;
			} else {
				this.description = "#y" + cardTypeString + DESCRIPTIONS[0] + DESCRIPTIONS[1] + cardTypeString + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4] + lastAction;
			}
		}
	}
	
	@Override
    public void onInitialApplication() {
		if (AbstractDungeon.player.hasPower(HauntedDebuff.POWER_ID)) {
			DuelistCard.removePower(AbstractDungeon.player.getPower(HauntedDebuff.POWER_ID), AbstractDungeon.player);
		}
		triggerHauntReduction();
    }
    
    @Override
    public void onRemove() {
		triggerHauntReduction();
    }
    
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
		triggerHauntReduction();
    }
    
    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
		triggerHauntReduction();
    }
    
    @Override
    public void onDrawOrDiscard() {
		triggerHauntReduction();
    }
  
    @Override
    public void atStartOfTurnPostDraw() {
    	flash();
    	triggerHauntReduction();
    }

	public void triggerHauntReduction() {
		if (this.hauntedCardBaseType != null) {
			this.addToTop(new HauntedReductionAction(1, this.hauntedCardBaseType));
		} else if (this.hauntedCardType != null) {
			this.addToTop(new HauntedReductionAction(1, this.hauntedCardType));
		}
	}
    
}
