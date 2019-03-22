# StS-DuelistMod
A Slay the Spire adaptation of Yu-Gi-Oh!

Discord Server: https://discord.gg/Tcwws6U

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

# Duelist Keywords
Summon - Counts monsters currently summoned. Maximum of 5 Summons.  
Tribute - Removes X Summons. Unless you have enough Summons to Tribute, you cannot play a Tribute monster.  
Resummon - Replays the card, ignoring Tribute costs. Some monsters trigger extra special effects when Resummoned.  
Increment - Increase your maximum Summons by X.  
Magnets - Associated with the 4 Magnet Warrior cards. This keyword just signifies effects that go with these cards.  
Ojamania - Add 2 random cards to your hand, they cost 0 this turn. Apply 1 random buff. Apply 2 random debuffs to an enemy.  
Dragon - Specific type of monster card. When Tributed for another Dragon, you gain 1 Strength.  
Spellcaster - Specific type of monster card. When Tributed for a Dragon, you lose 2 HP.  
Insect - Specific type of monster card. When Tributed for an Insect/Plant/Predaplant, apply 3 Poison to all enemies.  
Plant - Specific type of monster card. When Tributed for an Insect/Plant/Predaplant, apply 3 Poison to all enemies.  
Predaplant - More specific type of Plant. Predaplants are treated as Plants, but some cards trigger effects concerning ONLY Predaplants.  

# Gameplay Mechanics
## Summoning & Tributing
When you play lower cost/power monsters with the Summon keyword, you gain stacks of summons equal to the number found on the card, up to a certain maximum number. The default maximum is 5, but this number	can be increased via card effect. Having a lot of summons allows you to play more powerful monster cards with the Tribute keyword. To play a card with the Tribute keyword, you will need to have at least as many summons as the number found on the card. For example, in the starting deck you find the monster cards 7-Colored Fish, which has the text 'Summon 1', and Summoned Skull, which has the text 'Tribute 1'. In this example, if you have 0 summons, you would need to play 7-Colored Fish first to stack up 1 summon, and then you would be able to play Summoned Skull which would consume that 1 stack you just created with 7-Colored Fish, leaving you again with 0 summons. 

Additionally, summoning monsters succesfully adds those monsters to your currently summoned monster list. This list is found by hovering over the summon power icon below your health bar. You will find at the end of the power tooltip a list of all monsters you still currently have available to tribute. This can be helpful because many cards trigger special effects by tributing the right kind of monster, or in some cases, you want to avoid tributing some monsters. Generally, the text of the summoned monsters list is colored to aid you a bit. Monster names will be colored either White, Blue, Purple or Red. White is neutral - any monster with no Tribute effect will be colored white. Purple is associated with Tokens. Blue colored monsters are associated with some sort of positive tribute effect. Red colored monsters are associated with negative tribute effects. 

For example, you may come across the card Mystical Elf. This card has the 'Spellcaster' keyword, which indicates that tributing it for any monster with the 'Dragon' keyword will cause you to lose 2 HP. So when you play Mystical Elf, there will be one entry in your summoned monster list that is red and says Mystical Elf. If you play a Tribute card with the 'Dragon' keyword and Mystical Elf is removed from your list, then you will lose 2 HP. To prevent this from happening, you could either: summon more monsters after Mystical Elf and use those to Tribute for your Dragon card, or simply tribute Mystical Elf with a non-Dragon Tribute card before playing the Dragon. Tribute cards always pull from the end of the list first (most recently summoned monsters).
	
## Resummon
There is a similar keyword called 'Resummon' that allows you to replay monster cards for 0 energy. When you Resummon a monster, that monster will trigger all effects triggered when originally playing the card, except that Tribute monsters will not consume any summons and are played completely for free. To be clear, monsters with the 'Summon' keyword WILL summon additional monsters when resummoned, but monsters with the 'Tribute' keyword WILL NOT tribute when resummoned. All other card effects (damage, block, channel orbs, etc.) should trigger normally. Untargeted resummon effects that pull targeted monster cards will target random enemies.
	
## Increment
The 'Increment' keyword signifies that your maximum number of summons will increase (for combat) by the number found on the card. For example, Kuriboh has the text 'Increment 1'. When you play Kuriboh, you will be able to summon 6 monsters at one time without tributing any first, instead of the usual of 5. Increments certainly stack, so if you play multiple copies of Kuriboh in the same battle, you can raise your max summons even further. There is no limit to how high your maximum summons can be.
	
## Monster, Spell, Trap, Tokens
All Duelist cards are of type Monster, Spell, Trap or Token. This change is somewhat cosmetic only - and by that I mean that every card is also either an Attack, Skill or Power. The new card types used by this mod are for thematic flavor and gameplay reasons only. For example, some cards will specifically trigger effects regarding Monster cards. You should look at the type listed on the card in this situation. Conversely, some enemies may trigger effects when you play non-Attack cards. You should look at the picture shape of the card in this situation, as any card that is technically an Attack will still have the diagonally-cut squarish shape found on Attack cards in the base game. Duelist cards that are technically Skills will still have rectangular shaped card images, Powers will still be circular, you get the idea. The base game types (Attack, Skill, Power) and Duelist types (Monster, Trap, Spell) are not associated in any way. For example, there are Attack Monsters, Skill Monsters, Power Monsters, Attack Spells, Skill Spells, Power Traps, etc. Tokens are not cards that will ever be played, and exist only as a way to trigger certain effects, or generally, simply as a generic summon to tribute from your summons list.
	
## Custom Orbs
There are 12 custom orbs added with this mod. I don't believe it will be possible to channel these orbs outside of using the Duelist character though. These orbs are: Air, Buffer, DragonOrb, Earth, Fire, Gate, Glitch, MonsterOrb, Reducer, Shadow, Splash, and Summoner. Some may be channeled directly via card effect, others may only be channeled indirectly via random effects.

## Random Actions (Time Wizard & Glitch orb)
This card and this orb are the only ways to currently trigger a 'random action'. The list of actions for both is similar. The full list of actions as they appear in the array for each card is given below.

## Tokens
There are Generic Tokens, Kuriboh Tokens, Explosive Tokens, Shadow Tokens and Predaplant Tokens. Any other name for a Token that is given in game refers always to Generic Tokens. For example, Jam Breeding Machine summons Jam Tokens. These tokens have no special effects or purpose - they are just summon tokens to be tributed. But on the other hand, the other tokens DO have special effects.  
Explosive Tokens: 	These will cause you to lose 5 HP if you Tribute them.  
Kuriboh Tokens: 	These tokens give you Intangible if you Tribute them with any card that is NOT a Dragon.  
Shadow Tokens:		When Tributed, these tokens increase the Passive and Evoke amounts of ALL your currently-channeled Shadow orbs by 1.  
Predaplant Tokens:	These tokens simply provide extra ways to trigger Predaplant-specific Tribute effects. Otherwise, they are the same as every other Generic Token. These tokens are just Generic Tokens with the Predaplant keyword. 
  
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
You can change this option to remove large groups of the cards from the game. There are 5 different card sets to select from: Core, Limited, Reduced, Full and All. The Core set is the smallest, with only 61 cards, while the All set is the largest set with 186 cards. If you have both 'Remove cards' mod options turned off, the 'Allow crossover cards' option turned on, and the card set size set to 'All', then the game will have the maximum number of possible cards at 200. The number of cards currently turned on is always displayed in the options panel after the 'Active Duelist Cards:' label. This setting is largely untested, so there may be some bugs when using sets other than 'All'.

# Known Issues
- Downgrading cards does not work properly when cards that have Tribute-based upgrades become Downgraded. However in most cases, this is beneficial and it should never crash the game or cause any other major side effects.  
- Future Diary relic (Aspiration) sometimes causes issues  
- Buffer orb has problems with powers that don't stack, needs major overhaul  
- Pumprincess/Pumpking/Gigaplant cannot resummon each other, and Shallow Grave cannot resummon these cards either   
- Prismatic Shard should be removed from relic pools while the Duelist is loaded   
- Mirror Force is buggy and sometimes does way too much damage   
- Ultimate Offering can sometimes get into an infinite loop and murder you   
- Many cards with tribute-based upgrades are slightly buggy and can have undefined behavior   
- Monkei's Paw relic (Halation) causes various issues, beware   
- Time Maze and Toon Rollback/Toon Magic interaction is buggy   
- Heavy Storm doesn't always remove powers properly from everyone, and exhausts although the card text does not say so   
- Bottled Mercury relic sometimes gets set on the wrong card after save & quit (Infinite Spire)   
- Tomb Looter does not trigger properly when attacking with a max stack of summons with a Tribute card  

# To Do (ordered by priority)
- Balance updates  
- Add a few more starting decks  
- Add more random-generation-only cards   
- Add additional Relics and Potions  
- Translations (Chinese, Korean, maybe Russian)  
- Add event(s)  
- Add enemies  

# Creator Information
Thanks for playing the Duelist! Please let me know if you have any feedback or bug reports! 

Email: nyoxidestsmods@gmail.com

Discord: Nyoxide#3464

# Card List
  
#### Core  
7-Colored Fish - *Core*  
Armored Zombie - *Core*  
Axe of Despair - *Core*  
B. Eyes Ultimate - *Core*  
B.E. White Dragon - *Core*  
Baby Dragon - *Core*  
Blizzard Dragon - *Core*  
Buster Blader - *Core*  
Cannon Soldier - *Core*  
Card Destruction - *Core*  
Castle Dark Illusion - *Core*  
Castle Walls - *Core*  
Catapult Turtle - *Core*  
Cave Dragon - *Core*  
Celtic Guardian - *Core*  
Change of Heart - *Core*  
Dark Magician - *Core*  
Darklord Marie - *Core*  
Dian Keto - *Core*  
Dragon Capture Jar - *Core*  
Fiend Megacyber - *Core*  
Fissure - *Core*  
Flute of Summoning Dragon - *Core*  
Fortress Warrior - *Core*  
G. Dragon Champion - *Core*  
G. Fierce Knight - *Core*  
Gemini Elf - *Core*  
Giant Stone Soldier - *Core*  
Graceful Charity - *Core*  
Gravity Axe - *Core*  
Hane Hane - *Core*  
Hinotoma - *Core*  
Imperial Order - *Core*  
Injection Fairy Lily - *Core*  
Insect Queen - *Core*  
Island Turtle - *Core*  
Jam Breed Machine - *Core*  
Judge Man - *Core*  
Kazejin - *Core*  
Kuriboh - *Core*  
Labyrinth Wall - *Core*  
Lesser Dragon - *Core*  
Lord of D. - *Core*  
Mini L. Wall - *Core*  
Mirror Force - *Core*  
Monster Reborn - *Core*  
Mountain - *Core*  
Mystical Elf - *Core*  
Obelisk - *Core*  
Ookazi - *Core*  
Pot of Generosity - *Core*  
Pot of Greed - *Core*  
Prevent Rat - *Core*  
Pumpking - *Core*  
R. Eyes Black Dragon - *Core*  
Raigeki - *Core*  
Rain of Mercy - *Core*  
Relinquished - *Core*  
Sanga of Thunder - *Core*  
Scapegoat - *Core*  
Scrap Factory - *Core*  
Slifer Sky Dragon - *Core*  
Snow Dragon - *Core*  
Snowdust Dragon - *Core*  
Spirit of the Harp - *Core*  
Summoned Skull - *Core*  
Superheavy Benkei - *Core*  
Superheavy Scales - *Core*  
Superheavy Swordsman - *Core*  
Superheavy Waraji - *Core*  
Thunder Dragon - *Core*  
Tremendous Fire - *Core*  
Winged Dragon Ra - *Core*  
Yami - *Core*  
  
#### Limited    
A. Magnet Warrior - *Limited*  
Ancient Rules - *Limited*  
B. Magnet Warrior - *Limited*  
Bad Reaction - *Limited*  
D.M. Girl - *Limited*  
Dark Hole - *Limited*  
Emperor Mausoleum - *Limited*  
Exodia Head - *Limited*  
Exodia L. Arm - *Limited*  
Exodia L. Leg - *Limited*  
Exodia R. Arm - *Limited*  
Exodia R. Leg - *Limited*  
G. Magnet Warrior - *Limited*  
Millennium Shield - *Limited*  
Phoenix Feather - *Limited*  
Valkyrion - *Limited*  
  
#### Reduced  
Curse of Dragon - *Reduced*  
Cyber Dragon - *Reduced*  
Dark Factory - *Reduced*  
Fiend Skull Dragon - *Reduced*  
Five Headed Dragon - *Reduced*  
Giant Trunade - *Reduced*  
Harpie Feather Duster - *Reduced*  
Molten Zombie - *Reduced*  
Ojama Green - *Reduced*  
Ojama Yellow - *Reduced*  
Ojamagic - *Reduced*  
Ojamuscle - *Reduced*  
Pot of Avarice - *Reduced*  
Pot of Dichotomy - *Reduced*  
Pot of Duality - *Reduced*  
Pumprincess - *Reduced*  
R. Eyes Zombie Dragon - *Reduced*  
Red Medicine - *Reduced*  
Shard of Greed - *Reduced*  
Storming Mirror Force - *Reduced*  
Superheavy Brawler - *Reduced*  
Superheavy Daihachi - *Reduced*  
Superheavy Flutist - *Reduced*  
Superheavy General - *Reduced*  
Superheavy Magnet - *Reduced*  
Superheavy Ogre - *Reduced*  
Swords: Burning Light - *Reduced*  
Swords: Concealing Light - *Reduced*  
  
#### Full  
B.E. Toon Dragon - *Full*  
Dragon Master Knight - *Full*  
Gandora - *Full*  
L. Exodia Incarnate - *Full*  
Radiant Mirror Force - *Full*  
Red Eyes Toon - *Full*  
Superancient Dinobeast - *Full*  
Swords: Revealing Light - *Full*  
Time Wizard - *Full*  
Token Vacuum - *Full*  
Toon Barrel Dragon - *Full*  
Toon Briefcase - *Full*  
Toon D.M. Girl - *Full*  
Toon Dark Magician - *Full*  
Toon Gemini Elf - *Full*  
Toon Kingdom - *Full*  
Toon Magic - *Full*  
Toon Mask - *Full*  
Toon Mermaid - *Full*  
Toon Rollback - *Full*  
Toon S. Skull - *Full*  
Toon World - *Full*  
Trap Hole - *Full*  
  
#### All  
B. Skull Dragon - *All*  
Basic Insect - *All*  
Blackland Fire Dragon - *All*  
Book Secret Arts - *All*  
Bottomless Trap Hole - *All*  
Cheerful Coffin - *All*  
Cocoon of Evolution - *All*  
Darkfire Dragon - *All*  
Empress Mantis - *All*  
Fog King - *All*  
Gigaplant - *All*  
Grasschopper - *All*  
Heavy Storm - *All*  
Jerry Beans Man - *All*  
Jinzo - *All*  
King Yamimakai - *All*  
Lajinn Mystical Genie - *All*  
Levia-Dragon - *All*  
Machine Factory - *All*  
Machine King - *All*  
Man-Eater Bug - *All*  
Metal Dragon - *All*  
Monster Egg - *All*  
Ocean Dragon Lord - *All*  
P. Chimerafflesia - *All*  
P. Chlamydosundew - *All*  
P. Drosophyllum - *All*  
P. Flytrap - *All*  
P. Pterapenthes - *All*  
P. Sarraceniant - *All*  
P. Spinodionaea - *All*  
Petit Moth - *All*  
Polymerization - *All*  
Predaponics - *All*  
Predapruning - *All*  
Reinforcements - *All*
Revival Jam - *All*  
Steam Train King - *All*  
Stim Pack - *All*  
Super Solar Nutrient - *All*  
Sword of Deep-Seated - *All*  
The Creator - *All*  
Tri-Horned Dragon - *All*  
Tribute to Doomed - *All*  
Ultimate Offering - *All*
Violet Crystal - *All*  
White Night Dragon - *All*  
White-Horned Dragon - *All*  
Wiretap - *All*  
  
#### Bonus Mod-Crossover
Barrel Dragon - *Crossover* - *Replay the Spire*  
Blast Juggler - *Crossover* - *Replay the Spire*  
Dark Mirror Force - *Crossover* - *Replay the Spire*  
Flame Swordsman - *Crossover* - *Replay the Spire*  
Gate Guardian - *Crossover* - *Conspire*  
Legendary Fisherman - *Crossover* - *Conspire*  
Magic Cylinder - *Crossover* - *Replay the Spire*  
Nutrient Z - *Crossover* - *Replay the Spire*  
Ojama Black - *Crossover* - *Replay the Spire*  
Ojama King - *Crossover* - *Replay the Spire*  
Ojama Knight - *Crossover* - *Replay the Spire*  
Parasite Paracide - *Crossover* - *Replay the Spire*  
Suijin - *Crossover* - *Conspire*

#### Random Generation Only
Guardian Angel Joan - *Random generation only*  
Mini-Polymerization - *Random generation only*  
Shadow Toon - *Random generation only*  
Shallow Grave - *Random generation only*  
Wiseman's Chalice - *Random generation only*  
  
#### Toon cards removed by Mod Option  
B.E. Toon Dragon - *Toons*  
Red Eyes Toon - *Toons*  
Toon Barrel Dragon - *Toons*  
Toon Briefcase - *Toons*  
Toon Dark Magician - *Toons*  
Toon Gemini Elf - *Toons*  
Toon Magic - *Toons*  
Toon Mask - *Toons*  
Toon Mermaid - *Toons*  
Toon Rollback - *Toons*  
Toon S. Skull - *Toons*  
Toon World - *Toons*  
Toon Kingdom - *Toons*  
Toon D.M. Girl - *Toons*  
Shadow Toon - *Toons*  

#### Exodia cards removed by Mod Option  
L. Exodia Incarnate - *Exodia*  
Exodia Head - *Exodia*  
Exodia L. Arm - *Exodia*  
Exodia L. Leg - *Exodia*  
Exodia R. Arm - *Exodia*  
Exodia R. Leg - *Exodia*  

## Random Buff Pool (Small)
Strength 		(random)    
Dexterity 		always 1)    
Artifacts 		(random)    
Plated Armor 	(random)  
Regen			(random)  
Energized		(always 1)  
Thorns			(random)  
Focus			(random)  
  
Magic Cylinder and Ojama Knight are currently the only cards that pull random buffs from this pool.  
  
## Random Buff Pool (Full)
Strength 			(random)     
Dexterity 			always 1)      
Artifacts 			(random)      
Plated Armor 		(random)  
Regen				(random)  
Energized			(always 1)    
Thorns				(random)    
Focus				(random)    
Intangible			(always 1)  
Barricade			(no amount)  
Blur				(random)  
Burst				(random)  
Creative AI			(always 1)  
Dark Embrace		(random)  
Double Tap			(random)  
Equilibrium			(always 2)  
Feel No Pain		(random)  
Fire Breathing		(always 3)  
Juggernaut			(random)  
Metallicize			(random)  
Pen Nib				(always 1)  
Sadistic			(random)  
Storm				(always 1)  
Gaze of Anubis		(random)  
Tomb Looter			(random)  
Blessing of Ra		(random)  
Tomb Pilferer 		(random * 10)  
Retain Cards		(always 1)  
Pot of Generosity	(always 2)  
Reducer 			(random)  
Time Wizard			(always 1)  
Mayhem				(always 1)  
Envenom				(random)  


## Random Actions (List)

### Time Wizard
Draw 1 card  
Draw 1 card  
Draw 2 cards
Gain 10 HP
Gain 5 HP  
Gain 5 HP  
Draw 1 card  	
Draw 1 card  	
Draw 2 cards  	
Gain 10 HP  
Gain 5 HP  
Gain 5 HP  
Gain a random amount of gold (50-100)  
Gain a random amount of gold (10-50)  
Gain a random amount of gold (10-50)  
Gain a random amount of gold (10-50)  
Gain a random amount of gold (10-50)  
Gain a random amount of gold (5-200)  
Gain a random amount of gold (0-1000)  
Apply 1 random buff  	
Apply 2 random debuffs to random enemy  	
Apply 1 random debuff to random enemy  
Add 1 random Spell to hand  
Add 1 random Trap to hand  
Add 1 random Trap to hand  	
Add 1 random Dragon to hand  
Add 1 random Monster to hand  
Add 1 random Ethereal Duelist card to hand  
Gain 15 Block  	
Gain 10 Block  
Gain 10 Block  
Gain 5 Block  
Gain 5 Block  
Gain 5 Block  
Summon 1  	
Summon 1  	
Summon 2  
Increment 1  	
Increment 2  
Ojamania  	
Gain 1 Energy    
Gain 2 Energy   
Channel a Glitch  
Gain 1 Artifacts  
Gain 2 Artifacts  
Gain 3 Artifacts  

### Glitch Orb (Passive)
Draw 1 card  	
Draw 1 card  
Draw 2 cards  	
Gain 10 HP  
Gain 5 HP  
Gain 5 HP  
Gain a random amount of gold (50-100)  
Gain a random amount of gold (10-50)  
Gain a random amount of gold (10-50)  
Gain a random amount of gold (5-200)  
Apply 1 random buff  	
Apply 1 random debuff to random enemy  	
Apply 1 random debuff to random enemy  
Add 1 random Spell to hand  
Add 1 random Trap to hand  
Add 1 random Trap to hand  	
Add 1 random Spellcaster to hand  
Add 1 random Monster to hand  
Add 1 random Ethereal Duelist card to hand  
Gain 15 Block  	
Gain 10 Block  
Gain 10 Block  
Gain 5 Block  
Gain 5 Block  
Gain 5 Block  
Summon 1  	
Summon 1  	
Summon 2  
Increment 1  	
Increment 2  
Ojamania  	
Increase this orb's Passive amount by 1  	
Increase this orb's Evoke amount by 1  	
Increase this orb's Evoke amount by 1  
Increase this orb's Evoke amount by 1  	
Increase this orb's Evoke amount by 2  
Gain 1 Energy    
Gain 2 Energy     
Channel a Glitch  
Gain 1 Artifacts  
Gain 2 Artifacts  
Gain 3 Artifacts  

### Glitch Orb (Evoke)
Orb slots +1  
Draw 1 card  	
Draw 1 card  	
Draw 2 cards  	
Gain 10 HP  
Gain 5 HP  
Gain 5 HP  
Gain a random amount of gold (50-100)  
Gain a random amount of gold (10-50)  
Gain a random amount of gold (10-50)  
Gain a random amount of gold (5-200)  
Apply 1 random buff  	
Apply 1 random debuff to random enemy  	
Apply 1 random debuff to random enemy  
Add 1 random Spell to hand  
Add 1 random Trap to hand  
Add 1 random Trap to hand  	
Add 1 random Spellcaster to hand  
Add 1 random Monster to hand  
Add 1 random Ethereal Duelist card to hand  
Gain 15 Block  	
Gain 10 Block  
Gain 10 Block  
Gain 5 Block  
Gain 5 Block  
Gain 5 Block  
Summon 1  	
Summon 1  	
Summon 2  
Increment 1  	
Increment 2  
Ojamania  	
Increase this orb's Passive amount by 1  	
Increase this orb's Evoke amount by 1  	
Increase this orb's Evoke amount by 1  
Increase this orb's Evoke amount by 1  	
Increase this orb's Evoke amount by 2  
Gain 1 Energy    
Gain 2 Energy    
Channel a Glitch  
Gain 1 Artifacts  
Gain 2 Artifacts  
Gain 3 Artifacts  

## Screenshots
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