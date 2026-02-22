package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import duelistmod.DuelistMod;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class ImperionSuperconductiveBattlebot extends Berserkion {

    public static final String ID = DuelistMod.makeID("ImperionSuperconductiveBattlebot");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ImperionSuperconductiveBattlebot.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public ImperionSuperconductiveBattlebot() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.APEX);
        this.tags.add(Tags.ROCK);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.MAGNET);
        this.tags.add(Tags.NEVER_GENERATE);
        this.baseDamage = this.damage = 7;
        this.baseTributes = this.tributes = 3;
        this.setBaseGuardedCheck(15);
        this.setGuardedCheck(15);
        this.isMultiDamage = true;
        this.originalName = this.name;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ImperionSuperconductiveBattlebot();
    }

    @Override
    public void upgrade() {}

    @Override
    public boolean canUpgrade() {
        return false;
    }

}
