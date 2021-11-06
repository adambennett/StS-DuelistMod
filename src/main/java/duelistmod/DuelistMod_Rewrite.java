package duelistmod;

import basemod.*;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.screens.custom.*;
import duelistmod.interfaces.*;
import duelistmod.subscribers.*;
import org.apache.logging.log4j.*;

import java.util.*;

@SpireInitializer
public class DuelistMod_Rewrite
implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
EditCharactersSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber,
PostPowerApplySubscriber, OnPowersModifiedSubscriber, PostDeathSubscriber, OnCardUseSubscriber,
PostCreateStartingDeckSubscriber, RelicGetSubscriber, AddCustomModeModsSubscriber, PostDrawSubscriber,
PostDungeonInitializeSubscriber, OnPlayerLoseBlockSubscriber, PreMonsterTurnSubscriber, PostDungeonUpdateSubscriber,
StartActSubscriber, PostObtainCardSubscriber, PotionGetSubscriber, StartGameSubscriber,
PostUpdateSubscriber, RenderSubscriber, PostRenderSubscriber, PreRenderSubscriber, AddAudioSubscriber,
OnCreateDescriptionSubscriber, PreStartGameSubscriber
{

    public static final Logger logger = LogManager.getLogger("DuelistLogger");

    private static final CustomModes customModesSub;
    private static final CustomObjects customObjectsSub;
    private static final Dungeon dungeonSub;
    private static final GetPotion getPotionSub;
    private static final GetRelic getRelicSub;
    private static final OnCardUse onCardUseSub;
    private static final OnCreateDescription onCreateDescriptionSub;
    private static final OnLoseBlock onLoseBlockSub;
    private static final OnPowersModified onPowersModifiedSub;
    private static final PostBattle postBattleSub;
    private static final PostCreateDeck postCreateDeckSub;
    private static final PostDeath postDeathSub;
    private static final PostDraw postDrawSub;
    private static final PostObtainCard postObtainCardSub;
    private static final PostPowerApply postPowerApplySub;
    private static final PostUpdate postUpdateSub;
    private static final PreMonsterTurn preMonsterTurnSub;
    private static final PreStartGame preStartGame;
    private static final Renderers renderersSub;
    private static final StartAct startActSub;
    private static final StartBattle startBattleSub;
    private static final StartGame startGameSub;


    public DuelistMod_Rewrite() {
        BaseMod.subscribe(this);

    }

    public static void initialize() {

    }

    @Override
    public void receivePostInitialize() {

    }

    @Override
    public void receiveEditStrings() {
        customObjectsSub.receiveEditStrings();
    }

    @Override
    public void receiveEditCards() {
        customObjectsSub.receiveEditCards();
    }

    @Override
    public void receiveEditRelics() {
        customObjectsSub.receiveEditRelics();
    }

    @Override
    public void receiveEditCharacters() {
        customObjectsSub.receiveEditCharacters();
    }

    @Override
    public void receiveEditKeywords() {
        customObjectsSub.receiveEditKeywords();
    }

    @Override
    public void receiveAddAudio() {
        customObjectsSub.receiveAddAudio();
    }

    @Override
    public String receiveCreateCardDescription(String s, AbstractCard abstractCard) {
        return onCreateDescriptionSub.receiveCreateCardDescription(s, abstractCard);
    }

    @Override
    public void receivePostDungeonInitialize() {
        dungeonSub.receivePostDungeonInitialize();
    }

    @Override
    public void receivePostDungeonUpdate() {
        dungeonSub.receivePostDungeonUpdate();
    }

    @Override
    public void receivePostUpdate() {
        postUpdateSub.receivePostUpdate();
    }

    @Override
    public void receiveStartAct() {
        startActSub.receiveStartAct();
    }

    @Override
    public void receivePreStartGame() {
        preStartGame.receivePreStartGame();
    }

    @Override
    public void receiveStartGame() {
        startGameSub.receiveStartGame();
    }

    @Override
    public void receivePostCreateStartingDeck(AbstractPlayer.PlayerClass playerClass, CardGroup cardGroup) {
        postCreateDeckSub.receivePostCreateStartingDeck(playerClass, cardGroup);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        startBattleSub.receiveOnBattleStart(abstractRoom);
    }

    @Override
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        return preMonsterTurnSub.receivePreMonsterTurn(abstractMonster);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        postBattleSub.receivePostBattle(abstractRoom);
    }

    @Override
    public void receivePostDeath() {
        postDeathSub.receivePostDeath();
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        onCardUseSub.receiveCardUsed(abstractCard);
    }

    @Override
    public int receiveOnPlayerLoseBlock(int i) {
        return onLoseBlockSub.receiveOnPlayerLoseBlock(i);
    }

    @Override
    public void receivePowersModified() {
        onPowersModifiedSub.receivePowersModified();
    }

    @Override
    public void receivePostObtainCard(AbstractCard p) {
        postObtainCardSub.receivePostObtainCard(p);
    }

    @Override
    public void receiveRelicGet(AbstractRelic abstractRelic) {
        getRelicSub.receiveRelicGet(abstractRelic);
    }

    @Override
    public void receivePotionGet(AbstractPotion abstractPotion) {
        getPotionSub.receivePotionGet(abstractPotion);
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        postPowerApplySub.receivePostPowerApplySubscriber(abstractPower, abstractCreature, abstractCreature1);
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        postDrawSub.receivePostDraw(abstractCard);
    }

    @Override
    public void receiveCustomModeMods(List<CustomMod> list) {
        customModesSub.receiveCustomModeMods(list);
    }

    @Override
    public void receivePostRender(SpriteBatch spriteBatch) {
        renderersSub.receivePostRender(spriteBatch);
    }

    @Override
    public void receiveCameraRender(OrthographicCamera orthographicCamera) {
        renderersSub.receiveCameraRender(orthographicCamera);
    }

    @Override
    public void receiveRender(SpriteBatch spriteBatch) {
        renderersSub.receiveRender(spriteBatch);
    }

    static {
        customModesSub = new CustomModes();
        customObjectsSub = new CustomObjects();
        dungeonSub = new Dungeon();
        getPotionSub = new GetPotion();
        getRelicSub = new GetRelic();
        onCardUseSub = new OnCardUse();
        onCreateDescriptionSub = new OnCreateDescription();
        onLoseBlockSub = new OnLoseBlock();
        onPowersModifiedSub = new OnPowersModified();
        postBattleSub = new PostBattle();
        postCreateDeckSub = new PostCreateDeck();
        postDeathSub = new PostDeath();
        postDrawSub = new PostDraw();
        postObtainCardSub = new PostObtainCard();
        postPowerApplySub = new PostPowerApply();
        postUpdateSub = new PostUpdate();
        preMonsterTurnSub = new PreMonsterTurn();
        preStartGame = new PreStartGame();
        renderersSub = new Renderers();
        startActSub = new StartAct();
        startBattleSub = new StartBattle();
        startGameSub = new StartGame();
    }
}
