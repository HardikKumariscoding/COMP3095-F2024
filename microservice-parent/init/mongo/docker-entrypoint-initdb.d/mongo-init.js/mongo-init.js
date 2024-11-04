print('START');

// This line is used to get access to our database,
// if it existed, then we got access,if not, then it
// created
// that database, then we got access
db = db.getSiblingDB('product-service');

// Now we are gonna create a specific user
db.createUser(
    {
        user: 'admin',
        pwd: 'admin',
        roles: [{role: 'readWrite',db: 'product-service'}]
    }


);

db.createCollection('user');


print('END');
