package duelistmod.cards.nameless.war;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.NamelessTombCard;
import duelistmod.cards.pools.beast.GravityBehemoth;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class GravityBehemothNamelessWar extends NamelessTombCard {
    public static final String ID = DuelistMod.makeID("Nameless:War:GravityBehemoth");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GravityBehemoth.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 1;

    public GravityBehemothNamelessWar() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 18;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.misc = 0;
        this.originalName = this.name;
        this.baseTributes = this.tributes = 3;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.player()) {
            this.addToBot(new DamageAllEnemiesAction(duelist.getPlayer(), this.multiDamage, this.damageTypeForTurn, this.baseAFX));
            int roll = AbstractDungeon.cardRandomRng.random(1, 100);
            if (roll <= 35 || (this.upgraded && roll <= 45)) {
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(monster, duelist.getPlayer()));
                }
            }
        } else if (targets.size() > 0) {
            AbstractCreature target = targets.get(0);
            attack(target, this.baseAFX, this.damage);
        }
    }

    @Override
    public DuelistCard getStandardVersion() {
        return new GravityBehemoth();
    }

    @Override
    public AbstractCard makeCopy() {
        return new GravityBehemothNamelessWar();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            // upgrades stun chance
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
