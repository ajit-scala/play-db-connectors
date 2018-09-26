## Installing sbt
brew install sbt

## installing sbt new - templates

### step 1 install conscript:

Conscript is a distribution mechanism for Scala apps using GitHub and Maven repositories as the infrastructure. it is a package manager like brew or apt-get for managing and installing scala projects/dependencies.
http://www.foundweekends.org/giter8/setup.html

in our case its needed for sbt new templates.

### Install giter8
Giter8 is a command line tool to generate files and directories from templates published on Github or any other git repository. it uses sbt launcher.
brew install giter8


after above tools are installed sbt new templatename would work. This template is actually stored in github project.

## Creating a new scala Project

 - https://developer.lightbend.com/start/

 - or with sbt new git/template_name
   example 
    sbt new playframework/play-scala-seed.g8unfil
    sbt unfiltered/unfiltered.g8 --name=my-new-website
    g8 unfiltered/unfiltered.g8 --name=my-new-website
    
https://github.com/foundweekends/giter8/wiki/giter8-templates

## Getting started guides
https://developer.lightbend.com/guides/