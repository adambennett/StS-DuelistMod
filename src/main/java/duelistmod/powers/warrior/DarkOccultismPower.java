package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class DarkOccultismPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("DarkOccultismPower");
    private static final String IMG = DuelistMod.makePowerPath("StatueAnguishPatternPower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;
    private boolean activatedThisTurn = false;

    public DarkOccultismPower(final AbstractCreature owner, int blockGain) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = blockGain;
        this.duelist = AnyDuelist.from(owner);
        this.updateDescription();
    }

    @Override
    public void onPreUseCard(AbstractCard card) {
        if (!this.activatedThisTurn && card.hasTag(Tags.SPELL)) {
            this.activatedThisTurn = true;
            if (this.duelist.hasPower(VigorPower.POWER_ID)) {
                int amt = this.duelist.getPower(VigorPower.POWER_ID).amount;
                if (amt > 0) {
                    if (this.duelist.player()) {
                        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                            if (monster != null && !monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) {
                                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(monster, this.duelist.creature(), amt, AbstractGameAction.AttackEffect.NONE));
                            }
                        }
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, this.duelist.creature(), amt, AbstractGameAction.AttackEffect.NONE));
                    }
                }
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        this.activatedThisTurn = false;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
