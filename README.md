# ProgettoSOLab-Calcolatrice-Polacca-Inversa-
Calcolatrice Polacca Inversa a partire da un file di testo

**Per prima cosa eseguire la classe FP per creare il file di testo "expression.txt".
Poi eseguire la classe Main per ottenere il risultato dell'operazione.**

Progetto di laboratorio di sistemi operativi

Macchina data flow


Si realizzi un programma che simula il funzionamento di una macchina data flow
per la valutazioni di espressioni aritmetiche.
L’espressione è memorizzata in un file in forma polacca (notazione prefissa).
Il formato è il seguente:

<EXP>    ::= <OP> <EXP> <EXP> | <NUMBER>

<OP>     ::= + | * | / | -

<NUMBER> ::= real in java external format
Almeno uno spazio o newline è richiesto come delimitatore dei numeri è operatori.

Si consiglia di utilizzare la libreria java.util.Scanner


Ad esempio

* + 3.14 3.67 / 4.56 22.4 ----> OK

(3.14 + 3.67) * (4.56 / 22.4) = 1.38632142

* + / 2 - 1 3 5 * 2 4 ---> OK
((2 / (1 - 3)) + 5)) * (2 * 4) = 32

* / + 2 3 - 4 5 / 3 2 ----> OK
((2+3) / (4-5)) * (3/2) = -15/2

Realizzare il programma esplicitando il massimo parallelismo.
Il programma stampa oltre che al risultato della valutazione, la differenza tra i timestamp prima e dopo la valutazione
e il numero di threads utilizzati per la valutazione. 
Il progetto va discusso e sviluppato singolarmente.
