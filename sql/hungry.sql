DROP DATABASE IF EXISTS HungryDatabase;
CREATE DATABASE HungryDatabase;

USE HungryDatabase;

CREATE TABLE Users(
	userID INT(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
	username VARCHAR(50) NOT NULL,
    pass VARCHAR(500) NOT NULL
);

CREATE TABLE SearchHistory(
	searchID INT(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    userID INT(11) NOT NULL,
    searchQuery VARCHAR(50) NOT NULL, 
	numResults INT(11) NOT NULL,
    radius INT(11) NOT NULL,
    FOREIGN KEY fk1(userID) REFERENCES Users(userID)
);

/* use this stuff only for lists */

CREATE TABLE Item(
	itemID INT(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    itemType INT(11) NOT NULL, /* 0=recipe, 1=restaurant */
    itemName VARCHAR(50) NOT NULL, /* name of restaurant or recipe */
    rating DOUBLE NOT NULL, /* star rating */
    
    /* recipe params */
    picURL VARCHAR(500), 
    prepTime DOUBLE,
    cookTime DOUBLE, 
    /* to get instructions/ingredients: 'select * from instructions/ingredients where itemID=&' */
    
    /*restaurant params */
    websiteURL VARCHAR(500),
    price INT(11),
    address VARCHAR(500), 
    phone VARCHAR(500),
    driveTime INT(11)
);

CREATE TABLE Instructions(
	instrucID INT(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    itemID INT(11) NOT NULL,
    insIndex INT(11) NOT NULL,
    instruc VARCHAR(500) NOT NULL,
    FOREIGN KEY fk2(itemID) REFERENCES Item(itemID)
);

CREATE TABLE Ingredients(
	ingredID INT(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    itemID INT(11) NOT NULL,
    ingIndex INT(11) NOT NULL,
    ingred VARCHAR(500) NOT NULL,
    FOREIGN KEY fk3(itemID) REFERENCES Item(itemID)
);

CREATE TABLE Lists(
	LID INT(11) PRIMARY KEY AUTO_INCREMENT NOT NULL, /* don't use this one */
    userID INT(11) NOT NULL, 
    itemID INT(11) NOT NULL, /* which item are you talking about? */
    listID INT(11) NOT NULL, /* 1=fav, 2=explore, 3=do not show */
    itemIndex INT(11) NOT NULL, /* where is this item located in the list? */
	FOREIGN KEY fk4(userID) REFERENCES Users(userID),
    FOREIGN KEY fk5(itemID) REFERENCES Item(itemID)
    
);
