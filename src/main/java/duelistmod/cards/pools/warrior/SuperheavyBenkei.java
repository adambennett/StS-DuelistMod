package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.GuardedDuelistCard;
import duelistmod.actions.unique.SuperheavyBenkeiAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.List;

public class SuperheavyBenkei extends GuardedDuelistCard {

    public static final String ID = DuelistMod.makeID("SuperheavyBenkei");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SUPERHEAVY_BENKEI);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public SuperheavyBenkei() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 10;
        this.tributes = this.baseTributes = 3;
        this.setBaseGuardedCheck(8);
        this.setGuardedCheck(8);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SUPERHEAVY);
        this.misc = 0;
        this.originalName = this.name;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        tribute();
        if (targets != null && !targets.isEmpty()) {
            this.addToBot(new SuperheavyBenkeiAction(
                    this,
                    isGuardedActive(this, this.getGuardedCheck()),
                    duelist,
                    targets.get(0),
                    new DamageInfo(duelist.creature(), this.damage, this.damageTypeForTurn))
            );
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onGuardedTriggered(AnyDuelist duelist, List<AbstractCreature> targets) {
        // no effect unless unblocked damage is dealt in use()
    }

    @Override
    public AbstractCard makeCopy() {
        return new SuperheavyBenkei();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
