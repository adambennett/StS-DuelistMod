package duelistmod.cards.pools.insects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class CocoonEvolution extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CocoonEvolution");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.COCOON_EVOLUTION);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    private ArrayList<AbstractCard> tooltips;
    // /STAT DECLARATION/

    public CocoonEvolution() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 8;
        this.tributes = this.baseTributes = 2;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.COCOON);
        this.tags.add(Tags.INSECT);
        this.tags.add(Tags.METAL_RAIDERS);
        this.tags.add(Tags.OP_NATURE_DECK);
        this.startingOPNDeckCopies = 1;
        this.originalName = this.name;
        this.misc = 0;
        tooltips = new ArrayList<>();
        tooltips.add(new PetitRef());
		tooltips.add(new GreatRef());
		this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<DuelistCard> tribs = tribute(p, this.tributes, false, this);
    	block(this.block);
    	boolean foundPetit = false;
    	for (AbstractCard c : tribs)
    	{
    		if (c instanceof PetitMoth) { foundPetit = true; }
    	}
    	if (foundPetit && !p.hasPower(CocoonPower.POWER_ID)) { applyPowerToSelf(new CocoonPower(p, p, 3)); }
    	else if (foundPetit) 
    	{
    		CocoonPower power = (CocoonPower) player().getPower(CocoonPower.POWER_ID);
    		power.amount--;
			power.updateDescription();
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CocoonEvolution();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    

    
    @Override
   	public void renderCardTip(SpriteBatch sb) {
   		super.renderCardTip(sb);
   		boolean renderTip = (boolean) ReflectionHacks.getPrivate(this, AbstractCard.class, "renderTip");

   		int count = 0;
   		if (!Settings.hideCards && renderTip) {
   			if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard) {
   				return;
   			}
   			for (AbstractCard c : tooltips) {
   				float dx = (AbstractCard.IMG_WIDTH * 0.9f - 5f) * drawScale;
   				float dy = (AbstractCard.IMG_HEIGHT * 0.4f - 5f) * drawScale;
   				if (current_x > Settings.WIDTH * 0.75f) {
   					c.current_x = current_x + dx;
   				} else {
   					c.current_x = current_x - dx;
   				}
   				if (count == 0) {
   					c.current_y = current_y + dy;
   				} else {
   					c.current_y = current_y - dy;
   				}
   				c.drawScale = drawScale * 0.8f;
   				c.render(sb);
   				count++;
   			}
   		}
   	}














}
