package duelistmod.cards.pools.zombies;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ZombieMammoth extends DuelistCard {
    public static final String ID = DuelistMod.makeID("ZombieMammoth");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ZombieMammoth.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public ZombieMammoth() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 25;
        this.baseMagicNumber = this.magicNumber = 6;
        this.tributes = this.baseTributes = 1;
        this.misc = 0;      
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ZOMBIE);
    	this.tags.add(Tags.EXODIA_DECK);
        this.tags.add(Tags.BAD_MAGIC);
    	this.exodiaDeckCopies = 1;
    	this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        if (targets.size() > 0) {
            attack(targets.get(0), this.baseAFX, this.damage);
        }
        AnyDuelist duelist = AnyDuelist.from(this);
        ArrayList<UUID> exhausted = new ArrayList<>();
        ArrayList<AbstractCard> drawCards = new ArrayList<>(duelist.drawPile());
        if (drawCards.size() > this.magicNumber) {
            for (int i = 0; i < this.magicNumber; i++) {
                AbstractCard c = drawCards.get(AbstractDungeon.cardRandomRng.random(drawCards.size() - 1));
                while (exhausted.contains(c.uuid)) {
                    c = drawCards.get(AbstractDungeon.cardRandomRng.random(drawCards.size() - 1));
                }
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, duelist.drawPileGroup())); exhausted.add(c.uuid);
            }
        } else if (drawCards.size() > 0) {
            for (AbstractCard c : drawCards) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, duelist.drawPileGroup()));
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
        	if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
        	this.upgradeMagicNumber(-2);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }

	@Override
    public AbstractCard makeCopy() {
        return new ZombieMammoth();
    }
}
