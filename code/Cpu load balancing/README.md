## Symulacja rozproszonego algorytmu równoważącego obciążenie procesorów. ##

### [Opis problemu](https://www.ii.pwr.edu.pl/~juszczyszyn/so.htm) ###

_W systemie pracuje N identycznych procesorów. Na każdym z nich pojawiają się nowe zadania, z **_różną_**
częstotliwością i **_różnymi_** wymaganiami (każde zadanie potrzebuje jakiejś mocy obliczeniowej) . Symulujemy
następujące strategie przydziału zadań:_

**Na danym procesorze pojawia się zadanie. Następnie:**

* Dane cpu pyta się losowego procesora o aktualne obciążenie:

    * Jeśli jest mniejsze od progu p, proces jest tam wysyłany.

    * Jeśli nie, losujemy i pytamy następny, próbując co najwyżej z razy.

    * Jeśli wszystkie wylosowane są obciążone powyżej p, proces wykonuje się na danym procesorze.


* Jesli obciążenie danego cpu przekracza wartość progową p, proces zostaje wysłany na losowo wybrany inny procesor o
  obciążeniu mniejszym od p (jeśli wylosowany y ma obc.>p, losowanie powtarza się do skutku). Jeśli nie przekracza -
  proces wykonuje się na danym cpu.


* Jak w pkt 2, z tym że procesory o obciążeniu mniejszym od minimalnego progu r pytają losowo wybrane procesory i jesli
  obc. zapytanego jest większe od p, pytający przejmuje część jego zadań (założyć jaką).

**Przeprowadzić symulację strategii 1-3 dla N=ok.50-100 i długiej serii zadań do wykonania (parametry dobrać
samodzielnie, tak by całość zadziałała:). W każdym przypadku podać jako wynik:**

* Średnie obciążenie procesorów.


* Średnie odchylenie od wartości z pkt A.


* Ilość zapytań o obciążenie oraz migracji zadań.

__Użytkownik powinien mieć możliwość podania (zmiany) wartości p, r, z, N.__

### Dla jakich wartości zmiennych testować? ###

* **Przykładowe dane:**

| Lp. | ilość zadań | max. obciążenie | ilość procesorów | max. czas fazy |  z  |  p  |  r  |
|:---:|:-----------:|:---------------:|:----------------:|:--------------:|:---:|:---:|:---:|
| 1.  |   100000    |       60        |        50        |      170       | 10  | 80  | 30  |
| 2.  |    50000    |       90        |        70        |      200       |  4  | 70  | 10  |
| 3.  |    1000     |       80        |        60        |      250       |  3  | 80  | 40  |

* **Co oznaczają poszczególne zmienne:**

**Maksymalne obciążenie:**
maksymalna moc obliczeniowa jaką potrzebuje zadanie

**Maksymalny czas fazy:**
maksymalny czas wykonania się zadania

**z:**
ile maksymalnie razy będziemy próbali szukać procesora, który może przejąć zadanie

**p:**
maksymalny próg obiążenia

**r:**
minimalny próg obiążenia
                                                          

