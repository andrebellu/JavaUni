# Simulazione del Funzionamento di un Ascensore

Si richiede un programma per la simulazione del funzionamento di un ascensore in un edificio avente un certo numero di piani. L'ascensore è caratterizzato da una portata massima espressa come numero di persone.

Il programma richiede inizialmente:
- il numero di piani dell'edificio (almeno 10)
- la posizione iniziale dell'ascensore che può essere situato in uno qualunque dei piani dell'edificio.

Il programma riceve in ingresso da tastiera la sequenza delle chiamate effettuate all'ascensore: ogni chiamata è caratterizzata:
- dal piano al quale viene effettuata,
- dalla direzione che la persona intende dare all'ascensore (salire / scendere).

Ad esempio:
- una persona desidera salire, partendo dal piano 4 fino al piano 8
- una persona desidera scendere, partendo dal piano 6 fino al piano 3
- una persona desidera salire, partendo dal piano 2 fino al piano 9
- una persona desidera salire, partendo dal piano 1 fino al piano 8

Una volta inserito l'elenco dei comandi, si può avviare la simulazione: l'ascensore deve muoversi nella direzione richiesta dal primo comando inserito, e a ogni piano permettere la discesa delle persone arrivate a destinazione e la salita delle persone al piano.

L'ascensore continuerà a salire fino a che ci sono persone sull'ascensore che hanno una destinazione superiore al piano corrente; viceversa continuerà a scendere fino a che ci sono persone che hanno un piano di destinazione inferiore al piano corrente.

La simulazione si interrompe quando l'ascensore è vuoto e non ci sono comandi in coda.

Durante la simulazione, stampare a video i movimenti dell'ascensore:
- il piano toccato dall'ascensore,
- quante persone scendono e salgono,
- quante persone sono presenti sull'ascensore.

Inoltre:
- predisporre le funzionalità di caricamento / salvataggio da file dello stato attuale della simulazione,
- predisporre un set di dati caricabile in fase di avvio del programma che rappresenti lo stato iniziale della simulazione (piano di partenza dell'ascensore, almeno 5 persone in attesa e 3 persone sull'ascensore).

## Opzionale

Gestire il fatto che se l'ascensore è pieno (c'è già il numero massimo di persone consentite), una o più persone al piano potrebbero non poter salire e quindi resterebbero in attesa.

Si preveda la possibilità di effettuare una fermata di "emergenza" in cui tutte le persone presenti nell'ascensore vengano fatte scendere al piano attuale, aggiungendole poi nuovamente in coda alla lista di attesa. In questo step nessuna persona può salire sull'ascensore, va atteso lo step di simulazione successivo per poter caricare nuove persone in ascensore.

## Requisiti del Progetto

Il progetto deve essere corredato di:
- documentazione realizzata tramite javadoc,
- test di unità realizzati con junit,
- diagramma delle classi UML realizzabile con un qualsiasi programma di grafica.
