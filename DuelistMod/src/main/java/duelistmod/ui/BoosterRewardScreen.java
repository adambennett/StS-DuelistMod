package duelistmod.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.mainMenu.HorizontalScrollBar;
import com.megacrit.cardcrawl.ui.buttons.*;

public class BoosterRewardScreen extends CardRewardScreen
{
	private BuyPackButton buyPackBtn;
    private static final float PAD_X;
    private static final float CARD_TARGET_Y;
    public ArrayList<AbstractCard> rewardGroup;
    public AbstractCard codexCard;
    public AbstractCard discoveryCard;
    public boolean hasTakenAll;
    public boolean cardOnly;
    public RewardItem rItem;
    private boolean codex;
    private boolean draft;
    private boolean discovery;
    private boolean chooseOne;
    private String header;
    private SkipCardButton skipButton;
    private SingingBowlButton bowlButton;
    private final int SKIP_BUTTON_IDX = 0;
    private final int BOWL_BUTTON_IDX = 1;
    private int draftCount;
    private static final int MAX_CARDS_ON_SCREEN = 4;
    private static final int MAX_CARDS_BEFORE_SCROLL = 5;
    private static final float START_X;
    private boolean grabbedScreen;
    private float grabStartX;
    private float scrollX;
    private float targetX;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private HorizontalScrollBar scrollBar;
    public ConfirmButton confirmButton;
    private AbstractCard touchCard;
    private boolean isVoting;
    private boolean mayVote;
    
	public BoosterRewardScreen(int goldCost) {
        super();
        this.buyPackBtn = new BuyPackButton(goldCost);
    }
	
	@Override
	public void update() {
		super.update();
		this.buyPackBtn.hide();
		this.buyPackBtn.update();
	}
	
	@Override
	public void open(final ArrayList<AbstractCard> cards, final RewardItem rItem, final String header) 
	{
		super.open(cards, rItem, header);
		this.buyPackBtn.show(rItem);
		this.rewardGroup = cards;
	}
	
	@Override
	public void discoveryOpen() {
		super.discoveryOpen();
		this.buyPackBtn.hide();
	}

	@Override
	public void chooseOneOpen(final ArrayList<AbstractCard> choices) {
		super.chooseOneOpen(choices);
		this.buyPackBtn.hide();
	}
	
	@Override
	public void discoveryOpen(final AbstractCard.CardType type) {
		super.discoveryOpen(type);
		this.buyPackBtn.hide();
	}
	
	@Override
	public void carveRealityOpen(final ArrayList<AbstractCard> cardsToPickBetween)
	{
		super.carveRealityOpen(cardsToPickBetween);
		this.buyPackBtn.hide();
	}
	
	@Override
	public void foreignInfluenceOpen(final boolean upgraded) {
		super.foreignInfluenceOpen(upgraded);
		this.buyPackBtn.hide();
	}
	
	@Override
	public void codexOpen() {
		super.codexOpen();
		this.buyPackBtn.hide();
	}
	
	@Override
	public void draftOpen() {
		super.draftOpen();
		this.buyPackBtn.hide();
	}
	
	@Override
	public void render(final SpriteBatch sb) {
		super.render(sb);
		this.buyPackBtn.render(sb);
	}

	static {
        PAD_X = 40.0f * Settings.scale;
        CARD_TARGET_Y = Settings.HEIGHT * 0.45f;
        START_X = Settings.WIDTH - 300.0f * Settings.scale;
    }
	
}
