package duelistmod.abstracts;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.actions.common.FirstStrikeAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.interfaces.FirstStrikeCard;
import duelistmod.interfaces.RevengeCard;

import java.util.List;

public abstract class FirstStrikeDuelistCard extends DuelistCard implements FirstStrikeCard {

    public FirstStrikeDuelistCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    public abstract void onFirstStrikeTriggered(AnyDuelist duelist, AbstractCreature target);

    public final void triggerFirstStrike(FirstStrikeDuelistCard caller, AnyDuelist duelist, AbstractCreature target) {
        onFirstStrikeTriggered(duelist, target);
        FirstStrikeCard.super.triggerFirstStrike(caller, duelist, target);
    }

    public void firstStrikeSingleTarget(List<AbstractCreature> targets) {
        if (targets != null && !targets.isEmpty()) {
            AbstractCreature target = targets.get(0);
            int preHp = target.currentHealth;
            boolean full = target.currentHealth == target.maxHealth;
            attack(target, this.baseAFX, this.damage);
            AnyDuelist duelist = AnyDuelist.from(this);
            this.addToBot(new FirstStrikeAction(duelist, target, preHp, full, this));
        }
    }

    public void firstStrikeAllEnemies() {
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.player()) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (mo == null || mo.isDeadOrEscaped()) continue;
                int preHp = mo.currentHealth;
                boolean full = mo.currentHealth == mo.maxHealth;
                DamageInfo info = new DamageInfo(duelist.creature(), this.damage, this.damageTypeForTurn);
                this.addToBot(new DamageAction(mo, info, this.baseAFX));
                this.addToBot(new FirstStrikeAction(duelist, mo, preHp, full, this));
            }
        } else if (duelist.getEnemy() != null) {
            AbstractPlayer target = AbstractDungeon.player;
            int preHp = target.currentHealth;
            boolean full = target.currentHealth == target.maxHealth;
            DamageInfo info = new DamageInfo(duelist.creature(), this.damage, this.damageTypeForTurn);
            this.addToBot(new DamageAction(target, info, this.baseAFX));
            this.addToBot(new FirstStrikeAction(duelist, target, preHp, full, this));
        }
    }
}
