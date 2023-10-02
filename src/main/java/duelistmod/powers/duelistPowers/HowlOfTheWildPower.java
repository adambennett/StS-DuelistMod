package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

import java.util.HashSet;

public class HowlOfTheWildPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("HowlOfTheWildPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("HowlOfTheWildPower.png");
    private int beastsDrawnThisTurn = 0;
    private final HashSet<String> uniqueBeastIdsDrawnThisTurn = new HashSet<>();
    private final HashSet<String> uniqueBeastNamesDrawnThisTurn = new HashSet<>();
    private final int beastsNeeded = 4;

	public HowlOfTheWildPower(AbstractCreature owner, AbstractCreature source, int beastsCheck) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = beastsCheck;
		updateDescription(); 
	}

    @Override
    public void atStartOfTurn() {
        this.beastsDrawnThisTurn = 0;
        this.uniqueBeastIdsDrawnThisTurn.clear();
        this.uniqueBeastNamesDrawnThisTurn.clear();
    }

    @Override
    public void onCardDrawn(AbstractCard drawn) {
        if (drawn.hasTag(Tags.BEAST) && !uniqueBeastIdsDrawnThisTurn.contains(drawn.cardID)) {
            beastsDrawnThisTurn++;
            uniqueBeastIdsDrawnThisTurn.add(drawn.cardID);
            uniqueBeastNamesDrawnThisTurn.add(drawn.name);
        }
        if (beastsDrawnThisTurn >= beastsNeeded) {
            beastsDrawnThisTurn = 0;
            AnyDuelist duelist = AnyDuelist.from(this);
            duelist.applyPowerToSelf(new StrengthPower(duelist.creature(), this.amount));
        }
        this.updateDescription();
    }

	@Override
	public void updateDescription() {
        int remaining = beastsNeeded - beastsDrawnThisTurn;
        if (remaining < 0) {
            remaining = 0;
        }
        StringBuilder beastsDraw = null;
        for (String name : this.uniqueBeastNamesDrawnThisTurn) {
            if (beastsDraw == null) {
                beastsDraw = new StringBuilder(name);
            } else {
                beastsDraw.append(", ").append(name);
            }
        }
        if (beastsDraw == null) {
            beastsDraw = new StringBuilder("None");
        } else if (beastsDraw.toString().trim().endsWith(",")) {
            beastsDraw = new StringBuilder(beastsDraw.substring(0, beastsDraw.length() - 1));
        }
		this.description = DESCRIPTIONS[0] + remaining + (remaining == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]) + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4] + beastsDraw;
	}
}
