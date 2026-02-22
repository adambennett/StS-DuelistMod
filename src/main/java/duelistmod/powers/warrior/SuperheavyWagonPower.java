package duelistmod.powers.warrior;

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

public class SuperheavyWagonPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("SuperheavyWagonPower");
    public static final String IMG = DuelistMod.makePowerPath("ArtifactSanctumPower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;

    public SuperheavyWagonPower(final AbstractCreature owner, int skills) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = skills;
        this.duelist = AnyDuelist.from(owner);
        this.updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (this.amount > 0 && card.type == AbstractCard.CardType.SKILL) {
            AbstractCard c = card.makeStatEquivalentCopy();
            if (duelist.player()) {
                AbstractMonster mon = AbstractDungeon.getRandomMonster();
                if (mon != null && !mon.isDying && !mon.isDead) {
                    DuelistCard.resummon(c, mon, false, true);
                    this.amount--;
                }
            } else if (duelist.getEnemy() != null) {
                DuelistCard.anyDuelistResummon(c, duelist, AbstractDungeon.player, true);
                this.amount--;
            }
        }

        this.updateDescription();
        if (this.amount == 0) {
            this.duelist.removePowerFromSelf(this);
        }
    }

    @Override
    public void updateDescription() {
        String s = this.amount == 1 ? "" : DESCRIPTIONS[3];
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + s + DESCRIPTIONS[2];
    }
}
