package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class JunkGiant extends DuelistCard {

    public static final String ID = DuelistMod.makeID("JunkGiant");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("JunkGiant.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public JunkGiant() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 75;
        this.tributes = this.baseTributes = 19;
        this.baseMagicNumber = this.magicNumber = 1;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.EXEMPT);
        this.enemyIntent = AbstractMonster.Intent.ATTACK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        if (targets.size() > 0) {
            attack(targets.get(0));
        }
        this.resetGiantTributes();
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        AnyDuelist duelist = AnyDuelist.from(this);
        if (duelist.hasPower(ArtifactPower.POWER_ID) && duelist.getPower(ArtifactPower.POWER_ID).amount > 0) {
            this.modifyGiantTributes(-(this.magicNumber * duelist.getPower(ArtifactPower.POWER_ID).amount));
        }
    }

    @Override
    public void upgrade() {
    	if (!upgraded) {
    		if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
    		else { this.upgradeName(NAME + "+"); }
    		this.upgradeTributes(-2);
    		this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
    		this.initializeDescription();
    	}
    }

	@Override
    public AbstractCard makeCopy() {
        return new JunkGiant();
    }

}
