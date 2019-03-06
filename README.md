# StS-DuelistMod
A Slay the Spire adaptation of Yu-Gi-Oh!

## REQUIREMENTS
- Basemod
- StSLib

## CROSSOVER CONTENT FOR
- Replay the Spire
- Conspire
- Googly Eyes

# Introduction
The Duelist mod adds a new playable character, Yugi Moto. Yugi has an expansive set of 200+ new cards, all adapted from real Yu-Gi-Oh! cards. This mod also adds 8 new relics and 3 new potions. None of the content added with this mod should affect the base game, and none of the relics or potions are added to the shared pool (i.e. they are only obtainable when playing as The Duelist).

This is a work in progress, and there will likely be some bugs and minor issues. I will do my best to address any unforeseen problems that arise, and of course I will be doing my own continued playtesting and debugging. Expect periodic updates. My contact information is listed at the end of this description if you would like to reach me directly with issues/feedback/bug reports/suggestions/hate mail. Thanks for playing!

# Gameplay Mechanics
## Summoning & Tributing
When you play lower cost/power monsters with the Summon keyword, you gain stacks of summons equal to the number found on the card, up to a certain maximum number. The default maximum is 5, but this number	can be increased via card effect. Having a lot of summons allows you to play more powerful monster cards with the Tribute keyword. To play a card with the Tribute keyword, you will need to have at least as many summons as the number found on the card. For example, in the starting deck you find the monster cards 7-Colored Fish, which has the text 'Summon 1', and Summoned Skull, which has the text 'Tribute 1'. In this example, if you have 0 summons, you would need to play 7-Colored Fish first to stack up 1 summon, and then you would be able to play Summoned Skull which would consume that 1 stack you just created with 7-Colored Fish, leaving you again with 0 summons. 

Additionally, summoning monsters succesfully adds those monsters to your currently summoned monster list. This list is found by hovering over the summon power icon below your health bar. You will find at the end of the power tooltip a list of all monsters you still currently have available to tribute. This can be helpful because many cards trigger special effects by tributing the right kind of monster, or in some cases, you want to avoid tributing some monsters. Generally, the text of the summoned monsters list is colored to aid you a bit. Monster names will be colored either White, Blue, Purple or Red. White is neutral - any monster with no Tribute effect will be colored white. Purple is associated with Tokens. Blue colored monsters are associated with some sort of positive tribute effect. Red colored monsters are associated with negative tribute effects. 

For example, you may come across the card Mystical Elf. This card has the 'Spellcaster' keyword, which indicates that tributing it for any monster with the 'Dragon' keyword will cause you to lose 2 HP. So when you play Mystical Elf, there will be one entry in your summoned monster list that is red and says Mystical Elf. If you play a Tribute card with the 'Dragon' keyword and Mystical Elf is removed from your list, then you will lose 2 HP. To prevent this from happening, you could either: summon more monsters after Mystical Elf and use those to Tribute for your Dragon card, or simply tribute Mystical Elf with a non-Dragon Tribute card before playing the Dragon. Tribute cards always pull from the end of the list first (most recently summoned monsters).
	
## Resummon
There is a similar keyword called 'Resummon' that allows you to replay monster cards for 0 energy. When you Resummon a monster, that monster will trigger all effects triggered when originally playing the card, except that Tribute monsters will not consume any summons and are played completely for free. To be clear, monsters with the 'Summon' keyword WILL summon additional monsters when resummoned, but monsters with the 'Tribute' keyword WILL NOT tribute when resummoned. All other card effects (damage, block, channel orbs, etc.) should trigger normally. Untargeted resummon effects that pull targeted monster cards will target random enemies.
	
## Increment
The 'Increment' keyword signifies that your maximum number of summons will increase (for combat) by the number found on the card. For example, Kuriboh has the text 'Increment 1'. When you play Kuriboh, you will be able to summon 6 monsters at one time without tributing any first, instead of the usual of 5. Increments certainly stack, so if you play multiple copies of Kuriboh in the same battle, you can raise your max summons even further. There is no limit to how high your maximum summons can be.
	
## Monster, Spell, and Trap Cards
All Duelist cards are of type Monster, Spell or Trap. This change is somewhat cosmetic only - and by that I mean that every card is also either an Attack, Skill or Power. The new card types used by this mod are for thematic flavor and gameplay reasons only. For example, some cards will specifically trigger effects regarding Monster cards. You should look at the type listed on the card in this situation. Conversely, some enemies may trigger effects when you play non-Attack cards. You should look at the picture shape of the card in this situation, as any card that is technically an Attack will still have the diagonally-cut squarish shape found on Attack cards in the base game. Duelist cards that are technically Skills will still have rectangular shaped card images, Powers will still be circular, you get the idea. The base game types (Attack, Skill, Power) and Duelist types (Monster, Trap, Spell) are not associated in any way. For example, there are Attack Monsters, Skill Monsters, Power Monsters, Attack Spells, Skill Spells, Power Traps, etc.
	
## Custom Orbs
There are 12 custom orbs added with this mod. I don't believe it will be possible to channel these orbs outside of using the Duelist character though. These orbs are: Air, Buffer, DragonOrb, Earth, Fire, Gate, Glitch, MonsterOrb, Reducer, Shadow, Splash, and Summoner. Some may be channeled directly via card effect, others may only be channeled indirectly via random effects.

# Mod Options
## Remove all Toon cards
This option will remove the 14 Toon cards from the game. Toggle the box to your desired preference and then restart the game. Checking this option and then using the Toon deck may lead to undefined behavior.

## Remove all Exodia cards
This option will remove the 6 Exodia cards from the game. Functions the same as the 'Remove all Toons' option.

## Allow mod crossover cards
There are 14 bonus cards that can be added to the game if you have this option checked and you load the game with both Replay the Spire and Conspire.

## Starting Deck
This option lets you choose which deck you would like to use for your runs. I have defined 6 starting decks for now, and there are also 2 additional options for using a randomly-generated deck. Select the deck you would like and start a fresh run to try it out!

## Set Size
You can change this option to remove large groups of the cards from the game.

# Creator Information
Thanks for playing the Duelist! Please let me know if you have any feedback or bug reports! 

Email: nyoxidestsmods@gmail.com
Discord: Nyoxide#3464

Some in game screenshots taken of the first steam release build (3-6-19). Everything is absolutely always subject to change.

![CardsA](Screenshots/CardsA.PNG)
![CardsB](Screenshots/CardsB.PNG)
![CardsC](Screenshots/CardsC.PNG)
![CardsD](Screenshots/CardsD.PNG)
![CardsE](Screenshots/CardsE.PNG)
![CardsF](Screenshots/CardsF.PNG)
![CardsG](Screenshots/CardsG.PNG)
![CardsH](Screenshots/CardsH.PNG)
![CardsI](Screenshots/CardsI.PNG)
![CardsJ](Screenshots/CardsJ.PNG)
![CardsK](Screenshots/CardsK.PNG)
![CardsL](Screenshots/CardsL.PNG)
![CardsM](Screenshots/CardsM.PNG)
![CardsN](Screenshots/CardsN.PNG)
![InGameA](Screenshots/InGameA.PNG)
![InGameB](Screenshots/InGameB.PNG)