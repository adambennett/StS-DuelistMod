package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.GuardedDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.Arrays;
import java.util.List;

public class CubicWave extends GuardedDuelistCard {

    public static final String ID = DuelistMod.makeID("CubicWave");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CubicWave.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 3;

    public CubicWave() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.setBaseGuardedCheck(15);
        this.setGuardedCheck(15);
        this.isMultiDamage = true;
        this.exhaust = true;
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

        int extraBlock = 0;
        if (duelist.hasPower(DexterityPower.POWER_ID)) {
            int dex = duelist.getPower(DexterityPower.POWER_ID).amount;
            if (dex > 0) {
                duelist.block(dex);
                extraBlock += dex;
            }
        }

        if (duelist.hasPower(VigorPower.POWER_ID)) {
            int vigor = duelist.getPower(VigorPower.POWER_ID).amount;
            if (vigor > 0) {
                if (duelist.player()) {
                    this.multiDamage = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
                    Arrays.fill(this.multiDamage, vigor);
                    this.addToBot(new DamageAllEnemiesAction(duelist.creature(), this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                } else if (duelist.getEnemy() != null && targets != null && !targets.isEmpty()) {
                    attack(targets.get(0));
                }
            }
        }

        if (isGuardedActive(this, this.getGuardedCheck())) {
            triggerGuarded(duelist, targets);
        }

        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onGuardedTriggered(AnyDuelist duelist, List<AbstractCreature> targets) {
        int dmg = Math.max(0, duelist.creature().currentBlock);
        if (dmg > 0) {
            if (duelist.player()) {
                AbstractMonster rand = AbstractDungeon.getRandomMonster();
                if (rand != null) {
                    this.addToBot(new DamageAction(rand, new DamageInfo(duelist.creature(), dmg, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
            } else if (duelist.getEnemy() != null && targets != null && !targets.isEmpty()) {
                this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(duelist.creature(), dmg, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CubicWave();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeGuardedCheck(-5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
