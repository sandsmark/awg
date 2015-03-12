# GameState #

Vi har en singleton som til enhver tid holder på den nåværende situasjonen i spillet, GameState.
Den inneholder de gjeldene Config-, Map-, Units-, MainWindow-, og Playerobjektene.

## Config ##
Configklassen inneholder verdier for konfigurerbare deler av spillet, som for eksempel brettstørrelse, størrelse på Sprites, o.l.

## Map ##
Map-klassen inneholder kartet som til enhver tid er tilgjengelig, og et BufferImage som er en cachet versjon av kartet, som Canvas-objektet benytter seg av når den skal tegne opp på nytt.

## Units ##
Denne inneholder alle Unitsene som er i spillet, og generelle funksjoner for å manipulere de.

## MainWindow ##
Denne klassen inneholder GUI-kode og logikk som hjelper GUI-et.

## Player ##
Denne klassen inneholder informasjon om en spiller, så langt er den kun brukt til to objekter, AI og human, men vil også kunne benyttes til å utvide spillet mtp. multiplayer.

# Pappaklasser #

Unit er superklassen til alle de forskjellige unittypene, og inneholder metoder og variabler som er felles for alle sammen, som f. eks. en move-metode som oppdaterer hastighet og posisjon i forhold til target-Point'et.
Unit inneholder også et Sprite-objekt, som inneholder bildet som representerer enheten, og som inneholder metoder for å rotere og få neste sprite i en walkcycle, så vi får noe som ligner animasjon.


# Barneklasser #

Healer, Worker, Fighter er alle barn av Unit, og får dermed de generelle metodene og variablene. I tillegg implementerer de funksjoner som er spesielle for sine spesifikke unittyper, og bygger videre på f. eks. move()-metodene, og utvider de slik at de er bedre tilpasset de spesifikke undertypene.
For eksempel har Worker en move()-metode som i tillegg henter inn gull fra gullkildene på kartet, evt. oppdaterer target-Point for å reflektere om den skal tømmes eller hente mer gull.


# Trådklasser #

Vi har seks forskjellige trådklasser som blir instansiert når MainWindow blir laget, GraphicsThread, SelectThread, AIThread, WindowThread, MovementThread og Music.

## GraphicsThread ##
Denne tråden er hovedsakelig opptatt av å scrolle canvasen i forhold til musen over canvas-objektet.

## SelectThread ##
Denne tråden liker å holde styr på velging av Units, og tegne opp en slik en select-box på Canvasen.

## AIThread ##
I denne tråden lever den kunstige intelligensen.

## WindowThread ##
Denne har for vane å oppdatere MainWindowen i henhold hva som er valgt. Denne er deprekert, og vår GUI-ansvarlige jobber med å få utviklet en bedre løsning.

## MovementThread ##
Denne passer på at alle Units får beveget seg, og gjort det de skal.

## Music ##
Denne tråden spiller musikk, og er derivert fra Jorbis, med tunge modifikasjoner.

# AI #
AI har tre klasser, AI, AIRules, og AIThread.
AIThread kaller AI. AI sjekker med AIRules for hvordan den skal oppføre seg.

## Path ##
Dette er en klasse som inneholder en sti, bestående WeightedNodes, som brukes av en unit for å bevege seg rundt på kartet. Konstruktøren tar inn nåværende posisjon, og target, og benytter seg av A-star for å finne en sti frem til målet på kartet. Den genererer et nett over kartet, bestående av ruter i varierende størrelse, som den bruker for å finne stien (dette for å spare cpu og minne).