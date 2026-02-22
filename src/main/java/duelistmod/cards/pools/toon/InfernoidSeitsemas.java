package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.RevengeDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.BurningDebuff;
import duelistmod.variables.Tags;

import java.util.List;

public class InfernoidSeitsemas extends RevengeDuelistCard {

    public static final String ID = DuelistMod.makeID("InfernoidSeitsemas");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("InfernoidSeitsemas.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public InfernoidSeitsemas() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseTributes = this.tributes = 4;
        this.baseBlock = this.block = 10;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
        this.misc = 0;
        this.originalName = this.name;
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return super.isRevengeActive(card);
    }

    @Override
    public void onRevengeTriggered(AnyDuelist duelist) {
        block();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.player() && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (monster != null && !monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead && monster.hasPower(BurningDebuff.POWER_ID)) {
                    int amount = monster.getPower(BurningDebuff.POWER_ID).amount;
                    this.addToBot(new ApplyPowerAction(monster, duelist.getPlayer(), new BurningDebuff(monster, duelist.getPlayer(), amount), amount));
                }
            }
        } else if (duelist.getEnemy() != null && AbstractDungeon.player.hasPower(BurningDebuff.POWER_ID)) {
            int amount = AbstractDungeon.player.getPower(BurningDebuff.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, duelist.getEnemy(), new BurningDebuff(AbstractDungeon.player, duelist.creature(), amount), amount));
        }
        postDuelistUseCard(owner, targets);
    }

    // Prevent enemy duelists from using this unless it helps them
    @Override
    public boolean cardSpecificCanUse(final AbstractCreature owner) {
        AnyDuelist duelist = AnyDuelist.from(owner);
        if (duelist.getEnemy() != null) {
            return AbstractDungeon.player.hasPower(BurningDebuff.POWER_ID);
        }
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new InfernoidSeitsemas();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.upgradeBlock(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
