package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.powers.StrengthPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.LavaOrbEruptionResult;
import duelistmod.orbs.FireOrb;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.BurningDebuff;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.List;

public class DarkfireDragon extends DuelistCard {
    public static final String ID = DuelistMod.makeID("DarkfireDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.DARKFIRE_DRAGON);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public DarkfireDragon() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.MONSTER);
    	this.tags.add(Tags.DRAGON);
    	this.tags.add(Tags.ALL);
    	this.tags.add(Tags.LEGEND_BLUE_EYES);
    	this.tags.add(Tags.GOOD_TRIB);
    	this.misc = 0;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 3;
		this.magicNumber = this.baseMagicNumber = 10;
        this.secondMagic = this.baseSecondMagic = 1;
        this.enemyIntent = AbstractMonster.Intent.MAGIC;
    }

    @Override
    public LavaOrbEruptionResult lavaEvokeEffect() {
        applyPowerToSelf(new StrengthPower(AbstractDungeon.player, this.secondMagic));
        return new LavaOrbEruptionResult();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.channel(new FireOrb());
        if (duelist.player() && !targets.isEmpty()) {
            applyPower(new BurningDebuff(targets.get(0), duelist.getPlayer(), this.magicNumber), targets.get(0));
        } else if (duelist.getEnemy() != null) {
            duelist.applyPower(AbstractDungeon.player, duelist.creature(), new BurningDebuff(AbstractDungeon.player, duelist.creature(), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkfireDragon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
   
}
