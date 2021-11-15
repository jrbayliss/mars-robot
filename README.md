# Mars Robot
Engineering Coding Challenge

Normally I'd work outside in; functional tests, application layer then service layer.

TDD is about code quality, with 2-3 hours dev time I'll concentrate on the service first. 

The application layer is trivial, but potential time-consuming. It would flush out the service layer interface.

I want to make sure I get the correct requirements, I will check the sample data first. I did this using pen and paper.
```
Sample Input
5 3 
1 1 E
RFRFRFRF

3 2 N
FRRFLLFFRRFLL

0 3 W
LLFFFLFLFL

Sample Output
1 1 E
3 3 N LOST
2 3 S
```

### Working through the fist set of instructions
Grid width 5 and height 3.
Starting position 1,1 facing East.
```
2
1    E
0
  0  1  2  3  4
```

8 Instructions below (numbered 1-8). Validate we get `11E`. 
```
1 2 3 4 5 6 7 8
R F R F R F R F

1 11E + R = 11S
2 11S + F = 10S
3 10S + R = 10W
4 10W + F = 00W
5 00W + R = 00N
6 00N + F = 01N
7 01N + R = 01E
8 01E + F = 11E
```

I have written a lot of games, essentially you'd use a component entity system. I'll just apply one concept from it and my Robot entity will not contain behaviour.

### To Run
```./gradlew test```

### Notes after implementing the code
Next I would implement:
  * The application later
  * More scenarios i.e. lost top, bottom, left, right of the grid
  * Error handing for the application later safe guarding valid data for the service layer