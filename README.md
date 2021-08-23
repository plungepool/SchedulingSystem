# SchedulingSystem
### System for scheduling appointments
### Rob Duffy, v1.0, 8/23/21
IntelliJ IDEA 2021.2
JDK: Corretto 11.0.11
JavaFX: SDK 11.0.2
MySQL Driver: Maven mysql-connector-java:8.0.22

## Directions to Run
1. Open in IntelliJ IDE.
2. Ensure JavaFX %PATHTOFILE% is properly assigned and proper MySQL driver installed
  **NOTE:** In Intellij configuration, I could not use the [PATH_TO_FX] macro as specified in the setup video due to the following bug I believe so your own JavaFX path will have to be specified manually. https://github.com/openjfx/openjfx-docs/issues/120
3. Ensure VM configured properly for your system and run.

## Additional Report: Total appointment time
Included additonal report type that calculates total scheduled appointment time displated in hours/minutes/seconds based on specified appointment type and month.
