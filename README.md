# Arknights-Crawler
A program that will crawler through the Gamepress arknights wiki and scrape all unit information in order to be put into a database. This databse will then be used by my own DiscordBot.

# Technology Stack
 - Java: It's a language that I am extremely familiar in and wanted to use it for this project in order to expand my knowledge of Java.
 - Intellij IDEA: Used this IDE because its functionaly allows me to be able to write code efficiently. An example is how I used the Database Plugin within the program in order quickly be able to query the database and error check.
 - Selenium with ChromeDriver: I used Selenium because it easily allows me to manipulate different elements on a web page. This helps with dynamic elements on aweb page that will change depending on certain inputs from a user. Additionally, it allows me to easily webscrape information required from a web page. I used chromedriver because it's the browser I use.
 - MongoDb: I used this to insert information into my database. 
 - DataGrip: Allows me to easily create new tables as well as quickly changing values within a table to check for certain errors.
 - XAMPP: Contains a very easy way to create your own SQL (MariaDB) database on your computer. I used this to host my own server.

# Why I created this project
 - I wanted to put some web scraping knowledge that I had obtain in college through one of my courses, Compsci 121: Information Retrieval. 
 
# Problems that I have faced or am facing right now
 - Big Functions: It's very hard for me to be able to break down the code in certain functions. I don't have a full understanding of how the best way it is to split up a function that contains many lines, but does such a simple thing. 
 - Bad practices with xpath: I ended up indexing a lot of the html with xpath specific tags which in my opinion are not the best way to deal with the problem. If a modification happens to the website it can easily mess up the xpath. I started to apply a better method half way through the project. 
# Future features
 - Want to create another project that turns all of the information gathered here into an API. 
