package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.cards.other.tokens.BombCasing;
import duelistmod.cards.other.tokens.ExplosiveToken;
import duelistmod.cards.other.tokens.SuperExplodingToken;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;
import duelistmod.powers.EmperorPower;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ThereCanBeOnlyOnePower extends DuelistPower {

	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ThereCanBeOnlyOnePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("ThereCanBeOnlyOnePower.png");
	private final AnyDuelist duelist;
    private static final List<CardTags> validMonsterTypes = new ArrayList<>();

	public ThereCanBeOnlyOnePower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;
		this.duelist = AnyDuelist.from(this);
        validMonsterTypes.addAll(DuelistMod.monsterTypes);
        if (Util.getChallengeLevel() > 4) {
            validMonsterTypes.add(Tags.MEGATYPED);
            validMonsterTypes.add(Tags.ROSE);
            validMonsterTypes.add(Tags.OJAMA);
            validMonsterTypes.add(Tags.GIANT);
            validMonsterTypes.add(Tags.MAGNET);
        }
		updateDescription();
	}

    @Override
    public void atStartOfTurnPostDraw() {
        if (this.amount > 0 && this.duelist.hasPower(SummonPower.POWER_ID)) {
            SummonPower power = (SummonPower)this.duelist.getPower(SummonPower.POWER_ID);
            int uniqueTypesSummoned = power.getNumberOfUniqueMonsterTypesSummoned(true, true);
            int blockAmount = this.amount * uniqueTypesSummoned;
            if (blockAmount > 0) {
                this.duelist.block(blockAmount);
            }
        }
    }

    @Override
    public boolean modifyCanUse(final AbstractCreature p, final DuelistCard card) {
        return getMonsterTypePreventingSummon(card) == null;
    }

    @Override
    public String cannotUseMessage(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) {
        return "There Can Be Only One: " + getMonsterTypePreventingSummon(card);
    }

    // Returns null if no type is currently preventing the passed card from being summoned
    private String getMonsterTypePreventingSummon(DuelistCard card) {
        // No summons on card or no other cards summoned - always allow
        if (card.summons < 1 || !this.duelist.hasPower(SummonPower.POWER_ID) || this.duelist.getPower(SummonPower.POWER_ID).amount < 1) return null;

        // Card being played has no monster types - always allow
        boolean hasAnyTypes = false;
        for (CardTags type : validMonsterTypes) {
            if (card.hasTag(type)) {
                hasAnyTypes = true;
                break;
            }
        }
        if (!hasAnyTypes) return null;

        // At this point we begin checking if the card passes the type checks based on what will be summoned after it is played
        // Create a copy of the current summon list and modify it as if the card has been played
        // If after all tributes/detonations the card will summon a duplicate type, return that type name
        List<DuelistCard> cardsSummoned = new ArrayList<>();
        boolean mausoleumActive = duelist.hasPower(EmperorPower.POWER_ID) && !((EmperorPower)duelist.getPower(EmperorPower.POWER_ID)).flag;
        SummonPower summonPower = (SummonPower)this.duelist.getPower(SummonPower.POWER_ID);
        int tributes = mausoleumActive ? 0 : card.tributes;

        // Populate summons and remove tributes
        for (DuelistCard summoned : summonPower.getCardsSummoned()) {
            cardsSummoned.add(summoned);
        }
        for (int i = tributes; i > 0; i--) {
            if (cardsSummoned.size() > 0) {
                cardsSummoned.remove(cardsSummoned.get(cardsSummoned.size() - 1));
            }
        }

        // Handle Explosive Tokens and Detonations
        List<DuelistCard> explosiveTokens = cardsSummoned.stream().filter(c -> c instanceof ExplosiveToken ||  c instanceof SuperExplodingToken).collect(Collectors.toList());
        if (!explosiveTokens.isEmpty()) {
            if (!Util.isSpawningBombCasingOnDetonate() && card.xDetonate) {
                List<DuelistCard> filtered = cardsSummoned.stream().filter(c -> !(c instanceof ExplosiveToken || c instanceof SuperExplodingToken)).collect(Collectors.toList());
                cardsSummoned.clear();
                cardsSummoned.addAll(filtered);
            } else {
                List<DuelistCard> newList = new ArrayList<>();
                boolean removeAll = card.xDetonate || card.detonationCheckForSummonZones >= explosiveTokens.size();
                int removals = removeAll ? explosiveTokens.size() : card.detonationCheckForSummonZones;
                for (DuelistCard summoned : cardsSummoned) {
                    if (removals > 0 && (summoned instanceof ExplosiveToken || summoned instanceof SuperExplodingToken)) {
                        if (Util.isSpawningBombCasingOnDetonate()) {
                            newList.add(new BombCasing());
                        }
                        removals--;
                    } else {
                        newList.add(summoned);
                    }
                }
                cardsSummoned.clear();
                cardsSummoned.addAll(newList);
            }
        }

        // Card will not summon anything if max summons is reached somehow at this point
        if (cardsSummoned.size() >= summonPower.getMaxSummons()) return null;

        // Count types still summoned after all tribute/detonation effects resolve
        HashMap<CardTags, Integer> tagAmountsSummoned = new HashMap<>();
        for (DuelistCard c : cardsSummoned) {
            for (CardTags tag : c.uniqueTags()) {
                tagAmountsSummoned.compute(tag, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        // Final check to ensure no duplicate types will be summoned
        for (CardTags type : validMonsterTypes) {
            if (card.hasTag(type) && tagAmountsSummoned.getOrDefault(type, 0) > 0) {
                return DuelistMod.typeCardMap_NAME.get(type);
            }
        }
        return null;
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

}
