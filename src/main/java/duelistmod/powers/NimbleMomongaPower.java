package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.NoStackDuelistPower;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON;

public class NimbleMomongaPower extends NoStackDuelistPower {

    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("NimbleMomongaPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.PLACEHOLDER_POWER);
    private final AnyDuelist duelist;

    public NimbleMomongaPower(final AbstractCreature owner, final AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.duelist = AnyDuelist.from(this);
        this.updateDescription();
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
    	if (c.cost == 0 || c.costForTurn == 0){
            List<List<? extends AbstractCard>> cardGroupsToSearch = new ArrayList<>();
            cardGroupsToSearch.add(TheDuelist.cardPool.group);
            cardGroupsToSearch.add(DuelistMod.duelColorlessCards);
            cardGroupsToSearch.add(DuelistMod.myCards);
            ArrayList<AbstractCard> newList = CardFinderHelper.find(1, cardGroupsToSearch, (card) ->
                    !card.hasTag(Tags.NEVER_GENERATE) && card.rarity == COMMON
            );
            if (!newList.isEmpty()) {
                duelist.addCardsToHand(newList);
            }
    	}
    }

    @Override
	public void updateDescription() {
    	this.description = DESCRIPTIONS[0];
    }
}
