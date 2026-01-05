package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.variables.Strings;

public class AlphaMagnetPower extends DuelistPower {
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("AlphaMagnetPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.ALPHA_MAG_POWER);
    private boolean electrified = false;

    public AlphaMagnetPower(final AbstractCreature owner) {
        this(owner, owner);
    }

    public AlphaMagnetPower(final AbstractCreature owner, final AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = 0;
        this.updateDescription();
    }

    public void electrify(int amount) {
        //this.amount = amount;
        this.electrified = true;
        updateDescription();
    }

    @Override
    public void onDrawOrDiscard() {

    }

    @Override
    public void atStartOfTurn() {

    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        /*if (this.electrified && c.type.equals(CardType.ATTACK) && this.amount > 0) {
            DuelistCard.channel(new Lightning());
            this.amount--;
            updateDescription();
        }*/
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {

    }

    @Override
    public void updateDescription() {
        if (this.electrified) {
            this.name = NAME + " E";
        } else {
            this.name = NAME;
        }
        this.description = DESCRIPTIONS[0];
        /*if (this.amount < 1) {
            this.amount = 0;
            this.electrified = false;
        }
        if (this.electrified) {
            if (this.amount != 1) {
                this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            } else {
                this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
            }
        } else {
            this.description = DESCRIPTIONS[0];
        }*/
    }
}
