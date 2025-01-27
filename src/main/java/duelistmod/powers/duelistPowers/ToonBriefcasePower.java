package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.NoStackDuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;
import java.util.*;

public class ToonBriefcasePower extends NoStackDuelistPower {

	public AbstractCreature source;
	public static final String POWER_ID = duelistmod.DuelistMod.makeID("ToonBriefcasePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("ToonBriefPower.png");
	public ArrayList<AbstractCard> pieces = new ArrayList<>();
	private final HashSet<AbstractCard> toonsPlayedThisTurn = new HashSet<>();
	private final AnyDuelist duelist;

	public ToonBriefcasePower(final AbstractCreature owner, final AbstractCreature source) {
        super(owner, source);
        this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		this.duelist = AnyDuelist.from(this);
		this.updateDescription();
	}

	@Override
	public void atStartOfTurn() {
		this.toonsPlayedThisTurn.clear();
	}

	@Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) {
		if (this.toonsPlayedThisTurn.size() == 1 && card.hasTag(Tags.TOON)) {
			this.toonsPlayedThisTurn.add(card);
			HashMap<UUID, CardGroup> groups = new HashMap<>();
			HashMap<UUID, AbstractCard> cardByUUID = new HashMap<>();
			for (AbstractCard toon : this.toonsPlayedThisTurn) {
				if (this.duelist.hand().stream().anyMatch(c -> c.uuid.equals(toon.uuid))) {
					groups.put(toon.uuid, this.duelist.handGroup());
					cardByUUID.put(toon.uuid, toon);
					continue;
				}
				if (this.duelist.drawPile().stream().anyMatch(c -> c.uuid.equals(toon.uuid))) {
					groups.put(toon.uuid, this.duelist.drawPileGroup());
					cardByUUID.put(toon.uuid, toon);
					continue;
				}
				if (this.duelist.discardPile().stream().anyMatch(c -> c.uuid.equals(toon.uuid))) {
					groups.put(toon.uuid, this.duelist.discardPileGroup());
					cardByUUID.put(toon.uuid, toon);
					continue;
				}
				if (this.duelist.limbo().stream().anyMatch(c -> c.uuid.equals(toon.uuid))) {
					groups.put(toon.uuid, this.duelist.limboGroup());
					cardByUUID.put(toon.uuid, toon);
				}
			}
			for (Map.Entry<UUID, CardGroup> entry : groups.entrySet()) {
				if (cardByUUID.containsKey(entry.getKey())) {
					this.addToBot(new ExhaustSpecificCardAction(cardByUUID.get(entry.getKey()), entry.getValue(), true));
				}
			}
			if (this.duelist.hasPower(ArcanaPower.POWER_ID)) {
				int amount = this.duelist.getPower(ArcanaPower.POWER_ID).amount;
				this.duelist.applyPowerToSelf(new ArcanaPower(this.duelist.creature(), this.duelist.creature(), amount));
			}
			this.toonsPlayedThisTurn.clear();
		} else if (card.hasTag(Tags.TOON)) {
			this.toonsPlayedThisTurn.add(card);
		}
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}

}
