package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.NimbleDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class BladeArmorNinja extends NimbleDuelistCard {

    public static final String ID = DuelistMod.makeID("BladeArmorNinja");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BladeArmorNinja.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public BladeArmorNinja() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 6;
        this.tributes = this.baseTributes = 2;
        this.baseMagicNumber = this.magicNumber = 2; // vuln all enemies on Nimble
        this.isMultiDamage = true;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
        this.tags.add(Tags.CARDINAL);
        this.tags.add(Tags.NINJA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        ArrayList<DuelistCard> tributed = tribute();
        long ninjas = tributed.stream().filter(c -> c.hasTag(Tags.NINJA)).count();
        ninjas++;

        AnyDuelist duelist = AnyDuelist.from(this);
        for (int i = 0; i < ninjas; i++) {
            if (duelist.player()) {
                this.addToBot(new DamageAllEnemiesAction(owner, this.multiDamage, DamageInfo.DamageType.NORMAL, this.baseAFX));
            } else if (duelist.getEnemy() != null && targets != null && !targets.isEmpty()) {
                attack(targets.get(0));
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onNimbleTriggered(AnyDuelist duelist, List<AbstractCreature> targets) {
        vulnAllEnemies(duelist, this.magicNumber);
        if (this.upgraded) {
            vulnAllEnemies(duelist, this.magicNumber);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BladeArmorNinja();
    }

}
