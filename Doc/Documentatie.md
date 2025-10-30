# UML Onderbouwing Documentatie

## Inleiding
Deze documentatie bevat de belangrijkste ontwerpdiagrammen van het systeem, waaronder een Use Case Diagram (UCD), Klassendiagram (KD) en Sequencediagrammen (SD). Elk diagram is voorzien van een toelichting die de keuzes en structuur van het ontwerp onderbouwt. Het systeem representeert een gesimuleerde supermarktomgeving waarin klanten producten verzamelen, afrekenen en vertrekken, terwijl medewerkers vakken vullen.

---

## 1. Use Case Diagram (UCD)

### Beschrijving
Het Use Case Diagram toont de interacties tussen externe actoren en het systeem. In dit geval zijn er twee actoren:
- **Klant**: voert acties uit zoals producten pakken, afrekenen en het verlaten van de winkel.
- **Medewerker**: vult lege vakken bij.

### Onderbouwing
Het UCD is ontworpen om de functionele eisen van het systeem te visualiseren. Door de acties van klant en medewerker te scheiden, wordt duidelijk welke verantwoordelijkheden en interacties elk type gebruiker heeft. Dit helpt bij het definiëren van systeemfunctionaliteit en het opstellen van testscenario’s.

---

## 2. Klassendiagram (KD)

### Beschrijving
Het Klassendiagram beschrijft de structuur van het systeem in termen van klassen, attributen en relaties. Belangrijke klassen zijn onder andere:
- **World**: bevat lijsten van schappen, kassa’s en klanten.
- **Person**, **Klant**, **Medewerker**: representeren bewegende entiteiten met positie en snelheid.
- **Product**, **Schap**, **Kassa**: modelleren de winkelinhoud en het afrekenproces.
- **WorldGraph**, **Node**, **Edge**: ondersteunen navigatie en padberekening.

### Onderbouwing
Het KD is essentieel voor het begrijpen van de data-architectuur van het systeem. Door gebruik te maken van overerving (bijv. Klant en Medewerker erven van Person) wordt codehergebruik gestimuleerd. De relaties tussen klassen zoals `World → Schap`, `Klant → ProductList` en `WorldGraph → Node` maken duidelijk hoe objecten samenwerken en hoe data door het systeem stroomt.

---

## 3. Sequencediagrammen (SD)

### Beschrijving
Het Sequencediagram toont de tijdsgebonden interactie tussen objecten tijdens een typische klantreis:
1. Klant zoekt producten via het WorldGraph.
2. Klant beweegt naar het schap en verzamelt producten.
3. Klant gaat naar de kassa, betaalt en verlaat het systeem.

### Onderbouwing
Het SD maakt de dynamiek van het systeem inzichtelijk. Door de volgorde van methoden en interacties te visualiseren, wordt duidelijk hoe objecten samenwerken in real-time. De `alt`-loop met de conditie `[ProductList != null]` toont dat de klant meerdere producten kan verzamelen voordat hij naar de kassa gaat. Dit diagram ondersteunt het testen van logica en het debuggen van gedrag.

---

## 4. Relatie met Code

De UML-diagrammen zijn direct gekoppeld aan de implementatie. Klassen zoals `Klant`, `Product`, `WorldGraph` en `Kassa` zijn terug te vinden in de codebase. Door middel van duidelijke naamgeving en commentaar in de code is de structuur herkenbaar en onderhoudbaar. Eventueel kan documentatie gegenereerd worden met tools zoals **Doxygen** om de koppeling tussen ontwerp en implementatie verder te versterken.

---

## 5. Conclusie

De UML-diagrammen vormen samen een compleet beeld van het systeemontwerp. Ze helpen bij:
- Het communiceren van systeemgedrag aan ontwikkelaars en stakeholders.
- Het structureren van de codebase.
- Het voorbereiden van testcases en validatie.

Door deze onderbouwing toe te voegen aan het verslag wordt aangetoond dat het ontwerp doordacht is en aansluit bij de functionele eisen van het systeem.

