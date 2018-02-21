# PSI

Genetic Algorithm implemented and used on example of virtual mice navigating through labirynth.

Requied software:
-Apache Maven 3.5.2
-Java version: 1.8.0_161

How to run:
- In terminal, while being in main folder of the repo run:
    mvn package
- Execute "Arena-1.0-SNAPSHOT.jar" located in generated target/ folder (via terminal or by double clicking in GUI).

Example of executing default scenario with software:

Scenario 1
1. Creating population
    -"New Population" button
    -specify genotypes number (10 000 is a good working quantity)
    -close form with "ok" button
2. choose "One-Time Testing" from combobox
3. input number of tests per subject in the text field below (in the case of same labirynth, 1 test is enough, selecting more won't improve results)
4. "Execute Tests" button
5. wait untill tests are completed (after most of the buttons became clickable again, and the label of the botton shows you that number of completed tests is equal to number of ordered tests)
6. select first object on the list, filled with objects called "Geneable"
7. if in the label above lists, prints "Grades" number big enough for you, you are free to go.
8. if its not, press button "Next Generation" and start again from step 4.

At every part you can test one of the mouse, by selecting it from the list, and pressing "Test Game" button

In this application, possible score for every mouse, varies from 0 to 7, the more, the further mouse is able to go into the labirynth.
