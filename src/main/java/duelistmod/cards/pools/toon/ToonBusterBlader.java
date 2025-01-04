package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class ToonBusterBlader extends DynamicDamageCard {
    public static final String ID = DuelistMod.makeID("ToonBusterBlader");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ToonBusterBlader.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public ToonBusterBlader() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = this.originalDamage = 12;
        this.magicNumber = this.baseMagicNumber = 4;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
        this.tags.add(Tags.REQUIRES_TOON_WORLD);
        this.tags.add(Tags.TOON);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	attack(m, AttackEffect.SLASH_HORIZONTAL, this.damage);
		tribute(p, this.tributes, false, this);
    }

	@Override
	public int damageFunction() {
        AnyDuelist duelist = AnyDuelist.from(this);
        int total = 0;
		if (duelist.hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
			int dragons = pow.getNumberOfTypeSummonedForTributes(Tags.DRAGON, this.tributes);
			total = this.magicNumber * dragons;
		}
        for (DuelistCard c : duelist.getAllTributedCardsThisCombat()) {
            if (c.hasTag(Tags.DRAGON)) {
                total += this.magicNumber;
            }
        }
		return Math.max(0, total);
	}

    @Override
    public AbstractCard makeCopy() {
        return new ToonBusterBlader();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
