package duelistmod.cards;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.orbs.Surge;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class BookSecret extends DuelistCard 
{

    public static final String ID = duelistmod.DuelistMod.makeID("BookSecret");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.BOOK_SECRET);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public BookSecret() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 2;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.GENERATION_DECK);
        this.tags.add(Tags.OP_SPELLCASTER_DECK);
        this.startingOPSPDeckCopies = 1;
		this.generationDeckCopies = 2;
		this.originalName = this.name;
		this.exhaust = true;
		this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        List<List<? extends AbstractCard>> allGroups = new ArrayList<>();
        allGroups.add(TheDuelist.cardPool.group);
        allGroups.add(DuelistMod.duelColorlessCards);
        allGroups.add(DuelistMod.myCards);
        ArrayList<AbstractCard> randomCards = CardFinderHelper.find(this.magicNumber, allGroups, (c) ->  c.hasTag(Tags.SPELLCASTER) && !c.hasTag(Tags.TOKEN) && !c.hasTag(Tags.NEVER_GENERATE));
		for (AbstractCard randomMonster : randomCards) {
            boolean isSummon = randomMonster instanceof DuelistCard && ((DuelistCard) randomMonster).isSummonCard();
			this.addToBot(new RandomizedHandAction(randomMonster, false, true, false, true, false, isSummon, false, false, 1, 3, 0, 0, 0, 1));
		}
		
		AbstractOrb surge = new Surge();
		channel(surge);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BookSecret();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (DuelistMod.hasUpgradeBuffRelic) {
                this.upgradeMagicNumber(2);
            }
            else {
                this.upgradeMagicNumber(1);
            }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }


	
















}
