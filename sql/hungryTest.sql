INSERT INTO HungryDatabase.Users (username, pass) 
	VALUES ('master', 'root'),
		   ('temp', 'temp');

INSERT INTO HungryDatabase.SearchHistory (userID, searchQuery) 
	VALUES (1, 'sampleQuery'),
		   (2, 'sampleQuery');

INSERT INTO HungryDatabase.Item 
(itemType, itemName, rating, picURL, prepTime, cookTime, websiteURL, price, address, phone, driveTime) 
	VALUES (0, 'Recipe Name', 4.5, 'google.com', 15, 20, '', -1, '', '', -1),
		   (1, 'Restaurant Name', 4.2, '', -1, -1, 'facebook.com', 15, '3770 S Fig', '7148522946', 3);
           
INSERT INTO HungryDatabase.Instructions (itemID, insIndex, instruc) 
	VALUES (1, 1, 'Boil water'),
		   (1, 2, 'Put things in'),
           (1, 3, 'Take things out');
           
INSERT INTO HungryDatabase.Ingredients (itemID, ingIndex, ingred) 
	VALUES (1, 1, '1/2 cup water'),
		   (1, 2, '1 tbsp salt'),
           (1, 3, '5 cups parmesan cheese (shredded)');

INSERT INTO HungryDatabase.Lists (userID, itemID, listID, itemIndex)
	VALUES (1, 1, 1, 0),
		   (1, 2, 3, 0),
           (2, 1, 2, 0),
           (2, 2, 2, 1);
														