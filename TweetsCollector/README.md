# README #

# Tweets Collector #
* Start page: http://localhost:8080/
* Parameters also can use in url: http://localhost:8080/submit?keywords=KEYWORD1,KEYWORD2,...,KEYWORD(n)
* Tweets count can be limited by 'count' parameter: http://localhost:8080/submit?keywords=KEYWORD1,KEYWORD2&count=10
* Default 'count' value is 100 tweets
* Results will return in json format:
* - keywords["KEYWORD1","KEYWORD2",...]
* - tweets[{id,text,user,language,timestamp,relatedKeyword},...]
* - timestamp


* You can get your API key here: https://apps.twitter.com/
* Enter your API informations in application.properties file under src/main/resources

