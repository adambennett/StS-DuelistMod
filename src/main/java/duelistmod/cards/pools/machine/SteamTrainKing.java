package duelistmod.cards.pools.machine;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class SteamTrainKing extends DynamicDamageCard
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("SteamTrainKing");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.STEAM_TRAIN_KING);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public SteamTrainKing() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SUPERHEAVY);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.ALL);
        this.originalName = this.name;
        this.exhaust = true;
        this.isMultiDamage = true;
        this.tributes = this.baseTributes = 3;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, this.tributes, false, this);
    	if (this.damage > 0) {
    		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    	}

		ArrayList<AbstractCard> toDiscard = new ArrayList<>();
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
			if (c.hasTag(Tags.MONSTER)) {
				toDiscard.add(c);
			}
		}
		for (AbstractCard c : toDiscard) {
			AbstractDungeon.player.drawPile.moveToExhaustPile(c);
		}
    }

	@Override
	public int damageFunction() {
		int damageTotal = 0;
		if (AbstractDungeon.player != null && AbstractDungeon.player.drawPile != null && AbstractDungeon.player.drawPile.group != null) {
			for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
				if (c.hasTag(Tags.MONSTER)) {
					damageTotal += c.baseDamage;
				}
			}
		}
		return damageTotal;
	}

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SteamTrainKing();
    }



    // Upgraded stats.
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
