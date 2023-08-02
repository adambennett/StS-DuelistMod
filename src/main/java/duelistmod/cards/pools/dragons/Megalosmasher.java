package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.MegalosmasherDeathCheckActionEnemy;
import duelistmod.actions.common.MegalosmasherDeathCheckActionPlayer;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class Megalosmasher extends DuelistCard {

    public static final String ID = DuelistMod.makeID("Megalosmasher");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Megalosmasher.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public Megalosmasher() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage 				= 12;
        this.summons = this.baseSummons				= 2;
        this.specialCanUseLogic = true;
        this.useTributeCanUse   = false;
        this.useBothCanUse      = false;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
        this.tags.add(Tags.DINOSAUR);
        this.misc = 0;
        this.originalName = this.name;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        AnyDuelist duelist = AnyDuelist.from(this);
        summon();
        if (duelist.player()) {
            this.addToBot(new MegalosmasherDeathCheckActionPlayer(player(), this.multiDamage, this.damageTypeForTurn, this.baseAFX, this.upgraded));
        } else {
            this.addToBot(new MegalosmasherDeathCheckActionEnemy(duelist.creature(), this.damage, this.damageTypeForTurn, this.baseAFX, this.upgraded));
        }
    }

    @Override
    public int enemyHandScoreBonus(int currentScore) {
        long playerBuffs = AbstractDungeon.player.powers.stream().filter(p -> p.type == AbstractPower.PowerType.BUFF).count();
        return playerBuffs > 0 ? (int) (playerBuffs * 4): 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Megalosmasher();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }
}
