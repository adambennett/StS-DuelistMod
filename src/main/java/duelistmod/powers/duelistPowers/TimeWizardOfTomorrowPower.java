package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.NoStackDuelistPower;
import duelistmod.dto.AnyDuelist;
import java.util.ArrayList;
import java.util.List;

public class TimeWizardOfTomorrowPower extends DuelistPower {

	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("TimeWizardOfTomorrowPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("TimeWizardOfTomorrowPower.png");
	private final AnyDuelist duelist;
    private int selfDamage;
    private int enemyDamage;

	public TimeWizardOfTomorrowPower(AbstractCreature owner, AbstractCreature source, int selfDamage, int enemyDamage) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.selfDamage = selfDamage;
        this.enemyDamage = this.amount = enemyDamage;
        if (this.selfDamage != this.enemyDamage) {
            this.amount2 = this.selfDamage;
        }
		this.duelist = AnyDuelist.from(this);
		updateDescription();
	}

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        List<AbstractCreature> targets = new ArrayList<>();
        int roll = AbstractDungeon.cardRandomRng.random(1, 100);
        boolean isSelfDamage = false;
        if (roll > 50) {
            isSelfDamage = true;
            targets.add(duelist.creature());
        } else {
            if (this.duelist.player()) {
                ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
                for (AbstractMonster g : monsters) {
                    if (!g.isDead && !g.isDying && !g.isDeadOrEscaped() && !g.halfDead) {
                        targets.add(g);
                    }
                }
            } else if (this.duelist.getEnemy() != null) {
                targets.add(AbstractDungeon.player);
            }
        }

        for (AbstractCreature target : targets) {
            DuelistCard.staticThornAttack(target, AbstractGameAction.AttackEffect.LIGHTNING, isSelfDamage ? this.selfDamage : this.enemyDamage);
        }
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.enemyDamage + DESCRIPTIONS[1] + this.selfDamage + DESCRIPTIONS[2];
	}

    public int getSelfDamage() {
        return selfDamage;
    }

    public void setSelfDamage(int selfDamage) {
        this.selfDamage = selfDamage;
    }

    public int getEnemyDamage() {
        return enemyDamage;
    }

    public void setEnemyDamage(int enemyDamage) {
        this.enemyDamage = enemyDamage;
    }
}
