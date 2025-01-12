package duelistmod.abstracts;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import duelistmod.DuelistMod;
import duelistmod.actions.unique.PurgeSpecificCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.Treat;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public abstract class TreatCard extends DuelistCard implements Treat {

    private final String upgradeDescription;

    public TreatCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET, String upgradeDescription) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TOKEN);
        this.purgeOnUse = true;
        this.upgradeDescription = upgradeDescription;
        this.baseSecondMagic = this.secondMagic = 1;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo("Treat", "Considered to be a Token. Purges on use or at the end of turn."));
        return retVal;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        this.treat(false);
        postDuelistUseCard(owner, targets);
    }

    @Override
    public boolean canUpgrade() {
        return super.canUpgrade();
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        /*if (!this.upgraded && DuelistMod.persistentDuelistData.CardConfigurations.getTokensPurgeAtEndOfTurn()) {
            AnyDuelist duelist = AnyDuelist.from(this);
            AbstractDungeon.effectList.add(new ExhaustCardEffect(this));
            AbstractDungeon.actionManager.addToTop(new PurgeSpecificCard(this, duelist.handGroup()));
        }*/
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) {
            if (this.timesUpgraded > 0) {
                this.upgradeName(this.name + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(this.name + "+");
            }
            this.selfRetain = true;
            this.rawDescription = this.upgradeDescription;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
