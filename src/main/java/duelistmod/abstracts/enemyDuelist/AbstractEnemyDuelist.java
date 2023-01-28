package duelistmod.abstracts.enemyDuelist;

import basemod.animations.AbstractAnimation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import duelistmod.abstracts.DuelistStance;

import java.util.ArrayList;

public class AbstractEnemyDuelist extends AbstractMonster {

    public static AbstractEnemyDuelist enemyDuelist;
    public static boolean finishedSetup;
    public ArrayList<AbstractEnemyDuelistRelic> relics;
    public DuelistStance stance;
    public ArrayList<AbstractOrb> orbs;
    public ArrayList<AbstractCard> cardsPlayedThisCombat;
    public int maxOrbs;
    public int masterMaxOrbs;
    public int masterHandSize;
    public int gameHandSize;
    public int mantraGained;
    protected EnergyOrbInterface energyOrb;
    public CardGroup masterDeck;
    public CardGroup drawPile;
    public CardGroup hand;
    public CardGroup discardPile;
    public CardGroup exhaustPile;
    public CardGroup limbo;
    public CardGroup graveyard;
    public CardGroup cardPool;
    public AbstractCard cardInUse;
    public int damagedThisCombat;
    public int cardsPlayedThisTurn;
    public int attacksPlayedThisTurn;
    public boolean onSetupTurn;
    private static int attacksDrawnForAttackPhase;
    private static int setupsDrawnForSetupPhase;
    public String energyString;
    protected AbstractAnimation animation;

    public AbstractEnemyDuelist(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, int lowHP, int maxHP, int a7lowHP, int a7maxHP) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, "duelistModResources/images/char/enemies/" + imgUrl + ".png");
        this.mantraGained = 0;
        this.onSetupTurn = true;
        this.energyString = "[E]";
        enemyDuelist.finishedSetup = false;
        this.drawX = Settings.WIDTH * 0.75f - 150.0f * Settings.xScale;
        this.type = AbstractMonster.EnemyType.BOSS;
        //this.energyPanel = new EnemyEnergyPanel(this);
       // this.hand = new EnemyCardGroup(CardGroup.CardGroupType.HAND, this);
        //this.limbo = new EnemyCardGroup(CardGroup.CardGroupType.UNSPECIFIED, this);
        this.masterHandSize = 3;
        this.gameHandSize = 3;
        final int n = 0;
        this.maxOrbs = n;
        this.masterMaxOrbs = n;
        //this.stance = new EnNeutralStance();
        this.orbs = new ArrayList<>();
        this.relics = new ArrayList<>();
    }


    @Override
    public void takeTurn() {

    }

    @Override
    protected void getMove(int i) {

    }
}
