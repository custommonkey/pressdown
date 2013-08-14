The Why
-------

Code as communication

Communication between the developer and the compiler

Communication between the developer and other developers

Code as documentation

An expression of intent

The How
-------

The intent

    When some condition is present,
    then perform some action

The expression

    when(condition is true)
    then {
        performAction()
    }

The What
--------

    when(VRBO, HOMEAWAY_US, HOMEAWAY_UK)(`new`)(Bundle)
    then { (sub, selection, related) =>
      metalicBundles filter (months12)
    }
