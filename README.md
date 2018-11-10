
# Ad Server for Image Texture Ads
## Module Description
This module serves image texture ads request realtime to the user devices.
Ad Server interacts with other modules as follows:-
1. Ad Server regularly consumes an API from Advertiser Dashboard, to update itself with the details of all the available ads.
2. On receiving an ad request, Ad Server determines which ad needs to be served. Currently, it is in round robin fashion. In future, it needs to be done through bidding. Also, currently, this evaluation is done in realtime. In future, it needs to be done periodically and the result to be stored in a cache.
3. The status of each ad request served, cost, revenue etc. details is sent to the event receiver through flume queue management system.

## Pre-requisites
*  install maven 2+ ```sudo apt-get install maven```
* install java 6+
* MySQL
*  install git
* Setup flume - See event receiver module for this

## How to run
* ```mvn clean package```
* Start the tomcat server  
## License

This project is licensed under the MIT License

## Authors
* Sushil Kumar - [Github](https://github.com/sushilmiitb)
