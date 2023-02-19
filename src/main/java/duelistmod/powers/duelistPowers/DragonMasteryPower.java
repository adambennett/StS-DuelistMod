package duelistmod.powers.duelistPowers;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class DragonMasteryPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("DragonMasteryPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("DragonMasteryPower.png");
	private final AnyDuelist duelist;

	public DragonMasteryPower(AbstractCreature source, AbstractCreature owner, int turns) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
		this.duelist = AnyDuelist.from(this);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = turns;
		updateDescription(); 
	}
	
	@Override
	public void onPlayCard(AbstractCard c, AbstractMonster m) {
		if (AnyDuelist.from(c).creature().equals(this.duelist.creature()) && c.hasTag(Tags.DRAGON)) {
			ArrayList<DuelistCard> dragons = new ArrayList<>();
			for (AbstractCard card : this.duelist.drawPile())
			{
				if (card.hasTag(Tags.DRAGON) && card instanceof DuelistCard && !card.uuid.equals(c.uuid)) { dragons.add((DuelistCard) card); }
			}

			for (AbstractCard card : this.duelist.hand())
			{
				if (card.hasTag(Tags.DRAGON) && card instanceof DuelistCard && !card.uuid.equals(c.uuid)) { dragons.add((DuelistCard) card); }
			}

			for (AbstractCard card : this.duelist.discardPile())
			{
				if (card.hasTag(Tags.DRAGON) && card instanceof DuelistCard && !card.uuid.equals(c.uuid)) { dragons.add((DuelistCard) card); }
			}

			if (dragons.size() > 0) {
				if (this.amount > 0) { this.flashWithoutSound(); }
				for (int i = 0; i < this.amount; i++)
				{
					AbstractCard rand = dragons.get(AbstractDungeon.cardRandomRng.random(dragons.size() - 1));
					rand.modifyCostForCombat(-1);
				}
				this.duelist.handGroup().glowCheck();
			}
		}
	}
	
    @Override
    public void onEnemyUseCard(AbstractCard card) {
    	//onPlayCard(card, null);
    }

	@Override
	public void updateDescription() {
		if (this.amount < 0) { this.amount = 0; }
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
	}
}
