## Minimum Average Waiting Time

To use the program start SBT and `compile` the code. After compilation, one can use the `run` command to pass a file containing the input data. Example:

    $ sbt
    [info] Loading global plugins from /Users/bharadwaj/.sbt/0.13/plugins
    
    > compile
    [info] Compiling 1 Scala source to ...
    [success] Total time: 8 s, completed 7 Jan, 2017 3:26:02 PM
    
    > run
    program needs input file as argument. exiting
    [success] Total time: 0 s, completed 7 Jan, 2017 4:10:37 PM

    > run /tmp/input
    reading input file /tmp/input
    Minimum Average Wait Time = 9
    [success] Total time: 1 s, completed 7 Jan, 2017 4:10:34 PM
    
    > test
    [info] Compiling 1 Scala source to /Users/bharadwaj/Documents/Interviews/Risk Ident/pizza/target/scala-2.12/classes...
    [info] Compiling 1 Scala source to /Users/bharadwaj/Documents/Interviews/Risk Ident/pizza/target/scala-2.12/test-classes...
    [info] PizzaSpec:
    [info] Average Wait Time
    [info] - should say 9
    [info] - should say 8
    [info] - should say 1 for 1000 orders
    [info] - should say 1 for 10000 orders
    [info] - should say 1 for 100000 orders
    [info] - should say 1 for 1000000 orders
    [info] Run completed in 2 minutes, 45 seconds.
    [info] Total number of tests run: 6
    [info] Suites: completed 1, aborted 0
    [info] Tests: succeeded 6, failed 0, canceled 0, ignored 0, pending 0
    [info] All tests passed.
    [success] Total time: 170 s, completed 7 Jan, 2017 4:05:55 PM


