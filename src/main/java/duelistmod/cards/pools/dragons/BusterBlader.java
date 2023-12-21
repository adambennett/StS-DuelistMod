package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

import java.util.List;

public class BusterBlader extends DynamicDamageCard {
    public static final String ID = DuelistMod.makeID("BusterBlader");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.BUSTER_BLADER);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;

    public BusterBlader() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = this.originalDamage = 6;
        this.magicNumber = this.baseMagicNumber = 6;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.PHARAOH_SERVANT);
        this.tags.add(Tags.OP_DRAGON_DECK);
        this.tags.add(Tags.WARRIOR);
        this.startingOPDragDeckCopies = 1;
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 3;
        this.setupStartingCopies();
        this.enemyIntent = AbstractMonster.Intent.ATTACK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        if (targets.size() > 0) {
            AbstractCreature m = targets.get(0);
            attack(m, AttackEffect.BLUNT_LIGHT, this.damage);
        }
        tribute();
        postDuelistUseCard(owner, targets);
    }

	@Override
	public int damageFunction() {
        AnyDuelist duelist = AnyDuelist.from(this);
		if (duelist.hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
			int dragons = pow.getNumberOfTypeSummonedForTributes(Tags.DRAGON, this.tributes);
			return this.magicNumber * dragons;
		}
		return 0;
	}

    @Override
    public AbstractCard makeCopy() {
        return new BusterBlader();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (DuelistMod.hasUpgradeBuffRelic) {
                this.upgradeDamage(4);
            }
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
