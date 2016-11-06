# An application that analysis movement traces and can determine if users meet each other #

Program takes the movement traces of people and two uids and determines if users by given uids meet each other

Headers input file contains movements
```
timestamp | x | y | floor | uid
```

### How to run application? ###

* Clone this repo `git clone <link to this repo>`
* Execute `sbt "run a123 b456 /_your_path_/data.csv , 1"` where a123, b456 are first and second user's uids, `/_your_path_/data.csv` is the path to data csv file, `,` is a delimiter used in csv file, and `1` is a minimal distance


### What else? ###

* This application is written in functional style in Scala language using SBT. Project don't have any dependences.
* Data is put to nested hash maps, firs one is to retrieve movements for specific user in constant time, second one is to split movements by floors and the third one is to retrive user's position by time in constant time. 

The min time of algorithm is constant time (in case if users doen't have intercepted floors or one of the user doesn't have movenet data). 

The max time is linear and equals to count of movements of first user.

Space is linear and equal to count of movements both users.

* What else?
There are ways to imporve our solution. For example before checking positions for each time stamp we can have time periods (that's cover some bigger period of time) first check interseprion of periods and if there are any then check small time periods and so on. In that case we can achieve logariphmic time complexity