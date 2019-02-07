package defaultmod.cards;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;

public class Ojamagic extends CustomCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("Ojamagic");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.OJAMAGIC);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 0;
    private static final int MIN_CARDS = 1;
    private static final int MAX_CARDS = 10;
    private static final int MIN_CARDS_U = 5;
    private ArrayList<AbstractCard> tooltips;
    // /STAT DECLARATION/

    public Ojamagic() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

		tooltips = new ArrayList<>();
		tooltips.add(new RedMedicine());
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		int randomNumCards = 1;
		if (this.upgraded) { randomNumCards = ThreadLocalRandom.current().nextInt(MIN_CARDS_U, MAX_CARDS + 1); }
		else { randomNumCards = ThreadLocalRandom.current().nextInt(MIN_CARDS, MAX_CARDS + 1); }
		
		AbstractCard redMedicine = new RedMedicine();
		if (upgraded) 
		{
			redMedicine.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(redMedicine, randomNumCards, true, true));
		} 
		else 
		{
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(redMedicine, randomNumCards, true, true));
		}
	}

	@Override
	public AbstractCard makeCopy() { return new Ojamagic(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			for (AbstractCard c : tooltips) { c.upgrade(); }
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